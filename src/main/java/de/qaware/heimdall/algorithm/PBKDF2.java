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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Base class for all PBKDF2 implementations
 * @author Peter Kurfer
 */
public abstract class PBKDF2 implements HashAlgorithm {

    /**
     * Output size of the hash function in bit.
     */
    private static final int OUTPUT_SIZE_IN_BITS = 192;

    /**
     * Config key to store the iterations.
     */
    public static final String ITERATIONS_CONFIG_KEY = "i";

    /**
     * Default number of iterations.
     */
    public static final int DEFAULT_ITERATIONS = 20000;

    /**
     * Minimum number of iterations.
     */
    public static final int MINIMUM_ITERATIONS = 10000;

    /**
     * Radix for hex numbers.
     */
    private static final int RADIX_HEX = 16;

    private final String algorithmKey;
    private final int id;

    protected PBKDF2 (int id, String algorithmKey){
        this.id = id;
        this.algorithmKey = algorithmKey;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] hash(char[] password, byte[] salt, HashAlgorithmConfig config) throws AlgorithmException {
        Preconditions.checkNotNull(password, "password");
        Preconditions.checkNotNull(salt, "salt");
        Preconditions.checkNotNull(config, "config");

        int iterations = getIterationsFromConfig(config);

        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, OUTPUT_SIZE_IN_BITS);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithmKey);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new AlgorithmException(e);
        } catch (InvalidKeySpecException e) {
            throw new AlgorithmException(e);
        }
    }

    @Override
    public int getOutputSizeInBits() {
        return OUTPUT_SIZE_IN_BITS;
    }

    /**
     * Reads the iteration count from the given config.
     *
     * @param config Config.
     * @return Iteration count.
     */
    int getIterationsFromConfig(HashAlgorithmConfig config) throws AlgorithmException {
        assert config != null;

        String value = config.get(ITERATIONS_CONFIG_KEY);
        if (value == null) {
            throw new AlgorithmException("Iteration count config value '" + ITERATIONS_CONFIG_KEY + "' doesn't exist");
        }

        return Integer.parseInt(value, RADIX_HEX);
    }
}
