package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Comment;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.TestNGListener;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.Endpoints.commentEndpoint;

public class MyStepComment {
    static int userID;
    static Response putResponse ;
    static Comment comment;

    static JsonPath jsonpath;

    Random rand = new Random();
    static int id = 100;
    @Given("a comment")
    public void aComment() {
        String email = "\"testuser" + System.currentTimeMillis() + "@15ce.com\"";

//        String requestBody = "{\"post_id\":" + rand.nextInt(id) + 1 + " ,\"name\":\"sai" + rand.nextInt(id) + "\",\"email\": " + email + ",\"body\":\"is this most used in software testing nowadays234\"}";

        JSONObject sampleData = (JSONObject) TestNGListener.data_comment.get("putRequest");
        comment = new Comment((Long) sampleData.get("post_id"), (String) sampleData.get("name"), (String) sampleData.get("email"), (String) sampleData.get("body"));


        Response response = given()
                .body(comment)
                .when().post(commentEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Comment_schema.json")).statusCode(201).extract().response();
        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
    }

    @When("updating the comment")
    public void updatingTheComment() {
        String putRequestBody = "{\"user\":\"must exist5\",\"user_id\":" + rand.nextInt(id) + 1 + ",\"title\":\"REST API3 \",\"body\":\"is this most used in software testing nowadays\"}";

        putResponse = given().body(putRequestBody).when().put(commentEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("the comment is updated")
    public void theCommentIsUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @Given("a comment to del")
    public void aCommentToDel() {

        JSONObject sampleData = (JSONObject) TestNGListener.data_comment.get("delRequest");
        Comment comment = new Comment((Long) sampleData.get("post_id"), (String) sampleData.get("name"), (String) sampleData.get("email"), (String) sampleData.get("body"));


        Response response1 = given().body(comment).when().post(commentEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Comment_schema.json")).statusCode(201).extract().response();

//        ObjectMapper objectMapper = new ObjectMapper();

        jsonpath = new JsonPath(response1.asString());
        userID = jsonpath.getInt("id");
    }

    @When("delete the comment")
    public void deleteTheComment() {
        given()
                .when().delete(commentEndpoint + "/" + userID)
                .then().statusCode(204).extract().response();
    }

    @Then("the comment is delete")
    public void theCommentIsDelete() {
        given()
                .when().get(commentEndpoint + "/" + userID)
                .then().statusCode(404).extract().response();

    }
}
