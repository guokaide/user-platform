# Homework

## Week 05

### 问题

- [x] 1 修复本程序 org.geektimes.reactive.streams 包下问题
- [x] 2 完善 my-rest-client POST 方法
- [x] 3 阅读 Servlet 3.0 关于 Servlet 异步章节：`AsyncContext` 

### 解答

1 `org.geektimes.reactive.streams.DefaultSubscriber#onNext` 方法中， 
根据文档，当 Subscriber 对应的 Subscription#cancel 执行之后，Subscriber#onNext 将不在执行
此处数据阈值为 2，当 o <= 2 时，Subscriber#onNext 仍然执行，意味者可以发送数据 0，1， 2
当 o >= 2 时，Subscription#cancel 将会执行，此后，Subscriber#onNext 将不在执行，因此调整一下 `System.out.println("收到数据：" + o);` 位置即可，正确的输出如下：

```shell
收到数据：0
收到数据：1
收到数据：2
本次数据发布已忽略，数据为：3
本次数据发布已忽略，数据为：4
```



2 实现见：`org.geektimes.rest.client.HttpPostInvocation` 类；

测试见：`org.geektimes.rest.demo.RestClientPostDemo` 类






