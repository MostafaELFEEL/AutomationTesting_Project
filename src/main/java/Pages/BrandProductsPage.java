package Pages;

public class BrandProductsPage extends BasePage {

    // Add a parameter to the constructor to accept the state
    public BrandProductsPage(String passedBrandName) {
        this.brandName = passedBrandName; // Save it to this specific instance
        framework.waitUntilURLToBe(expectedURL());
    }

    @Override
    public String expectedURL() {
        return URL + "brand_products/" + brandName;
    }
}