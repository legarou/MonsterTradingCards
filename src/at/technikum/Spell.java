package at.technikum;

public class Spell extends Card {

    protected Spell(String name, Integer damage, ElementType elementType) {
        super(name, damage, elementType);
        setDescription("Spell Card: " + getDescription());
    }
}
