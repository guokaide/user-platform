package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import org.geektimes.cache.AbstractCacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * {@link javax.cache.CacheManager} based on Lettuce
 */
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;
    private StatefulRedisConnection<byte[], byte[]> connection;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        this.redisClient = RedisClient.create(RedisURI.create(uri));
        this.connection = redisClient.connect(new ByteArrayCodec());
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        RedisCommands<byte[], byte[]> syncCommands = connection.sync();
        return new LettuceCache(this, cacheName, configuration, syncCommands);
    }

    @Override
    protected void doClose() {
        connection.close();
        redisClient.shutdown();
    }
}
