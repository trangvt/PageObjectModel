package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LoggerUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeTest
    @Parameters({"browser"})
    public void setup(String browserName) {
        // 1. Khởi tạo driver bằng DriverFactory, truyền tên trình duyệt nhận được từ testng.xml
        driver = DriverFactory.getDriver(browserName);

        // 2. Lấy baseUrl từ ConfigReader (ConfigReader đã tự động tải file properties đã chỉ định)
        this.baseUrl = ConfigReader.getProperty("base_url");
        LoggerUtil.info("BaseTest - setup - baseUrl: " + this.baseUrl);
    }

    @AfterTest
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
