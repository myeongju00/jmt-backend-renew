package com.gdsc.jmt.global.dto;

import com.gdsc.jmt.global.messege.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JMTApiResponse<T> {
    T data;
    String message;
    String code;

    public static <G> JMTApiResponse<G> createResponseWithMessage(G data, ResponseMessage responseMessage) {
        return new JMTApiResponse<>(data, responseMessage.getMessage(), responseMessage.toString());
    }
}
