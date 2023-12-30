package org.grow.canteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanteenApplication {
public String PORT=System.getenv("PORT");
    public static void main(String[] args) {
        SpringApplication.run(CanteenApplication.class, args);
    }

}
