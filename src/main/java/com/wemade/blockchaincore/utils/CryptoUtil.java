package com.wemade.blockchaincore.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoUtil {

    public static byte[] keccak256(byte[]... data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (byte[] b : data) {
                digest.update(b);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다", e);
        }
    }

}
