package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * 设计：装饰器模式
 *
 * 被装饰的类 Converter 和装饰类 PrioritizedConverter 都是 Converter
 *
 * @param <T>
 */
class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter<T>> {

    private final Converter<T> converter;

    private final int priority;

    public PrioritizedConverter(Converter<T> converter, int priority) {
        this.converter = converter;
        this.priority = priority;
    }

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        return converter.convert(value);
    }

    public int getPriority() {
        return priority;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    @Override
    public int compareTo(PrioritizedConverter<T> other) {
        return Integer.compare(other.getPriority(), this.getPriority());
    }
}
