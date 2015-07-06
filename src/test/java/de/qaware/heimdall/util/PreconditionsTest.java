package de.qaware.heimdall.util;

import org.testng.annotations.Test;

public class PreconditionsTest {
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCheckNotNull() throws Exception {
        Object reference = null;
        Preconditions.checkNotNull(reference, "reference");
    }

    @Test
    public void testCheckNotNull2() throws Exception {
        Object reference = new Object();
        Preconditions.checkNotNull(reference, "reference");
    }
}