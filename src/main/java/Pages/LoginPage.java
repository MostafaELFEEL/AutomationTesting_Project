package Pages;

import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

private static final  By loginEmailLocator = By.xpath("//*[@data-qa=\"login-email\"]");
private static final  By loginPasswordLocator = By.xpath("//*[@data-qa=\"login-password\"]");
private static final  By loginButtonLocator = By.xpath("//*[@data-qa=\"login-button\"]");
private static final  By signupNameLocator = By.xpath("//*[@data-qa=\"signup-name\"]");
private static final  By signupEmailLocator = By.xpath("//*[@data-qa=\"signup-email\"]");
private static final  By signupButtonLocator = By.xpath("//*[@data-qa=\"signup-button\"]");
private static final  By loginTextLocator = By.xpath("//*[@class=\"login-form\"]//h2");
private static final  By signupTextLocator = By.xpath("//*[@class=\"signup-form\"]//h2");
private static final  By falseLoginTextLocator = By.xpath("//form[@action=\"/login\"]/p");
private static final  By falseSignupTextLocator = By.xpath("//form[@action=\"/signup\"]/p");

    public  LoginPage(){
        framework.waitUntilURLToBe(expectedURL());
    }

    public String getLoginText(){
        return framework.getText(loginTextLocator);
    }


    public BasePage performLogin(UserProfile userProfile) {
        framework.sendKeys(loginEmailLocator, userProfile.getEmail());
        framework.sendKeys(loginPasswordLocator, userProfile.getPassword());
        framework.click(loginButtonLocator);
        return new BasePage();
    }

    public SignupPage performSignup(UserProfile userProfile) {
        framework.sendKeys(signupNameLocator, userProfile.getName());
        framework.sendKeys(signupEmailLocator, userProfile.getEmail());
        framework.click(signupButtonLocator);
        return new SignupPage();
    }

    public void performFalseSignup(UserProfile userProfile) {
        framework.sendKeys(signupNameLocator, userProfile.getName());
        framework.sendKeys(signupEmailLocator, userProfile.getEmail());
        framework.click(signupButtonLocator);

    }

    public String getSignupText(){
        return framework.getText(signupTextLocator);
    }

    public void performFalseLogin(UserProfile userProfile) {
        framework.sendKeys(loginEmailLocator, userProfile.getEmail());
        framework.sendKeys(loginPasswordLocator, userProfile.getPassword());
        framework.click(loginButtonLocator);

    }

    public String getFalseLoginText(){
        return framework.getText(falseLoginTextLocator);
    }

    public String getFalseSignupText(){
        return framework.getText(falseSignupTextLocator);
    }

    @Override
    public String expectedURL (){
        return URL + "login";
    }

}
