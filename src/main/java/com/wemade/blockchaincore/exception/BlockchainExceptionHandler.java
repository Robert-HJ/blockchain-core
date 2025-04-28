package com.wemade.blockchaincore.exception;

import com.wemade.blockchaincore.constants.ErrorConstants;
import com.wemade.blockchaincore.dto.BlockchainResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class BlockchainExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMessageNotReadableException Occurred : {}", ex.getMessage());
        return BlockchainResponseDTO.error(HttpStatus.UNPROCESSABLE_ENTITY, joinMessage(getRequestURI(request), ErrorConstants.RES_ERR_BIND, ex.getMessage()));
    }

    @ExceptionHandler(BlockchainException.class)
    public ResponseEntity<Object> exception(WebRequest request, BlockchainException be){

        // FIXME: 현재 요청한 사용자의 정보 추가 및 로깅 메세지 포맷 수정과 웹훅 등 부가 로직 처리 추가
//        try {
//            LoginUserInfo userInfo = getUserInfoForCache(extractToken(request));
//            userInfo...
//
//        } catch (Exception e) {
//            log.error("occurred error for get login user info : {}", e.getMessage());
//        }

        log.error("Exception Occurred : {}", be.getMessage());

        return BlockchainResponseDTO.error(be.getStatus(), joinMessage(getRequestURI(request), be.getClientMessage()));
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            return servletWebRequest.getRequest().getRequestURI();
        }

        return null;
    }

    private String joinMessage(String... message) {
        if (message == null || message.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(message[0]);

        if (message.length > 1) {
            sb.append(" [");
            for (int i = 1; i < message.length; i++) {
                if (message[i] != null) {
                    if (i > 1) {
                        sb.append(" ");
                    }
                    sb.append(message[i]);
                }
            }
            sb.append("]");
        }

        return sb.toString();
    }

}
