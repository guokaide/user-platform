package org.geektimes.configuration.microprofile.config.source;

import java.util.Map;

/**
 * JVM ConfigSource
 */
public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {

    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties", 400);
    }

    /**
     * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
     * 建议不使用 {@link java.util.Properties}, 因为 getProperty 是同步的方法，会把多线程限制成单线程
     * - Dapplication.name=user-web
     *
     * {@link System#getProperties()}
     */
    @Override
    protected void prepareConfigData(Map configData) {
        configData.putAll(System.getProperties());
    }

}
