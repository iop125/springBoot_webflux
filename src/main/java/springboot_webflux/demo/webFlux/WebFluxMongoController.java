package springboot_webflux.demo.webFlux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springboot_webflux.demo.webFlux.mongo.Student;

import javax.annotation.Resource;

/**
 * 翻译： Spring WebFlux是一个自底向上构建的非阻塞Web框架，用于利多核、下一代处理器并处理大量并发连接
 * j2ee7以上 同时不支持关系型数据库
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class WebFluxMongoController {

//    @Resource
//    private StudentRepository studentRepository;

    //以数组形式一次性返回数据
    @GetMapping("/getAll")
    public Flux<Student> getAll() {


//        return studentRepository.findAll();
        return Flux.just(new Student("11", "是1么", 1), new Student("22", "是2么", 2));
    }

    //以sse形式实时返回数据
    @GetMapping(value = "/sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllSSE() {
//        return studentRepository.findAll();
        return Flux.just(new Student("11", "是1么", 1), new Student("22", "是2么", 2));

    }

    @PostMapping("/save")
    public Mono<Student> save(@RequestBody Student student) {
        Mono<Student> mono = Mono.fromSupplier(() ->
                new Student("23", "是么", 22)
        );
//        return studentRepository.save(student);
        return mono;
    }

    @PostMapping(value = "/saveForm", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Student> saveForm(Student student) {
        Mono<Student> mono = Mono.fromSupplier(() ->
                student
        );
        return mono;
//        return studentRepository.save(student);
    }

    /**
     * 无状态删除
     *
     * @param id
     * @return 删除是否成功状态都是200  没有错误标示
     */
    @DeleteMapping("/del/{id}")
    public Mono<Void> delById(@PathVariable("id") String id) {
//        return studentRepository.deleteById(id);
//        Mono<Void> mono = (Mono<Void>) Mono.fromSupplier(
//
//        );
        return null;
    }


    /**
     * @param id
     * @return
     */
    @DeleteMapping("/delByIdStatus/{id}")
    public Mono<ResponseEntity<Void>> delByIdStatus(@PathVariable("id") String id) {
//        编写该处理器的
//        思路 是 ：首先根据 id 对该对象进行查询，若存在则先将该查询结果删
//        除，然后再将其映射为状态码 200 ；若不存在，则将查询结果映射为状态码 404 。
//        对于该处理器，需要注意以下几点：ResponseEntity 可以封装响应体中所携带的数据及响应码，其泛型用于指定携带数据的
//        类型。若响应体中没有携带数据，则泛型为 Void 。本例中要返回的 ResponseEntity 中仅
//        封装了响应码不携带任何数据，所以泛型为 Void 。 响应码只能采用 HttpStatus 枚举类型
//        常量表示，这是 ResponseEntity 的构造器所要求的。为什么做映射时使用 flatMap()flatMap()，不使用 map()map()？首先这两个方法都是 Mono 的方法，不
//        是 Stream 的方法，与 Stream 的两个同名方法无关，但均是做映射的。 若需要对 对象数
//        据 先 执行操作后再做映射，则 使用 flatMap()；若纯粹是一种数据映射，没有数据操作
//        则使用 map() 。
//        在 Mono 的访求中， 对于没有返回值的方法，若想为其添加返回值，则可链式调用 then()
//        方法，由then()then()方法返回想返回的值。，
//        由于SpringSpring--DataData--JPAJPA的的delete()delete()方法没方法没有返回值，所以这里使用有返回值，所以这里使用then()then()为其添加返回值。为其添加返回值。
//        return studentRepository.findById(id)
//                .flatMap(student -> studentRepository.delete(student))
//                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
//                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
        return null;
    }
}
