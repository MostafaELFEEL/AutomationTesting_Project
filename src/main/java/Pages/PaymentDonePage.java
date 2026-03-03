package Pages;

import org.openqa.selenium.By;
import java.io.File;

public class PaymentDonePage extends BasePage {

    private static final By orderPlacedTextLocator = By.xpath("//*[@data-qa=\"order-placed\"]/b");
    private static final By continueButtonLocator = By.xpath("//*[@data-qa=\"continue-button\"]");

    // Constructor updated to use safe Explicit Wait
    public PaymentDonePage(){
        framework.waitUntilURLContains(expectedURL());
    }

    @Override
    public String expectedURL(){
        return URL + "payment_done/";
    }

    // Dynamic locator method for the invoice
    private By getDownloadInvoiceLocator(String invoiceId) {
        return By.xpath("//a[@href='/download_invoice/" + invoiceId + "']");
    }

    // Pass the ID when you want to click it
    public void downloadInvoice(String invoiceId){
        framework.click(getDownloadInvoiceLocator(invoiceId));
    }

    public String getOrderPlacedText(){
        return framework.getText(orderPlacedTextLocator);
    }

    public BasePage clickContinueButton(){
        framework.click(continueButtonLocator);
        return new BasePage();
    }

    // NOTE: Subject to OS race conditions in parallel mode!
    public boolean isFileDownloaded(String expectedFileName) {
        String home = System.getProperty("user.home");
        String downloadDir = home + "/Downloads";
        File file = new File(downloadDir + "/" + expectedFileName);
        return file.exists();
    }
}