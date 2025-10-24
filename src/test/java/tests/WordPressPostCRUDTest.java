package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WordPressPostCRUDTest {

    private static int postId; 

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://dev.emeli.in.ua/wp-json/wp/v2";
        RestAssured.authentication = RestAssured.preemptive()
                .basic("admin", "Engineer_123");
    }

    @Test(priority = 1)
    public void createPost() {
        String requestBody = """
            {
              "title": "Post created via API",
              "content": "This is a test post created using RestAssured.",
              "status": "publish"
            }
        """;

        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .post("/posts")
                .then()
                    .log().all()
                    .extract().response();

        Assert.assertEquals(response.statusCode(), 201);
        postId = response.jsonPath().getInt("id");
        System.out.println("Created Post ID: " + postId);
        Assert.assertNotNull(postId);
    }

    @Test(priority = 2, dependsOnMethods = "createPost")
    public void getPost() {
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("/posts/" + postId)
                .then()
                    .log().all()
                    .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), postId);
    }

    @Test(priority = 3, dependsOnMethods = "getPost")
    public void updatePost() {
        String requestBody = """
            {
              "title": "Updated Title via API",
              "content": "This post was updated using PUT request."
            }
        """;

        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .put("/posts/" + postId)
                .then()
                    .log().all()
                    .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title.rendered"), "Updated Title via API");
    }

    @Test(priority = 4, dependsOnMethods = "updatePost")
    public void deletePost() {
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .delete("/posts/" + postId + "?force=true")
                .then()
                    .log().all()
                    .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("deleted"));
        System.out.println("Post deleted successfully");
    }
}
