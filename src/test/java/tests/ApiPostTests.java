package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pages.ApiPostPage;
import base.ApiBasePage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiPostTests extends ApiBasePage {

    private static ApiPostPage apiPostPage;
    private static int postId;

    @BeforeAll
    public static void init() {
        ApiBasePage.setup();
        apiPostPage = new ApiPostPage();
    }

    @Test
    @Order(1)
    @DisplayName("Create Post via API")
    public void testCreatePost() {
        Response response = apiPostPage.createPost("Post created via API", "This is a test post created using RestAssured.");
        assertEquals(201, response.statusCode());
        postId = response.jsonPath().getInt("id");
        assertNotNull(postId);
        System.out.println("✅ Created Post ID: " + postId);
    }

    @Test
    @Order(2)
    @DisplayName("Get Post by ID")
    public void testGetPost() {
        Response response = apiPostPage.getPost(postId);
        assertEquals(200, response.statusCode());
        assertEquals(postId, response.jsonPath().getInt("id"));
        System.out.println("✅ Fetched Post ID: " + postId);
    }

    @Test
    @Order(3)
    @DisplayName("Update Post")
    public void testUpdatePost() {
        Response response = apiPostPage.updatePost(postId, "Updated Title via API", "This post was updated using PUT request.");
        assertEquals(200, response.statusCode());
        assertEquals("Updated Title via API", response.jsonPath().getString("title.rendered"));
        System.out.println("✅ Post updated successfully");
    }

    @Test
    @Order(4)
    @DisplayName("Delete Post")
    public void testDeletePost() {
        Response response = apiPostPage.deletePost(postId);
        assertEquals(200, response.statusCode());
        assertTrue(response.jsonPath().getBoolean("deleted"));
        System.out.println("✅ Post deleted successfully");
    }

    @AfterAll
    public static void cleanup() {
        ApiBasePage.teardown();
    }
}
