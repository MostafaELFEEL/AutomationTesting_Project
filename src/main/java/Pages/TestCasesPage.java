package Pages;

import org.openqa.selenium.By;

public class TestCasesPage extends BasePage {

    private static final By testCasesTextLocator = By.xpath("//b");
    public TestCasesPage(){
        framework.waitUntilURLToBe(expectedURL());
    }

    public String getTestCasesText(){
        return framework.getText(testCasesTextLocator);
    }

    @Override
    public String expectedURL (){
        return URL + "test_cases";
    }
}
