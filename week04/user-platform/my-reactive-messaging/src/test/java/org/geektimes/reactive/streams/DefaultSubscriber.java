package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;

    private int count = 0;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
    }

    @Override
    public void onNext(Object o) {
        // Subscriber 行为：当其对应当 Subscription#cancel 执行之后，Subscriber#onNext 将不在执行
        // 此处数据阈值为 2，当 o <= 2 时，Subscriber#onNext 仍然执行，意味者可以发送数据 0，1， 2
        // 当 o >= 2 时，Subscription#cancel 将会执行，此后，Subscriber#onNext 将不在执行
        System.out.println("收到数据：" + o);
        if (++count > 2) { // 当到达数据阈值 2 时，取消 Publisher 给当前 Subscriber 发送数据
            subscription.cancel();
            return;
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("遇到异常：" + t);
    }

    @Override
    public void onComplete() {
        System.out.println("收到数据完成");
    }
}
