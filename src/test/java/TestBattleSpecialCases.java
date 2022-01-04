import at.technikum.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.util.stream.Stream;

public class TestBattleSpecialCases {

    @DisplayName("Test: twoMonsters")
    @ParameterizedTest()
    @MethodSource("provideTwoMonsters")
    void testTwoMonsters(CardMonsterType monsterOne, CardElementType elementOne, CardMonsterType monsterTwo, CardElementType elementTwo, int expected) {

        BattleSpecialCases battleClass = new BattleSpecialCases(monsterOne, elementOne, monsterTwo, elementTwo);
        assertThat(battleClass.twoMonsters()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideTwoMonsters() {
        return Stream.of(
                Arguments.of(CardMonsterType.DRAGON, CardElementType.FIRE, CardMonsterType.GOBLIN, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.NORMAL, CardMonsterType.FIREELF, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.GOBLIN, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.FIREELF, CardElementType.FIRE, CardMonsterType.DRAGON, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.ORK, CardElementType.FIRE, 1),
                Arguments.of(CardMonsterType.ORK, CardElementType.WATER, CardMonsterType.WIZZARD, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.FIRE, CardMonsterType.KRAKEN, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.FIREELF, CardElementType.NORMAL, CardMonsterType.FIREELF, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.NORMAL, CardMonsterType.DRAGON, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.GOBLIN, CardElementType.NORMAL, CardMonsterType.GOBLIN, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.ORK, CardElementType.NORMAL, CardMonsterType.ORK, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.FIREELF, CardElementType.WATER, CardMonsterType.WIZZARD, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.ORK, CardElementType.WATER, CardMonsterType.ORK, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.WATER, CardMonsterType.KNIGHT, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.KNIGHT, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.KNIGHT, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.FIRE, 0)
        );

    }

    @DisplayName("Test: spellAndMonster")
    @ParameterizedTest()
    @MethodSource("provideSpellAndMonster")
    void testSpellAndMonster(CardMonsterType monsterOne, CardElementType elementOne, CardMonsterType monsterTwo, CardElementType elementTwo, int expected) {

        BattleSpecialCases battleClass = new BattleSpecialCases(monsterOne, elementOne, monsterTwo, elementTwo);
        assertThat(battleClass.spellAndMonster()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideSpellAndMonster() {
        return Stream.of(
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.WATER, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KNIGHT, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.KRAKEN, CardElementType.WATER, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.KRAKEN, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.NORMAL, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.WIZZARD, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.KRAKEN, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.KRAKEN, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.ORK, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.SPELL, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.WATER, CardMonsterType.ORK, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.WATER, CardMonsterType.FIREELF, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.GOBLIN, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.NORMAL, 0)
                );

    }

    @DisplayName("Test: checkForSpecialCase")
    @ParameterizedTest()
    @MethodSource("provideCheckForSpecialCase")
    void testCheckForSpecialCase(CardMonsterType monsterOne, CardElementType elementOne, CardMonsterType monsterTwo, CardElementType elementTwo, int expected) {

        BattleSpecialCases battleClass = new BattleSpecialCases(monsterOne, elementOne, monsterTwo, elementTwo);
        assertThat(battleClass.checkForSpecialCase()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideCheckForSpecialCase() {
        return Stream.of(
                // two spells
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.SPELL, CardElementType.WATER, 0),
                // two monsters
                Arguments.of(CardMonsterType.DRAGON, CardElementType.FIRE, CardMonsterType.GOBLIN, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.NORMAL, CardMonsterType.FIREELF, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.GOBLIN, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.FIREELF, CardElementType.FIRE, CardMonsterType.DRAGON, CardElementType.NORMAL, 1),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.ORK, CardElementType.FIRE, 1),
                Arguments.of(CardMonsterType.ORK, CardElementType.WATER, CardMonsterType.WIZZARD, CardElementType.WATER, 2),
                // mixed
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.WATER, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KNIGHT, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.KNIGHT, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.KNIGHT, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KNIGHT, CardElementType.FIRE, 1),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KNIGHT, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.SPELL, CardElementType.FIRE, CardMonsterType.KRAKEN, CardElementType.WATER, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.KRAKEN, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.WATER, CardMonsterType.KRAKEN, CardElementType.NORMAL, 2),
                Arguments.of(CardMonsterType.SPELL, CardElementType.NORMAL, CardMonsterType.WIZZARD, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.KRAKEN, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.KRAKEN, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.ORK, CardElementType.WATER, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.FIRE, CardMonsterType.SPELL, CardElementType.WATER, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.SPELL, CardElementType.NORMAL, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.NORMAL, CardMonsterType.SPELL, CardElementType.FIRE, 0),
                Arguments.of(CardMonsterType.WIZZARD, CardElementType.WATER, CardMonsterType.ORK, CardElementType.WATER, 1),
                Arguments.of(CardMonsterType.DRAGON, CardElementType.WATER, CardMonsterType.FIREELF, CardElementType.FIRE, 2),
                Arguments.of(CardMonsterType.GOBLIN, CardElementType.WATER, CardMonsterType.DRAGON, CardElementType.NORMAL, 2)
        );

    }

}
