package utils;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;

import static io.restassured.RestAssured.given;

public class TestNGListener implements ITestListener {
    public static JSONObject data,data_comment,data_post,data_todo;
    @Override
    public void onStart(ITestContext context) {
        data = UserJsonData.inputDateInit();
//        QAEnvProps.init();

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
