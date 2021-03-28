package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 设计：模板模式
 *
 * 基于 Map 数据结构 {@link ConfigSource} 实现
 */
public abstract class MapBasedConfigSource implements ConfigSource {

    // read-only, 不允许在运行时发生变化，构造时就要确定好
    private final String name;

    // read-only, 不允许在运行时发生变化，构造时就要确定好
    // https://github.com/eclipse/microprofile-config
    private final int ordinal;

    private final Map<String, String> source;

    protected MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.source = getProperties();
    }

    /**
     * 获取配置数据 Map
     *
     * 设计思考：此方法不允许子类继承
     *
     * @return 不可变 Map 类型的配置数据
     */
    @Override
    public final Map<String, String> getProperties() {
        // 不同实现通用，每次产生的数据都是新的
        Map<String, String> configData = new HashMap<>();
        // 不同实现不同，学习这种抽象的设计
        try {
            prepareConfigData(configData);
        } catch (Throwable cause) {
            throw new IllegalStateException("准备配置数据发生错误", cause);
        }
        // 不同实现通用，返回值是不可变的
        return Collections.unmodifiableMap(configData);
    }

    /**
     * 准备配置数据
     *
     * @param configData 这里不设置泛型，子类实现起来比较简单
     * @throws Throwable 子类遇到 Exception 或者 Error，由外部进行处理，这样实现起来比较简单
     */
    protected abstract void prepareConfigData(Map configData) throws Throwable;

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int getOrdinal() {
        return ordinal;
    }

    @Override
    public Set<String> getPropertyNames() {
        return source.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return source.get(propertyName);
    }

}
