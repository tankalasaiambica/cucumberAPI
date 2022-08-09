package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)

@Getter@Setter
public class Comment {
    private String name;
    private  String email;
    private  String body;
    private  Long post_id;

    private int id;

    public Comment()
    {}

    public Comment(Long post_id,String name, String email,String body) {
        this.name = name;
        this.email = email;
        this.body = body;
        this.post_id = post_id;
    }




}
