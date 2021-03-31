package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * Converter<T> 设计方面的几个问题：
 *
 * 1. value == null 时，要不要返回 null，抛出 NullPointerException 是否合理？（输入是不可控的）
 *
 * 2. 对于某个 value 来说，Converter 是否能够转应该如何处理？ 能转的话，应该怎么转？
 *
 * @param <T>
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(String value) {
        if (value == null) {
            throw new NullPointerException("The value must not be null!");
        }
        return doConvert(value);
    }

    protected abstract T doConvert(String value);
}
