package cn.horace.cronjob.executor.httpserver;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.commons.utils.IOUtils;
import cn.horace.cronjob.commons.utils.SignUtils;
import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.service.DispatcherService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Horace
 */
public class ExecutorServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServlet.class);
    private DispatcherService dispatcherService = DispatcherService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        MsgObject result;

        ServletInputStream inputStream = req.getInputStream();
        String body = new String(IOUtils.copy(inputStream));
        if (!this.verifySign(req, body)) {
            result = MsgObject.msgCodes(MsgCodes.ERROR_SIGN);
            writer.write(JSONObject.toJSONString(result));
            writer.flush();
            return;
        }

        try {
            if (!this.dispatcherService.isRunning()) {
                logger.warn("received execute request, executor is not running, ignore the task, params:{}", body);
                result = MsgObject.msgCodes(MsgCodes.ERROR_EXECUTE_SHUTDOWN);
                writer.write(JSONObject.toJSONString(result));
                writer.flush();
                return;
            }

            TaskParams taskParams = JSONObject.parseObject(body, TaskParams.class);
            int size = this.dispatcherService.add(taskParams);
            logger.debug("received execute request, queueSize:{}, params:{}", size, taskParams);
            result = MsgObject.success();
        } catch (Throwable e) {
            logger.error("received execute request error, msg:{}", e.getMessage(), e);
            result = MsgObject.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        writer.write(JSONObject.toJSONString(result));
        writer.flush();
    }

    /**
     * 验证签名
     *
     * @param req  请求对象
     * @param body
     * @return 返回是否验证成功
     */
    private boolean verifySign(HttpServletRequest req, String body) {
        String signKey = CronJobExecutorClient.getInstance().getConfig().getSignKey();
        String sign = req.getHeader("sign");
        String token = req.getHeader("token");
        String times = req.getHeader("times");
        Map<String, String> params = new HashMap<>();
        String serverSign = SignUtils.sign(signKey, token, times, body, params);
        boolean success = serverSign.equals(sign);
        if (!success) {
            logger.error("received execute request, sign verify failed, signKey:{}, serverSign:{}, sign:{}, token:{}, times:{}, body:{}", signKey, serverSign, sign, token, times, body);
        }
        return success;
    }
}