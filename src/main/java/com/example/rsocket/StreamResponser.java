package com.example.rsocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j

@RequiredArgsConstructor
public class StreamResponser {
    private final ObjectMapper objectMapper;

    public void doTransfer() {
        String serverHost = "localhost";
        int serverPort = 7000;

        log.info("=======================================");
        log.info("message transfer get started !!");
        log.info(" Bind host {}, port {}", serverHost, serverPort);
        log.info("=======================================");

        RSocketServer.create((setup, sendingSocket) -> Mono.just(new RSocket() {
                @Override
                public Flux<Payload> requestStream(Payload payload) {
                    log.info("[request payload] = {}", getArg(payload));
                    return Flux.just(
                            DefaultPayload.create("sample response 1", ""),
                            DefaultPayload.create("sample response 2", "")
                    ).doOnSubscribe(it -> log.info("request appended !!"));
                }
        }))
        .bindNow(TcpServerTransport.create(serverHost, serverPort));

    }

    private String getArg(Payload payload) {
        return payload.getDataUtf8();
    }
}
