package utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class SuperHeroJsonData {
    public static JSONObject data;

    public static JSONObject inputDateInit() {

        JSONParser parser = new JSONParser();

        try {
            data = (JSONObject) parser.parse(new FileReader("D:\\training\\API\\sprint2_project\\SuperHeroController\\src\\test\\resources\\SuperHeroTestData.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

}
