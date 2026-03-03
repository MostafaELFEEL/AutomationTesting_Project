package Pages;

import org.openqa.selenium.By;

public class AccountDeletedPage extends BasePage {

    private static final By continueButtonLocator = By.xpath("//*[@data-qa=\"continue-button\"]");
    private static final By AccountDeletedTextLocator = By.xpath("//b");


    public AccountDeletedPage(){
        framework.waitUntilURLToBe(expectedURL());
    }
    public BasePage pressContinueButton(){
        framework.click(continueButtonLocator);
        return new BasePage();
    }

    public String getAccountDeletedText(){
        return framework.getText(AccountDeletedTextLocator);
    }

    @Override
    public String expectedURL(){
        return URL + "delete_account";
    }
}
