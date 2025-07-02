package tests;

import org.testng.annotations.Test;
import utils.LoggerUtil;

import static org.testng.Assert.assertTrue;

public class CheckOutTest {
    @Test(dependsOnMethods = {"tests.LoginTest.testLoginSuccess"})
    public void addItemToCart() {
        LoggerUtil.info("CheckOutTest - addItemToCart");
        // Giả lập logic thêm sản phẩm vào giỏ

        boolean itemAdded = true; // Giả sử thêm vào giỏ thành công
        assertTrue(itemAdded, "CheckOutTest - addItemToCart - Thêm sản phẩm vào giỏ thất bại!");
    }
}
