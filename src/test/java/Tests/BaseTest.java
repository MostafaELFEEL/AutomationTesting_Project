package Tests;

import Pages.BasePage;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static final String UserProfiles =  "userProfile";
    protected static final String falseUserProfiles = "falseUserProfile";
    protected static final String randomUserProfiles = "randomUserProfile";


    // --- Configuration Methods ---

    @BeforeMethod
    @Step("Setup: Launch browser and navigate to Home Page")
    public void beforeMethod() {
        BasePage page = new BasePage(); // ✅ FIXED: Local instance hooks into ThreadLocal
        // 1. Launch browser
        page.openBrowser();

        // 2. Navigate to url 'http://automationexercise.com'
        page = page.goToHomePage();

        // 3. Verify that home page is visible successfully
        Assert.assertEquals(page.actualURL(), page.expectedURL(), "Home page URL mismatch.");
    }

    @AfterMethod
    @Step("Teardown: Close browser")
    public void afterMethod() {
        BasePage page =  new BasePage(); // ✅ FIXED: Hooks into ThreadLocal
        page.takeScreenShot();
        page.closeBrowser();
    }


}
