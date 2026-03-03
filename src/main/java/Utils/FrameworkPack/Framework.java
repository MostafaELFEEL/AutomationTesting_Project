package Utils.FrameworkPack;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

public class Framework {

    // ThreadLocal declarations isolate these objects for each running thread
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private ThreadLocal<Actions> action = new ThreadLocal<>();

    // --- Getters for ThreadLocal variables ---
    public WebDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    public Actions getAction() {
        return action.get();
    }

    // --- Initialization ---

    // 1.1. Initialize browser for the current thread
    @SuppressWarnings("null")
    public void initializeBrowser(WebDriver driverInstance) {
        driver.set(driverInstance); // Save driver to this specific thread
        action.set(new Actions(driverInstance));

        getDriver().manage().window().maximize();
        getDriver().switchTo().window(getDriver().getWindowHandle());
    }

    // 1.2.
    public void initExplicitWait(int timeoutSeconds) {
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds)));
    }

    // --- Waits ---

    // 2.
    @SuppressWarnings("null")
    public void implicitWait(int seconds) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    // 3.
    @SuppressWarnings("null")
    public void explicitWait(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @SuppressWarnings("null")
    public void waitForVisibility(By locator) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @SuppressWarnings("null")
    public boolean isElementVisible(By locator) {
        try {
            return getDriver().findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // 4. fluentWait
    @SuppressWarnings("null")
    public void fluentWait(By locator, int timeoutSeconds, int pollingMillis, String timeoutMessage) {
        Wait<WebDriver> fluentWait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(NoSuchElementException.class)
                .withMessage(timeoutMessage);

        fluentWait.until(webDriver -> webDriver.findElement(locator));
    }

    // --- Navigation ---

    // 5. Navigate to the specified URL
    @SuppressWarnings("null")
    public void navigateToURL(String url) {
        getDriver().navigate().to(url);
        getWait().until(ExpectedConditions.urlToBe(url));
    }

    @SuppressWarnings("null")
    public void waitUntilURLToBe(String url) {
        getWait().until(ExpectedConditions.urlToBe(url));
    }

    // 6. Return the current page title
    public String getPageTitle() {
        return getDriver().getTitle();
    }

    // 7. Return the current page URL
    public String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }

    // --- Interactions ---

    // 8. Click on an element after waiting for it to be clickable
    public void click(By locator) {
        int retries = 3; // configurable
        for (int i = 0; i < retries; i++) {
            try {
                @SuppressWarnings("null")
                WebElement element = getWait().until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return; // success
            } catch (ElementClickInterceptedException e) {
                // Optionally scroll or wait before retrying
                @SuppressWarnings("null")
                WebElement element = getDriver().findElement(locator);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
            }
        }
        throw new RuntimeException("Failed to click element after retries: " + locator);
    }

    // 9. Perform a right-click (context click) on an element
    @SuppressWarnings("null")
    public void rightClick(By locator) {
        @SuppressWarnings("null")
        WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        getAction().contextClick(element).perform();
    }

    // 10. Send keys (text) to an element
    public void sendKeys(By locator, String text) {
        @SuppressWarnings("null")
        WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear(); // optional: clears existing text
        element.sendKeys(text);
    }

    // 11. Get visible text from an element
    public String getText(By locator) {
        @SuppressWarnings("null")
        WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    // 12. selectDropdownByVisibleText
    public void selectDropdownByVisibleText(By locator, String visibleText) {
        @SuppressWarnings("null")
        WebElement dropdown = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    // 13. Select a dropdown option by value attribute
    public void selectDropdownByValue(By locator, String value) {
        @SuppressWarnings("null")
        WebElement dropdown = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    public void clickAtZeroZero() {
        getAction().moveByOffset(1, 1).click().perform();
    }

    // 14. Select a dropdown option by index
    public void selectDropdownByIndex(By locator, int index) {
        @SuppressWarnings("null")
        WebElement dropdown = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }

    // 15. Drag source element and drop it on target element
    @SuppressWarnings("null")
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        @SuppressWarnings("null")
        WebElement source = getWait().until(ExpectedConditions.visibilityOfElementLocated(sourceLocator));
        @SuppressWarnings("null")
        WebElement target = getWait().until(ExpectedConditions.visibilityOfElementLocated(targetLocator));
        getAction().dragAndDrop(source, target).perform();
    }

    // 16. Check a checkbox if not already checked
    public void checkCheckbox(By locator) {
        @SuppressWarnings("null")
        WebElement checkbox = getWait().until(ExpectedConditions.elementToBeClickable(locator));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // 17. Uncheck a checkbox if currently checked
    public void uncheckCheckbox(By locator) {
        @SuppressWarnings("null")
        WebElement checkbox = getWait().until(ExpectedConditions.elementToBeClickable(locator));
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // 18. Select a radio button if not already selected
    public void selectRadioButton(By locator) {
        @SuppressWarnings("null")
        WebElement radioButton = getWait().until(ExpectedConditions.elementToBeClickable(locator));
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    // --- Window & Alert Handling ---

    // 19. Switch browser window focus to window with given title
    @SuppressWarnings("null")
    public void switchToWindowByTitle(String windowTitle) {
        Set<String> allWindows = getDriver().getWindowHandles();
        for (String handle : allWindows) {
            getDriver().switchTo().window(handle);
            if (Objects.equals(getDriver().getTitle(), windowTitle)) {
                return; // Found the window
            }
        }
        throw new NoSuchWindowException("Window with title '" + windowTitle + "' not found.");
    }

    // 20. Switch browser window focus to window with given handle if it exists
    @SuppressWarnings("null")
    public void switchToWindowByHandle(String windowHandle) {
        Set<String> allWindows = getDriver().getWindowHandles();
        if (allWindows.contains(windowHandle)) {
            getDriver().switchTo().window(windowHandle);
        } else {
            throw new NoSuchWindowException("Window with handle '" + windowHandle + "' not found.");
        }
    }

    // 21. Close the currently focused window
    public void closeCurrentWindow() {
        getDriver().close();
    }

    // 22. Navigate back in browser history
    public void navigateBack() {
        getDriver().navigate().back();
    }

    // 23. Navigate forward in browser history
    public void navigateForward() {
        getDriver().navigate().forward();
    }

    // 24. Refresh the current page
    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    // 25. Scroll viewport to bring the element into view
    @SuppressWarnings("null")
    public void scrollToElement(By locator) {
        @SuppressWarnings("null")
        WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        getAction().scrollToElement(element).perform();
    }

    // 26. Accept (click OK on) an active JavaScript alert
    public void acceptAlert() {
        getWait().until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
    }

    // 27. Dismiss (click Cancel on) an active JavaScript alert
    public void dismissAlert() {
        getWait().until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().dismiss();
    }

    // 28. Get the text content of an active JavaScript alert
    public String getAlertText() {
        getWait().until(ExpectedConditions.alertIsPresent());
        return getDriver().switchTo().alert().getText();
    }

    // 29. Send text input to a prompt alert and accept it
    @SuppressWarnings("null")
    public void sendTextToAlert(String text) {
        getWait().until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(text);   // type into the prompt
        alert.accept();         // click OK
    }

    // --- Utilities ---

    // 30. Close the browser and quit the WebDriver session
    public void closeBrowser() {
        if (getDriver() != null) {
            getDriver().quit();  // closes all windows and ends the session

            // CRITICAL: Clean up the ThreadLocal memory to prevent leaks during parallel runs
            driver.remove();
            wait.remove();
            action.remove();
        }
    }

    @SuppressWarnings("null")
    public void screenshot() {
        // 1. Format timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(dtf);

        // 2. Folder name
        String folderName = "screenshots";
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 3. Capture screenshot
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        File dest = new File(directory, "screenshot_" + timestamp + ".png");

        try {
            // 4. Save file locally
            FileHandler.copy(src, dest);

            // 5. Attach to Allure (use the saved file)
            Allure.addAttachment("Screenshot - " + timestamp, Files.newInputStream(dest.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }

    @SuppressWarnings("null")
    public By setLocatorXpath(String locator) {
        return By.xpath(locator);
    }

    public void actionSendKey(Keys key) {
        getAction().sendKeys(key).perform();
    }

    @SuppressWarnings("null")
    public void actionLeftClick(By locator) {
        @SuppressWarnings("null")
        WebElement element = getWait().until(ExpectedConditions.elementToBeClickable(locator));
        getAction().click(element).perform();
    }

    public void handleAlert(boolean accept) {
        Alert alert = getWait().until(ExpectedConditions.alertIsPresent());

        if (accept) {
            if (alert != null) {
                alert.accept();
            }
        } // clicks OK
        else {
            if (alert != null) {
                alert.dismiss();
            }
        } // clicks Cancel
    }

    public void selectByVisibleText(By locator, String value) {
        @SuppressWarnings("null")
        WebElement element = getDriver().findElement(locator);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(value);
    }


    @SuppressWarnings("null")
    public void waitUntilURLContains(String fraction) {
    getWait().until(ExpectedConditions.urlContains(fraction));
}
}