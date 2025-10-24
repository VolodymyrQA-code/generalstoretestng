package pages;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiPostPage {

    public Response createPost(String title, String content) {
        String requestBody = """
            {
              "title": "%s",
              "content": "%s",
              "status": "publish"
            }
        """.formatted(title, content);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getPost(int postId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts/" + postId)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response updatePost(int postId, String newTitle, String newContent) {
        String requestBody = """
            {
              "title": "%s",
              "content": "%s"
            }
        """.formatted(newTitle, newContent);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/posts/" + postId)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response deletePost(int postId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/posts/" + postId + "?force=true")
                .then()
                .log().all()
                .extract()
                .response();
    }
}
