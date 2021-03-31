package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Comparator;

/**
 * {@link ConfigSource} 优先级比较器
 *
 * 设计：类似于这种无状态的比较器，可以通过单例模式实现
 *
 */
public class ConfigSourceOrdinalComparator implements Comparator<ConfigSource> {

    /**
     * Singleton instance {@link ConfigSourceOrdinalComparator}
     */
    public static final Comparator<ConfigSource> INSTANCE = new ConfigSourceOrdinalComparator();

    private ConfigSourceOrdinalComparator() {
    }

    @Override
    public int compare(ConfigSource o1, ConfigSource o2) {
        return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
    }
}
