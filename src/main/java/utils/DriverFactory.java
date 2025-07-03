package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    // Sử dụng ThreadLocal để hỗ trợ chạy song song
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    // Phương thức này nhận browserName làm tham số
    public static WebDriver getDriver(String browserName) {
        if (driver.get() == null) {
            initializeDriver(browserName);
        }
        return driver.get();
    }

    private static void initializeDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();

                // Cấu hình ChromeOptions
                ChromeOptions options = new ChromeOptions();
                if (System.getProperty("os.name").toLowerCase().contains("linux") && System.getenv("CI") != null) {
                    options.addArguments("--headless"); // Chạy ở chế độ headless (không GUI)
                    options.addArguments("--no-sandbox"); // Tránh lỗi quyền trên môi trường ảo
                    options.addArguments("--disable-dev-shm-usage"); // Tối ưu hóa tài nguyên
                    options.addArguments("--user-data-dir=/tmp/chrome-user-data-" + System.currentTimeMillis()); // Tránh xung đột thư mục dữ liệu
                    options.addArguments("--remote-debugging-port=9222");
                }

                driver.set(new ChromeDriver(options));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            case "edge": // Hỗ trợ Edge
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Browser " + browserName + " is not supported. Please choose 'chrome', 'firefox', or 'edge'.");
        }
        driver.get().manage().window().maximize();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Xóa WebDriver khỏi ThreadLocal
        }
    }
}
