package com.lzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lzz on 2017/7/28.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        /**
         * com.lzz.proxyservice.ProxyManager.scheduleRun()
         */
        SpringApplication.run(Application.class, args);
    }

}