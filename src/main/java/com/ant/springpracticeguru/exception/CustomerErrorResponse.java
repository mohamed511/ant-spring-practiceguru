package com.ant.springpracticeguru.exception;

import lombok.Data;

@Data
public class CustomerErrorResponse {
    private int status;
    private String message;
    private long timeStamp;


}
