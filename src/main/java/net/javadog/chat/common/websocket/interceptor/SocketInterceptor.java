package net.javadog.chat.common.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.shiro.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * SocketInterceptor
 *
 * @author: hdx
 * @Date: 2022-06-06 16:58
 * @version: 1.0
 **/
@Slf4j
public class SocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            // 获取请求链接以前的token参数.
            String params = servletRequest.getHeader("Sec-WebSocket-Protocol");
            if (StringUtils.isNotBlank(params)) {
                // [0]是token,[1]是username,前端拼接过来，用来鉴权
                String[] param = params.split("\\|");
                return JwtUtil.verify(param[0], param[1]);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            HttpServletResponse resp = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
            if (!StringUtils.isEmpty(req.getHeader("Sec-WebSocket-Protocol"))) {
                // 重要：如果不给前端返回则前端无法建立连接！！
                resp.addHeader("Sec-WebSocket-Protocol", req.getHeader("Sec-WebSocket-Protocol"));
            }
        }
    }
}
