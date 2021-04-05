# Homework

## Week 04

### 问题

- [x] #### 1 完善 my dependency-injection 模块
  - [x] 1.1 脱离 web.xml 配置实现 ComponentContext 自动初始化
  - [x] 1.2 使用独立模块并且能够在 user-web 中运行成功

- [x] #### 2 完善 my-configuration 模块
  - [x] 2.1 Config 对象如何能被 my-web-mvc 使用
  - [x] 2.2 可能在 ServletContext 获取如何通过 ThreadLocal 获取

- [x] #### 3 去提前阅读 Servlet 规范中 Security 章节（Servlet 容器安全）


### 解答

#### 1 完善 my dependency-injection 模块

​	1.1 脱离 web.xml 配置实现 ComponentContext 自动初始化

`org.geektimes.context.servlet.ComponentContextInitializer` 类实现 ComponentContext 的自动初始化

```java
public class ComponentContextInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        ClassicComponentContext context = new ClassicComponentContext();
        context.init(servletContext);
        servletContext.addListener(ComponentContextInitializerListener.class);
    }
}
```

然后配置`javax.servlet.ServletContainerInitializer` 

```
org.geektimes.context.servlet.ComponentContextInitializer
```

利用 Java SPI 加载`org.geektimes.context.servlet.ComponentContextInitializer` 类，这样就可以脱离 web.xml了。

由于 `ServletContainerInitializer` 启动顺序早于 `ServletContextListener`，因此`ServletContainerInitializer` 中初始化 ClassicComponentContext 之后，`ServletContextListener` 即可利用其进行依赖查找。

`org.geektimes.context.servlet.ComponentContextInitializerListener` 类实现 ComponentContext#destroy

```java
public class ComponentContextInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComponentContext context = ClassicComponentContext.getInstance();
        context.destroy();
    }

}
```

​	1.2 使用独立模块并且能够在 user-web 中运行成功

在 user-web 模块中引入即可使用：

```xml
        <dependency>
            <groupId>org.geekbang.projects</groupId>
            <artifactId>my-dependency-injection</artifactId>
            <version>${revision}</version>
        </dependency>
```



#### 2 完善 my-configuration 模块

​	2.1 Config 对象如何能被 my-web-mvc 使用

可以通过 ServletContext 使用，例如：`org.geektimes.projects.user.web.listener.TestingListener#testPropertyFromServletContext`

配置见：`microprofile-config.properties` 

​	2.2 可能在 ServletContext 获取如何通过 ThreadLocal 获取

例如：`org.geektimes.configuration.microprofile.config.servlet.listener.ConfigServletRequestListener`

```java
public class ConfigServletRequestListener implements ServletRequestListener {

    private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        ServletContext servletContext = request.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config config = configProviderResolver.getConfig(classLoader);
        configThreadLocal.set(config);
    }

    public static Config getConfig() {
        return configThreadLocal.get();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 防止 OOM
        configThreadLocal.remove();
    }

}
```

每次 ServletRequest 都可以从 ThreadLocal 中取出配置，当请求结束的时候，可以将 ThreadLocal 清空。