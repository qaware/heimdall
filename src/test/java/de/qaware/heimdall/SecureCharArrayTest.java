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
package de.qaware.heimdall;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SecureCharArrayTest {
    @Test
    public void testGetChars() throws Exception {
        SecureCharArray sut = new SecureCharArray("foo".toCharArray());

        assertThat(sut.getChars(), is("foo".toCharArray()));
    }

    @Test
    public void testClose() throws Exception {
        char[] array = "foo".toCharArray();

        new SecureCharArray(array).close();

        assertThat(array, is("000".toCharArray()));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testDisposed() throws Exception {
        SecureCharArray sut = new SecureCharArray("foo".toCharArray());
        sut.close();

        sut.getChars();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testCloseTwice() throws Exception {
        SecureCharArray sut = new SecureCharArray("foo".toCharArray());
        sut.close();
        sut.close();
    }
}