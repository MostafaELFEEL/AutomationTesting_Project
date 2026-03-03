package Pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage{


    private static final By addressDeliveryTextLocator = By.xpath("//*[@id=\"address_delivery\"]/li[4]");
    private static final By addressInvoiceTextLocator = By.xpath("//*[@id=\"address_invoice\"]/li[4]");
    private static final By commentSectionLocator = By.name("message");
    private static final By placeOrderButtonLocator = By.xpath("//*[@href=\"/payment\"]");

    public CheckoutPage(){
        framework.waitUntilURLToBe(expectedURL());
    }
    public String getAddressDeliveryText(){
        return framework.getText(addressDeliveryTextLocator);
    }
    public String getAddressInvoiceText(){
        return framework.getText(addressInvoiceTextLocator);
    }


    public void comment(String commentText){
        framework.sendKeys(commentSectionLocator, commentText);
    }

    public PaymentPage clickPlaceOrderButton(){
        framework.click(placeOrderButtonLocator);
        return new PaymentPage();
    }
    @Override
    public String expectedURL (){
        return URL + "checkout";
    }
}
