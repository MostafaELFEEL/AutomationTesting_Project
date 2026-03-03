package Pages;

import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class ProductDetailPage extends BasePage {

    private static final By productNameLocator = By.xpath("//div[@class=\"product-information\"]/h2");
    private static final By categoryLocator = By.xpath("//div[@class=\"product-information\"]/p[1]");
    private static final By priceLocator = By.xpath("//div[@class=\"product-information\"]/span/span");
    private static final By availabilityLocator = By.xpath("//div[@class=\"product-information\"]/p[2]/b");
    private static final By conditionLocator = By.xpath("//div[@class=\"product-information\"]/p[3]/b");
    private static final By brandLocator = By.xpath("//div[@class=\"product-information\"]/p[4]/b");
    private static final By quantityLocator = By.id("quantity");
    private static final By addToCartButtonLocator = By.xpath("//*[@class=\"btn btn-default cart\"]");
    private static final By reviewTextBoxLocator = By.id("review");
    private static final By submitButtonLocator = By.id("button-review");
    private static final By writeYourReviewTextLocator = By.xpath("//a[@href=\"#reviews\"]");
    private static final By successMessageLocator = By.xpath("//*[@id=\"review-section\"]//span");

    // ✅ FIXED: Constructor now accepts the productID from the previous page
    public ProductDetailPage(String passedProductID) {
        this.productID = passedProductID; // Save it to this thread's instance
        framework.waitUntilURLToBe(expectedURL());
    }

    public String getSuccessMessage(){
        return framework.getText(successMessageLocator);
    }

    public String getCategoryText() {
        return framework.getText(categoryLocator);
    }

    public String getProductName() {
        return framework.getText(productNameLocator);
    }

    public String getPriceText() {
        return framework.getText(priceLocator);
    }

    public String getAvailabilityText() {
        return framework.getText(availabilityLocator);
    }

    public String getConditionText() {
        return framework.getText(conditionLocator);
    }

    public String getBrandText() {
        return framework.getText(brandLocator);
    }

    @Override
    public String expectedURL(){
        return URL + "product_details/" + productID;
    }

    public void increaseQuantity(String quantity){
        framework.sendKeys(quantityLocator, quantity);
    }

    public void clickAddToCartButton(){
        framework.click(addToCartButtonLocator);
    }
    
    public void makeReview(UserProfile user,String reviewText){
        framework.sendKeys(nameTextboxLocator, user.getName());
        framework.sendKeys(emailTextboxLocator,user.getEmail());
        framework.sendKeys(reviewTextBoxLocator, reviewText);
        framework.click(submitButtonLocator);
    }

    public String getWriteYourReviewText(){
        return framework.getText(writeYourReviewTextLocator);
    }
}