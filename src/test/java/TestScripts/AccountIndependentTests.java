package TestScripts;

import Pages.*;
import TestDataProvider.TestDataProvider;
import Utils.PojoPack.UserProfile;
import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.*;

public class AccountIndependentTests extends BaseTest {

    // --- Configuration Methods ---

    @BeforeMethod
    @Step("Setup: Launch browser and navigate to Home Page")
    public void beforeMethod() {
        BasePage page =  new BasePage(); // ✅ FIXED: Hooks into ThreadLocal
        page.openBrowser();
        page = page.goToHomePage();
        // 3. Verify that home page is visible successfully
        Assert.assertEquals(page.actualURL(), page.expectedURL(), "Home page URL mismatch");
    }

    @AfterMethod
    @Step("Teardown: Close browser")
    public void afterMethod() {
        BasePage page =  new BasePage(); // ✅ FIXED: Hooks into ThreadLocal
        page.takeScreenShot();
        page.closeBrowser();
    }

    // --- Helper Methods ---

    @Step("Step: Create a new account with user: {userProfile.name}")
    public BasePage createAccount(BasePage page, UserProfile userProfile) { // ✅ FIXED: Accept & Return Page
        // 4. Click on 'Signup / Login' button
        page = page.goToLoginPage();

        // 5. Verify 'New User Signup!' is visible
        Assert.assertEquals(((LoginPage) page).getSignupText(), "New User Signup!", "Signup header text mismatch");

        // 6. Enter name and email address & 7. Click 'Signup' button
        page = ((LoginPage) page).performSignup(userProfile);
        Assert.assertEquals(page.actualURL(), page.expectedURL(), "Signup page URL mismatch");
        
        // 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
        Assert.assertEquals(((SignupPage) page).getEnterAAccountInfoText(), "ENTER ACCOUNT INFORMATION", "Account info header mismatch");

        // 9-13. Fill details and Click 'Create Account button'
        page = ((SignupPage) page).performSignup(userProfile);
        Assert.assertEquals(page.actualURL(), page.expectedURL(), "Account Created page URL mismatch");
        
        // 14. Verify that 'ACCOUNT CREATED!' is visible
        Assert.assertEquals(((AccountCreatedPage) page).getAccountCreatedText(), "ACCOUNT CREATED!", "Account created message mismatch");

        // 15. Click 'Continue' button
        page = ((AccountCreatedPage) page).pressContinueButton();

        // 16. Verify that 'Logged in as username' is visible
        Assert.assertEquals(page.getLoggedinAsNameText(), "Logged in as " + userProfile.getName(), "Logged in user text mismatch");
        
        return page; // ✅ FIXED: Return the state so the test can keep going!
    }

    @Step("Step: Delete the current user account")
    public BasePage deleteAccount(BasePage page) { // ✅ FIXED: Accept & Return Page
        // 17. Click 'Delete Account' button
        page = page.performDeleteAccount();

        // 18. Verify that 'ACCOUNT DELETED!' is visible
        Assert.assertEquals(((AccountDeletedPage) page).getAccountDeletedText(), "ACCOUNT DELETED!", "Account deleted message mismatch");

        // Click 'Continue' button
        page = ((AccountDeletedPage) page).pressContinueButton();

        // Final URL verification
        Assert.assertEquals(page.actualURL(), "https://automationexercise.com/", "Final navigation to home page failed");
        
        return page; // ✅ FIXED: Return state
    }

    // --- Test Cases ---

