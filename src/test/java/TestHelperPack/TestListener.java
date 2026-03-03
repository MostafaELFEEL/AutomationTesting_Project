package TestHelperPack;



import Pages.BasePage;

import org.testng.ITestListener;

import org.testng.ITestResult;



public class TestListener implements ITestListener {



    private final BasePage page = new BasePage();



    @Override

    public void onTestFailure(ITestResult result) {

        System.out.println("❌ Test failed: " + result.getName());



// Capture screenshot

        try {

            page.takeScreenShot();

            System.out.println("📸 Screenshot captured for " + result.getName());

        } catch (Exception e) {

            System.err.println("Failed to capture screenshot: " + e.getMessage());

        }



// Close browser

// try {

// page.closeBrowser();

// System.out.println("🛑 Browser closed after failure.");

// } catch (Exception e) {

// System.err.println("Failed to close browser: " + e.getMessage());

// }

    }



    @Override

    public void onTestSuccess(ITestResult result) {

        System.out.println("✅ Test passed: " + result.getName());



// Capture screenshot on success

// try {

// //page.takeScreenShot();

// System.out.println("📸 Screenshot captured for " + result.getName());

// } catch (Exception e) {

// System.err.println("Failed to capture screenshot: " + e.getMessage());

// }

// // Close browser

// try {

// page.closeBrowser();

// System.out.println("🛑 Browser closed after success.");

// } catch (Exception e) {

// System.err.println("Failed to close browser: " + e.getMessage());

// }

    }




}