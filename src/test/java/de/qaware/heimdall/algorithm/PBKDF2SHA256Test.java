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
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PBKDF2SHA256Test {
    @Test
    public void testHash() throws Exception {
        PBKDF2SHA256 sut = new PBKDF2SHA256();

        byte[] salt = new byte[]{1, 2, 3, 4, 5};
        HashAlgorithmConfig config = sut.getDefaultConfig();

        byte[] hash = sut.hash("password".toCharArray(), salt, config);

        assertThat(hash.length, is(192 / 8));

        assertThat(hash, is(new byte[]{-36, -36, 108, -75, 54, 3, 23, 69, -14, -12, 28, -14, 102, -71, -126, -109, 57, -97, -82, 27, -55, -75, 17, 20}));
    }

    @Test(expectedExceptions = AlgorithmException.class)
    public void testIterationConfigDoesntExists() throws Exception {
        PBKDF2SHA256 sut = new PBKDF2SHA256();

        byte[] salt = new byte[]{1, 2, 3, 4, 5};
        HashAlgorithmConfig config = new HashAlgorithmConfig();
        sut.hash("password".toCharArray(), salt, config);
    }
}
