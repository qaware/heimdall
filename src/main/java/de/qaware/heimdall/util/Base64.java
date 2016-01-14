package de.qaware.heimdall.util;

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
        Preconditions.checkNotNull(base64, "base64");

        return org.apache.commons.codec.binary.Base64.decodeBase64(base64);
    }

    /**
     * Encodes the given byte array into a base64 string.
     *
     * @param data Byte array.
     * @return Base64 string.
     */
    public static String encode(byte[] data) {
        Preconditions.checkNotNull(data, "data");

        return org.apache.commons.codec.binary.Base64.encodeBase64String(data);
    }
}
