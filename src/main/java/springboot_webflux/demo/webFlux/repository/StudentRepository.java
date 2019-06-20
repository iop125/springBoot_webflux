package springboot_webflux.demo.webFlux.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springboot_webflux.demo.webFlux.mongo.Student;

/**
 * 第一个范型 是该repository操作的对象类型
 * 第二个泛型 为操作对象主键的类型
 */
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
    /**
     * 查询年龄区间
     * @param begin
     * @param end
     * @return
     */
    Flux<Student> findByAgeBetween(int begin, int end);

    /**
     * 查询年龄区间 语句版
     * @param begin
     * @param end
     * @return
     */
    @Query("{'age':{$gte:?0,$lte:?1}}")
    Flux<Student> queryByAge(int begin, int end);
}
