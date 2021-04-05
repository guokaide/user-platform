package org.geektimes.context.servlet;

import org.geektimes.context.ClassicComponentContext;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class ComponentContextInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        ClassicComponentContext context = new ClassicComponentContext();
        context.init(servletContext);
        servletContext.addListener(ComponentContextInitializerListener.class);
    }
}
