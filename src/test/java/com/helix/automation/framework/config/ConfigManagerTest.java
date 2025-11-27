package com.helix.automation.framework.config;

import org.testng.annotations.*;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManagerTest {
    private String originalUserDir;
    private String originalSysBaseUrl;

    @BeforeMethod
    public void beforeMethod() {
        originalUserDir = System.getProperty("user.dir");
        originalSysBaseUrl = System.getProperty("web.baseUrl");
    }

    @AfterMethod
    public void afterMethod() {
        // restore
        if (originalUserDir != null) System.setProperty("user.dir", originalUserDir);
        else System.clearProperty("user.dir");
        if (originalSysBaseUrl != null) System.setProperty("web.baseUrl", originalSysBaseUrl);
        else System.clearProperty("web.baseUrl");
        ConfigManager.reload();
    }

    @Test
    public void testClasspathFallback() {
        // set to a temp dir without .env.config so loader uses classpath properties
        Path tmp = Path.of(System.getProperty("java.io.tmpdir"));
        System.setProperty("user.dir", tmp.toString());
        ConfigManager.reload();

        String base = ConfigManager.getBaseUrl();
        Assert.assertEquals(base, "https://classpath.dev.passthenote.test", "Should read base url from classpath config-dev.properties");
        String api = ConfigManager.getApiBaseUrl();
        Assert.assertEquals(api, "https://api.classpath.dev.passthenote.test");
    }

    @Test
    public void testEnvFileOverlay() throws IOException {
        Path tmp = Files.createTempDirectory("cfgtest");
        Path envFile = tmp.resolve(".env.config");
        Files.writeString(envFile, "BASE_URL=https://envfile.local.test\nAPI_BASE_URL=https://api.envfile.local.test\n");

        System.setProperty("user.dir", tmp.toString());
        // ensure no system-prop override
        System.clearProperty("web.baseUrl");

        ConfigManager.reload();

        // .env.config defines BASE_URL which maps to getBaseUrl via alias
        Assert.assertEquals(ConfigManager.getBaseUrl(), "https://envfile.local.test");
        Assert.assertEquals(ConfigManager.getApiBaseUrl(), "https://api.envfile.local.test");

        // cleanup
        Files.deleteIfExists(envFile);
        Files.deleteIfExists(tmp);
    }

    @Test
    public void testSystemPropertyPrecedence() throws IOException {
        Path tmp = Files.createTempDirectory("cfgtest2");
        Path envFile = tmp.resolve(".env.config");
        Files.writeString(envFile, "BASE_URL=https://envfile2.local.test\n");

        System.setProperty("user.dir", tmp.toString());
        System.setProperty("web.baseUrl", "https://system.prop.test");

        ConfigManager.reload();
        // system property should win
        Assert.assertEquals(ConfigManager.getBaseUrl(), "https://system.prop.test");

        Files.deleteIfExists(envFile);
        Files.deleteIfExists(tmp);
    }
}
