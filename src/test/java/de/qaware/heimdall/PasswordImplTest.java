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
package de.qaware.heimdall;

import de.qaware.heimdall.algorithm.HashAlgorithm;
import de.qaware.heimdall.algorithm.HashAlgorithmRegistry;
import de.qaware.heimdall.algorithm.HashAlgorithmRegistryImpl;
import de.qaware.heimdall.algorithm.PBKDF2;
import de.qaware.heimdall.config.ConfigCoder;
import de.qaware.heimdall.config.ConfigCoderImpl;
import de.qaware.heimdall.config.HashAlgorithmConfig;
import de.qaware.heimdall.salt.SaltProvider;
import de.qaware.heimdall.salt.SecureSaltProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PasswordImplTest {
    @Test
    public void testHash() throws Exception {
        int outputSizeInBits = 192;
        byte[] salt = new byte[]{1, 2, 3, 4, 5};
        HashAlgorithmConfig defaultConfig = new HashAlgorithmConfig();
        defaultConfig.put("i", "5000");

        SaltProvider saltProvider = mock(SaltProvider.class);
        when(saltProvider.create(outputSizeInBits)).thenReturn(salt);

        HashAlgorithm hashAlgorithm = mock(HashAlgorithm.class);
        when(hashAlgorithm.getId()).thenReturn(1);
        when(hashAlgorithm.getDefaultConfig()).thenReturn(defaultConfig);
        when(hashAlgorithm.getOutputSizeInBits()).thenReturn(outputSizeInBits);
        when(hashAlgorithm.hash("password".toCharArray(), salt, defaultConfig)).thenReturn(new byte[]{5, 4, 3, 2, 1});

        HashAlgorithmRegistry registry = mock(HashAlgorithmRegistry.class);

        ConfigCoder configCoder = mock(ConfigCoder.class);
        when(configCoder.encode(defaultConfig)).thenReturn("config");

        HashAlgorithm defaultHashAlgorithm = mock(HashAlgorithm.class);

        PasswordImpl sut = new PasswordImpl(saltProvider, configCoder, registry, defaultHashAlgorithm);
        String hash = sut.hash("password".toCharArray(), hashAlgorithm, defaultConfig);

        assertThat(hash, is("2:1:AQIDBAU=:config:BQQDAgE="));
    }

    @Test
    public void testVerify() throws Exception {
        PasswordImpl sut = createSut();
        // 'password' hashed with PBKDF2, 20000 iterations
        String hash = "2:1:phiU3EgfZQUDGJG/Eq4AuGuKLBPetJ+7:i=4e20:P/n0KVm64f8otqSeKmEqvqAmhOrhU8Q4";

        assertThat(sut.verify("password".toCharArray(), hash), is(true));
        assertThat(sut.verify("foobar".toCharArray(), hash), is(false));
    }

    @Test
    public void testNeedsRehash() throws Exception {
        PasswordImpl sut = createSut();

        HashAlgorithmConfig deprecatedConfig = new HashAlgorithmConfig();
        deprecatedConfig.put(PBKDF2.ITERATIONS_CONFIG_KEY, Integer.toHexString(PBKDF2.MINIMUM_ITERATIONS - 1));
        String deprecatedHash = sut.hash("password".toCharArray(), new PBKDF2(), deprecatedConfig);

        // Hash is deprecated because iteration count is too low.
        assertThat(sut.needsRehash(deprecatedHash), is(true));
    }

    @Test
    public void testNeedsRehashDueToOldVersion() throws Exception {
        PasswordImpl sut = createSut();
        String oldHash = "1:1:3ZuNhoI4vvk+CX1MMCISKToo6EoRNOFE:i=4e20:h/+2yvl0FLu9slPmzdnqvgWeqlEUTQa9";

        assertThat(sut.needsRehash(oldHash), is(true));
    }

    @Test
    public void testMinimalIterations() throws Exception {
        PasswordImpl sut = createSut();

        HashAlgorithmConfig config = new HashAlgorithmConfig();
        config.put(PBKDF2.ITERATIONS_CONFIG_KEY, Integer.toHexString(PBKDF2.MINIMUM_ITERATIONS));
        String hash = sut.hash("password".toCharArray(), new PBKDF2(), config);

        // Hash is not deprecated because iteration count is the minimal allowed.
        assertThat(sut.needsRehash(hash), is(false));
    }

    @Test(expectedExceptions = PasswordException.class)
    public void testMalformedHash() throws Exception {
        PasswordImpl sut = createSut();
        sut.verify("foo".toCharArray(), "1:");
    }

    @Test(expectedExceptions = PasswordException.class)
    public void testUnsupportedHashVersion() throws Exception {
        PasswordImpl sut = createSut();

        sut.verify("foo".toCharArray(), "1000");
    }

    @Test
    public void testWorkflow() throws Exception {
        PasswordImpl sut = createSut();
        String hash = sut.hash("foobar".toCharArray());

        assertThat(sut.needsRehash(hash), is(false));
        assertThat(sut.verify("foobar".toCharArray(), hash), is(true));
        assertThat(sut.verify("bar".toCharArray(), hash), is(false));
    }

    private PasswordImpl createSut() {
        SaltProvider saltProvider = new SecureSaltProvider();
        ConfigCoder configCoder = new ConfigCoderImpl();
        HashAlgorithmRegistry registry = new HashAlgorithmRegistryImpl(new PBKDF2());
        HashAlgorithm defaultHashAlgorithm = new PBKDF2();

        return new PasswordImpl(saltProvider, configCoder, registry, defaultHashAlgorithm);
    }
}
