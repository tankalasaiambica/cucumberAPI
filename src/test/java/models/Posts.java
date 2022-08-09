package models;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter@Setter
public class Posts {
    private int user_id;

    private int id;
    private String title;
    private String Body;

    public Posts(){

    }

    public Posts(int user_id, String title, String body) {
        this.user_id = user_id;
        this.title = title;
        Body = body;
    }


}
