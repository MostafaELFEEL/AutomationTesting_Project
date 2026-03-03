package Pages;

import org.openqa.selenium.By;

public class ProductsPage extends BasePage {

    private static final By searchProductsLocator = By.id("search_product");
    private static final By submitSearchLocator = By.id("submit_search");

   public ProductsPage(){
       framework.waitUntilURLToBe(expectedURL());
   }

@Override
public String expectedURL(){
    return URL + "products";
}



public void searchProducts(String product){
    framework.sendKeys(searchProductsLocator,product);
    framework.click(submitSearchLocator);
}


}
