package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Posts;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.TestNGListener;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.Endpoints.postEndpoint;

public class MyStepPosts {
    Random rand = new Random();
    int id = 1000;
    static  Posts posts;
    static JsonPath jsonpath;

    static int userID;

    static Response putResponse;


    @Given("a post")
    public void aPost() {

//            String requestBody = "{\"user_id\":"+ rand.nextInt(id) + 1 +",\"title\":\"REST API2 \",\"body\":\"is this most used in software testing nowadays\"}";

        JSONObject sampleData = (JSONObject) TestNGListener.data_post.get("putRequest");
        posts = new Posts(rand.nextInt(id) + 1, (String) sampleData.get("title"), (String) sampleData.get("body"));
        Response response = given().body(posts)
                .when().post(postEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Posts_schema.json")).statusCode(201).extract().response();
        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
    }

    @When("updating the post")
    public void updatingThePost() {
        String putRequestBody = "{\"user_id\":" + userID + ",\"title\":\"REST API3 \",\"body\":\"is this most used in software testing nowadays\"}";

        putResponse = given().body(putRequestBody)
                .when().put(postEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("the post is updated")
    public void thePostIsUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @Given("a post to del")
    public void aPostToDel() {
        JSONObject sampleData = (JSONObject)TestNGListener.data_post.get("delRequest");
        posts = new Posts(rand.nextInt(id) * 2, (String) sampleData.get("title"), (String) sampleData.get("body"));

        Response response1 = given().body(posts)
                .when().post(postEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Posts_schema.json")).statusCode(201).extract().response();
        jsonpath = new JsonPath(response1.asString());
        userID = jsonpath.getInt("id");

    }

    @When("delete the post")
    public void deleteThePost() {
        given()
                .when().delete(postEndpoint + "/" + userID)
                .then().statusCode(204).extract().response();

    }

    @Then("the post is delete")
    public void thePostIsDelete() {
        given()
                .when().get(postEndpoint + "/" + userID)
                .then().statusCode(404).extract().response();
    }
}
