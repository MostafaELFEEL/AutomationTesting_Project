package Pages;

import Utils.PojoPack.UserProfile;
import org.openqa.selenium.By;

public class ContactUsPage extends BasePage {
    private static final By nameTextboxLocator = By.name("name");
    private static final By emailTextboxLocator = By.name("email");
    private static final By subjectTextboxLocator = By.name("subject");
    private static final By messageTextboxLocator = By.name("message");
    private static final By uploadFileLocator = By.name("upload_file");
    private static final By submitButtonLocator = By.name("submit");
    private static final By getInTouchTextLocator = By.xpath("//div[@class=\"contact-form\"]//h2");
    private static final By successMessageLocator = By.xpath("//div[@class=\"status alert alert-success\"]");
    private static final By homeButtonLocator = By.xpath("//a[@class=\"btn btn-success\"]");
    public ContactUsPage(){
        framework.waitUntilURLToBe(expectedURL());
    }
    public void fillContactUsForm(UserProfile userProfile){
        framework.sendKeys(nameTextboxLocator, userProfile.getName());
        framework.sendKeys(emailTextboxLocator, userProfile.getEmail());
        framework.sendKeys(subjectTextboxLocator,"subject");
        framework.sendKeys(messageTextboxLocator,"message");
        framework.sendKeys(uploadFileLocator, rootPath + "\\testfile.txt");
        framework.click(submitButtonLocator);
        framework.handleAlert(true);
    }

    public String getGetInTouchText(){
        return framework.getText(getInTouchTextLocator);
    }

    public String getSuccessMessageText(){
        return framework.getText(successMessageLocator);
    }

    public BasePage clickHomeButton(){
        framework.click(homeButtonLocator);
        return  new BasePage();
    }

    @Override
    public String expectedURL (){
        return URL + "contact_us";
    }





}
