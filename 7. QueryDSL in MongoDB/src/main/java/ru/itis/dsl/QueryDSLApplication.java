package ru.itis.dsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackages = "ru.itis.dsl.jpaRepositories")
@SpringBootApplication
public class QueryDSLApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryDSLApplication.class, args);
    }

}
