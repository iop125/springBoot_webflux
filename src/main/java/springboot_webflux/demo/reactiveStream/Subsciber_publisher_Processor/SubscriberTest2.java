package springboot_webflux.demo.reactiveStream.Subsciber_publisher_Processor;


import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

public class SubscriberTest2 {


    public static void main(String[] a) throws InterruptedException {
        //定义发布者，发布消息为Integer数据
        SubmissionPublisher<Integer> publisher =  new SubmissionPublisher<>();
        //定义订阅者 消费的消息为Integer数据
        SomeSubscriber2 subscriber2 = new SomeSubscriber2("订阅者2");
        //创建处理器
        SomeProcessor someProcessor = new SomeProcessor("处理器");
        //创建发布者与订阅者间的订阅关系
        publisher.subscribe(someProcessor);
        someProcessor.subscribe(subscriber2);
        for (int i = 0; i < 300; i++) {
            //submit() shiyige block方法 当发布过系统默认数量的消息后该方法阻塞。没有无限的缓冲区 内存大小限制
            // 这样就通过订阅者控制了发布者的发布速度，背压在这里体现
            System.out.println("生成发布消息：" + i);
            publisher.submit(i);
        }
        //一定要关闭  finally中
        publisher.close();
        //为了防止订阅者没有消费完消息
        System.out.println("主线程开吃等待");
        Thread.currentThread().join(15000);
    }
}
