package co.geographs.agrinsa.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppCtxHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public void setApplicationContext(final ApplicationContext appContext) {
        ctx = appContext;
    }
    
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}