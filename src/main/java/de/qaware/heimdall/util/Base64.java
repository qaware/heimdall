package de.qaware.heimdall.util;

import java.util.Objects;

/**
 * Base64 encoding.
 */
public final class Base64 {
    /**
     * No instances allowed.
     */
    private Base64() {
    }

    /**
     * Decodes the given base64 string into a byte array.
     *
     * @param base64 Base64 string.
     * @return Byte array.
     */
    public static byte[] decode(String base64) {
        Objects.requireNonNull(base64, "base64");

        return java.util.Base64.getDecoder().decode(base64);
    }

    /**
     * Encodes the given byte array into a base64 string.
     *
     * @param data Byte array.
     * @return Base64 string.
     */
    public static String encode(byte[] data) {
        Objects.requireNonNull(data, "data");

        return java.util.Base64.getEncoder().encodeToString(data);
    }
}
