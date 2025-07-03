package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = null;
        try {
            // Tìm field "driver" trong class hiện tại hoặc các class cha
            driver = getDriverFromTestClass(testClass);
            if (driver == null) {
                System.out.println("Not found WebDriver in Test Class.");
                return;
            }
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destination = new File("screenshots/" + result.getName() + "_" + timestamp + ".png");
            // Tạo thư mục nếu chưa tồn tại
            //destination.getParentFile().mkdirs();
            FileUtils.copyFile(source, destination);
            System.out.println("Saved screenshots: " + destination.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error] screenshots: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private WebDriver getDriverFromTestClass(Object testClass) throws IllegalAccessException {
        Class<?> clazz = testClass.getClass();
        while (clazz != null) {
            try {
                java.lang.reflect.Field driverField = clazz.getDeclaredField("driver");
                driverField.setAccessible(true); // Bỏ qua private/protected
                return (WebDriver) driverField.get(testClass);
            } catch (NoSuchFieldException e) {
                // Chuyển sang kiểm tra class cha
                clazz = clazz.getSuperclass();
            }
        }
        return null; // Không tìm thấy driver
    }
}
