package Pages;

import org.openqa.selenium.By;

public class CartsPage extends BasePage {

    private By getProductQuantityLocator(String id) {
        return By.xpath("//*[@id='product-" + id + "']//button");
    }

    private By getProductTotalPriceLocator(String id) {
        return By.xpath("//*[@id='product-" + id + "']//p[@class='cart_total_price']");
    }

    private By getProductPriceLocator(String id) {
        return By.xpath("//*[@id='product-" + id + "']//td[@class='cart_price']");
    }

// Making these 'static' means Java only creates one copy of the instructions
    // in memory for all threads to share, rather than a new copy per page object.
    private static final By proceedToCheckoutButtonLocator = By.xpath("//a[@class='btn btn-default check_out']");
    private static final By checkoutLoginButtonLocator = By.xpath("//div[@class='modal-content']//a[@href='/login']");
    private static final By xButtonLocator = By.xpath("//i[@class='fa fa-times']");
    private static final By cartIsEmptyTextLocator = By.xpath("//*[@id='empty_cart']//b");

    // Methods
    public CartsPage(){
        framework.waitUntilURLToBe(expectedURL());
    }

    public String getProductName(String id) {
        return framework.getText(By.xpath("//a[@href='/product_details/" + id + "']"));
    }



    public String getProductQuantity(String id) {
        return framework.getText(getProductQuantityLocator(id));
    }

    public String getProductTotalPrice(String id) {
        return framework.getText(getProductTotalPriceLocator(id));
    }

    public String getProductPrice(String id) {
        return framework.getText(getProductPriceLocator(id));
    }

    public CheckoutPage clickProceedToCheckoutButtonWhileRegistered() {

            framework.click(proceedToCheckoutButtonLocator);

        return new CheckoutPage();
    }

    public void clickProceedToCheckoutButtonWhileNotRegistered(){
        framework.click(proceedToCheckoutButtonLocator);
    }

    public LoginPage clickCheckoutLoginButton() {
        while(actualURL().equals(expectedURL())) {
            framework.click(checkoutLoginButtonLocator);
        }

        return new LoginPage();
    }

    public void clickXButton() {
        framework.click(xButtonLocator);
    }

    public String getCartIsEmptyText() {
        return framework.getText(cartIsEmptyTextLocator);
    }

    @Override
    public String expectedURL() {
        return URL + "view_cart";
    }
}