package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    // Hằng số chứa tên file cấu hình. BẠN SẼ THAY ĐỔI GIÁ TRỊ NÀY KHI CHUYỂN MÔI TRƯỜNG
    private final static String CONFIG_FILE_NAME = "config-dev.properties";

    // Khối static để tải file properties khi ConfigReader được load lần đầu
    static {
        properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (inputStream == null) {
                System.err.println("Error: Configuration file '" + CONFIG_FILE_NAME + "' not found in classpath.");
                throw new RuntimeException("Configuration file '" + CONFIG_FILE_NAME + "' not found.");
            }
            properties.load(inputStream);
            System.out.println("Loaded configurations from: " + CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file: " + CONFIG_FILE_NAME, e);
        }
    }

    // Phương thức công khai để lấy giá trị của một thuộc tính
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Property '" + key + "' not found in " + CONFIG_FILE_NAME);
            throw new RuntimeException("Property '" + key + "' not found in " + CONFIG_FILE_NAME);
        }
        return value;
    }
}
