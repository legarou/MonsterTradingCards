package at.technikum;

public class Card {
    private final String name;
    private final int damage;
    private final CardElementType elementType;
    private final CardMonsterType cardMonsterType;

    public Card(String name, Integer damage, CardElementType elementType, CardMonsterType cardMonsterType) {
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.cardMonsterType = cardMonsterType;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public CardElementType getElementType() {
        return elementType;
    }

    public CardMonsterType getMonsterType() {
        return cardMonsterType;
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
