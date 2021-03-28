package org.geektimes.configuration.microprofile.config.converter;

public class StringConverter extends AbstractConverter<String> {
    @Override
    protected String doConvert(String value) {
        return value;
    }
}
