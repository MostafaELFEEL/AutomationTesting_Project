package TestScripts;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("API Testing")
public class APITests extends BaseTest {

    // --- Configuration ---

    @BeforeClass
    @Step("Setup: Initialize Base URI for API Tests")
    public void setupAPI() {
        RestAssured.baseURI = "https://automationexercise.com";
    }

    // --- Product API Tests ---

    @Test(groups = {"API", "Products"})
    @Description("API 1: Get All Products List - Verify that a GET request successfully retrieves the entire list of products.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-01")
    @TmsLink("TC-API-01")
    @Feature("Products API")
    @Story("Retrieve All Products")
    public void api01_getAllProductsList() {

        // 1. Send GET request
        Response response = RestAssured.given()
                .when()
                .get("/api/productsList");

        // 2. Extract raw string and attach to Allure Report
        String rawBody = response.asString();
        Allure.addAttachment("API 1 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags so REST Assured can parse it as pure JSON
        String cleanJson = rawBody.replace("<html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .replace("</html>", "")
                .trim();

        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertFalse(js.getList("products").isEmpty(), "The products list is empty!");
        Assert.assertNotNull(js.getString("products[0].name"), "First product name is missing.");
    }

    @Test(groups = {"API", "Products"})
    @Description("API 2: POST To All Products List - Verify that sending a POST request to the products list endpoint returns a 405 error.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-02")
    @TmsLink("TC-API-02")
    @Feature("Products API")
    @Story("Unsupported Methods - Products")
    public void api02_postToAllProductsList() {

        // 1. Send POST request
        Response response = RestAssured.given()
                .when()
                .post("/api/productsList");

        // 2. Extract raw string and attach to Allure Report
        String rawBody = response.asString();
        Allure.addAttachment("API 2 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .replace("</html>", "")
                .trim();

        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 405, "JSON responseCode mismatch. Expected 405.");
        Assert.assertEquals(
                js.getString("message"),
                "This request method is not supported.",
                "Error message text mismatch."
        );
    }

    // --- Brands API Tests ---

    @Test(groups = {"API", "Brands"})
    @Description("API 3: Get All Brands List - Verify that a GET request successfully retrieves the entire list of brands.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-03")
    @TmsLink("TC-API-03")
    @Feature("Brands API")
    @Story("Retrieve All Brands")
    public void api03_getAllBrandsList() {

        // 1. Send GET request
        Response response = RestAssured.given()
                .when()
                .get("/api/brandsList");

        // 2. Extract raw string and attach to Allure Report
        String rawBody = response.asString();
        Allure.addAttachment("API 3 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .replace("</html>", "")
                .trim();

        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertFalse(js.getList("brands").isEmpty(), "The brands list is empty!");
        Assert.assertNotNull(js.getString("brands[0].brand"), "First brand name is missing.");
    }

    @Test(groups = {"API", "Brands"})
    @Description("API 4: PUT To All Brands List - Verify that sending a PUT request to the brands list endpoint returns a 405 error.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-04")
    @TmsLink("TC-API-04")
    @Feature("Brands API")
    @Story("Unsupported Methods - Brands")
    public void api04_putToAllBrandsList() {

        // 1. Send PUT request
        Response response = RestAssured.given()
                .when()
                .put("/api/brandsList");

        // 2. Extract raw string and attach to Allure Report
        String rawBody = response.asString();
        Allure.addAttachment("API 4 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .replace("</html>", "")
                .trim();

        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 405, "JSON responseCode mismatch. Expected 405.");
        Assert.assertEquals(
                js.getString("message"),
                "This request method is not supported.",
                "Error message text mismatch."
        );
    }

    // --- Search Product API Tests ---

    @Test(groups = {"API", "Products", "Search"})
    @Description("API 5: POST To Search Product - Verify that searching for a product returns a 200 status and a list of matching products.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-05")
    @TmsLink("TC-API-05")
    @Feature("Search API")
    @Story("Search with valid parameter")
    public void api05_postToSearchProduct() {

        // 1. Send POST request with form parameter
        Response response = RestAssured.given()
                .formParam("search_product", "top")
                .when()
                .post("/api/searchProduct");

        // 2. Extract raw string and attach to Allure
        String rawBody = response.asString();
        Allure.addAttachment("API 5 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertFalse(js.getList("products").isEmpty(), "The searched products list is empty!");
    }

    @Test(groups = {"API", "Products", "Search"})
    @Description("API 6: POST To Search Product without parameter - Verify that omitting the search parameter returns a 400 Bad Request.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-06")
    @TmsLink("TC-API-06")
    @Feature("Search API")
    @Story("Search with missing parameter")
    public void api06_postToSearchProductWithoutParam() {

        // 1. Send POST request WITHOUT parameters
        Response response = RestAssured.given()
                .when()
                .post("/api/searchProduct");

        // 2. Extract raw string and attach to Allure
        String rawBody = response.asString();
        Allure.addAttachment("API 6 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 400, "JSON responseCode mismatch. Expected 400.");
        Assert.assertEquals(js.getString("message"), "Bad request, search_product parameter is missing in POST request.", "Error message text mismatch.");
    }

    @Test(priority = 2, dataProvider = UserProfiles, dataProviderClass = TestDataProvider.TestDataProvider.class, groups = {"API", "Authentication", "Account_Lifecycle"})
    @Description("API 7: POST To Verify Login with valid details - Verify that providing a valid email and password returns User exists message.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-07")
    @TmsLink("TC-API-07")
    @Feature("Login API")
    @Story("Verify valid login")
    public void api07_postToVerifyLoginWithValidDetails(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send POST request dynamically using the DataProvider POJO
        Response response = RestAssured.given()
                .formParam("email", userProfile.getEmail())
                .formParam("password", userProfile.getPassword())
                .when()
                .post("/api/verifyLogin");

        // 2. Extract raw string and attach to Allure
        String rawBody = response.asString();
        Allure.addAttachment("API 7 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertEquals(js.getString("message"), "User exists!", "Success message text mismatch.");
    }

    @Test(groups = {"API", "Authentication"})
    @Description("API 8: POST To Verify Login without email - Verify that omitting the email parameter returns a 400 Bad Request.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-08")
    @TmsLink("TC-API-08")
    @Feature("Login API")
    @Story("Verify login with missing email")
    public void api08_postToVerifyLoginWithoutEmail() {

        // 1. Send POST request with ONLY the password parameter
        Response response = RestAssured.given()
                .formParam("password", "test")
                .when()
                .post("/api/verifyLogin");

        // 2. Extract raw string and attach to Allure
        String rawBody = response.asString();
        Allure.addAttachment("API 8 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 400, "JSON responseCode mismatch. Expected 400.");
        Assert.assertEquals(js.getString("message"), "Bad request, email or password parameter is missing in POST request.", "Error message text mismatch.");
    }

    @Test(groups = {"API", "Authentication"})
    @Description("API 9: DELETE To Verify Login - Verify that sending a DELETE request to the verify login endpoint returns a 405 error.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-09")
    @TmsLink("TC-API-09")
    @Feature("Login API")
    @Story("Unsupported Methods - Login")
    public void api09_deleteToVerifyLogin() {

        // 1. Send DELETE request
        Response response = RestAssured.given()
                .when()
                .delete("/api/verifyLogin");

        // 2. Extract raw string and attach to Allure
        String rawBody = response.asString();
        Allure.addAttachment("API 9 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 405, "JSON responseCode mismatch. Expected 405.");
        Assert.assertEquals(js.getString("message"), "This request method is not supported.", "Error message text mismatch.");
    }

    @Test(
            dataProvider = falseUserProfiles,
            dataProviderClass = TestDataProvider.TestDataProvider.class, // Make sure the import/path matches your project
            groups = {"API", "Authentication"}
    )
    @Description("API 10: POST To Verify Login with invalid details - Verify that providing an invalid email and password returns a 404 error.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-10")
    @TmsLink("TC-API-10")
    @Feature("Login API")
    @Story("Verify invalid login")
    public void api10_postToVerifyLoginWithInvalidDetails(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send POST request dynamically using the invalid DataProvider POJO
        Response response = RestAssured.given()
                .formParam("email", userProfile.getEmail())
                .formParam("password", userProfile.getPassword())
                .when()
                .post("/api/verifyLogin");

        // 2. Extract and attach
        String rawBody = response.asString();
        Allure.addAttachment("API 10 Response Body", "text/html", rawBody);

        // 3. Strip HTML tags
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 4. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 404, "JSON responseCode mismatch. Expected 404.");
        Assert.assertEquals(js.getString("message"), "User not found!", "Error message text mismatch.");
    }

    @Test(priority = 1, dataProvider = UserProfiles, dataProviderClass = TestDataProvider.TestDataProvider.class, groups = {"API", "Account_Lifecycle"})
    @Description("API 11: POST To Create/Register User Account - Create a new user using the DataProvider.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-11")
    @TmsLink("TC-API-11")
    @Feature("Account API")
    @Story("Create User Account")
    public void api11_postToCreateAccount(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send POST request mapping POJO data to form parameters
        Response response = RestAssured.given()
                .formParam("name", userProfile.getName())
                .formParam("email", userProfile.getEmail())
                .formParam("password", userProfile.getPassword())
                .formParam("title", userProfile.getTitle()) // Assuming title isn't in POJO, update if it is
                .formParam("birth_date", userProfile.getDay())
                .formParam("birth_month", userProfile.getMonth())
                .formParam("birth_year", userProfile.getYear())
                .formParam("firstname", userProfile.getFirstName())
                .formParam("lastname", userProfile.getLastName())
                .formParam("company", userProfile.getCompany())
                .formParam("address1", userProfile.getAddress()) // Update getter if needed
                .formParam("address2", userProfile.getAddress2())
                .formParam("country", userProfile.getCountry())
                .formParam("zipcode", userProfile.getZipcode())
                .formParam("state", userProfile.getState())
                .formParam("city", userProfile.getCity())
                .formParam("mobile_number", userProfile.getMobileNumber())
                .when()
                .post("/api/createAccount");

        // 2. Extract, attach, and clean
        String rawBody = response.asString();
        Allure.addAttachment("API 11 Response Body", "text/html", rawBody);
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 3. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 201, "JSON responseCode mismatch. Expected 201.");
        Assert.assertEquals(js.getString("message"), "User created!", "Success message text mismatch.");
    }

    @Test(priority = 2, dataProvider = UserProfiles, dataProviderClass = TestDataProvider.TestDataProvider.class, groups = {"API", "Account_Lifecycle"})
    @Description("API 14: GET user account detail by email - Retrieve the account details we just created.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-14")
    @TmsLink("TC-API-14")
    @Feature("Account API")
    @Story("Get User Details")
    public void api14_getUserDetailByEmail(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send GET request using query parameters
        Response response = RestAssured.given()
                .queryParam("email", userProfile.getEmail())
                .when()
                .get("/api/getUserDetailByEmail");

        // 2. Extract, attach, and clean
        String rawBody = response.asString();
        Allure.addAttachment("API 14 Response Body", "text/html", rawBody);
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 3. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");

        // Assert that the email returned matches the email from our POJO
        Assert.assertEquals(js.getString("user.email"), userProfile.getEmail(), "Email mismatch in user details!");
    }

    @Test(priority = 3, dataProvider = UserProfiles, dataProviderClass = TestDataProvider.TestDataProvider.class, groups = {"API", "Account_Lifecycle"})
    @Description("API 13: PUT METHOD To Update User Account - Update the existing user account.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-13")
    @TmsLink("TC-API-13")
    @Feature("Account API")
    @Story("Update User Account")
    public void api13_putToUpdateAccount(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send PUT request (Updating the company name as an example)
        Response response = RestAssured.given()
                .formParam("name", userProfile.getName())
                .formParam("email", userProfile.getEmail())
                .formParam("password", userProfile.getPassword())
                .formParam("title", userProfile.getTitle())
                .formParam("birth_date", userProfile.getDay())
                .formParam("birth_month", userProfile.getMonth())
                .formParam("birth_year", userProfile.getYear())
                .formParam("firstname", userProfile.getFirstName())
                .formParam("lastname", userProfile.getLastName())
                .formParam("company", "Updated Automation Co") // <-- Data modification
                .formParam("address1", userProfile.getAddress())
                .formParam("address2", userProfile.getAddress2())
                .formParam("country", userProfile.getCountry())
                .formParam("zipcode", userProfile.getZipcode())
                .formParam("state", userProfile.getState())
                .formParam("city", userProfile.getCity())
                .formParam("mobile_number", userProfile.getMobileNumber())
                .when()
                .put("/api/updateAccount");

        // 2. Extract, attach, and clean
        String rawBody = response.asString();
        Allure.addAttachment("API 13 Response Body", "text/html", rawBody);
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 3. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertEquals(js.getString("message"), "User updated!", "Success message text mismatch.");
    }

    @Test(priority = 4, dataProvider = UserProfiles, dataProviderClass = TestDataProvider.TestDataProvider.class, groups = {"API", "Account_Lifecycle"})
    @Description("API 12: DELETE METHOD To Delete User Account - Clean up the created data.")
    @Owner("Mostafa Ashraf Ibrahim Ali El-Feel")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation", url = "https://automationexercise.com/api_list")
    @Issue("API-12")
    @TmsLink("TC-API-12")
    @Feature("Account API")
    @Story("Delete User Account")
    public void api12_deleteAccount(Utils.PojoPack.UserProfile userProfile) {

        // 1. Send DELETE request using form parameters
        Response response = RestAssured.given()
                .formParam("email", userProfile.getEmail())
                .formParam("password", userProfile.getPassword())
                .when()
                .delete("/api/deleteAccount");

        // 2. Extract, attach, and clean
        String rawBody = response.asString();
        Allure.addAttachment("API 12 Response Body", "text/html", rawBody);
        String cleanJson = rawBody.replace("<html>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "").trim();
        JsonPath js = new JsonPath(cleanJson);

        // 3. Safe Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status Code mismatch.");
        Assert.assertEquals(js.getInt("responseCode"), 200, "JSON responseCode mismatch.");
        Assert.assertEquals(js.getString("message"), "Account deleted!", "Success message text mismatch.");
    }
}