package Pages;

public class CategoryProductsPage extends BasePage {
    
    // Add the parameter to the constructor
    public CategoryProductsPage(String passedCategoryName){
        this.categoryName = passedCategoryName; // Save it to this thread's instance
        framework.waitUntilURLToBe(expectedURL());
    }
    
    @Override
    public String expectedURL (){
        return URL + "category_products/" + categoryName;
    }
}