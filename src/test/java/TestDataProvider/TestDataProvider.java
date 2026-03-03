package TestDataProvider;

import Utils.HelperPack.HelperClass;
import org.testng.annotations.DataProvider;
import Utils.PojoPack.UserProfile;
import java.util.List;

public class TestDataProvider {

    // --- THE PRIVATE HELPER METHOD ---
    // This does all the heavy lifting so we don't repeat the loop 3 times
    private Object[][] getProfileData(String fileName, boolean randomizeEmail) {
        List<UserProfile> profiles = HelperClass.readArrayFromFile(fileName, UserProfile.class);
        Object[][] result = new Object[profiles.size()][1];

        for (int i = 0; i < profiles.size(); i++) {
            UserProfile currentProfile = profiles.get(i);

            // If the flag is true, inject a unique ID into the email
            if (randomizeEmail) {
                String originalEmail = currentProfile.getEmail();
                // Adding 'i' ensures that even lightning-fast loops generate 100% unique IDs
                String uniqueID = System.currentTimeMillis() + "x" + i; 
                String randomizedEmail = originalEmail.replace("@", "+" + uniqueID + "@");
                currentProfile.setEmail(randomizedEmail);
            }

            result[i][0] = currentProfile;
        }
        return result;
    }

    // --- 1. EXACT TRUE DATA ---
    // Use this for tests where you just need to login with an existing user
    @DataProvider(name = "userProfile")
    public Object[][] getUserProfile() {
        return getProfileData("UserProfiles.json", false);
    }

    // --- 2. RANDOMIZED TRUE DATA ---
    // Use this for tests where you need to register a brand new user from scratch
    @DataProvider(name = "randomUserProfile")
    public Object[][] getRandomUserProfile() {
        return getProfileData("UserProfiles.json", true);
    }

    // --- 3. EXACT FALSE DATA ---
    // Use this for testing invalid logins or "Email already exists" errors
    @DataProvider(name = "falseUserProfile")
    public Object[][] getFalseUserProfile() {
        return getProfileData("FalseUserProfiles.json", false);
    }
}