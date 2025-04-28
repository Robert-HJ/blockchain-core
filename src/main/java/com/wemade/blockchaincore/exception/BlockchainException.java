package com.wemade.blockchaincore.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class BlockchainException extends RuntimeException {

    private final HttpStatus status;
    private final String clientMessage;

//    private boolean isNotificationNeeded = false;
//    private boolean isLogging = true;
//    private final String requestId = UUID.randomUUID().toString().replaceAll("-", "");

    public BlockchainException(Exception e) {
        super(e);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.clientMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    public BlockchainException(HttpStatus status, String... clientMessage) {
        super(String.join(" ", clientMessage));
        this.status = status;
        this.clientMessage = String.join(" ", clientMessage);
    }
}
