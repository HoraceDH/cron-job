package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.UserResult;
import cn.horace.cronjob.scheduler.entities.UserEntity;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
@Component
public class UserAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserAdapter.class);

    /**
     * 对象转换
     *
     * @param user 用户
     * @return
     */
    public UserResult convert(UserEntity user) {
        UserResult userResult = new UserResult();
        userResult.setId(String.valueOf(user.getId()));
        userResult.setUsername(user.getUsername());
        userResult.setNickname(user.getNickname());
        userResult.setEmail(user.getEmail());
        userResult.setPhone(user.getPhone());
        userResult.setAvatar(user.getAvatar());
        userResult.setState(user.getState());
        userResult.setAddress(user.getAddress());
        userResult.setSignature(user.getSignature());
        userResult.setCreateTime(DateFormatUtils.format(user.getCreateTime().getTime(), Constants.DATE_FORMAT));
        return userResult;
    }

    /**
     * 对象转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<UserResult> convert(List<UserEntity> entityList) {
        List<UserResult> result = new ArrayList<>();
        for (UserEntity user : entityList) {
            result.add(this.convert(user));
        }
        return result;
    }
}