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

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moritz.kammerer
 */
public class HashAlgorithmRegistryImpl implements HashAlgorithmRegistry {
    /**
     * Map from algorithm id to algorithm.
     */
    private final Map<Integer, HashAlgorithm> algorithms = new HashMap<Integer, HashAlgorithm>();

    /**
     * Constructor.
     *
     * @param hashAlgorithms Hash algorithms known to the registry.
     */
    public HashAlgorithmRegistryImpl(HashAlgorithm... hashAlgorithms) {
        Preconditions.checkNotNull(hashAlgorithms, "hashAlgorithms");

        for (HashAlgorithm hashAlgorithm : hashAlgorithms) {
            algorithms.put(hashAlgorithm.getId(), hashAlgorithm);
        }
    }

    @Override
    public HashAlgorithm getAlgorithm(int id) throws AlgorithmException {
        HashAlgorithm algorithm = algorithms.get(id);
        if (algorithm == null) {
            throw new AlgorithmException("Couldn't find algorithm with id " + id);
        }

        return algorithm;
    }
}
