package org.geektimes.cache.redis;

import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.*;
import java.util.Set;

public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final RedisCommands<byte[], byte[]> syncCommands;

    public LettuceCache(CacheManager cacheManager, String cacheName,
                        Configuration<K, V> configuration, RedisCommands<byte[], byte[]> syncCommands) {
        super(cacheManager, cacheName, configuration);
        this.syncCommands = syncCommands;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        return syncCommands.exists(keyBytes) == 1;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        return getEntry(keyBytes);
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        byte[] valueBytes = syncCommands.get(keyBytes);
        return ExpirableEntry.of(deserialize(keyBytes), deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(entry.getKey());
        byte[] valueBytes = serialize(entry.getValue());
        syncCommands.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        syncCommands.del(keyBytes);
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        // TODO
    }


    @Override
    protected Set<K> keySet() {
        // TODO
        return null;
    }

    @Override
    protected void doClose() {

    }

    // 是否可以抽象出一套序列化和反序列化的 API
    private byte[] serialize(Object value) throws CacheException {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
        return bytes;
    }

    private <T> T deserialize(byte[] bytes) throws CacheException {
        if (bytes == null) {
            return null;
        }
        T value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
        return value;
    }

}
