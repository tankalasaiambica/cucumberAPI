package utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class TodoJsonData {
    private static JSONObject data;

    public static JSONObject inputDateInit() {
        JSONParser parser = new JSONParser();
        try {
            data = (JSONObject) parser.parse(new FileReader("D:\\training\\API\\bdd\\src\\test\\resources\\TodoTestData.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

}
