package com.example.rsocket;

import io.rsocket.core.RSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DaemonRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {

//        RSocketServer.create(((setup, sendingSocket) -> {
//
//        }));


        log.info("Welcome. ");

        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    log.info("I'm alive.");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    break;
                }
            }
            log.info("=======================================");
            log.info("message receiver stopped!!");
            log.info("=======================================");
        });

        thread.start();
        thread.interrupt();
    }
}
