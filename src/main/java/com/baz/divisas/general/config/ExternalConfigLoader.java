package com.baz.divisas.general.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** Loads legacy properties and applies environment and local .env overrides. */
public final class ExternalConfigLoader {

    private static final String ENV_FILE = "APP_ENV_FILE";
    private static final Map<String, String> DOTENV = loadDotenv();

    private ExternalConfigLoader() {
        // Utility class.
    }

    public static Properties loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }

        for (String key : properties.stringPropertyNames()) {
            String value = resolve(key);
            if (value != null) {
                properties.setProperty(key, value);
            }
        }
        return properties;
    }

    public static String resolve(String key) {
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }
        value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        return DOTENV.get(key);
    }

    public static String resolvePath(String key, String fallback) {
        String value = resolve(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    private static Map<String, String> loadDotenv() {
        Map<String, String> values = new HashMap<>();
        String configuredPath = System.getenv(ENV_FILE);
        if (configuredPath == null || configuredPath.isBlank()) {
            configuredPath = System.getProperty(ENV_FILE, ".env");
        }

        Path path = Path.of(configuredPath);
        if (!Files.isRegularFile(path)) {
            return values;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                if (trimmed.startsWith("export ")) {
                    trimmed = trimmed.substring("export ".length()).trim();
                }

                int separator = trimmed.indexOf('=');
                if (separator <= 0) {
                    continue;
                }

                String key = trimmed.substring(0, separator).trim();
                String value = trimmed.substring(separator + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\""))
                        || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(key, value);
            }
        } catch (IOException ignored) {
            // Fall back to the legacy property file when .env is unavailable.
        }
        return values;
    }
}
