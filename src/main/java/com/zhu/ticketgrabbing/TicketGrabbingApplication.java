package com.zhu.ticketgrabbing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TicketGrabbingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketGrabbingApplication.class, args);
    }

}
