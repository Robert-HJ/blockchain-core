package com.wemade.blockchaincore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * API 응답에 사용되는 기본 응답 헤더 클래스
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApiResponse {

    @JsonProperty("Result")
    private int result;            // 결과 코드 값

    @JsonProperty("ResultString")
    private String resultString;   // 결과 코드 메시지

    @JsonProperty("Desc")
    private String desc;        // 상세 메시지

}
