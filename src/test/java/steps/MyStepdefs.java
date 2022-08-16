package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.SuperHero;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static utils.TestNGListener.data;

public class MyStepdefs {
    static SuperHero superHero, putRequestBody, deleteRequestBody;

    static JsonPath jsonpath;
    static int userID;

    static String error;
    Response response, putResponse;
    JSONObject sampleData, putData, deleteData, postInvalid;

    @Given("a user details")
    public void aUserDetails() {
        sampleData = (JSONObject) data.get("createRequest");
        putData = (JSONObject) data.get("putRequest");
        deleteData = (JSONObject) data.get("delRequest");
    }

    @When("baseurl is given")
    public void baseurlIsGiven() {
        response = given().when().get(Endpoints.SuperHeroEndpoint);

    }

    @Then("display list")
    public void displayList() {
        Assert.assertEquals(200, response.getStatusCode());

    }


    @Given("create superhero")
    public void createSuperhero() {
//        creating single user and display a single user
        superHero = new SuperHero((String) sampleData.get("name"), (String) sampleData.get("superName"),
                (String) sampleData.get("profession"), ((Long) sampleData.get("age")).intValue(), (boolean) sampleData.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
    }

    @When("baseurl is given with created Id")
    public void baseurlIsGivenWithCreatedId() {
        response = given()
                .when().get(Endpoints.SuperHeroEndpoint + "/" + userID);
    }

    @Then("display single superHero")
    public void displaySingleSuperHero() {

        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
        Assert.assertEquals(200, response.getStatusCode());

    }


    @When("searching using invalid id")
    public void searchingUsingInvalidId() {
//        when accessing using invalid id
        response = given()
                .when().get(Endpoints.SuperHeroEndpoint + "/" + 1000);
        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("error");

    }

    @Then("display error")
    public void displayError() {
        Assert.assertEquals(error, "Internal Server Error");
        Assert.assertEquals(500, response.getStatusCode());
//        System.out.println("please enter valid id:"+ error);
    }

    @When("searching using invalid url")
    public void searchingUsingInvalidUrl() {
        response = given()
                .when().get(Endpoints.SuperHeroEndpoint + "s");
        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("error");
    }

    @Then("display message")
    public void displayMessage() {
        Assert.assertEquals(error, "Not Found");
        Assert.assertEquals(404, response.getStatusCode());
    }


    @When("post the superhero")
    public void postTheSuperhero() {
//        posting a new superhero
        superHero = new SuperHero((String) sampleData.get("name"), (String) sampleData.get("superName"),
                (String) sampleData.get("profession"), ((Long) sampleData.get("age")).intValue(), (boolean) sampleData.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

    }

    @Then("the super hero is created")
    public void theSuperHeroIsCreated() {

        Assert.assertEquals(201, response.getStatusCode());
    }


    @When("creating a superhero with no name value")
    public void creatingASuperheroWithNoNameValue() {
        postInvalid = (JSONObject) data.get("postInvalidName");

        superHero = new SuperHero((String) postInvalid.get("name"), (String) postInvalid.get("superName"),
                (String) postInvalid.get("profession"), ((Long) postInvalid.get("age")).intValue(), (boolean) postInvalid.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("message");

    }

    @Then("the super hero is not created")
    public void theSuperHeroIsNotCreated() {
        Assert.assertEquals(error, "Name is required");
    }


    @When("creating a superhero with no super name value")
    public void creatingASuperheroWithNoSuperNameValue() {
        postInvalid = (JSONObject) data.get("postInvalidSuperName");

        superHero = new SuperHero((String) postInvalid.get("name"), (String) postInvalid.get("superName"),
                (String) postInvalid.get("profession"), ((Long) postInvalid.get("age")).intValue(), (boolean) postInvalid.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("message");
    }

    @Then("cannot create without super name")
    public void cannotCreateWithoutSuperName() {
        Assert.assertEquals(error, "Super Name is required");
    }

    @When("creating a superhero with no profession value")
    public void creatingASuperheroWithNoProfessionValue() {
        postInvalid = (JSONObject) data.get("postInvalidProfession");

        superHero = new SuperHero((String) postInvalid.get("name"), (String) postInvalid.get("superName"),
                (String) postInvalid.get("profession"), ((Long) postInvalid.get("age")).intValue(), (boolean) postInvalid.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("message");
    }

    @Then("cannot create without profession")
    public void cannotCreateWithoutProfession() {
        Assert.assertEquals(error, "Profession is required");
    }

    @When("creating a superhero with no age value")
    public void creatingASuperheroWithNoAgeValue() {
        postInvalid = (JSONObject) data.get("postInvalidAge");

        superHero = new SuperHero((String) postInvalid.get("name"), (String) postInvalid.get("superName"),
                (String) postInvalid.get("profession"), 0, (boolean) postInvalid.get("canFly"));

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("message");
    }

    @Then("cannot create without age")
    public void cannotCreateWithoutAge() {
        Assert.assertEquals(error, "Age is required");
    }


    @When("creating a superhero with no canFly value")
    public void creatingASuperheroWithNoCanFlyValue() {

        postInvalid = (JSONObject) data.get("postInvalidcanFly");

        superHero = new SuperHero((String) postInvalid.get("name"), (String) postInvalid.get("superName"),
                (String) postInvalid.get("profession"), ((Long) postInvalid.get("age")).intValue());

        response = given()
                .body(superHero)
                .when().post(Endpoints.SuperHeroEndpoint);


    }

    @Then("create with default canFly value")
    public void createWithDefaultCanFlyValue() {
        Assert.assertEquals(201, response.getStatusCode());
    }


    @Given("Create a user")
    public void createAUser() {
        //create request for updating user


        putRequestBody = new SuperHero((String) putData.get("name"), (String) putData.get("superName"),
                (String) putData.get("profession"), ((Long) putData.get("age")).intValue(), (boolean) sampleData.get("canFly"));

        Response response = given()
                .body(putRequestBody)
                .when().post(Endpoints.SuperHeroEndpoint)
                .then().statusCode(201).extract().response();
        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
    }


    @When("updating a user name")
    public void updatingAUserName() {

        JSONObject jsonObject = (JSONObject) data.get("updateData");
        superHero.setName((String) jsonObject.get("name"));
//        putRequestBody.setName("sai");

        putResponse = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("User name must be updated")
    public void userNameMustBeUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @When("updating super name of user")
    public void updatingSuperNameOfUser() {
        JSONObject jsonObject = (JSONObject) data.get("updateData");
        superHero.setSuperName((String) jsonObject.get("superName"));
//        putRequestBody.setName("sai");

        putResponse = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("User super name must be updated")
    public void userSuperNameMustBeUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @When("updating profession of user")
    public void updatingProfessionOfUser() {
        JSONObject jsonObject = (JSONObject) data.get("updateData");
        superHero.setProfession((String) jsonObject.get("profession"));
//        putRequestBody.setName("sai");

        putResponse = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("User profession must be updated")
    public void userProfessionMustBeUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);
    }

    @When("updating age of user")
    public void updatingAgeOfUser() {
        JSONObject jsonObject = (JSONObject) data.get("updateData");
        superHero.setAge(((Long) putData.get("age")).intValue());
//        putRequestBody.setName("sai");

        putResponse = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("User age must be updated")
    public void userAgeMustBeUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);

    }

    @When("updating canFly  status of user")
    public void updatingCanFlyStatusOfUser() {
        JSONObject jsonObject = (JSONObject) data.get("updateData");
        superHero.setCanFly((boolean) jsonObject.get("canFly"));
//        putRequestBody.setName("sai");

        putResponse = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();
    }

    @Then("User canFly status must be updated")
    public void userCanFlyStatusMustBeUpdated() {
        jsonpath = new JsonPath(putResponse.asString());
        int userID2 = jsonpath.getInt("id");
        Assert.assertEquals(userID, userID2);

    }

    @When("update invalid id")
    public void updateInvalidId() {
        response = given()
                .body(putRequestBody)
                .when().put(Endpoints.SuperHeroEndpoint + "/" + 1000);

        jsonpath = new JsonPath(response.asString());
        error = jsonpath.getString("error");
        System.out.println("error"+error);
    }

    @Then("display invalid error")
    public void displayInvalidError() {
        Assert.assertEquals(error, "Internal Server Error");
    }


    @Given("Create a user to delete")
    public void createAUserToDelete() {
        deleteData = (JSONObject) data.get("delRequest");

        deleteRequestBody = new SuperHero((String) deleteData.get("name"), (String) deleteData.get("superName"),
                (String) deleteData.get("profession"), ((Long) deleteData.get("age")).intValue(), (boolean) deleteData.get("canFly"));

        response = given()
                .body(deleteRequestBody)
                .when().post(Endpoints.SuperHeroEndpoint);

        jsonpath = new JsonPath(response.asString());
        userID = jsonpath.getInt("id");
//        error = jsonpath.getString("message");
    }

    @When("delete the super hero")
    public void deleteTheSuperHero() {
        response = given().when().delete(Endpoints.SuperHeroEndpoint + "/" + userID);
        jsonpath = new JsonPath(response.asString());


    }

    @Then("super hero Deleted")
    public void superHeroDeleted() {
        error = response.asString();
        Assert.assertEquals("Deleted successfully...!", error);
        Assert.assertEquals(200, response.getStatusCode());


    }

    @When("deleted invalid super hero")
    public void deletedInvalidSuperHero() {
        response = given().when().delete(Endpoints.SuperHeroEndpoint + "/" + 1000);
        jsonpath = new JsonPath(response.asString());
    }

    @Then("display deleted msg")
    public void displayDeletedMsg() {
        error = response.asString();
        Assert.assertEquals("Deleted successfully...!", error);
        Assert.assertEquals(200, response.getStatusCode());
    }


}
