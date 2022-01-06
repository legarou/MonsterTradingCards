import at.technikum.Battle.Battle;
import at.technikum.Cards.Card;
import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import at.technikum.User.Stack;
import at.technikum.User.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.util.stream.Stream;

public class TestBattle {

    private Battle battleClass;

    //MOCKITO PAGE 11 !!

    @BeforeAll
    static void initAll() {



        //System.out.println(monster1.toString());
        //System.out.println(spell1.toString());
    }

    @BeforeEach
    void init() {
        Card monster1 = new Card("Water Wizzard", 15, CardElementType.WATER, CardMonsterType.WIZZARD);
        Card monster2 = new Card("Fire Dragon", 25, CardElementType.FIRE, CardMonsterType.DRAGON);
        Card monster3 = new Card("Dumb Goblin", 10, CardElementType.NORMAL, CardMonsterType.GOBLIN);
        Card monster4 = new Card("Misguided Knight", 15, CardElementType.NORMAL, CardMonsterType.KNIGHT);

        Card spell1 = new Card("Water Mirror", 20, CardElementType.WATER, CardMonsterType.SPELL);
        Card spell2 = new Card("Fireball", 25, CardElementType.FIRE, CardMonsterType.SPELL);
        Card spell3 = new Card("Rain Arrows", 15, CardElementType.NORMAL, CardMonsterType.SPELL);
        Card spell4 = new Card("Hellfire", 50, CardElementType.FIRE, CardMonsterType.SPELL);

        User player1 = new User("Edmund");
        User player2 = new User("Elizabeth");

        Stack stack1 = player1.getCardStack();
        Stack stack2 = player2.getCardStack();

        stack1.addCardToStack(monster1);
        stack1.addCardToStack(monster3);
        stack1.addCardToStack(spell4);
        stack1.addCardToStack(spell3);
        stack1.addToDeck(monster1);
        stack1.addToDeck(monster3);
        stack1.addToDeck(spell4);
        stack1.addToDeck(spell3);

        stack2.addCardToStack(monster2);
        stack2.addCardToStack(monster4);
        stack2.addCardToStack(spell1);
        stack2.addCardToStack(spell2);
        stack2.addToDeck(monster2);
        stack2.addToDeck(monster4);
        stack2.addToDeck(spell1);
        stack2.addToDeck(spell2);

        System.out.println("Player1");
        player1.getCardStack().printStack();
        System.out.println("Player2");
        player2.getCardStack().printStack();

        battleClass = new Battle(player1, player2);
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
