package com.wemade.blockchaincore.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

// FIXME: 로직 정상적으로 동작하는지 확인 필요 by Robert

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HexUtil {
    private static final int UINT_BITS = Integer.SIZE << (Long.numberOfLeadingZeros(~0L) >> 6);

    private static class DecodeError extends RuntimeException {
        public DecodeError(String message) {
            super(message);
        }
    }

    private static final DecodeError ERR_EMPTY_STRING = new DecodeError("empty hex string");
    private static final DecodeError ERR_SYNTAX = new DecodeError("invalid hex string");
    private static final DecodeError ERR_MISSING_PREFIX = new DecodeError("hex string without 0x prefix");
    private static final DecodeError ERR_ODD_LENGTH = new DecodeError("hex string of odd length");
    private static final DecodeError ERR_EMPTY_NUMBER = new DecodeError("hex string \"0x\"");
    private static final DecodeError ERR_LEADING_ZERO = new DecodeError("hex number with leading zero digits");
    private static final DecodeError ERR_UINT64_RANGE = new DecodeError("hex number > 64 bits");
    private static final DecodeError ERR_UINT_RANGE = new DecodeError("hex number > " + UINT_BITS + " bits");
    private static final DecodeError ERR_BIG256_RANGE = new DecodeError("hex number > 256 bits");

    /**
     * Converts bytes to hex string with '0x' prefix
     */
    public static String toHex(byte[] src) {
        return "0x" + bytesToHex(src);
    }

    /**
     * Converts a string to 32-byte array
     */
    public static byte[] stringToBytes32(String str) {
        byte[] result = new byte[32];
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(strBytes, 0, result, 0, Math.min(strBytes.length, 32));
        return result;
    }

    /**
     * Converts a 32-byte array to string (stops at first zero byte)
     */
    public static String bytes32ToString(byte[] bytes32) {
        if (bytes32.length != 32) {
            throw new IllegalArgumentException("Input must be 32 bytes");
        }

        int zeroIndex = 0;
        while (zeroIndex < bytes32.length && bytes32[zeroIndex] != 0) {
            zeroIndex++;
        }

        return new String(bytes32, 0, zeroIndex, StandardCharsets.UTF_8);
    }

    /**
     * Converts a 32-byte array to string (uses all 32 bytes)
     */
    public static String bytes32ToString2(byte[] bytes32) {
        if (bytes32.length != 32) {
            throw new IllegalArgumentException("Input must be 32 bytes");
        }

        return new String(bytes32, 0, 32, StandardCharsets.UTF_8);
    }

    /**
     * Returns bytes represented by the hexadecimal string, with or without '0x' prefix
     */
    public static byte[] fromHex(String s) {
        if (s.length() > 1 && (s.startsWith("0x") || s.startsWith("0X"))) {
            s = s.substring(2);
        }

        if (s.length() % 2 == 1) {
            s = "0" + s;
        }

        return hex2Bytes(s);
    }

    /**
     * Returns bytes represented by the hexadecimal string
     */
    public static byte[] hex2Bytes(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Decodes a hex string with 0x prefix
     */
    public static byte[] decode(String input) throws DecodeError {
        if (input.isEmpty()) {
            throw ERR_EMPTY_STRING;
        }

        if (!has0xPrefix(input)) {
            throw ERR_MISSING_PREFIX;
        }

        String hexPart = input.substring(2);
        if (hexPart.length() % 2 != 0) {
            throw ERR_ODD_LENGTH;
        }

        try {
            return hex2Bytes(hexPart);
        } catch (IllegalArgumentException e) {
            throw ERR_SYNTAX;
        }
    }

    /**
     * Checks if input has 0x prefix
     */
    public static boolean has0xPrefix(String input) {
        return input.length() >= 2 && input.charAt(0) == '0' && (input.charAt(1) == 'x' || input.charAt(1) == 'X');
    }

    /**
     * Checks if input has 0x prefix and converts to lowercase
     */
    public static String encodeAddress(String str) {
        if (has0xPrefix(str)) {
            return str.toLowerCase();
        } else {
            return "0x" + str.toLowerCase();
        }
    }

    /**
     * Utility method to convert bytes to hex
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xff));
        }
        return result.toString();
    }
}
