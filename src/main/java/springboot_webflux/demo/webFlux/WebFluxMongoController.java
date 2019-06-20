package springboot_webflux.demo.webFlux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springboot_webflux.demo.webFlux.mongo.Student;
import springboot_webflux.demo.webFlux.repository.StudentRepository;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 翻译： Spring WebFlux是一个自底向上构建的非阻塞Web框架，用于利多核、下一代处理器并处理大量并发连接
 * j2ee7以上 同时不支持关系型数据库
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class WebFluxMongoController {

    @Resource
    private StudentRepository studentRepository;

    //以数组形式一次性返回数据
    @GetMapping("/getAll")
    public Flux<Student> getAll() {
        return studentRepository.findAll();
    }

    //以sse形式实时返回数据
    @GetMapping(value = "/sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllSSE() {
        return studentRepository.findAll();

    }

    @PostMapping("/save")
    public Mono<Student> save(@RequestBody Student student) {

        return studentRepository.save(student);
    }

    @PostMapping( "/saveForm")
    public Mono<Student> saveForm(@Valid Student student) {

        return studentRepository.save(student);
    }

    /**
     * 无状态删除
     *
     * @param id
     * @return 删除是否成功状态都是200  没有错误标示
     */
    @DeleteMapping("/del/{id}")
    public Mono<Void> delById(@PathVariable("id") String id) {
        return studentRepository.deleteById(id);
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
//        方法，由then()方法返回想返回的值。，
//        由于Spring--Data-JPA的的delete()方法没有返回值，所以这里使用有返回值，所以这里使用then()为其添加返回值。
//       System.out.println("------"+studentRepository.findById(id).block().toString());
        return studentRepository.findById(id)
                .flatMap(student -> studentRepository.delete(student)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Student>> update(@PathVariable("id") String id,Student student) {
        validate.validateName(student.getName());
        return studentRepository.findById(id)
                .flatMap(stu -> {
                    stu.setName(student.getName());
                    stu.setAge(student.getAge());
                    return studentRepository.save(stu);
                })
                .map(stu->new ResponseEntity<Student>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
    }

    /**
     * @
     */
    @GetMapping("/age/{begin}/{end}")
    public Flux<Student> findBetween(@PathVariable("end") int end,@PathVariable("begin") int begin) {

        if(begin ==0){
            return studentRepository.queryByAge(begin,end);
        }
        return studentRepository.findByAgeBetween(begin,end);

    }
}
