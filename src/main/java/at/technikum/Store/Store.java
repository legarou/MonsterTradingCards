package at.technikum.Store;

import at.technikum.Cards.Card;
import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class Store {
    @Getter
    @JsonProperty(value = "Id")
    private UUID storeID;
    @Getter
    @JsonProperty(value = "CardToTrade")
    private UUID cardToTrade;
    @Getter
    @JsonProperty(value = "MonsterType")
    private StoreMonsterType monsterType;
    @Getter
    @JsonProperty(value = "MinimumDamage")
    private int damage;
    @Getter
    @JsonProperty(value = "ElementType")
    private StoreElementType elementType;

    public Store() {

    }

    public Store(HashMap<String, String> hashMap) {
        System.out.println("storeID " + hashMap.get("storeID"));
        this.storeID = UUID.fromString(hashMap.get("storeID"));
        System.out.println("storeCardID " + hashMap.get("storeCardID"));
        this.cardToTrade = UUID.fromString(hashMap.get("storeCardID"));
        System.out.println("requirementSpellMonster " + hashMap.get("requirementSpellMonster"));
        this.monsterType = StoreMonsterType.valueOf(hashMap.get("requirementSpellMonster"));
        System.out.println("requirementMinDamage " + hashMap.get("requirementMinDamage"));
        this.damage = Integer.parseInt(hashMap.get("requirementMinDamage"));
        System.out.println("requirementElementType " + hashMap.get("requirementElement"));
        this.elementType = StoreElementType.valueOf(hashMap.get("requirementElement"));
    }

    public boolean checkRequirements(Card card) {
        if(card.getDamage() < damage) {
            return false;
        }

        if(! (StoreElementType.NONE == elementType) ) {
            if( !(elementType.name().equals(card.getElementType().name()))) {
                return false;
            }
        }

        switch (monsterType) {
            case NONE -> {
                return true;
            }
            case SPELL -> {
                if( ! (CardMonsterType.SPELL == card.getCardMonsterType())) {
                    return false;
                }
                else {
                    return true;
                }
            }
            case MONSTER -> {
                if( CardMonsterType.SPELL == card.getCardMonsterType()) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }

        return true;
    }
}
