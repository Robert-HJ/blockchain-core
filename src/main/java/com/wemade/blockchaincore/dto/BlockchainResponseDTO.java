package com.wemade.blockchaincore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wemade.blockchaincore.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
public class BlockchainResponseDTO<T> {

    @JsonProperty("Result")
    private int result;            // 결과 코드 값

    @JsonProperty("ResultString")
    private String resultString;   // 결과 코드 메시지

    @JsonProperty("Desc")
    private String desc;        // 상세 메시지

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    public BlockchainResponseDTO(ResultCodeEnum resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null, "");
    }

    public BlockchainResponseDTO(ResultCodeEnum resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMessage(), data, "");
    }

    public BlockchainResponseDTO(ResultCodeEnum resultCode, String... desc) {
        this(resultCode.getCode(), resultCode.getMessage(), null, desc);
    }

    public BlockchainResponseDTO(int result, String resultString, T data, String... desc) {
        this.result = result;
        this.resultString = resultString;
        this.desc = String.join(",", desc);
        this.data = data;
    }

    public static <R> ResponseEntity<BlockchainResponseDTO<R>> success(R data) {
        return ResponseEntity.ok(new BlockchainResponseDTO<>(ResultCodeEnum.SUCCESS, data));
    }

    public static ResponseEntity<BlockchainResponseDTO<Void>> success() {
        return ResponseEntity.ok(new BlockchainResponseDTO<>(ResultCodeEnum.SUCCESS, null));
    }

    public static ResponseEntity<Object> error(HttpStatus status, String clientMessage) {
        return new ResponseEntity<>(new BlockchainResponseDTO<Void>(ResultCodeEnum.FAILED, clientMessage), status);
    }

}



















