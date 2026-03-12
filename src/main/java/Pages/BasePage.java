package Pages;

import Utils.FrameworkPack.Framework;
import Utils.HelperPack.ConfigLoader;
import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class BasePage {

    protected final static Framework framework = new Framework();

    protected final static String URL = ConfigLoader.getProperty("baseUrl").toLowerCase();
    protected final static int timeout = Integer.parseInt(ConfigLoader.getProperty("timeout"));
    //protected final static String rootPath = ConfigLoader.getProperty("rootPath");

    // Common locators (Safe to be static because they are 'final' constants)
    protected final static By footerLocator = By.id("footer");
    protected final static By footerBottomLocator = By.xpath("//div[@class='footer-bottom']");
    protected final static By headerLocator = By.id("header");
    protected final static By loginLocator = By.xpath("//a[@href='/login']");
    protected final static By logoutLocator = By.xpath("//a[@href='/logout']");
    protected final static By deleteAccountLocator = By.xpath("//a[@href='/delete_account']");
    protected final static By contactUsLocator = By.xpath("//a[@href='/contact_us']");
    protected final static By testCasesLocator = By.xpath("//a[@href='/test_cases']");
    protected final static By productsLocator = By.xpath("//a[@href='/products']");
    protected final static By cartsLocator = By.xpath("//a[@href='/view_cart']");
    protected final static By subscriptionTextLocator = By.xpath("//div[@class='single-widget']/h2");
    protected final static By loggedinAsTextLocator = By.xpath("//li[10]/a");
    protected final static By subscribeEmailTextboxLocator = By.id("susbscribe_email");
    protected final static By subscribeButtonLocator = By.id("subscribe");
    protected final static By subscribeAlertTextLocator = By.xpath("//*[@id='success-subscribe']/div");
    protected final static By continueShoppingButtonLocator = By.xpath("//div[@class='modal-content']//button");
    protected final static By categoryTextLocator = By.xpath("//div[@class='left-sidebar']/h2");
    protected final static By scrollUpLocator = By.id("scrollUp");
    protected final static By practiceWebsiteTextLocator = By.xpath("//*[@id='slider-carousel']//div[@class='item active']//h2");
    protected final static By brandsTextLocator = By.xpath("//div[@class='brands_products']/h2");
    protected final static By titleTextLocator = By.xpath("//*[@class='title text-center']");
    protected final static By recommendedItemsTitleLocator = By.xpath("//div[@class='recommended_items']//*[@class='title text-center']");
    
    protected static final By viewCartButtonLocator = By.xpath("//*[@id='cartModal']//*[@href='/view_cart']");
    protected static final By nameTextboxLocator = By.id("name");
    protected static final By emailTextboxLocator = By.id("email");

    // ✅ FIXED: Removed 'static' so every thread gets its own copy of these variables
    protected String categoryName;
    protected String brandName;
    protected String productID;

    // ✅ Generic product locators
    protected By getProductAddToCartButtonLocator(String id) {
        return By.xpath("//a[@data-product-id='" + id + "']");
    }

    protected By getProductDetailLocator(String id) {
        return By.xpath("//*[@href='/product_details/" + id + "']");
    }

    // ✅ Generic category locators
    protected By getCategoryLocator(String name) {
        return By.xpath("//*[@href='#" + name + "']");
    }

    protected By getCategoryProductsLocator(String id) {
        return By.xpath("//*[@href='/category_products/" + id + "']");
    }

    // ✅ Generic brand locators
    protected By getBrandLocator(String brand) {
        return By.xpath("//a[@href='/brand_products/" + brand + "']");
    }

    // Methods
    public CartsPage clickViewCartButton() {
        framework.click(viewCartButtonLocator);
        return new CartsPage();
    }

    public String getTitleText() {
        return framework.getText(titleTextLocator);
    }

public BrandProductsPage clickBrand(String brand) {
        framework.click(getBrandLocator(brand));
        // We no longer need to save it to BasePage's instance.
        // We just pass it directly to the next page!
        return new BrandProductsPage(brand); 
    }

    public void clickRecommendedItemAddToCart(String id) {
        // ✅ FIXED: Moved the locator inside the method so it is built fresh per thread
        By recommendedItemAddToCartLocator = By.xpath("//*[@class='item active']//*[@data-product-id='" + id + "']");
        framework.click(recommendedItemAddToCartLocator);
    }

    public String getRecommendedItemsTitle() {
        return framework.getText(recommendedItemsTitleLocator);
    }

    public String getPracticeWebsiteText() {
        return framework.getText(practiceWebsiteTextLocator);
    }

    public String getBrandsText() {
        return framework.getText(brandsTextLocator);
    }

    public void clickCategory(String name) {
        framework.click(getCategoryLocator(name));
    }

    public String getCategoryText() {
        return framework.getText(categoryTextLocator);
    }

public CategoryProductsPage clickCategoryProducts(String id) {
        while (!framework.getCurrentURL().equals(URL + "category_products/" + id)) {
            framework.click(getCategoryProductsLocator(id));
        }
        // Pass the state safely to the next page!
        return new CategoryProductsPage(id); 
    }

    public void takeScreenShot() {
        framework.screenshot();
    }

    public void openBrowser() {
        framework.initializeBrowser(ConfigLoader.createDriver());
        framework.implicitWait(timeout);
        framework.initExplicitWait(timeout);
    }

    public void closeBrowser() {
        framework.closeBrowser();
    }

    public BasePage goToHomePage() {
        framework.navigateToURL(URL);
        return new BasePage();
    }

    public String expectedURL() {
        return URL;
    }

    public String actualURL() {
        return framework.getCurrentURL();
    }

    public String getLoggedinAsNameText() {
        return framework.getText(loggedinAsTextLocator);
    }

    public LoginPage performLogout() {
        framework.click(logoutLocator);
        return new LoginPage();
    }

    public AccountDeletedPage performDeleteAccount() {
        framework.click(deleteAccountLocator);
        return new AccountDeletedPage();
    }

    public ContactUsPage goToContactUsPage() {
        while (!framework.getCurrentURL().equals(URL + "contact_us")) {
            framework.click(contactUsLocator);
        }
        return new ContactUsPage();
    }

    public TestCasesPage goToTestCasesPage() {
        while (!framework.getCurrentURL().equals(URL + "test_cases")) {
            framework.click(testCasesLocator);
        }
        return new TestCasesPage();
    }

    public ProductsPage goToProductsPage() {
        while (!framework.getCurrentURL().equals(URL + "products")) {
            framework.click(productsLocator);
        }
        return new ProductsPage();
    }

    public LoginPage goToLoginPage() {
        while (!framework.getCurrentURL().equals(URL + "login")) {
            framework.click(loginLocator);
        }
        return new LoginPage();
    }

    public CartsPage goToCartsPage() {
        while (!framework.getCurrentURL().equals(URL + "view_cart")) {
            framework.click(cartsLocator);
        }
        return new CartsPage();
    }

    public void scrollToFooter() {
        framework.scrollToElement(footerLocator);
    }

    public void scrollToHeader() {
        framework.scrollToElement(headerLocator);
    }

    public String getSubscriptionText() {
        return framework.getText(subscriptionTextLocator);
    }

    public void preformSubscribe(UserProfile userProfile) {
        framework.sendKeys(subscribeEmailTextboxLocator, userProfile.getEmail());
        framework.click(subscribeButtonLocator);
    }

    public String getSubscribeAlertText() {
        return framework.getText(subscribeAlertTextLocator);
    }

   public ProductDetailPage goToProductDetailPage(String id) {
        while (!framework.getCurrentURL().equals(URL + "product_details/" + id)) {
            framework.click(getProductDetailLocator(id));
        }
        // ✅ Pass the state safely to the next page!
        return new ProductDetailPage(id); 
    }

    public void clickProductAddToCartButton(String id) {
        framework.click(getProductAddToCartButtonLocator(id));
    }

    public void clickContinueShoppingButton() {
        framework.click(continueShoppingButtonLocator);
    }

    public void clickAtZeroZero() {
        framework.clickAtZeroZero();
    }

    public void performScrollUpWithArrow() {
        framework.click(scrollUpLocator);
    }
}