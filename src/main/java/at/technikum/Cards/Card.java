package at.technikum.Cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

public class Card {
    @Getter
    @JsonProperty(value = "Id")
    private  UUID cardID;
    @Getter
    private  String ownerID;
    @Getter
    private boolean inDeck;
    @Getter
    private boolean tradeLock;
    @Getter
    @JsonProperty (value = "Name")
    private  String name;
    @Getter
    @JsonProperty (value = "Damage")
    private  int damage;
    @Getter
    @JsonProperty (value = "ElementType")
    private  CardElementType elementType;
    @Getter
    @JsonProperty (value = "MonsterType")
    private  CardMonsterType cardMonsterType;

    public Card(UUID cardID, String ownerID, boolean inDeck, boolean tradeLock, String name, Integer damage, CardElementType elementType, CardMonsterType cardMonsterType) {
        this.cardID = cardID;
        this.ownerID = ownerID;
        this.inDeck = inDeck;
        this.tradeLock = tradeLock;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.cardMonsterType = cardMonsterType;
    }

    public Card(UUID cardID, String name, Integer damage, CardElementType elementType, CardMonsterType cardMonsterType) {
        this.cardID = cardID;
        this.ownerID = "";
        this.inDeck = false;
        this.tradeLock = true;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.cardMonsterType = cardMonsterType;
    }

    public Card() {
    }

    @Override
    public String toString() {
        if(CardMonsterType.SPELL == cardMonsterType){
            return "Spell Card: " + name + " has " + damage + " damage and is of ElementType " + elementType;
        }
        else {
            return "Monster Card: " + name + " has " + damage + " damage and is of ElementType " + elementType + " and of MonsterType " + cardMonsterType;
        }

    }
}
