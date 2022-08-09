package steps;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import utils.*;

import static io.restassured.RestAssured.given;

public class BaseTest {

    static JSONObject data, data_comment, data_post, data_todo;

    @BeforeTest
    public void beforeTest() {
        data = UserJsonData.inputDateInit();
        data_comment = CommentJsonData.inputDateInit();
        data_post = PostsJsonData.inputDateInit();
        data_todo = TodoJsonData.inputDateInit();
        QAEnvProps.init();
        RestAssured.baseURI = QAEnvProps.getValue("beseurl");
        RestAssured.requestSpecification = given().header(new Header("Accept", "application/json")).header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer " + QAEnvProps.getValue("token"))).log().all();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }
}