package com.avinash.azure.sastknprvdr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.AccessControlException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FixedSASTokenProviderTest {

    private FixedSASTokenProvider fixedSASTokenProvider;
    private Configuration mockConfiguration;

    @Before
    public void setUp() {
        fixedSASTokenProvider = new FixedSASTokenProvider();
        mockConfiguration = Mockito.mock(Configuration.class);
    }

    @Test(expected = NullPointerException.class)
    public void testGetSASToken_NullAccountName() throws IOException {
        fixedSASTokenProvider.getSASToken(null, "filesystem", "path", "operation");
    }

    @Test(expected = AccessControlException.class)
    public void testGetSASToken_TokenNotDefined() throws IOException {
        String accountName = "testAccount";
        when(mockConfiguration.get("fs.azure.sas.fixed.token.testAccount.dfs.core.windows.net")).thenReturn(null);

        fixedSASTokenProvider.initialize(mockConfiguration, accountName);
        fixedSASTokenProvider.getSASToken(accountName, "filesystem", "path", "operation");
    }

    @Test
    public void testGetSASToken_Success() throws IOException {
        String accountName = "testAccount";
        String expectedToken = "mockToken";
        when(mockConfiguration.get("fs.azure.sas.fixed.token.testAccount.dfs.core.windows.net")).thenReturn(expectedToken);

        fixedSASTokenProvider.initialize(mockConfiguration, accountName);
        String actualToken = fixedSASTokenProvider.getSASToken(accountName, "filesystem", "path", "operation");

        assertEquals(expectedToken, actualToken);
    }

    @Test(expected = NullPointerException.class)
    public void testInitialize_NullConfiguration() throws IOException {
        fixedSASTokenProvider.initialize(null, "testAccount");
    }

    @Test
    public void testInitialize_Success() throws IOException {
        String accountName = "testAccount";
        fixedSASTokenProvider.initialize(mockConfiguration, accountName);
        assertNotNull(fixedSASTokenProvider);
    }
}
