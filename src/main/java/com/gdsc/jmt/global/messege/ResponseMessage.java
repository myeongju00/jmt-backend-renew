package com.gdsc.jmt.global.messege;

import org.springframework.http.HttpStatus;


public interface ResponseMessage {
    String getMessage();
    HttpStatus getStatus();
}
