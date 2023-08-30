package com.example.SummerProject.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/*
    기능 : 소켓 연결
    주요 기능 : 소켓의 STOMP 기법을 통해 구독자, 발행자 개념의 로직과 브로커연결, 웹소켓 연결설정
 */

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 발행자가 "/app" 경로로 메시지를 주면 가공해서 구독자들에게 전달
        registry.setApplicationDestinationPrefixes("/app");
        // 구독 경로 설정. 구독자가 "/pub" 경로로 메시지를 구독함

        registry.enableSimpleBroker("/pub");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 커넥션을 맺는 경로 설정. 만약 WebSocket을 사용할 수 없는 브라우저라면 다른 방식을 사용하도록 설정
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}
