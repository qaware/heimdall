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
package de.qaware.securepassword.config;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.core.Is.is;

/**
 * @author moritz.kammerer
 */
public class ConfigCoderImplTest {
    @Test
    public void testEncode() throws Exception {
        HashAlgorithmConfig config = new HashAlgorithmConfig();
        config.put("foo", "bar");
        config.put("foobaz", "42");

        ConfigCoderImpl sut = new ConfigCoderImpl();
        String encoded = sut.encode(config);

        String[] split = encoded.split(";");
        assertThat(split, hasItemInArray("foobaz=42"));
        assertThat(split, hasItemInArray("foo=bar"));
    }

    @Test
    public void testEncodeEmpty() throws Exception {
        HashAlgorithmConfig config = new HashAlgorithmConfig();

        ConfigCoderImpl sut = new ConfigCoderImpl();
        String encoded = sut.encode(config);

        assertThat(encoded, is(""));
    }

    @Test
    public void testDecode() throws Exception {
        ConfigCoderImpl sut = new ConfigCoderImpl();
        HashAlgorithmConfig config = sut.decode("foobaz=42;foo=bar");

        assertThat(config.size(), is(2));
        assertThat(config.get("foo"), is("bar"));
        assertThat(config.get("foobaz"), is("42"));
    }
}
