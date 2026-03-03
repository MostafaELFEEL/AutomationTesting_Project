package Utils.HelperPack;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.util.HashMap;
import java.util.Map;





public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static WebDriver createDriver() {
        String browser = ConfigLoader.getProperty("browser").toLowerCase();
        String headlessValue = ConfigLoader.getProperty("headless");
        boolean headless = Boolean.parseBoolean(headlessValue);

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addExtensions(new File("src/test/resources/extensions/uBlock-Origin-Lite-Chrome-Web-Store.crx"));

                Map<String, Object> chromePrefs = new HashMap<>();
                // --- Block Password Saving & Leak Detection ---
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("profile.password_manager_leak_detection", false);

                // --- Block Address & Credit Card Saving ---
                chromePrefs.put("autofill.profile_enabled", false);       // Blocks "Save Address"
                chromePrefs.put("autofill.credit_card_enabled", false);   // Blocks "Save Card"

                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1200");
                    chromeOptions.addArguments("--disable-gpu");
                }
                return new ChromeDriver(chromeOptions);

            case "edge":
                EdgeOptions edgeOptions = getEdgeOptions(headless);
                return new EdgeDriver(edgeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = getFirefoxOptions(headless);
                return new FirefoxDriver(firefoxOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static @NonNull FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.addExtension(new File("src/test/resources/extensions/ublock_origin-1.69.0.xpi"));

        // --- Block Password Saving & Leak Detection ---
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        profile.setPreference("signon.management.page.breach-alerts.enabled", false);

        // --- Block Address & Credit Card Saving ---
        profile.setPreference("extensions.formautofill.addresses.enabled", false);  // Blocks "Save Address"
        profile.setPreference("extensions.formautofill.creditcards.enabled", false);// Blocks "Save Card"
        profile.setPreference("dom.forms.autocomplete.formautofill", false);        // Extra safety

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);

        if (headless) {
            firefoxOptions.addArguments("-headless");
            firefoxOptions.addArguments("--width=1920");
            firefoxOptions.addArguments("--height=1200");
        }
        return firefoxOptions;
    }

    private static @NonNull EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addExtensions(new File("src/test/resources/extensions/AdGuard-Hacked.crx"));

        Map<String, Object> edgePrefs = new HashMap<>();
        // --- Block Password Saving & Leak Detection ---
        edgePrefs.put("credentials_enable_service", false);
        edgePrefs.put("profile.password_manager_enabled", false);
        edgePrefs.put("profile.password_manager_leak_detection", false);

        // --- Block Address & Credit Card Saving ---
        edgePrefs.put("autofill.profile_enabled", false);       // Blocks "Save Address"
        edgePrefs.put("autofill.credit_card_enabled", false);   // Blocks "Save Card"

        edgeOptions.setExperimentalOption("prefs", edgePrefs);
        //edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        if (headless) {
            edgeOptions.addArguments("--headless=new");
            edgeOptions.addArguments("--window-size=1920,1200");
            edgeOptions.addArguments("--disable-gpu");
        }
        return edgeOptions;
    }






}
