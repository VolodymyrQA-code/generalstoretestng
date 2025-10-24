package tests;

import io.restassured.response.Response;
import org.testng.annotations.*;
import pages.ApiPostPage;
import base.ApiBasePage;

import static org.testng.Assert.*;

public class ApiPostTests extends ApiBasePage {

    private ApiPostPage apiPostPage;
    private int postId;

    @BeforeClass
    public void init() {
        setup(); // ApiBasePage setup
        apiPostPage = new ApiPostPage();
    }

    @Test(priority = 1, description = "Create Post via API")
    public void testCreatePost() {
        Response response = apiPostPage.createPost("Post created via API", "This is a test post created using RestAssured.");
        assertEquals(response.statusCode(), 201);
        postId = response.jsonPath().getInt("id");
        assertNotNull(postId);
        System.out.println("✅ Created Post ID: " + postId);
    }

    @Test(priority = 2, description = "Get Post by ID", dependsOnMethods = "testCreatePost")
    public void testGetPost() {
        Response response = apiPostPage.getPost(postId);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getInt("id"), postId);
        System.out.println("✅ Fetched Post ID: " + postId);
    }

    @Test(priority = 3, description = "Update Post", dependsOnMethods = "testGetPost")
    public void testUpdatePost() {
        Response response = apiPostPage.updatePost(postId, "Updated Title via API", "This post was updated using PUT request.");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getString("title.rendered"), "Updated Title via API");
        System.out.println("✅ Post updated successfully");
    }

    @Test(priority = 4, description = "Delete Post", dependsOnMethods = "testUpdatePost")
    public void testDeletePost() {
        Response response = apiPostPage.deletePost(postId);
        assertEquals(response.statusCode(), 200);
        assertTrue(response.jsonPath().getBoolean("deleted"));
        System.out.println("✅ Post deleted successfully");
    }

    @AfterClass
    public void cleanup() {
        teardown(); // ApiBasePage teardown
    }
}
