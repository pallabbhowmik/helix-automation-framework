package com.helix.automation.framework.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    private static final Properties props = new Properties();

    static {
        // determine env name from system property, env var, or default to 'dev'
        String env = System.getProperty("env");
        if (env == null || env.isEmpty()) env = System.getenv("ENV");
        if (env == null || env.isEmpty()) env = "dev";

        load(props, env);
    }

    /**
     * Reload configuration at runtime (useful for tests) — re-applies the same overlay order.
     * Note: this reads system properties, environment variables and the project .env.config
     */
    public static synchronized void reload() {
        props.clear();
        String env = System.getProperty("env");
        if (env == null || env.isEmpty()) env = System.getenv("ENV");
        if (env == null || env.isEmpty()) env = "dev";
        load(props, env);
    }

    private static void load(Properties target, String env) {
        // 1) load classpath resource for defaults (fallback)
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config-" + env + ".properties")) {
            if (is != null) target.load(is);
        } catch (Exception e) { throw new RuntimeException(e); }

        // 2) overlay values from .env.config file in project root if present
        Path envFile = Paths.get(System.getProperty("user.dir"), ".env.config");
        if (Files.exists(envFile)) {
            try (BufferedReader br = Files.newBufferedReader(envFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) continue;
                    String[] parts = line.split("=", 2);
                    target.setProperty(parts[0].trim(), parts[1].trim());
                }
            } catch (Exception e) { throw new RuntimeException(e); }
        }

        // 3) overlay environment variables
        Map<String, String> envs = System.getenv();
        envs.forEach((k, v) -> { if (v != null && !v.isEmpty()) target.setProperty(k, v); });

        // 4) overlay system properties (-D...)
        System.getProperties().forEach((k, v) -> { if (k instanceof String && v != null) target.setProperty((String) k, v.toString()); });
    }

    public static String get(String key) { return props.getProperty(key); }

    public static String getBaseUrl() {
        // system property has highest precedence
        String sys = System.getProperty("web.baseUrl");
        if (sys != null && !sys.isEmpty()) return sys;

        // environment / .env.config keys (aliases) should override classpath defaults
        String alias = firstNonEmpty(props.getProperty("BASE_URL"), props.getProperty("BASEURL"), props.getProperty("base_url"));
        if (alias != null) return alias;

        // final fallback is classpath/web.property (or value set under that key)
        return props.getProperty("web.baseUrl");
    }

    public static String getApiBaseUrl() {
        String sys = System.getProperty("api.baseUrl");
        if (sys != null && !sys.isEmpty()) return sys;
        String alias = firstNonEmpty(props.getProperty("API_BASE_URL"), props.getProperty("api_base_url"));
        if (alias != null) return alias;
        return props.getProperty("api.baseUrl");
    }

    public static String getBrowser() {
        String sys = System.getProperty("browser");
        if (sys != null && !sys.isEmpty()) return sys;
        return firstNonEmpty(props.getProperty("BROWSER"), props.getProperty("browser"));
    }

    // added helper getters for common keys that may be present in .env.config
    public static String getEnv() { return props.getProperty("env", props.getProperty("ENV", "dev")); }
    public static String getUsername() { return props.getProperty("USERNAME", props.getProperty("username")); }
    public static String getPassword() { return props.getProperty("PASSWORD", props.getProperty("password")); }

    private static String firstNonEmpty(String... vals) {
        if (vals == null) return null;
        for (String v : vals) if (v != null && !v.isEmpty()) return v;
        return null;
    }
}
