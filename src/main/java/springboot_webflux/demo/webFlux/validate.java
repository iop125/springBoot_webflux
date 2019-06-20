package springboot_webflux.demo.webFlux;

import springboot_webflux.demo.webFlux.exception.StudetException;

import java.util.stream.Stream;

public class validate {

    private static String [] errorName = {"123","0"};

    public static void validateName(String name){
        Stream.of(errorName).filter(vname ->name.equalsIgnoreCase(vname)).findAny().ifPresent(vname->{
            throw new StudetException("name is error",name,"name");
        });
    }
}
