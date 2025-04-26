package com.wemade.blockchaincore.enums;

public enum  ResultCodeEnum {

    SUCCESS(0, "Success"),
    FAILED(1, "Failed", "요청이 실패하였습니다."),
    USER_ID_NOT_FOUND(13, "UserIDNotFound", "유저 아이디가 존재하지 않습니다"),
    INVALID_BIRTHDATE(14, "invalid birthdate", "잘못된 생년월일"),
    MINOR_USER(15, "minor user", "미성년자"),
    ALREADY_CONFIRMED_USER(16, "already confirmed user", "이미 확인된 사용자"),
    DISAGREE_WITH_TERMS(17, "disagree with terms", "개인정보 미동의"),
    ACCESS_TOKEN_INVALID(101, "AccessTokenInvalid", "접속 토큰이 유효하지 않음"),
    USER_NOT_FOUND(102, "UserNotFound", "유저 정보를 찾을 수 없음"),
    NOT_FOUND(404, "Not Found", "다큐먼트를 찾지 못했습니다."),

    // Agreement On Notification
    OK(200, "OK", "등록 완료"),
    BAD_REQUEST(400, "Bad Request", "요청이 실패하였습니다."),
    UNAUTHORIZED(401, "Unauthorized", "유저 아이디가 존재하지 않습니다"),
    FORBIDDEN(403, "Forbidden", "인증이 안됐습니다."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "internal server error");

    private final int code;
    private final String message;
    private final String description;

    /**
     * 결과 코드 생성자
     *
     * @param code 코드 값
     * @param message 코드 메시지
     */
    ResultCodeEnum(int code, String message) {
        this(code, message, "");
    }

    /**
     * 결과 코드 생성자
     *
     * @param code 코드 값
     * @param message 코드 메시지
     * @param description 코드 설명 (한글)
     */
    ResultCodeEnum(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    /**
     * 코드 값을 반환합니다.
     *
     * @return 코드 값
     */
    public int getCode() {
        return code;
    }

    /**
     * 코드 메시지를 반환합니다.
     *
     * @return 코드 메시지
     */
    public String getMessage() {
        return message;
    }

    /**
     * 코드 설명을 반환합니다.
     *
     * @return 코드 설명
     */
    public String getDescription() {
        return description;
    }

    /**
     * 코드 값으로 ResultCode를 찾습니다.
     *
     * @param code 코드 값
     * @return 해당 코드 값을 가진 ResultCode, 없으면 null
     */
    public static ResultCodeEnum fromCode(int code) {
        for (ResultCodeEnum resultCode : ResultCodeEnum.values()) {
            if (resultCode.code == code) {
                return resultCode;
            }
        }
        return null;
    }

    /**
     * toString 메서드 오버라이드
     *
     * @return 코드 메시지
     */
    @Override
    public String toString() {
        return this.message;
    }

}
