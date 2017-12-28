/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 QAware GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.qaware.heimdall.salt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Salt provider which uses {@link SecureRandom} for salt creation.
 */
public class SecureSaltProvider implements SaltProvider {

    /**
     * OS name like 'Windows 10'
     */
    private static final String OS = System.getProperty("os.name");

    /**
     * Secure random generator.
     */
    private SecureRandom random;

    public SecureSaltProvider() {
        try {
            if (isWindows()) {
                /* if the current system is Windows
                 * the native pseudo random number generator should be used to improve security */
                random = SecureRandom.getInstance("Windows-PRNG");
            } else {
                random = new SecureRandom();
            }
        } catch (NoSuchAlgorithmException e) {
            /* if lookup fails fallback to default */
            random = new SecureRandom();
        }
    }

    private static boolean isWindows() {
        return OS.startsWith("Windows");
    }

    @Override
    public byte[] create(int sizeInBits) {
        if (sizeInBits % 8 != 0) {
            throw new IllegalArgumentException("sizeInBits must be divisible by 8");
        }

        byte[] salt = new byte[sizeInBits / 8];
        random.nextBytes(salt);

        return salt;
    }
}
