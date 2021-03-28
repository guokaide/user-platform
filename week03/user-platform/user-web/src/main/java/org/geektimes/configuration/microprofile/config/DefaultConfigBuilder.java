package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.Converters;
import org.geektimes.configuration.microprofile.config.source.ConfigSources;

/**
 * ConfigBuilder 的 ConfigSource 有三个来源：
 * 1. 默认，为框架内置的
 * 2. SPI 扩展的，可能是第三方提供
 * 3. 程序扩展的，可能是程序提供的
 */
public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;

    private final Converters converters;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        this.configSources = new ConfigSources(classLoader);
        this.converters = new Converters(classLoader);
    }

    /**
     * 根据文档有三个默认的 ConfigSource 实现，因此，这部分可以硬编码实现
     *
     * see {@link ConfigSources#addDefaultSources()}
     *
     * @return ConfigBuilder
     */
    @Override
    public ConfigBuilder addDefaultSources() {
        configSources.addDefaultSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredSources() {
        configSources.addDiscoveredSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredConverters() {
        converters.addDiscoveredConverters();
        return this;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        configSources.setClassLoader(loader);
        converters.setClassLoader(loader);
        return this;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        configSources.addConfigSources(sources);
        return this;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        this.converters.addConverters(converters);
        return this;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        this.converters.addConverter(converter, priority, type);
        return this;
    }

    @Override
    public Config build() {
        return new DefaultConfig(configSources, converters);
    }

}
