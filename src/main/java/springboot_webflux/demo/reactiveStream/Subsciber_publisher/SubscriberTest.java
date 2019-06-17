package springboot_webflux.demo.reactiveStream.Subsciber_publisher;


import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

public class SubscriberTest {


    public static void main(String[] a) throws InterruptedException {
        SubmissionPublisher<Integer> publisher = null;

        //定义发布者，发布消息为Integer数据
        publisher = new SubmissionPublisher<>();
        //定义订阅者 消费的消息为Integer数据
        SomeSubscriber subscriber1 = new SomeSubscriber("订阅者1");
//        SomeSubscriber subscriber2 = new SomeSubscriber("订阅者2");
        //创建发布者与订阅者间的订阅关系
        publisher.subscribe(subscriber1);
//        publisher.subscribe(subscriber2);
        //生产10条消息发布
        for (int i = 0; i < 300; i++) {
            //submit() shiyige block方法 当发布过系统默认数量的消息后该方法阻塞。没有无限的缓冲区 内存大小限制
            // 这样就通过订阅者控制了发布者的发布速度，背压在这里体现
            System.out.println("test：" + i);

            publisher.submit(new Random().nextInt(100));
        }
        //一定要关闭  finally中
        publisher.close();
        //为了防止订阅者没有消费完消息
        System.out.println("主线程开吃等待");
        Thread.currentThread().join(15000);
    }
}
