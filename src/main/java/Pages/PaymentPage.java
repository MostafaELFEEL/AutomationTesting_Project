package Pages;

import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class PaymentPage extends BasePage {

    private static final By nameOnCardLocator   = By.name("name_on_card");
    private static final By cardNumberLocator   = By.name("card_number");
    private static final By cvcLocator          = By.name("cvc");
    private static final By expiryMonthLocator  = By.name("expiry_month");
    private static final By expiryYearLocator   = By.name("expiry_year");
    private static final By payAndConfirmButtonLocator = By.id("submit");
    private static final By orderSuccessMessageLocator = By.xpath("//div[@class=\"payment-information\"]//div[@class=\"alert-success alert\"]");


    public PaymentPage(){
        framework.waitUntilURLToBe(expectedURL());
    }
    @Override
    public String expectedURL() {
        return URL + "payment";
    }

    public void enterPaymentDetails(UserProfile userProfile) {
        framework.sendKeys(nameOnCardLocator, userProfile.getNameOnCard());
        framework.sendKeys(cardNumberLocator, userProfile.getCardNumber());
        framework.sendKeys(cvcLocator, userProfile.getCvc());
        framework.sendKeys(expiryMonthLocator, userProfile.getExpirationDateMonth());
        framework.sendKeys(expiryYearLocator, userProfile.getExpirationDateYear());
    }


    public PaymentDonePage clickPayAndConfirmButton() {
        framework.click(payAndConfirmButtonLocator);
        return new PaymentDonePage();
    }

    public String getOrderSuccessMessage() {
        return framework.getText(orderSuccessMessageLocator);
    }
}