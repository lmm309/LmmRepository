package com.test.k3sdemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class K3sdemoApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(K3sdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("K3sdemoApplication is running ...");
    }
}
