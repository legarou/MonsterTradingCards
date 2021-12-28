package at.technikum;

public class Spell extends Card {

    protected Spell(String name, Integer damage, ElementType elementType) {
        super(name, damage, elementType);
    }

    @Override
    public String toString() {
        return "Spell Card: " + super.toString();
    }
}
