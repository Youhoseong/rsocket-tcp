package com.example.rsocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaemonRunner implements CommandLineRunner {

    private final StreamResponser sender;

    @Override
    public void run(String... args) {

        sender.doTransfer();

        log.info("Welcome. ");

        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    log.info("I'm alive.");
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    break;
                }
            }
            log.info("=======================================");
            log.info("message receiver stopped!!");
            log.info("=======================================");
        });

        thread.start();
    }
}
