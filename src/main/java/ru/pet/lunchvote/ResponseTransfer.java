package ru.pet.lunchvote;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseTransfer {
    private String message;
    private int status;
    private String error;

    public ResponseTransfer(String message, HttpStatus status) {
        this.message = message;
        this.error = status.getReasonPhrase();
        this.status = status.value();
    }
}
