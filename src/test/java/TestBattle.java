import at.technikum.Battle.Battle;
import at.technikum.Cards.Card;
import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import at.technikum.User.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TestBattle {

    private List<Card> deckOne = new ArrayList<>();
    private List<Card> deckTwo = new ArrayList<>();
    private String playerOne;
    private String playerTwo;

    //MOCKITO PAGE 11 !!
    @BeforeEach
    void init() {
        Card monster1 = new Card(UUID.fromString("99f8f8dc-e65e-4a95-aa2c-385823f36e2a"), "Water Wizzard", 15, CardElementType.WATER, CardMonsterType.WIZZARD);
        Card monster2 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782fb352fe2a"), "Fire Dragon", 25, CardElementType.FIRE, CardMonsterType.DRAGON);
        Card monster3 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-723fcf66662a"), "Dumb Goblin", 10, CardElementType.NORMAL, CardMonsterType.GOBLIN);
        Card monster4 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-763623f36e2a"), "Misguided Knight", 15, CardElementType.NORMAL, CardMonsterType.KNIGHT);

        Card spell1 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782823f36e2a"), "Water Mirror", 20, CardElementType.WATER, CardMonsterType.SPELL);
        Card spell2 = new Card(UUID.fromString("9fe43dac-e25e-4a95-aa2c-782823f36e2a"), "Fireball", 25, CardElementType.FIRE, CardMonsterType.SPELL);
        Card spell3 = new Card(UUID.fromString("99f4fedc-e25e-4a95-aa2c-782823f36e2a"), "Rain Arrows", 15, CardElementType.NORMAL, CardMonsterType.SPELL);
        Card spell4 = new Card(UUID.fromString("9365a8dc-e25e-4a95-aa2c-782823f36e2a"), "Hellfire", 50, CardElementType.FIRE, CardMonsterType.SPELL);

        playerOne = "Edmund";
        playerTwo = "Elizabeth";

        deckOne = List.of(new Card[]{monster1, monster3, spell4, spell3});
        deckTwo = List.of(new Card[]{monster2, monster4, spell1, spell2});

    }

    @DisplayName("Test: start")
    @ParameterizedTest()
    @MethodSource("provideForTestStart")
    void testStart(int deckSizeOne, int deckSizeTwo, boolean expected) {
        List deckOne = new ArrayList(this.deckOne);
        List deckTwo = new ArrayList(this.deckTwo);

        while(deckSizeOne < deckOne.size()) {
            deckOne.remove(0);
        }
        while(deckSizeTwo < deckTwo.size()) {
            deckTwo.remove(0);
        }

        while(deckSizeOne > deckOne.size()) {
            deckOne.add(new Card());
        }
        while(deckSizeTwo > deckTwo.size()) {
            deckTwo.add(new Card());
        }

        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);
        assertThat(battle.start()).isEqualTo(expected);

    }
    public static Stream<Arguments> provideForTestStart() {
        return Stream.of(
                Arguments.of(4,4,true),
                Arguments.of(4,3,false),
                Arguments.of(3,4,false),
                Arguments.of(3,3,false),
                Arguments.of(5,3,false),
                Arguments.of(3,5,false),
                Arguments.of(5,5,false),
                Arguments.of(5,4,false),
                Arguments.of(4,5,false)
        );

    }

    @Test
    void succeedingTest() {}

    /*
    @DisplayName("Test: start")
    @ParameterizedTest()
    @MethodSource("provideStart")
    void testStart(int expected) {
        battleClass = new Battle(player1, player2);
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideStart() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );
    }
    */

    @DisplayName("Test: oneRoundFight")
    @ParameterizedTest()
    @MethodSource("provideOneRoundFight")
    void testOneRoundFight(int expected) {
        //assertThat().isEqualTo(expected);
    }
    public static Stream<Arguments> provideOneRoundFight() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }

    @DisplayName("Test: elementFight")
    @ParameterizedTest()
    @MethodSource("provideElementFight")
    void testElementFight(int expected) {
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideElementFight() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }

    @DisplayName("Test: cardFight")
    @ParameterizedTest()
    @MethodSource("provideCardFight")
    void testCardFight(int expected) {
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideCardFight() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }

    @DisplayName("Test: battlePostprocessing")
    @ParameterizedTest()
    @MethodSource("provideBattlePostprocessing")
    void testBattlePostprocessingt(int expected) {
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideBattlePostprocessingt() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }

    @DisplayName("Test: isGameOver")
    @ParameterizedTest()
    @MethodSource("provideIsGameOver")
    void testIsGameOver(int expected) {
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideIsGameOver() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }

    /*
    @DisplayName("Test: proclaimWinner")
    @ParameterizedTest()
    @MethodSource("provideProclaimWinner")
    void testProclaimWinner(int expected) {
        //assertThat().isEqualTo(expected);
    }

    public static Stream<Arguments> provideProclaimWinner() {
        return Stream.of(
                Arguments.of(),
                Arguments.of()
        );

    }
    */

}
