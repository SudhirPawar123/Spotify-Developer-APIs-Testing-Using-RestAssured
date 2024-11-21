package json.db;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestingJsonDb {
    @Test
    public void getPostData(){
        Response response = given()
                .when()
                .get("http://localhost:3000/posts");

        response.prettyPrint();
    }
@Test
public void addPostData(){
        Response response = given()
                .accept("application/json")
            .header("Content-Type", "application/json")
            .body("    {\n" +
                    "            \"id\": \"3\",\n" +
                    "            \"title\": \"Api testing\",\n" +
                    "            \"author\": \"Ram\"\n" +
                    "        }")
            .when()
            .post("http://localhost:3000/posts");
        response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 201);
}

    @Test
    public void updatePostData(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("  {\n" +
                        "        \"id\": \"2\",\n" +
                        "        \"title\": \"Api testing\",\n" +
                        "        \"author\": \"Somesh\"\n" +
                        "}")
                .when()
                .put("http://localhost:3000/posts/2?id=2");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deletePostData(){
        Response response = given()
                .when()
                .delete("http://localhost:3000/posts/2?id=2");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getCommentsData(){
        Response response = given()
                .when()
                .get("http://localhost:3000/comments");
        response.prettyPrint();
    }

    @Test
    public void addCommentsData(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("  {\n" +
                        "        \"id\": \"1\",\n" +
                        "        \"body\": \"some special comment\",\n" +
                        "        \"postId\": 2\n" +
                        "    }")
                .when()
                .post("http://localhost:3000/comments");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void updateCommentsData(){
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"body\": \"some special about actors comment\",\n" +
                        "    \"postId\": 2\n" +
                        "}")
                .when()
                .put("http://localhost:3000/comments/1?id=1");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deleteCommentsData(){
        Response response = given()
                .when()
                .delete("http://localhost:3000/comments/1?id=2");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test

    public void getProfileData(){
        Response response = given()
                .when()
                .get("http://localhost:3000/profile");
        response.prettyPrint();
    }
    @Test
    public void postProfileData(){
        Response response=given()
                .header("Content-Type", "application/json")
                .body("   {{\n" +
                        "    \"name\": \"Darshan\"\n" +
                        "}\n")
                .when()
                .post("http://localhost:3000/profile");
        response.prettyPrint();
    }
    @Test
    public void deleteProfileData(){
        Response response=given()
                .accept("application/json")
                .body(" {\n" +
                        "    \"name\": \"Rajkumar Patil\"\n" +
                        "}")
                .when()
                .delete("http://localhost:3000/profile/1");
        response.prettyPrint();

    }
    @Test
    public void updateProfileData(){
        Response response=given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body(" {\n" +
                        "    \"name\": \"Rajkumar Patil\"\n" +
                        "}")
                .when()
                .delete("http://localhost:3000/profile");
        response.prettyPrint();
    }

}

