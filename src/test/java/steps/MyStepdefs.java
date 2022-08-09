package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {

    int userID;

    User user;

    Response putResponse;

    @Given("a user")
    public void aUser() {
        //create request
        JSONObject sampleData = (JSONObject) TestNGListener.data.get("putRequest");
        String email = "tester" + System.currentTimeMillis() + "@15ce.com";
        user = new User((String) sampleData.get("name"), email, (String) sampleData.get("gender"), (String) sampleData.get("status"));

        Response response = given()
                .body(user)
                .when().post(Endpoints.userEndpoint)
                .then().statusCode(201).extract().response();
        JsonPath jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");

    }

    @When("updating the user")
    public void updatingTheUser() {
        //put request
        putResponse = given()
                .body(user)
                .when().put(Endpoints.userEndpoint + "/" + userID)
                .then().body(matchesJsonSchemaInClasspath("user_schema.json")).statusCode(200).extract().response();
    }

    @Then("the user is updated")
    public void theUserIsUpdated() throws JsonProcessingException {
        //assertion
        ObjectMapper objectMapper = new ObjectMapper();
        User responseUser = objectMapper.readValue(putResponse.asString(), User.class);
        Assert.assertEquals(user.getEmail(), responseUser.getEmail());
    }

    @Given("a user to del")
    public void aUserToDel() {
        JSONObject sampleData = (JSONObject) TestNGListener.data.get("delRequest");
        String email = "tester" + System.currentTimeMillis() + "@15ce.com";
        User user = new User((String) sampleData.get("name"), email, (String) sampleData.get("gender"), (String) sampleData.get("status"));


        Response response1 = given()
                .body(user)
                .when().post("users")
                .then().statusCode(201).extract().response();
        JsonPath jsonpath = new JsonPath(response1.asString());
        userID = jsonpath.getInt("id");
    }

    @When("delete the user")
    public void deleteTheUser() {

        given()
                .when().delete("users/" + userID)
                .then().statusCode(204).extract().response();
    }

    @Then("the user is delete")
    public void theUserIsDelete() {
        given()
                .when().get("users/" + userID)
                .then().statusCode(404).extract().response();
    }

    @When("updating the user with invalid input")
    public void updatingTheUserWithInvalidInput() {
    }

    @Then("the user cannot be updated")
    public void theUserCannotBeUpdated() {
    }

    @When("^updating the user with invalid input \"(.*)\" and \"(.*)\"$")
    public void updatingTheUserWithInvalidInputAnd(String arg0, String arg1) {
        System.out.println(arg0);
        System.out.println(arg1);
    }
}
