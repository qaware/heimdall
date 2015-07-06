package de.qaware.heimdall.util;

import org.testng.annotations.Test;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Base64Test {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Test
    public void testDecode() throws Exception {
        byte[] data = Base64.decode("aGVsbG8sIHdvcmxk");

        assertThat(new String(data, UTF_8), is("hello, world"));

    }

    @Test
    public void testEncode() throws Exception {
        String base64 = Base64.encode("hello, world".getBytes(UTF_8));

        assertThat(base64, is("aGVsbG8sIHdvcmxk"));
    }
}