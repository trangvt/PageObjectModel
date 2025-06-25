package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = "screenshots/" + testName + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(destination));
            LoggerUtil.info("Screenshot saved: " + destination);
        } catch (Exception e) {
            LoggerUtil.error("Failed to capture screenshot", e);
        }
    }
}
