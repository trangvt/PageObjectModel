package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        // Không cần làm gì ở đây cho mục đích này
    }

    @Override
    public void onFinish(ITestContext context) {
        Properties properties = new Properties();
        properties.setProperty("Base URL", ConfigReader.getProperty("base_url"));
        // Thêm các thuộc tính môi trường khác nếu cần

        try (FileWriter writer = new FileWriter("target/allure-results/environment.properties")) {
            properties.store(writer, "Allure Environment Information");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
