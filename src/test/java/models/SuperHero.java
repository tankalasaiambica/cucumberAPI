package models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)

@Getter
@Setter
public class SuperHero implements Serializable {

    private int id;

    private String name;

    public SuperHero(String name, String superName, String profession, int age) {
        this.name = name;
        this.superName = superName;
        this.profession = profession;
        this.age = age;
    }

    private String superName;
    private String profession;
    private int age;
    private boolean canFly;

    // Constructor, Getter and Setter
    public SuperHero(){}
    public SuperHero(String name, String superName, String profession, int age, boolean canFly) {
//        this.id = id;
        this.name = name;
        this.superName = superName;
        this.profession = profession;
        this.age = age;
        this.canFly = canFly;
    }
}