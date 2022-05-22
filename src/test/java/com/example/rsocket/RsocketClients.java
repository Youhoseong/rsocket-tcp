package com.example.rsocket;


import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

@Slf4j
@ActiveProfiles("local")
public class RsocketClients {

    Mono<RSocket> rSocket;

    @Test
    void test() {
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 7000));


        RSocketClient.from(rSocket)
                .requestStream(Mono.just(DefaultPayload.create("this is request.")))
                .map(it -> {
                    log.info("payload {}", it.getDataUtf8());
                    return it;
                })
                .doOnSubscribe(it -> log.info("Subscribe On !!"))
                .blockLast();

    }
}
