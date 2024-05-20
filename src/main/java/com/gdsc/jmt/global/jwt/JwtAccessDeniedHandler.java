package com.gdsc.jmt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.jmt.global.dto.JMTApiResponse;
import com.gdsc.jmt.global.messege.DefaultMessage;
import com.gdsc.jmt.global.messege.ResponseMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 필요한 권한이 없이 접근하려 할때 403
        ResponseMessage errorMessage = DefaultMessage.FORBIDDEN;
        JMTApiResponse jmtApiResponse = JMTApiResponse.createResponseWithMessage(null, errorMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jmtApiResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getStatus().value());
        response.getWriter().write(json);
    }
}
