package org.taohansen.at_infnet_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.taohansen.at_infnet_springboot.repositories")
public class AtInfnetSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtInfnetSpringApplication.class, args);
    }

}
