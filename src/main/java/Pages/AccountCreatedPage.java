package Pages;

import org.openqa.selenium.By;

public class AccountCreatedPage extends BasePage {

    private static final By continueButtonLocator = By.xpath("//*[@id=\"form\"]//a");
    private static final By AccountCreatedTextLocator = By.xpath("//b");


    public AccountCreatedPage(){
        framework.waitUntilURLToBe(expectedURL());
    }

    public BasePage pressContinueButton(){
        framework.click(continueButtonLocator);
        return new BasePage();
    }

    public String getAccountCreatedText(){
        return framework.getText(AccountCreatedTextLocator);
    }

    @Override
    public String expectedURL(){
        return URL + "account_created";
    }
}
