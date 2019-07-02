package org.hellosix.south.door;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("org.hellosix.south.door.dao")
@SpringBootApplication
public class SouthDoorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SouthDoorApplication.class, args);
    }
}
