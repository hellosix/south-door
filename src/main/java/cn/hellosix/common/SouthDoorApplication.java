package cn.hellosix.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lzz on 2017/7/28.
 */
@SpringBootApplication
@EnableScheduling
public class SouthDoorApplication {

    public static void main(String[] args) {
        /**
         * ProxyManager.scheduleRun()
         */
        SpringApplication.run(SouthDoorApplication.class, args);
    }

}