package TestHelperPack;

import io.qameta.allure.Allure;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 2; // Sets maximum retries (e.g., 2 retries = 3 total attempts)

    @Override
    public boolean retry(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();

        if (!iTestResult.isSuccess()) {
            if (count < maxTry) {
                count++;

                String message = "⚠️ FLAKY TEST CAUGHT: Retrying '" + testName + "' -> Attempt " + count + " of " + maxTry;

                // 1. Prints to the terminal for your live console viewing
                System.out.println(message);

                // 2. Injects the message directly into the Allure Report timeline!
                Allure.step(message);

                return true;
            } else {
                String failMessage = "❌ TEST FAILED: '" + testName + "' officially failed after " + (count + 1) + " total attempts.";
                System.out.println(failMessage);
                Allure.step(failMessage);
            }
        }
        return false;
    }
}