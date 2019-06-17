package springboot_webflux.demo.reactiveStream.Subsciber_publisher_Processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * 消息处理器
 *
 * 这个类即是订阅者又是发布者  对于订阅者来说它是发布者  所以SubmissionPublisher<String>的类型是String
 * Flow.Processor<Integer,String>  中的类型是发布者输入的类型和订阅者输出的类型
 *
 * 这里要使用处理器
 * Processor 。该处理器的处理逻辑是， 将 发布者发布的 大于 50 的消息
 * 过滤掉，并将小于 50 的 Integer 消息转换为 String 后发布 给订阅者。
 */
public class SomeProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer,String> {
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

    /**
     *  将 发布者发布的 大于 50 的消息
     *  * 过滤掉，并将小于 50 的 Integer 消息转换为 String 后发布 给订阅者。
     *
     * @param item
     */
    @Override
    public void onNext(Integer item) {
        System.out.println("处理者的消息：" + item);
       if(item<50){
           try {
               TimeUnit.SECONDS.sleep(1);
           } catch (Exception e) {
               e.printStackTrace();
           }
         this.submit("该消息处理："+item);
       }else{
           this.submit(""+item);
       }

        this.subscription.request(8);


    }

    //当订阅过程中出现异常时会自动调用
    @Override
    public void onError(Throwable throwable) {
        System.out.println("处理者的消息异常：" + throwable);
        //取消订阅消息
        this.subscription.cancel();

    }

    //当令牌中的消息全部消费处理完成时会自动调用方法
    @Override
    public void onComplete() {
        System.out.println("处理者消息全部处理完成");

    }
}