    @Test(
            groups = {"User Authentication & Account Management"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Retry Analyzer
    )
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Case 1: Register User - Verify that home page is visible, new user can signup, account created, logged in, and account deleted.")
    @Link(name = "Test Case 1", url = "https://automationexercise.com/test_cases#1")
    @Issue("AUTH-01")
    @TmsLink("TC-01")
    @Epic("User Authentication & Account Management")
    @Feature("User Registration")
    @Story("Register User Flow")
    public void TC01(UserProfile userProfile) {
        BasePage page = new BasePage(); // ✅ FIXED: Create local page

        // Execute registration steps (Pass and re-assign the page)
        page = createAccount(page, userProfile);

        // Execute deletion steps
        page = deleteAccount(page);
    }

    @Test(
            groups = {"Communication & Marketing"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Retry Analyzer
    )
    @Description("Test Case 6: Contact Us Form - Verify that a user can successfully submit a contact inquiry with a file upload.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 6: Contact Us Form", url = "https://automationexercise.com/test_cases#6")
    @Issue("CNT-06")
    @TmsLink("TC-06")
    @Epic("Communication & Marketing")
    @Feature("Contact Support")
    @Story("Submit Contact Us Inquiry")
    public void TC06(UserProfile userProfile) {
        BasePage page =  new BasePage();

        // 4. Click on 'Contact Us' button
        page = page.goToContactUsPage();

        // 5. Verify 'GET IN TOUCH' is visible
        Assert.assertEquals(
                ((ContactUsPage) page).getGetInTouchText(),
                "GET IN TOUCH",
                "Contact page header 'GET IN TOUCH' is incorrect."
        );

        // 6. Enter name, email, subject and message
        // 7. Upload file
        // 8. Click 'Submit' button
        // 9. Click OK button (Handled within fillContactUsForm or subsequent page action)
        ((ContactUsPage) page).fillContactUsForm(userProfile);

        // 10. Verify success message 'Success! Your details have been submitted successfully.' is visible
        Assert.assertEquals(
                ((ContactUsPage) page).getSuccessMessageText(),
                "Success! Your details have been submitted successfully.",
                "Success message text mismatch."
        );

        // 11. Click 'Home' button and verify that landed to home page successfully
        page = ((ContactUsPage) page).clickHomeButton();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to return to Home Page after submitting contact form."
        );
    }

    @Test(
            groups = {"UI/UX & Site Navigation"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Retry Analyzer
    )
    @Description("Test Case 7: Verify Test Cases Page - Verify that the user is navigated to the test cases page successfully.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 7: Verify Test Cases Page", url = "https://automationexercise.com/test_cases#7")
    @Issue("NAV-07")
    @TmsLink("TC-07")
    @Epic("UI/UX & Site Navigation")
    @Feature("Navigation")
    @Story("Access Test Cases Page")
    public void TC07() {
        BasePage page =  new BasePage();
        
        // 4. Click on 'Test Cases' button
        page = page.goToTestCasesPage();

        // 5. Verify user is navigated to test cases page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Test Cases page."
        );
    }

@Test(
            groups = {"Product Discovery & Interaction"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 8: Verify All Products and product detail page - Verify navigation to products list and product detail visibility.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 8: Verify All Products", url = "https://automationexercise.com/test_cases#8")
    @Issue("PROD-08")
    @TmsLink("TC-08")
    @Epic("Product Discovery & Interaction")
    @Feature("Product Browsing")
    @Story("View Product Details")
    public void TC08() {

        BasePage page =  new BasePage();
        // 4. Click on 'Products' button
        page = page.goToProductsPage();

        // 5. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Products page."
        );

        // 6. The products list is visible
        Assert.assertEquals(
                page.getTitleText(),
                "ALL PRODUCTS",
                "Products page header mismatch."
        );

        // 7. Click on 'View Product' of first product
        page = page.goToProductDetailPage("1");

        // 8. User is landed to product detail page
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Product 1 detail page."
        );

        // 9. Verify that detail is visible: product name, category, price, availability, condition, brand
        ProductDetailPage detailPage = (ProductDetailPage) page;

        Assert.assertEquals(detailPage.getProductName(), "Blue Top", "Product Name mismatch.");
        Assert.assertEquals(detailPage.getCategoryText(), "Category: Women > Tops", "Category text mismatch.");
        Assert.assertEquals(detailPage.getPriceText(), "Rs. 500", "Price text mismatch.");
        Assert.assertEquals(detailPage.getAvailabilityText(), "Availability:", "Availability text mismatch.");
        Assert.assertEquals(detailPage.getConditionText(), "Condition:", "Condition text mismatch.");
        Assert.assertEquals(detailPage.getBrandText(), "Brand:", "Brand text mismatch.");
    }

    @Test(
            groups = {"Product Discovery & Interaction"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 9: Search Product - Verify that users can search for a product and see 'SEARCHED PRODUCTS' results.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 9: Search Product", url = "https://automationexercise.com/test_cases#9")
    @Issue("PROD-09")
    @TmsLink("TC-09")
    @Epic("Product Discovery & Interaction")
    @Feature("Search Functionality")
    @Story("Search for Products")
    public void TC09() {

        BasePage page =  new BasePage();
        // 4. Click on 'Products' button
        page = page.goToProductsPage();

        // 5. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Products page."
        );

        Assert.assertEquals(
                page.getTitleText(),
                "ALL PRODUCTS",
                "Products page header mismatch."
        );

        // 6. Enter product name in search input and click search button
        ((ProductsPage) page).searchProducts("Blue Top");

        // 7. Verify 'SEARCHED PRODUCTS' is visible
        Assert.assertEquals(
                page.getTitleText(),
                "SEARCHED PRODUCTS",
                "Searched Products header mismatch."
        );


        // 8. Verify all the products related to search are visible
        //page.takeScreenShot(); // Visual validation of search results
    }


    @Test(
            groups = {"Communication & Marketing"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 10: Verify Subscription in home page - Verify that a user can subscribe to the newsletter from the home page footer.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 10: Subscription", url = "https://automationexercise.com/test_cases#10")
    @Issue("MKT-10")
    @TmsLink("TC-10")
    @Epic("Communication & Marketing")
    @Feature("Newsletter Subscription")
    @Story("Subscribe via Home Page")
    public void TC10(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // 4. Scroll down to footer
        page.scrollToFooter();

        // 5. Verify text 'SUBSCRIPTION'
        Assert.assertEquals(
                page.getSubscriptionText(),
                "SUBSCRIPTION",
                "Footer subscription header mismatch."
        );

        // 6. Enter email address in input and click arrow button
        page.preformSubscribe(userProfile);

        // 7. Verify success message 'You have been successfully subscribed!' is visible
        Assert.assertEquals(
                page.getSubscribeAlertText(),
                "You have been successfully subscribed!",
                "Subscription success message mismatch."
        );
    }
@Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 11: Verify Subscription in Cart page - Verify that a user can subscribe to the newsletter from the Cart page footer.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 11: Verify Subscription in Cart page", url = "https://automationexercise.com/test_cases#11")
    @Issue("MKT-11")
    @TmsLink("TC-11")
    @Epic("Cart & Checkout Workflow")
    @Feature("Newsletter Subscription")
    @Story("Subscribe via Cart Page")
    public void TC11(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // 4. Click 'Cart' button
        page = page.goToCartsPage();

        // Verify user is navigated to cart page
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 5. Scroll down to footer
        page.scrollToFooter();

        // 6. Verify text 'SUBSCRIPTION'
        Assert.assertEquals(
                page.getSubscriptionText(),
                "SUBSCRIPTION",
                "Footer subscription header mismatch."
        );

        // 7. Enter email address in input and click arrow button
        page.preformSubscribe(userProfile);

        // 8. Verify success message 'You have been successfully subscribed!' is visible
        Assert.assertEquals(
                page.getSubscribeAlertText(),
                "You have been successfully subscribed!",
                "Subscription success message mismatch."
        );

    }


    @Test(
            groups = {"Cart & Checkout Workflow"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 12: Add Products in Cart - Verify that products can be added to the cart and their details (price, quantity, total) are correct.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 12: Add Products in Cart", url = "https://automationexercise.com/test_cases#12")
    @Issue("CART-12")
    @TmsLink("TC-12")
    @Epic("Cart & Checkout Workflow")
    @Feature("Cart Management")
    @Story("Add Multiple Items to Cart")
    public void TC12() {

        BasePage page =  new BasePage();
        // 4. Click 'Products' button
        page = page.goToProductsPage();

        // 2. Navigate to url 'http://automationexercise.com' (Verified via Products page transition)
        // 5. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Products page."
        );

        // 5. Hover over first product and click 'Add to cart'
        // (Assuming helper methods handle the hover+click action)
        page.clickProductAddToCartButton("1");

        // 6. Click 'Continue Shopping' button
        page.clickContinueShoppingButton();

        // 7. Hover over second product and click 'Add to cart'
        page.clickProductAddToCartButton("2");

        // 8. Click 'View Cart' button
        // (Note: The user might click 'Continue Shopping' then manually go to cart, or click 'View Cart' from modal)
        page.clickContinueShoppingButton(); // Closing the modal to navigate cleanly
        page = page.goToCartsPage();

        // 9. Verify both products are added to Cart
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        CartsPage cartPage = (CartsPage) page;

        // 10. Verify their prices, quantity and total price

        // --- Product 1 Verification (Blue Top) ---
        Assert.assertEquals(cartPage.getProductName("1"), "Blue Top", "Product 1 Name mismatch.");
        Assert.assertEquals(cartPage.getProductPrice("1"), "Rs. 500", "Product 1 Price mismatch.");
        Assert.assertEquals(cartPage.getProductQuantity("1"), "1", "Product 1 Quantity mismatch.");
        Assert.assertEquals(cartPage.getProductTotalPrice("1"), "Rs. 500", "Product 1 Total Price mismatch.");

        // --- Product 2 Verification (Men Tshirt) ---
        Assert.assertEquals(cartPage.getProductName("2"), "Men Tshirt", "Product 2 Name mismatch.");
        Assert.assertEquals(cartPage.getProductPrice("2"), "Rs. 400", "Product 2 Price mismatch.");
        Assert.assertEquals(cartPage.getProductQuantity("2"), "1", "Product 2 Quantity mismatch.");
        Assert.assertEquals(cartPage.getProductTotalPrice("2"), "Rs. 400", "Product 2 Total Price mismatch.");
    }



    @Test(
            groups = {"Cart & Checkout Workflow"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 13: Verify Product quantity in Cart - Verify that adding a product with increased quantity updates the cart correctly.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 13: Verify Product quantity in Cart", url = "https://automationexercise.com/test_cases#13")
    @Issue("CART-13")
    @TmsLink("TC-13")
    @Epic("Cart & Checkout Workflow")
    @Feature("Cart Management")
    @Story("Modify Product Quantity")
    public void TC13() {

        BasePage page =  new BasePage();
        // 4. Click 'View Product' for any product on home page
        page = page.goToProductDetailPage("1");

        // 5. Verify product detail is opened
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Product 1 detail page."
        );

        // 6. Increase quantity to 4
        ((ProductDetailPage) page).increaseQuantity("4");

        // 7. Click 'Add to cart' button
        ((ProductDetailPage) page).clickAddToCartButton();

        // 8. Click 'View Cart' button
        // (Closing the success modal first to navigate via header/menu if necessary, or navigating directly)
        page.clickContinueShoppingButton();
        page = page.goToCartsPage();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 9. Verify that product is displayed in cart page with exact quantity
        Assert.assertEquals(
                ((CartsPage) page).getProductQuantity("1"),
                "4",
                "Product quantity in cart does not match the added amount."
        );

    }
@Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 14: Place Order: Register while Checkout - Verify that a user can register during the checkout process and complete the order.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 14: Place Order: Register while Checkout", url = "https://automationexercise.com/test_cases#14")
    @Issue("CART-14")
    @TmsLink("TC-14")
    @Epic("Cart & Checkout Workflow")
    @Feature("Checkout Process")
    @Story("Place Order (Register during Checkout)")
    public void TC14(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // 4. Add products to cart
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton();
        page.clickProductAddToCartButton("2");
        page.clickContinueShoppingButton();

        // 5. Click 'Cart' button
        page = page.goToCartsPage();

        // 6. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 7. Click Proceed To Checkout
        ((CartsPage) page).clickProceedToCheckoutButtonWhileNotRegistered();

        // 8. Click 'Register / Login' button (Modal interaction)
        page = ((CartsPage) page).clickCheckoutLoginButton();

        // 9. Fill all details in Signup and create account
        page = ((LoginPage) page).performSignup(userProfile); // Enter Name/Email
        page = ((SignupPage) page).performSignup(userProfile); // Enter Address/Details

        // 10. Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertEquals(
                ((AccountCreatedPage) page).getAccountCreatedText(),
                "ACCOUNT CREATED!",
                "Account creation success message mismatch."
        );

        page = ((AccountCreatedPage) page).pressContinueButton();

        // 11. Verify ' Logged in as username' at top
        Assert.assertEquals(
                page.getLoggedinAsNameText(),
                "Logged in as " + userProfile.getName(),
                "Logged in user text mismatch."
        );

        // 12. Click 'Cart' button
        page = page.goToCartsPage();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to return to Cart page."
        );

        // 13. Click 'Proceed To Checkout' button
        page = ((CartsPage) page).clickProceedToCheckoutButtonWhileRegistered();

        // 14. Verify Address Details and Review Your Order
        // Note: Assuming getAddressDeliveryText returns the specific line matching the user profile address
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressDeliveryText(),
                userProfile.getAddress(),
                "Delivery address in checkout does not match user profile."
        );



        // 15. Enter description in comment text area and click 'Place Order'
        ((CheckoutPage) page).comment("edges");


        page = ((CheckoutPage) page).clickPlaceOrderButton();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Payment page."
        );

        // 16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        ((PaymentPage) page).enterPaymentDetails(userProfile);


        // 17. Click 'Pay and Confirm Order' button
        page = ((PaymentPage) page).clickPayAndConfirmButton();
        //page.takeScreenShot();

        // 18. Verify success message 'Your order has been placed successfully!'
        // Using getOrderPlacedText as per previous logic, asserting strict equality
        Assert.assertEquals(
                ((PaymentDonePage) page).getOrderPlacedText(),
                "ORDER PLACED!",
                "Order placement success message mismatch."
        );

        page = ((PaymentDonePage) page).clickContinueButton();

        // 19. Click 'Delete Account' button
        // 20. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        page = deleteAccount(page); // ✅ FIXED: Pass and re-assign the local page object!
    }



    @Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 15: Place Order: Register before Checkout - Verify that a user can register an account first, then add products and complete the checkout process.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 15: Place Order: Register before Checkout", url = "https://automationexercise.com/test_cases#15")
    @Issue("CART-15")
    @TmsLink("TC-15")
    @Epic("Cart & Checkout Workflow")
    @Feature("Checkout Process")
    @Story("Place Order (Register before Checkout)")
    public void TC15(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // Steps 1-7: Launch browser, Navigate to URL, Sign up, Verify Account Created & Login
        page = createAccount(page, userProfile); // ✅ FIXED: Pass and re-assign the local page object!

        // 8. Add products to cart
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton();
        page.clickProductAddToCartButton("2");
        page.clickContinueShoppingButton();

        // 9. Click 'Cart' button
        page = page.goToCartsPage();

        // 10. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 11. Click Proceed To Checkout
        page = ((CartsPage) page).clickProceedToCheckoutButtonWhileRegistered();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Checkout page."
        );

        // 12. Verify Address Details and Review Your Order
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressDeliveryText(),
                userProfile.getAddress(),
                "Delivery address in checkout does not match the registered user profile."
        );



        // 13. Enter description in comment text area and click 'Place Order'
        ((CheckoutPage) page).comment("edges");
        //page.takeScreenShot();

        page = ((CheckoutPage) page).clickPlaceOrderButton();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Payment page."
        );

