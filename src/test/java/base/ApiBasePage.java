package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class ApiBasePage {

    protected static final String BASE_URI = "https://dev.emeli.in.ua/wp-json/wp/v2";
    protected static final String USERNAME = "admin";
    protected static final String PASSWORD = "Engineer_123";

    @BeforeAll
    public static void setup() {
        System.out.println("=== Setting up API test configuration ===");
        RestAssured.baseURI = BASE_URI;
        RestAssured.authentication = RestAssured.preemptive().basic(USERNAME, PASSWORD);
    }

    @AfterAll
    public static void teardown() {
        System.out.println("=== API Test Execution Finished ===");
    }
}
