package springboot_webflux.demo.webFlux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springboot_webflux.demo.webFlux.mongo.Student;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 翻译： Spring WebFlux是一个自底向上构建的非阻塞Web框架，用于利多核、下一代处理器并处理大量并发连接
 * j2ee7以上 同时不支持关系型数据库
 */
@Slf4j
@RestController
@RequestMapping("/webFlux")
public class WebFluxController {
    @GetMapping("/test")
    public String test() {
        log.info("test --kaishi");
        doSome();
        log.info("test --jieshu");
        return "--test-";
    }

    /**
     * 异步处理dosome方法  可以增加吞吐量
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/monoDemo")
    public Mono<String> monoDemo() throws InterruptedException {
        log.info("monoDemo --kaishi");
        //提供者可以理解为返回  是函数编程里面的东西  异步处理
        Mono<String> mono = Mono.fromSupplier(() -> doSome());
        log.info("monoDemo --jieshu");
        return mono;
    }

    public String doSome() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "--0-";
    }

    public String doSome(String s) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("monoDemo --jieshu3" + s);
        return "--0-";
    }

    /**
     * flux表示可以包含多个处理器
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/fluxDemo")
    public Flux<String> fluxDemo() {
        //Flux 表示可以包含0或多个元素的异步序列
        return Flux.just("1", "2", "3");
    }

    /**
     * flux表示可以包含多个处理器
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/arrayFluxDemo")
    public Flux<String> ArrayFluxDemo(@RequestParam String[] hobby) {
        return Flux.fromArray(hobby);
    }

    @GetMapping("/listFluxDemo")
    public Flux<String> listFluxDemo(@RequestParam String[] hobby) {
        return Flux.fromArray(hobby);
    }

    @GetMapping("/listFluxStreamDemo")
    public Flux<String> listFluxStreamDemo(@RequestParam List<String> hobby) {
        log.info("listFluxDemo --kaishi");
        //将list转换成stream 在将stream转换成
        Flux flux = Flux.fromStream(hobby.stream().map(i -> doSome("time--" + i)));
        log.info("listFluxDemo --jieshu");
        return flux;
    }

    /**
     * sse 服务端推送事件
     *
     * @param hobby
     * @return
     */
    @GetMapping(value = "/sseDemo", produces = "text/event-stream")
    public Flux<String> sseDemo(@RequestParam String[] hobby) {
        return Flux.fromArray(hobby);
    }

    /**
     * flux表示可以包含多个处理器
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/fluxDemo1")
    public Flux<Student> fluxDemo1() {
        //Flux 表示可以包含0或多个元素的异步序列
        return Flux.just(new Student("11", "是1么", 1), new Student("22", "是2么", 2));
    }
}
