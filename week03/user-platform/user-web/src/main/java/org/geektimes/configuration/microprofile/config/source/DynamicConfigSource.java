package org.geektimes.configuration.microprofile.config.source;

import java.util.Map;

/**
 * 动态配置源 ConfigSource
 */
public class DynamicConfigSource extends MapBasedConfigSource {

    // 为了动态更新，所以存贮在本地
    private Map configData;

    protected DynamicConfigSource() {
        super("Dynamic ConfigSource", 500);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        this.configData = configData;
    }

    // 异步更新
    public void onUpdate(String data) {
    }
}
