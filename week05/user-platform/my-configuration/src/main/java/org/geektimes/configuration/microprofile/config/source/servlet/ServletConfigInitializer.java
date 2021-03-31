package org.geektimes.configuration.microprofile.config.source.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * 执行顺序：
 * 1. ServletContainerInitializer 启动
 * 2. ServletContextListener 启动
 * 3. ServletRequestListener 启动
 *
 */
public class ServletConfigInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        // 增加 ServletContextListener
        servletContext.addListener(ServletContextConfigInitializer.class);
    }
}
