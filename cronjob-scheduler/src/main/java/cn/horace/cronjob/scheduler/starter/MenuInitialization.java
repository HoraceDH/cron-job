package cn.horace.cronjob.scheduler.starter;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.scheduler.entities.MenuEntity;
import cn.horace.cronjob.scheduler.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 菜单初始化
 * <p>
 *
 * @author Horace
 */
@Configuration
public class MenuInitialization implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(MenuInitialization.class);
    private ApplicationContext context;
    @Resource
    private GuidGenerate guidGenerate;
    @Resource
    private MenuService menuService;
    private Map<String, Long> parentMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        parentMap.put("/manager-api/statistics", 1L);
        parentMap.put("/manager-api/user", 3L);
        parentMap.put("/manager-api/tenant", 4L);
        parentMap.put("/manager-api/alarm", 4L);
        parentMap.put("/manager-api/schedulers", 5L);
        parentMap.put("/manager-api/menu", 6L);
        parentMap.put("/manager-api/executor", 8L);
        parentMap.put("/manager-api/app", 9L);
        parentMap.put("/manager-api/task", 10L);
        parentMap.put("/manager-api/tasklog", 11L);
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        Map<String, Object> beansMap = this.context.getBeansWithAnnotation(RestController.class);
        for (Object bean : beansMap.values()) {
            Class<?> clazz = bean.getClass();
            String uriPrefix = "";
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                uriPrefix = requestMapping.value()[0];
            }
            if (!uriPrefix.startsWith("/manager-api")) {
                continue;
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if (postMapping != null) {
                    String path = uriPrefix + postMapping.value()[0];
                    MenuEntity temp = this.menuService.getMenu(path);
                    if (temp == null) {
                        MenuEntity menuEntity = new MenuEntity();
                        long parentId = this.parentMap.get(uriPrefix);
                        menuEntity.setId(this.guidGenerate.genId());
                        menuEntity.setMenu(false);
                        menuEntity.setComponent("");
                        menuEntity.setLocale(false);
                        menuEntity.setName(postMapping.name());
                        menuEntity.setIcon("SmileTwoTone");
                        menuEntity.setParentId(parentId);
                        menuEntity.setPath(path);
                        this.menuService.addMenu(menuEntity);
                        logger.debug("add menu, {}", menuEntity);
                    }
                }
            }
        }

    }
}