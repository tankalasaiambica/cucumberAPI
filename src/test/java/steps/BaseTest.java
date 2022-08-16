package steps;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import utils.QAEnvProps;
import utils.SuperHeroJsonData;

import static io.restassured.RestAssured.given;

public class BaseTest {

    static JSONObject data;

    @BeforeTest
    public void beforeTest() {
        data = SuperHeroJsonData.inputDateInit();
        QAEnvProps.init();
        RestAssured.baseURI = QAEnvProps.getValue("beseurl");
        RestAssured.requestSpecification = given().header(new Header("Accept", "application/json")).header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Basic Auth " + QAEnvProps.getValue("token"))).log().all();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }
}