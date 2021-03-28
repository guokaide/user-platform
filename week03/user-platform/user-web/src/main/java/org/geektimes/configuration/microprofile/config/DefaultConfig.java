package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.Converters;
import org.geektimes.configuration.microprofile.config.source.ConfigSources;

import java.util.*;
import java.util.stream.StreamSupport;

public class DefaultConfig implements Config {

    private final ConfigSources configSources;

    private final Converters converters;

    DefaultConfig(ConfigSources configSources, Converters converters) {
        this.configSources = configSources;
        this.converters = converters;
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        ConfigValue configValue = getConfigValue(propertyName);
        if (configValue == null) {
            return null;
        }
        Converter<T> converter = doGetConverter(propertyType);
        String propertyValue = configValue.getValue();
        return converter == null ? null : converter.convert(propertyValue);
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        String propertyValue = null;
        ConfigSource configSource = null;

        Iterator<ConfigSource> iterator = configSources.iterator();

        while (iterator.hasNext()) {
            configSource = iterator.next();
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue != null) {
                break;
            }
        }

        if (propertyValue == null) {
            return null;
        }

        return new DefaultConfigValue(propertyName, propertyValue, transformPropertyValue(propertyValue),
                configSource.getName(), configSource.getOrdinal());
    }

    /**
     * 转换属性值（如果需要）
     * @param propertyValue
     * @return
     */
    private String transformPropertyValue(String propertyValue) {
        return propertyValue;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return StreamSupport.stream(configSources.spliterator(), false)
                .map(ConfigSource::getPropertyNames)
                .collect(LinkedHashSet::new, Set::addAll, Set::addAll);
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return configSources;
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        Converter converter = doGetConverter(forType);
        return converter == null ? Optional.empty() : Optional.of(converter);
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }

    protected <T> Converter<T> doGetConverter(Class<T> forType) {
        List<Converter> converters = this.converters.getConverters(forType);
        return converters.isEmpty() ? null : converters.get(0);
    }
}
