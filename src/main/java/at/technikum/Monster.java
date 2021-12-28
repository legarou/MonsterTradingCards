package at.technikum;

public class Monster extends Card {
    private final MonsterType monsterType;

    protected Monster(String name, Integer damage, ElementType elementType, MonsterType monsterType) {
        super(name, damage, elementType);
        this.monsterType = monsterType;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    @Override
    public String toString() {
        return "Monster Card: " + super.toString() + " and of MonsterType " + monsterType;
    }
}
