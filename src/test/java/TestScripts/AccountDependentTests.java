package TestScripts;

import Pages.*;
import TestDataProvider.TestDataProvider;
import Utils.HelperPack.HelperClass;
import Utils.PojoPack.UserProfile;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AccountDependentTests extends BaseTest {

    private List<UserProfile> testUsers;

    // --- Configuration Methods ---

    @BeforeClass(description = "Setup: Create accounts for all profiles to be used in dependent tests")
    public void setUp() {
        // Load all profiles from JSON
        testUsers = HelperClass.readArrayFromFile("userProfiles.json", UserProfile.class);

        // Create accounts for all profiles
        for (UserProfile user : testUsers) {
            BasePage page = new BasePage(); // ✅ FIXED: Local instance for setup thread
            page.openBrowser();
            
            page = page.goToHomePage();
            Assert.assertEquals(page.expectedURL(), page.actualURL(), "Home page URL mismatch during setup.");

            page = page.goToLoginPage();
            page = ((LoginPage) page).performSignup(user);
            page = ((SignupPage) page).performSignup(user);
            page = ((AccountCreatedPage) page).pressContinueButton();

            page.closeBrowser();
        }
    }

    @AfterClass(description = "Teardown: Delete all accounts created during setup")
    public void tearDown() {
        for (UserProfile user : testUsers) {
            BasePage page = new BasePage(); // ✅ FIXED: Local instance for teardown thread
            page.openBrowser();
            
            page = page.goToHomePage();
            Assert.assertEquals(page.actualURL(), page.expectedURL(), "Home page URL mismatch during teardown.");

            page = page.goToLoginPage();
            page = ((LoginPage) page).performLogin(user);
            page = page.performDeleteAccount();
            page = ((AccountDeletedPage) page).pressContinueButton();

            page.closeBrowser();
        }
    }

    @BeforeMethod(description = "Setup: Launch browser and navigate to home page")
    public void beforeMethod() {
        BasePage page = new BasePage(); // ✅ FIXED: Local instance hooks into ThreadLocal
        // 1. Launch browser
        page.openBrowser();

        // 2. Navigate to url 'http://automationexercise.com'
        page = page.goToHomePage();

        // 3. Verify that home page is visible successfully
        Assert.assertEquals(page.actualURL(), page.expectedURL(), "Home page URL mismatch.");
    }

    @AfterMethod(description = "Teardown: Close browser")
    public void afterMethod() {
        BasePage page = new BasePage(); // ✅ FIXED: Local instance hooks into ThreadLocal
        page.takeScreenShot();
        page.closeBrowser();
    }

    // --- Helper Methods ---

    @Step("Step: Perform login for user: {userProfile.name}")
    public void performLogin(BasePage page, UserProfile userProfile) { // ✅ FIXED: Accept page parameter
        // 4. Click on 'Signup / Login' button
        page = page.goToLoginPage();

        // 5. Verify 'Login to your account' is visible
        Assert.assertEquals(
                ((LoginPage) page).getLoginText(),
                "Login to your account",
                "Login header text mismatch."
        );

        // 6. Enter correct email address and password
        // 7. Click 'login' button
        page = ((LoginPage) page).performLogin(userProfile);

        // 8. Verify that 'Logged in as username' is visible
        Assert.assertEquals(
                page.getLoggedinAsNameText(),
                "Logged in as " + userProfile.getName(),
                "Logged in user text mismatch."
        );

        // Verify navigation back to the expected URL after login
        Assert.assertEquals(page.expectedURL(), page.actualURL(), "URL mismatch after successful login.");
    }

    // --- Test Cases ---

    @Test(
            groups = {"User Authentication & Account Management"},
            dataProvider = UserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 2: Login User with correct email and password - Verifies that a registered user can log in successfully.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "Test Case 2: Valid Login", url = "https://automationexercise.com/test_cases#2")
    @Issue("AUTH-02")
    @TmsLink("TC-02")
    @Epic("User Authentication & Account Management")
    @Feature("Login Functionality")
    @Story("Valid User Login")
    public void TC02(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Create local page object

        // Execute steps 4 through 8 of the login flow
        performLogin(page, userProfile); // ✅ FIXED: Pass page object

        // Note: Steps 9 & 10 (Delete Account & Verify Deletion) are handled globally
    }


    @Test(
            groups = {"User Authentication & Account Management"},
            dataProvider = falseUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 3: Login User with incorrect email and password - Verifies that an error message is displayed when attempting to log in with invalid credentials.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 3: Invalid Login", url = "https://automationexercise.com/test_cases#3")
    @Issue("AUTH-03")
    @TmsLink("TC-03")
    @Epic("User Authentication & Account Management")
    @Feature("Login Functionality")
    @Story("Invalid User Login")
    public void TC03(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Create local page object

        // 4. Click on 'Signup / Login' button
        page = page.goToLoginPage();

        // 5. Verify 'Login to your account' is visible
        Assert.assertEquals(
                ((LoginPage) page).getLoginText(),
                "Login to your account",
                "Login header text mismatch."
        );

        // 6. Enter incorrect email address and password
        // 7. Click 'login' button
        ((LoginPage) page).performFalseLogin(userProfile);

        // 8. Verify error 'Your email or password is incorrect!' is visible
        Assert.assertEquals(
                ((LoginPage) page).getFalseLoginText(),
                "Your email or password is incorrect!",
                "Invalid login error message mismatch."
        );

        // Verify the user remains on the login page after a failed attempt
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "URL mismatch after failed login attempt."
        );
    }



    @Test(
            groups = {"User Authentication & Account Management"},
            dataProvider = UserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 4: Logout User - Verifies that a logged-in user can successfully log out and is redirected to the login page.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 4: Logout User", url = "https://automationexercise.com/test_cases#4")
    @Issue("AUTH-04")
    @TmsLink("TC-04")
    @Epic("User Authentication & Account Management")
    @Feature("Logout Functionality")
    @Story("Valid User Logout")
    public void TC04(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Create local page object

        // Steps 4-8: Click login, verify header, enter valid credentials, click login, and verify logged in state
        performLogin(page, userProfile); // ✅ FIXED: Pass page object

        // 9. Click 'Logout' button
        page = page.performLogout();

        // 10. Verify that user is navigated to login page
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the login page after logging out."
        );
    }

    @Test(
            groups = {"User Authentication & Account Management"},
            dataProvider = UserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 5: Register User with existing email - Verifies that an error message is displayed when attempting to register with an already existing email.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 5: Register User with existing email", url = "https://automationexercise.com/test_cases#5")
    @Issue("AUTH-05")
    @TmsLink("TC-05")
    @Epic("User Authentication & Account Management")
    @Feature("User Registration")
    @Story("Invalid Registration (Existing Email)")
    public void TC05(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Create local page object

        // 4. Click on 'Signup / Login' button
        page = page.goToLoginPage();

        // 5. Verify 'New User Signup!' is visible
        Assert.assertEquals(
                ((LoginPage) page).getSignupText(),
                "New User Signup!",
                "Signup header text mismatch."
        );

        // 6. Enter name and already registered email address
        // 7. Click 'Signup' button
        ((LoginPage) page).performFalseSignup(userProfile);

        // 8. Verify error 'Email Address already exist!' is visible
        Assert.assertEquals(
                ((LoginPage) page).getFalseSignupText(),
                "Email Address already exist!",
                "Existing email error message mismatch."
        );
    }

@Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = UserProfiles, // Standardized lowercase 'u' based on previous context
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 16: Place Order: Login before Checkout - Verify that a user can log in first, add products, and complete the checkout process seamlessly.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 16: Place Order: Login before Checkout", url = "https://automationexercise.com/test_cases#16")
    @Issue("CART-16")
    @TmsLink("TC-16")
    @Epic("Cart & Checkout Workflow")
    @Feature("Checkout Process")
    @Story("Place Order (Login before Checkout)")
    public void TC16(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Thread-local page instantiation

        // Steps 1-6: Launch browser, navigate, verify home page, click login, enter details, and verify logged-in state
        // (Handled by the @BeforeMethod and your performLogin helper method)
        performLogin(page, userProfile); // ✅ FIXED: Pass the local page object

        // 7. Add products to cart
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton();
        page.clickProductAddToCartButton("2");
        page.clickContinueShoppingButton();

        // 8. Click 'Cart' button
        page = page.goToCartsPage();

        // 9. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 10. Click Proceed To Checkout
        page = ((CartsPage) page).clickProceedToCheckoutButtonWhileRegistered();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Checkout page."
        );

        // 11. Verify Address Details and Review Your Order
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressDeliveryText(),
                userProfile.getAddress(),
                "Delivery address in checkout does not match the logged-in user profile."
        );


        // 12. Enter description in comment text area and click 'Place Order'
        ((CheckoutPage) page).comment("edges");
        //page.takeScreenShot();

        page = ((CheckoutPage) page).clickPlaceOrderButton();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Payment page."
        );

        // 13. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        ((PaymentPage) page).enterPaymentDetails(userProfile);


        // 14. Click 'Pay and Confirm Order' button
        page = ((PaymentPage) page).clickPayAndConfirmButton();
        //page.takeScreenShot();

        // 15. Verify success message 'Your order has been placed successfully!'
        Assert.assertEquals(
                ((PaymentDonePage) page).getOrderPlacedText(),
                "ORDER PLACED!",
                "Order placement success message mismatch."
        );

        // Steps 16 & 17: Click 'Delete Account' button and Verify 'ACCOUNT DELETED!'
        // Note: Handled globally in the @AfterClass tearDown() method to preserve the test accounts for other dependent methods.
    }

    @Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = UserProfiles, // Standardized to match the setup in AccountDependentTests
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 20: Search Products and Verify Cart After Login - Verify that products added to the cart while logged out are retained after logging in.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 20: Cart Retention", url = "https://automationexercise.com/test_cases#20")
    @Issue("CART-20")
    @TmsLink("TC-20")
    @Epic("Cart & Checkout Workflow")
    @Feature("Cart Retention")
    @Story("Persist Cart After Login")
    public void TC20(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Thread-local page instantiation

        // Steps 1 & 2: Launch browser and navigate to url (Handled in BeforeMethod)

        // 3. Click on 'Products' button
        page = page.goToProductsPage();

        // 4. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Products page."
        );

        // 5. Enter product name in search input and click search button
        ((ProductsPage) page).searchProducts("Blue Top");

        // 6. Verify 'SEARCHED PRODUCTS' is visible
        Assert.assertEquals(
                page.getTitleText(),
                "SEARCHED PRODUCTS",
                "Searched products header mismatch."
        );

        // 7. Verify all the products related to search are visible
        //page.takeScreenShot();

        // 8. Add those products to cart
        // (Reusing the click actions mapped from previous tests)
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton(); // Closes the modal

        // 9. Click 'Cart' button and verify that products are visible in cart
        page = page.goToCartsPage();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // Verifying the product exists before login
        Assert.assertEquals(
                ((CartsPage) page).getProductName("1"),
                "Blue Top",
                "Product 'Blue Top' is not visible in the cart before login."
        );

        // 10. Click 'Signup / Login' button and submit login details
        // Utilizing the performLogin helper method which handles navigation to login and submitting details
        performLogin(page, userProfile); // ✅ FIXED: Pass the local page object

        // 11. Again, go to Cart page
        page = page.goToCartsPage();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page after login."
        );

        // 12. Verify that those products are visible in cart after login as well
        Assert.assertEquals(
                ((CartsPage) page).getProductName("1"),
                "Blue Top",
                "Product 'Blue Top' is not retained in the cart after login."
        );
    }
}
