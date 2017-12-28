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
package de.qaware.heimdall.algorithm;

import de.qaware.heimdall.config.HashAlgorithmConfig;
import de.qaware.heimdall.util.Preconditions;

/**
 * Implementation of the PBKDF2SHA256 algorithm.
 */
public class PBKDF2SHA256 extends PBKDF2 {

    /**
     * Name of the PBKDF2SHA256 algorithm in the Java Security library.
     */
    private static final String PBKDF2_WITH_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

    /**
     * ID of the algorithm.
     */
    private static final int ID = 2;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isAlgorithmDeprecated() {
        return false;
    }

    @Override
    public boolean isConfigDeprecated(HashAlgorithmConfig config) throws AlgorithmException {
        Preconditions.checkNotNull(config, "config");

        int iterations = getIterationsFromConfig(config);

        return iterations < MINIMUM_ITERATIONS;
    }

    @Override
    public HashAlgorithmConfig getDefaultConfig() {
        HashAlgorithmConfig defaultConfig = new HashAlgorithmConfig();
        defaultConfig.put(ITERATIONS_CONFIG_KEY, Integer.toHexString(DEFAULT_ITERATIONS));

        return defaultConfig;
    }

    @Override
    public int getOutputSizeInBits() {
        return OUTPUT_SIZE_IN_BITS;
    }

    @Override
    protected String getAlgorithmKey() {
        return PBKDF2_WITH_HMAC_SHA256;
    }
}
