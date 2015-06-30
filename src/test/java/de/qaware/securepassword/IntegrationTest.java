/*
* #%L
* QAcommons - The QAware Standard Library
* %%
* Copyright (C) 2014 QAware GmbH
* %%
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
* #L%
*/
package de.qaware.securepassword;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class IntegrationTest {
    @Test
    public void testFramework() throws Exception {
        Password sut = PasswordFactory.create();

        String hash = sut.hash(new SecureCharArray("hello_world".toCharArray()));
        assertThat(hash, is(notNullValue()));

        boolean matches = sut.verify(new SecureCharArray("hello_world".toCharArray()), hash);
        assertThat(matches, is(true));

        matches = sut.verify(new SecureCharArray("nonono".toCharArray()), hash);
        assertThat(matches, is(false));
    }
}
