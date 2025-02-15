package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.adapter.UserAdapter;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.constants.UserState;
import cn.horace.cronjob.scheduler.entities.TokenEntity;
import cn.horace.cronjob.scheduler.entities.UserEntity;
import cn.horace.cronjob.scheduler.entities.UserEntityExample;
import cn.horace.cronjob.scheduler.mappers.UserEntityMapper;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import cn.horace.cronjob.scheduler.bean.params.CreateUserParams;
import cn.horace.cronjob.scheduler.bean.params.GetUserListParams;
import cn.horace.cronjob.scheduler.bean.params.LoginParams;
import cn.horace.cronjob.scheduler.bean.result.LoginResult;
import cn.horace.cronjob.scheduler.bean.result.UserListResult;
import cn.horace.cronjob.scheduler.bean.result.UserResult;
import cn.horace.cronjob.scheduler.service.TokenService;
import cn.horace.cronjob.scheduler.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务
 * <p>
 *
 * @author Horace
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserEntityMapper mapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private UserAdapter userAdapter;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 用户登录
     *
     * @param params 登录参数
     * @return
     */
    @Override
    public Result<LoginResult> userLogin(LoginParams params) {
        UserEntityExample example = new UserEntityExample();
        example.or().andUsernameEqualTo(params.getUsername());
        List<UserEntity> users = this.mapper.selectByExample(example);
        if (users == null || users.isEmpty()) {
            logger.warn("user login failed, not found user, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_USER_NOT_FOUND);
        }
        UserEntity user = users.get(0);
        if (user.getState() == UserState.DISABLED.getValue()) {
            return Result.msgCodes(MsgCodes.ERROR_USER_DISABLED);
        }
        if (!user.getPassword().equals(params.getPassword())) {
            return Result.msgCodes(MsgCodes.ERROR_USER_PASSWORD);
        }
        String token = this.tokenService.generateToken(user.getId());
        return Result.success(new LoginResult(token));
    }

    /**
     * 获取用户信息
     *
     * @param token 用户Token
     * @return
     */
    @Override
    public Result<UserResult> getUser(String token) {
        TokenEntity entity = this.tokenService.getToken(token);
        if (entity == null || entity.getExpireTime().getTime() < System.currentTimeMillis()) {
            return Result.msgCodes(MsgCodes.ERROR_USER_INVALID_TOKEN);
        }
        // 获取用户信息
        UserEntity user = this.getUserById(entity.getUserId());
        if (user == null) {
            return Result.msgCodes(MsgCodes.ERROR_USER_NOT_FOUND);
        }
        return Result.success(this.userAdapter.convert(user));
    }

    /**
     * 登出
     *
     * @param token 用户Token
     */
    @Override
    public void logout(String token) {
        // 将Token过期
        this.tokenService.expireToken(token);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public UserEntity getUserById(long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 获取用户列表
     *
     * @param params 请求参数
     * @return
     */
    @Override
    public Result<UserListResult> getUserList(GetUserListParams params) {
        long id = 0;
        if (StringUtils.isNotBlank(params.getId())) {
            id = Long.parseLong(params.getId());
        }
        Result<UserListResult> result = Result.success();
        UserEntityExample example = new UserEntityExample();

        // 条件设置
        UserEntityExample.Criteria criteria = example.or();
        if (id > 0) {
            criteria.andIdEqualTo(id);
        }
        if (params.getState() != -1) {
            criteria.andStateEqualTo(params.getState());
        }
        if (StringUtils.isNotBlank(params.getUsername())) {
            criteria.andUsernameLike(LikeUtils.toLikeString(params.getUsername()));
        }
        if (StringUtils.isNotBlank(params.getNickname())) {
            criteria.andNicknameLike(LikeUtils.toLikeString(params.getNickname()));
        }
        if (StringUtils.isNotBlank(params.getEmail())) {
            criteria.andEmailLike(LikeUtils.toLikeString(params.getEmail()));
        }
        if (StringUtils.isNotBlank(params.getPhone())) {
            criteria.andPhoneLike(LikeUtils.toLikeString(params.getPhone()));
        }
        if (StringUtils.isNotBlank(params.getAddress())) {
            criteria.andAddressLike(LikeUtils.toLikeString(params.getAddress()));
        }
        if (StringUtils.isNotBlank(params.getSignature())) {
            criteria.andSignatureLike(LikeUtils.toLikeString(params.getSignature()));
        }
        UserListResult userListResult = new UserListResult();
        userListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`create_time` desc limit " + offset + ", " + limit);
        List<UserEntity> entityList = this.mapper.selectByExample(example);
        userListResult.setCurrent(params.getCurrent());
        userListResult.setPageSize(params.getPageSize());
        userListResult.setData(this.userAdapter.convert(entityList));
        result.setData(userListResult);
        return result;
    }

    /**
     * 重置用户密码
     *
     * @param token  当前用户的Token
     * @param userId 需要重置的用户ID
     * @return
     */
    @Override
    public Result<String> resetPassword(String token, String userId) {
        Result<String> result = Result.success();
        TokenEntity tokenEntity = this.tokenService.getToken(token);
        // 1 代表超级管理员
        if (tokenEntity.getUserId().equals(Constants.ADMIN_USER_ID)) {
            String newPassword = RandomStringUtils.randomAlphanumeric(10);
            result.setData(newPassword);
            String md5Password = DigestUtils.md5Hex(newPassword);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(Long.parseLong(userId));
            userEntity.setPassword(md5Password);
            int update = this.mapper.updateByPrimaryKeySelective(userEntity);
            if (update > 0) {
                this.tokenService.expireToken(Long.parseLong(userId));
            }
            return result;
        }
        return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
    }

    /**
     * 创建用户
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<String> createUser(CreateUserParams params) {
        Result<String> result = Result.success();
        // 参数检查
        if (StringUtils.isBlank(params.getUsername()) || StringUtils.isBlank(params.getNickname())
                || StringUtils.isBlank(params.getEmail()) || StringUtils.isBlank(params.getPhone())) {
            logger.warn("failed to create user, params error, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        // 判断是否是正确的手机号格式
        String regex = "^1\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(params.getPhone());
        if (!matcher.matches()) {
            logger.warn("failed to create user, invalid phone number, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        // 用户是否重复，这里用户量不大，简单判断就行
        UserEntity user = this.getUserByName(params.getUsername());
        if (user != null) {
            logger.warn("failed to create user, repeated username, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_USER_NAME_REPEATED);
        }
        long userId = this.guidGenerate.genId();
        UserEntity entity = new UserEntity();
        Date date = new Date();
        entity.setId(userId);
        entity.setUsername(params.getUsername());
        entity.setNickname(params.getNickname());
        entity.setPassword(params.getPassword());
        entity.setEmail(params.getEmail());
        entity.setPhone(params.getPhone());
        String defaultAvatar = "/icons/default-avatar.png";
        entity.setAvatar(StringUtils.isBlank(params.getAvatar()) ? defaultAvatar : params.getAvatar());
        entity.setAddress(params.getAddress());
        entity.setSignature(params.getSignature());
        int insert = this.mapper.insertSelective(entity);
        if (insert == 1) {
            result.setData(String.valueOf(userId));
            return result;
        }
        logger.error("failed to create user, insert database error, insertCount:{}, params:{}", insert, params);
        return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return
     */
    @Override
    public UserEntity getUserByName(String username) {
        UserEntityExample example = new UserEntityExample();
        example.or().andUsernameEqualTo(username);
        List<UserEntity> userEntities = this.mapper.selectByExample(example);
        if (userEntities != null && !userEntities.isEmpty()) {
            return userEntities.get(0);
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param userId    用户ID
     * @param userState 用户状态
     * @return
     */
    @Override
    public Result<Void> updateUserState(long userId, UserState userState) {
        Result<Void> result = Result.success();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setState(userState.getValue());
        if (userState == UserState.DISABLED) {
            // 使登录token过期
            this.tokenService.expireToken(userId);
        }
        int count = this.mapper.updateByPrimaryKeySelective(userEntity);
        if (count <= 0) {
            result = Result.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        return result;
    }
}