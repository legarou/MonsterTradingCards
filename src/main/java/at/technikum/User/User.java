package at.technikum.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class User {
    @Getter
    @JsonProperty (value = "Username")
    private String username;
    @Getter
    @JsonProperty (value = "Password")
    private String password;
    @Getter
    @JsonProperty (value = "Bio")
    private String bio;
    @Getter
    @JsonProperty (value = "Name")
    private String name;
    @Getter
    @JsonProperty (value = "Image")
    private String image;
    @Getter
    private int elo;
    @Getter
    private int coins;
    @Getter
    private Stack cardStack;

    public User() {

    }

    public User(String username) {
        this.username = username;
        this.coins = 20;
        this.cardStack = new Stack();
    }
}
