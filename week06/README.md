# Homework

## Week 06

### 问题

- [x] 1 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache
- [x] 2 提供一套抽象 API 实现对象的序列化和反序列化

### 解答

1 具体实现见 my-cache 模块： `org.geektimes.cache.redis.LettuceCacheManager` 及 `org.geektimes.cache.redis.LettuceCache`

测试见：`org.geektimes.cache.CachingTest#testSampleRedis`

2 这个问题对 API 设计的理解还不够深刻，简单的实现了一下，见 my-cache 模块： `org.geektimes.seri` package

