package Pages;

import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class SignupPage extends BasePage {
    private static final By maleGenderCheckboxLocator = By.id("id_gender1");
    private static final By femaleGenderCheckboxLocator = By.id("id_gender2");

    private static final By passwordTextboxLocator = By.id("password");
    private static final By daysListLocator = By.id("days");
    private static final By monthsListLocator = By.id("months");
    private static final By yearsListLocator = By.id("years");
    private static final By newsletterCheckboxLocator = By.id("newsletter");
    private static final By optinCheckBoxLocator = By.id("optin");
    private static final By firstNameTextboxLocator = By.id("first_name");
    private static final By lastNameTextboxLocator = By.id("last_name");
    private static final By address1TextboxLocator = By.id("address1");
    private static final By address2TextboxLocator = By.id("address2");
    private static final By stateTextboxLocator = By.id("state");
    private static final By cityTextboxLocator = By.id("city");
    private static final By zipcodeTextboxLocator = By.id("zipcode");
    private static final By mobileNumberTextboxLocator = By.id("mobile_number");
    private static final By createAccountButtonLocator = By.xpath("//*[@data-qa=\"create-account\"]");
    private static final By enterAAccountInfoTextLocator = By.xpath("//div/h2/b");

    public SignupPage(){
        framework.waitUntilURLToBe(expectedURL());
    }

    public AccountCreatedPage performSignup(UserProfile userProfile) {
        if (userProfile.getTitle().equals("Mr.")) {
            framework.click(maleGenderCheckboxLocator);
        } else {
            framework.click(femaleGenderCheckboxLocator);
        }

        framework.sendKeys(passwordTextboxLocator, userProfile.getPassword());

        // Select day, month, and year from dropdowns
        framework.selectByVisibleText(daysListLocator, userProfile.getDay());
        framework.selectByVisibleText(monthsListLocator, userProfile.getMonth());
        framework.selectByVisibleText(yearsListLocator, userProfile.getYear());

        framework.checkCheckbox(newsletterCheckboxLocator);
        framework.checkCheckbox(optinCheckBoxLocator);
        framework.sendKeys(firstNameTextboxLocator, userProfile.getFirstName());
        framework.sendKeys(lastNameTextboxLocator, userProfile.getLastName());
        framework.sendKeys(address1TextboxLocator, userProfile.getAddress());
        framework.sendKeys(address2TextboxLocator, userProfile.getAddress2());
        framework.sendKeys(stateTextboxLocator, userProfile.getState());
        framework.sendKeys(cityTextboxLocator, userProfile.getCity());
        framework.sendKeys(zipcodeTextboxLocator, userProfile.getZipcode());
        framework.sendKeys(mobileNumberTextboxLocator, userProfile.getMobileNumber());
        framework.click(createAccountButtonLocator);

        return new AccountCreatedPage();
    }



    public String getEnterAAccountInfoText(){
        return framework.getText(enterAAccountInfoTextLocator);
    }

    @Override
    public String expectedURL (){
        return URL + "signup";
    }
}
