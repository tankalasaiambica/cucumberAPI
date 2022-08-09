package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Todos;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.TestNGListener;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.Endpoints.TodoEndpoint;

public class MyStepTodo {
    Random rand = new Random();
    int id = 1000;

    static Todos todo;
    static JsonPath jsonpath;
    static int userID;
    static Response putResponse;

    @Given("a Todo")
    public void aTodo() {

        String s = String.valueOf(java.time.Clock.systemUTC().instant());
//        String requestBody = "{\"user_id\":" + rand.nextInt(id) + 1 + ",\"title\":\"REST API" + rand.nextInt(id) + 1 + "\",\"due_on\": \"" + s + "\", \"status\":\"completed\"}";

        JSONObject sampleData = (JSONObject) TestNGListener.data_todo.get("putRequest");
        todo = new Todos(rand.nextInt(id) * 2, (String) sampleData.get("title") + rand.nextInt(id), (String) sampleData.get("due_on"), (String) sampleData.get("status"));

        Response response = given()
                .body(todo)
                .when().post(TodoEndpoint)
                .then().statusCode(201).extract().response();
        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
    }

    @When("updating the Todo")
    public void updatingTheTodo() {
        String s = String.valueOf(java.time.Clock.systemUTC().instant());
        String putRequestBody = "{\"user_id\":" + rand.nextInt(id) * 2 + ",\"title\":\"REST API" + rand.nextInt(id) + 1 + "\",\"due_on\": \"" + s + "\", \"status\":\"completed\"}";

        putResponse = given()
                .body(putRequestBody)
                .when().put(TodoEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("the Todo is updated")
    public void theTodoIsUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @Given("a Todo to del")
    public void aTodoToDel() {
        String s = String.valueOf(java.time.Clock.systemUTC().instant());
//        String requestBody = "{\"user_id\":" + rand.nextInt(id) + 1 + ",\"title\":\"REST API" + rand.nextInt(id) + 1 + "\",\"due_on\": \"" + s + "\", \"status\":\"completed\"}";

        JSONObject sampleData = (JSONObject) TestNGListener.data_todo.get("delRequest");
        Todos todo = new Todos(rand.nextInt(id) + 1, (String) sampleData.get("title"), (String) sampleData.get("due_on"), (String) sampleData.get("status"));

        Response response1 = given()
                .body(todo)
                .when().post(TodoEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Todo_schema.json")).statusCode(201).extract().response();
        jsonpath = new JsonPath(response1.asString());
        userID = jsonpath.getInt("id");
    }

    @When("delete the del")
    public void deleteTheDel() {
        given()
                .when().delete(TodoEndpoint + "/" + userID)
                .then().statusCode(204).extract().response();

    }

    @Then("the Todo is delete")
    public void theTodoIsDelete() {

        given()
                .when().get(TodoEndpoint + "/" + userID)
                .then().statusCode(404).extract().response();

    }


}
