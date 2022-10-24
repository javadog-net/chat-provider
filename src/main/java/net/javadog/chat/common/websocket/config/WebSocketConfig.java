package net.javadog.chat.common.websocket.config;

import net.javadog.chat.common.websocket.interceptor.SocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Description: websocket配置
 * @Author: hdx
 * @Date: 2022/2/13 20:50
 * @Version: 1.0
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 连接路径配置
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 路径"/websocket"被注册为STOMP端点，对外暴露，客户端通过该路径获取与socket的连接
        registry.addEndpoint("/socket").addInterceptors(new SocketInterceptor())
                .setAllowedOrigins("*");
    }

    /**
     * 服务端接收消息路径配置
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 消息代理的前缀 该路径消息会被代理通过广播方式发给客户端（广播路径）
        // simple-单聊通知; topic-群聊通知; invitation-邀请通知; msgRevoke-消息撤销
        config.enableSimpleBroker("/simple", "/topic", "/invitation", "/msgHandle");
        // 过滤该路径集合发送过来的消息，被@MessageMapping注解的方法接收处理具体决定广播还是单点发送到客户端
        config.setApplicationDestinationPrefixes("/app", "/queue");
    }

}