        // 14. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        ((PaymentPage) page).enterPaymentDetails(userProfile);


        // 15. Click 'Pay and Confirm Order' button
        page = ((PaymentPage) page).clickPayAndConfirmButton();
        //page.takeScreenShot();

        // 16. Verify success message 'Your order has been placed successfully!'
        Assert.assertEquals(
                ((PaymentDonePage) page).getOrderPlacedText(),
                "ORDER PLACED!",
                "Order placement success message mismatch."
        );

        // Steps 17-18: Delete Account and Verify Deletion
        page = deleteAccount(page); // ✅ FIXED: Pass and re-assign the local page object!
    }

@Test(
            groups = {"Cart & Checkout Workflow"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 17: Remove Products From Cart - Verify that products can be successfully removed from the cart.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 17: Remove Products From Cart", url = "https://automationexercise.com/test_cases#17")
    @Issue("CART-17")
    @TmsLink("TC-17")
    @Epic("Cart & Checkout Workflow")
    @Feature("Cart Management")
    @Story("Remove Items")
    public void TC17() {

        BasePage page =  new BasePage();
        // 4. Add products to cart
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton();
//        page.clickProductAddToCartButton("2");
//        page.clickContinueShoppingButton();

        // 5. Click 'Cart' button
        page = page.goToCartsPage();

        // 6. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // Workaround: Click at (0,0) to reset focus or handle UI overlay interference
        page.clickAtZeroZero();

        // 7. Click 'X' button corresponding to particular product
        // Removing first product

                ((CartsPage) page).clickXButton();



        // Workaround: Click at (0,0) to reset focus or handle UI overlay interference
        page.clickAtZeroZero();

//        // Removing second product
//        ((CartsPage) page).clickXButton();

        // 8. Verify that product is removed from the cart
        // Verifying the cart is completely empty after removing both items
        Assert.assertEquals(
                ((CartsPage) page).getCartIsEmptyText(),
                "Cart is empty!",
                "Cart empty message mismatch."
        );
    }


    @Test(
            groups = {"Product Discovery & Interaction"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 18: View Category Products - Verify that products can be filtered by Category and Sub-category (Women/Men).")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 18: View Category Products", url = "https://automationexercise.com/test_cases#18")
    @Issue("CAT-18")
    @TmsLink("TC-18")
    @Epic("Product Discovery & Interaction")
    @Feature("Category Filtering")
    @Story("Navigate Categories")
    public void TC18() {
        BasePage page =  new BasePage();

        // 3. Verify that categories are visible on left sidebar
        Assert.assertEquals(
                page.getCategoryText(),
                "CATEGORY",
                "Category sidebar header mismatch."
        );

        // Workaround: Reset focus/close overlay
        page.clickAtZeroZero();

        // 4. Click on 'Women' category
        page.clickCategory("Women");

        // Workaround: Reset focus/close overlay
        page.clickAtZeroZero();

        // 5. Click on any category link under 'Women' category, for example: Dress (Using Tops here based on code)
        page = page.clickCategoryProducts("2");

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Category Products page."
        );

        // 6. Verify that category page is displayed and confirm text 'WOMEN - TOPS PRODUCTS'
        Assert.assertEquals(
                page.getTitleText(),
                "WOMEN - TOPS PRODUCTS",
                "Women - Tops Category page title mismatch."
        );

        // Workaround: Reset focus/close overlay
        page.clickAtZeroZero();

        // 7. On left sidebar, click on any sub-category link of 'Men' category
        page.clickCategory("Men");

        // Workaround: Reset focus/close overlay
        page.clickAtZeroZero();

        page = page.clickCategoryProducts("3");

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Category Products page."
        );

        // 8. Verify that user is navigated to that category page
        Assert.assertEquals(
                page.getTitleText(),
                "MEN - TSHIRTS PRODUCTS",
                "Men - Tshirts Category page title mismatch."
        );
    }

   @Test(
            groups = {"Product Discovery & Interaction"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 19: View & Cart Brand Products - Verify that products can be filtered by Brands via the left sidebar.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 19: View & Cart Brand Products", url = "https://automationexercise.com/test_cases#19")
    @Issue("BRND-19")
    @TmsLink("TC-19")
    @Epic("Product Discovery & Interaction")
    @Feature("Brand Filtering")
    @Story("Navigate Brands")
    public void TC19() {
        BasePage page =  new BasePage();

        // 3. Click on 'Products' button
        page = page.goToProductsPage();

        // Verify navigation to Products page
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Products page."
        );

        // 4. Verify that Brands are visible on left sidebar
        Assert.assertEquals(
                page.getBrandsText(),
                "BRANDS",
                "Brands sidebar header mismatch."
        );

        // 5. Click on any brand name (e.g., Polo)
        page = page.clickBrand("Polo");

        // 6. Verify that user is navigated to brand page and brand products are displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Polo brand page."
        );

        // 7. On left sidebar, click on any other brand link (e.g., Madame)
        page = page.clickBrand("Madame");

        // 8. Verify that user is navigated to that brand page and can see products
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Madame brand page."
        );
    }

    @Test(
            groups = {"Product Discovery & Interaction"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 21: Add review on product - Verify that a user can successfully submit a review on a product's detail page.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 21: Add review on product", url = "https://automationexercise.com/test_cases#21")
    @Issue("PROD-21")
    @TmsLink("TC-21")
    @Epic("Product Discovery & Interaction")
    @Feature("Product Reviews")
    @Story("Submit a Review")
    public void TC21(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // 3. Click on 'Products' button
        page = page.goToProductsPage();

        // 4. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Products page."
        );

        // 5. Click on 'View Product' button (Navigating to Product 1 detail page)
        page = page.goToProductDetailPage("1");

        // Verify navigation to the product detail page
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to the Product Details page."
        );

        // 6. Verify 'Write Your Review' is visible
        Assert.assertEquals(
                ((ProductDetailPage) page).getWriteYourReviewText(),
                "WRITE YOUR REVIEW",
                "Write your review header mismatch."
        );

        // 7. Enter name, email and review
        // 8. Click 'Submit' button
        ((ProductDetailPage) page).makeReview(userProfile, "edges");

        // 9. Verify success message 'Thank you for your review.'
        Assert.assertEquals(
                ((ProductDetailPage) page).getSuccessMessage(),
                "Thank you for your review.",
                "Review submission success message mismatch."
        );
    }

    @Test(
            groups = {"Product Discovery & Interaction"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 22: Add to cart from Recommended items - Verify that a user can successfully add a product to their cart from the Recommended Items section at the bottom of the home page.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 22: Add to cart from Recommended items", url = "https://automationexercise.com/test_cases#22")
    @Issue("PROD-22")
    @TmsLink("TC-22")
    @Epic("Product Discovery & Interaction")
    @Feature("Recommended Items")
    @Story("Add Recommended Item to Cart")
    public void TC22() {
        BasePage page =  new BasePage();

        // 3. Scroll to bottom of page
        page.scrollToFooter();

        // 4. Verify 'RECOMMENDED ITEMS' are visible
        Assert.assertEquals(
                page.getRecommendedItemsTitle(),
                "RECOMMENDED ITEMS",
                "Recommended Items header mismatch."
        );

        // 5. Click on 'Add To Cart' on Recommended product
        page.clickRecommendedItemAddToCart("4");

        // 6. Click on 'View Cart' button
        page = page.clickViewCartButton();

        // 7. Verify that product is displayed in cart page
        Assert.assertEquals(
                ((CartsPage) page).getProductName("4"),
                "Stylish Dress",
                "Product name in cart does not match the recommended item added."
        );
    }


@Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 23: Verify address details in checkout page - Verify that the delivery and billing addresses at checkout match the address entered during account registration.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 23: Verify address details in checkout page", url = "https://automationexercise.com/test_cases#23")
    @Issue("CART-23")
    @TmsLink("TC-23")
    @Epic("Cart & Checkout Workflow")
    @Feature("Checkout Process")
    @Story("Address Verification")
    public void TC23(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // Steps 1-7: Launch browser, Navigate to URL, Sign up, Verify Account Created & Login
        page = createAccount(page, userProfile); // ✅ FIXED: Pass and re-assign the local page object

        // 8. Add products to cart
        page.clickProductAddToCartButton("1");

        // Handling the modal and navigating to the cart page
        page.clickViewCartButton();
        page = page.goToCartsPage();

        // 10. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 11. Click Proceed To Checkout
        page = ((CartsPage) page).clickProceedToCheckoutButtonWhileRegistered();

        // 12. Verify that the delivery address is same address filled at the time registration of account
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressDeliveryText(),
                userProfile.getAddress(),
                "Delivery address does not match the registered user profile."
        );

        // 13. Verify that the billing address is same address filled at the time registration of account
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressInvoiceText(),
                userProfile.getAddress(),
                "Billing/Invoice address does not match the registered user profile."
        );

        // Steps 14-15: Click 'Delete Account' button and Verify 'ACCOUNT DELETED!'
        page = deleteAccount(page); // ✅ FIXED: Pass and re-assign the local page object
    }



    @Test(
            groups = {"Cart & Checkout Workflow"},
            dataProvider = randomUserProfiles,
            dataProviderClass = TestDataProvider.class,
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 24: Download Invoice after purchase order - Verify the end-to-end flow of placing an order and downloading the generated invoice.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Case 24: Download Invoice", url = "https://automationexercise.com/test_cases#24")
    @Issue("CART-24")
    @TmsLink("TC-24")
    @Epic("Cart & Checkout Workflow")
    @Feature("Order Management")
    @Story("Download Invoice")
    public void TC24(UserProfile userProfile) {

        BasePage page =  new BasePage();
        // 4. Add products to cart
        page.clickProductAddToCartButton("1");
        page.clickContinueShoppingButton();
        page.clickProductAddToCartButton("2");
        page.clickContinueShoppingButton();

        // 5. Click 'Cart' button
        page = page.goToCartsPage();

        // 6. Verify that cart page is displayed
        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Cart page."
        );

        // 7. Click Proceed To Checkout
        ((CartsPage) page).clickProceedToCheckoutButtonWhileNotRegistered();

        // 8. Click 'Register / Login' button
        page = ((CartsPage) page).clickCheckoutLoginButton();

        // 9. Fill all details in Signup and create account
        page = ((LoginPage) page).performSignup(userProfile);
        page = ((SignupPage) page).performSignup(userProfile);

        // 10. Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertEquals(
                ((AccountCreatedPage) page).getAccountCreatedText(),
                "ACCOUNT CREATED!",
                "Account creation success message mismatch."
        );

        page = ((AccountCreatedPage) page).pressContinueButton();

        // 11. Verify ' Logged in as username' at top
        Assert.assertEquals(
                page.getLoggedinAsNameText(),
                "Logged in as " + userProfile.getName(),
                "Logged in user text mismatch."
        );

        // 12. Click 'Cart' button
        page = page.goToCartsPage();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to return to Cart page."
        );

        // 13. Click 'Proceed To Checkout' button
        page = ((CartsPage) page).clickProceedToCheckoutButtonWhileRegistered();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Checkout page."
        );

        // 14. Verify Address Details and Review Your Order
        Assert.assertEquals(
                ((CheckoutPage) page).getAddressDeliveryText(),
                userProfile.getAddress(),
                "Delivery address does not match user profile."
        );


        // 15. Enter description in comment text area and click 'Place Order'
        ((CheckoutPage) page).comment("edges");


        page = ((CheckoutPage) page).clickPlaceOrderButton();

        Assert.assertEquals(
                page.actualURL(),
                page.expectedURL(),
                "Failed to navigate to Payment page."
        );

        // 16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        ((PaymentPage) page).enterPaymentDetails(userProfile);


        // 17. Click 'Pay and Confirm Order' button
        page = ((PaymentPage) page).clickPayAndConfirmButton();
        //page.takeScreenShot();

        // 18. Verify success message 'Your order has been placed successfully!'
        Assert.assertEquals(
                ((PaymentDonePage) page).getOrderPlacedText(),
                "ORDER PLACED!",
                "Order placement success message mismatch."
        );


        // Workaround: Click at (0,0) to reset focus or handle UI overlay interference
        page.clickAtZeroZero();

        // 19. Click 'Download Invoice' button and verify invoice is downloaded successfully.
        ((PaymentDonePage) page).downloadInvoice("900");

        // ✅ FIXED: Replaced infinite loop with a safe 10-second timeout!
        int attempts = 0;
        while(!((PaymentDonePage) page).isFileDownloaded("invoice.txt") && attempts < 10) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            attempts++;
        }
        Assert.assertTrue(((PaymentDonePage) page).isFileDownloaded("invoice.txt"), "Invoice file was not downloaded within 10 seconds.");
        
        // 20. Click 'Continue' button
        page = ((PaymentDonePage) page).clickContinueButton();

        // 21. Click 'Delete Account' button
        // 22. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        page = deleteAccount(page); // ✅ FIXED: Pass and re-assign the local page object
    }



   @Test(
            groups = {"UI/UX & Site Navigation"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality - Verify that the scroll up arrow brings the user to the top of the page.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 25: Scroll Up with Arrow", url = "https://automationexercise.com/test_cases#25")
    @Issue("UI-25")
    @TmsLink("TC-25")
    @Epic("UI/UX & Site Navigation")
    @Feature("Page Scrolling")
    @Story("Scroll Up with Arrow Button")
    public void TC25() {

        BasePage page =  new BasePage();
        // 1. Launch browser (Handled in BeforeMethod)
        // 2. Navigate to url 'http://automationexercise.com' (Handled in BeforeMethod)
        // 3. Verify that home page is visible successfully (Handled in BeforeMethod)

        // 4. Scroll down page to bottom
        page.scrollToFooter();

        // 5. Verify 'SUBSCRIPTION' is visible
        Assert.assertEquals(
                page.getSubscriptionText(),
                "SUBSCRIPTION",
                "Footer subscription header is not visible."
        );

        // 6. Click on arrow at bottom right side to move upward
        page.performScrollUpWithArrow();

        // 7. Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers' text is visible on screen
        Assert.assertEquals(
                page.getPracticeWebsiteText(),
                "Full-Fledged practice website for Automation Engineers",
                "Top page header text mismatch after scrolling up."
        );
    }

    @Test(
            groups = {"UI/UX & Site Navigation"},
            retryAnalyzer = TestHelperPack.RetryAnalyzer.class // ✅ FIXED: Added Retry Analyzer
    )
    @Description("Test Case 26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality - Verify that manually scrolling up brings the user to the top of the page.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Test Case 26: Scroll Up without Arrow", url = "https://automationexercise.com/test_cases#26")
    @Issue("UI-26")
    @TmsLink("TC-26")
    @Epic("UI/UX & Site Navigation")
    @Feature("Page Scrolling")
    @Story("Manual Scroll Up")
    public void TC26() {
        BasePage page =  new BasePage();

        // 1. Launch browser (Handled in BeforeMethod)
        // 2. Navigate to url 'http://automationexercise.com' (Handled in BeforeMethod)
        // 3. Verify that home page is visible successfully (Handled in BeforeMethod)

        // 4. Scroll down page to bottom
        page.scrollToFooter();

        // 5. Verify 'SUBSCRIPTION' is visible
        Assert.assertEquals(
                page.getSubscriptionText(),
                "SUBSCRIPTION",
                "Footer subscription header is not visible."
        );

        // 6. Scroll up page to top
        page.scrollToHeader();

        // 7. Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers' text is visible on screen
        Assert.assertEquals(
                page.getPracticeWebsiteText(),
                "Full-Fledged practice website for Automation Engineers",
                "Top page header text mismatch after manual scroll up."
        );
    }
}