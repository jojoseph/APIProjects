package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class UserTests {

    // Stores the created user ID for reuse across test methods
    String userId;

    /**
     * Validates that the user API response matches the expected JSON schema
     * Priority 1: Runs first in the test sequence
     */
    @Test(priority = 1)
    public void validateUserSchema() {
        given() // Start building the request specification
            .when().get("https://api.example.com/user/1") // Send GET request
            .then() // Begin assertions
            .assertThat()
            .body(matchesJsonSchemaInClasspath("UserSchema.json")); //git test Validate against schema file
    }

    /**
     * Tests successful user creation
     * Priority 2: Runs after schema validation
     * Stores created user ID in class variable for subsequent tests
     */
    @Test(priority = 2)
    public void createUserSuccess() {
        String payload = "{ \"name\": \"John\", \"email\": \"john@example.com\" }";

        Response response = given()
            .header("Content-Type", "application/json") // Set content type
            .body(payload) // Attach request body
        .when()
            .post("https://api.example.com/users"); // Send POST request

        response.then().statusCode(201); // Verify 201 Created status
        userId = response.jsonPath().getString("id"); // Extract user ID from response
        assertNotNull(userId); // Validate ID was generated
    }

    /**
     * Retrieves the user created in createUserSuccess()
     * Priority 3: Runs after user creation
     * dependsOnMethods ensures this only runs if createUserSuccess passes
     */
    @Test(priority = 3, dependsOnMethods = "createUserSuccess")
    public void getUser() {
        given()
            .pathParam("id", userId) // Inject stored user ID into URL
        .when()
            .get("https://api.example.com/users/{id}") // Send GET with path parameter
        .then()
            .statusCode(200) // Verify 200 OK
            .body("id", equalTo(Integer.parseInt(userId))); // Validate response contains correct ID
    }

    /**
     * DataProvider for parameterized testing
     * @return 2D array of test data (name/email combinations)
     */
    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][] {
            {"Alice", "alice@test.com"},
            {"Bob", "bob@test.com"}
        };
    }

    /**
     * Parameterized test for creating multiple users
     * @param name User name from data provider
     * @param email User email from data provider
     */
    @Test(dataProvider = "userData")
    public void createMultipleUsers(String name, String email) {
        given()
            .header("Content-Type", "application/json")
            .body("{ \"name\": \"" + name + "\", \"email\": \"" + email + "\" }") // Dynamic body
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201); // Verify creation for each data set
    }
}