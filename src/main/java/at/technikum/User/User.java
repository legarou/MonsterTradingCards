package at.technikum.User;

import at.technikum.Cards.Card;
import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
    @Setter
    private ArrayList<Card> deck;

    public User() {

    }

    public User(String username) {
        this.username = username;
        this.deck = null;
    }

    public User(HashMap<String, String> hashMap) {
        this.username = hashMap.get("username");
        this.elo = Integer.parseInt(hashMap.get("elo"));
        this.coins = Integer.parseInt(hashMap.get("coins"));
        this.password = hashMap.get("password");
        this.bio = hashMap.get("bio");
        this.image = hashMap.get("image");
        this.name = hashMap.get("name");

    }
}
