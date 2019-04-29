package com.ttn.reap;

import com.ttn.reap.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
@EnableJpaRepositories("com.ttn.reap.repository")
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("welcome to reap!!!");
    }
}
