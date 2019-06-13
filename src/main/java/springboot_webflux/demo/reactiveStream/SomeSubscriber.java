package springboot_webflux.demo.reactiveStream;


import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * 订阅者
 */
public class SomeSubscriber implements Flow.Subscriber {
    //声明订阅令牌
    private Flow.Subscription subscription;

    //发布者第一次发布消息的时候会自动调用这个方法 会把令牌发送个订阅者
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        //设置订阅者首次向发布者（令牌）订阅消息的数量
        this.subscription.request(10l);
    }

    //订阅者每接收一次消息数据就会自动调用一次
    //订阅者对数据的消费就这
    @Override
    public void onNext(Object item) {
        System.out.println("当前订阅者的消息：" + item);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //设置订阅者向发布者再次订阅消息的数量，即每消费一条消息
        //则在向发布者订阅多条数据
        this.subscription.request(8);

        //也可以取消订阅消息
        if ("满足默写条件时" == "取消订阅") {
            this.subscription.cancel();
        }
    }

    //当订阅过程中出现异常时会自动调用
    @Override
    public void onError(Throwable throwable) {
        System.out.println("当前订阅者的消息异常：" + throwable);
        //取消订阅消息
        this.subscription.cancel();

    }

    //当令牌中的消息全部消费完成时会自动调用方法
    @Override
    public void onComplete() {
        System.out.println("消息全部消费完成");

    }
}
