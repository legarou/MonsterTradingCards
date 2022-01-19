import at.technikum.Battle.Battle;
import at.technikum.Battle.BattleOutcome;
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

    @BeforeEach
    void init() {
        Card wizzard_damage15 = new Card(UUID.fromString("99f8f8dc-e65e-4a95-aa2c-385823f36e2a"), "Water Wizzard", 15, CardElementType.WATER, CardMonsterType.WIZZARD);
        Card dragon_damage25 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782fb352fe2a"), "Fire Dragon", 25, CardElementType.FIRE, CardMonsterType.DRAGON);
        Card goblin_damage10 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-723fcf66662a"), "Dumb Goblin", 10, CardElementType.NORMAL, CardMonsterType.GOBLIN);
        Card knight_damage15 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-763623f36e2a"), "Misguided Knight", 15, CardElementType.NORMAL, CardMonsterType.KNIGHT);

        Card spell_20water = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782823f36e2a"), "Water Mirror", 20, CardElementType.WATER, CardMonsterType.SPELL);
        Card spell2_25fire = new Card(UUID.fromString("9fe43dac-e25e-4a95-aa2c-782823f36e2a"), "Fireball", 25, CardElementType.FIRE, CardMonsterType.SPELL);
        Card spell3_15normal = new Card(UUID.fromString("99f4fedc-e25e-4a95-aa2c-782823f36e2a"), "Rain Arrows", 15, CardElementType.NORMAL, CardMonsterType.SPELL);
        Card spell4_50fire = new Card(UUID.fromString("9365a8dc-e25e-4a95-aa2c-782823f36e2a"), "Hellfire", 50, CardElementType.FIRE, CardMonsterType.SPELL);

        playerOne = "Edmund";
        playerTwo = "Elizabeth";

        deckOne = List.of(new Card[]{wizzard_damage15, goblin_damage10, spell4_50fire, spell3_15normal});
        deckTwo = List.of(new Card[]{dragon_damage25, knight_damage15, spell_20water, spell2_25fire});

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
                Arguments.of(4, 4, true),
                Arguments.of(4, 3, false),
                Arguments.of(3, 4, false),
                Arguments.of(3, 3, false),
                Arguments.of(5, 3, false),
                Arguments.of(3, 5, false),
                Arguments.of(5, 5, false),
                Arguments.of(5, 4, false),
                Arguments.of(4, 5, false)
        );

    }

    @DisplayName("Test: cardFight")
    @ParameterizedTest()
    @MethodSource("provideCardFight")
    void testCardFight(int damage1, int damage2, BattleOutcome battleOutcome, BattleOutcome expected) {
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);
        Card card1 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", damage1, CardElementType.FIRE, CardMonsterType.DRAGON);
        Card card2 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", damage2, CardElementType.NORMAL, CardMonsterType.WIZZARD);

        battle.setBattleOutcome(battleOutcome);
        battle.setPlayCardOne(card1);
        battle.setPlayCardTwo(card2);

        assertThat(battle.cardFight()).isEqualTo(expected);

    }

    public static Stream<Arguments> provideCardFight() {
        return Stream.of(
                Arguments.of(10,60, BattleOutcome.DRAW, BattleOutcome.PLAYER2),
                Arguments.of(60,10, BattleOutcome.DRAW, BattleOutcome.PLAYER1),
                Arguments.of(30,30, BattleOutcome.DRAW, BattleOutcome.DRAW),
                Arguments.of(10,60, BattleOutcome.PLAYER1, BattleOutcome.PLAYER2),
                Arguments.of(30,60, BattleOutcome.PLAYER1, BattleOutcome.PLAYER1),
                Arguments.of(30,120, BattleOutcome.PLAYER1, BattleOutcome.DRAW),
                Arguments.of(40,40, BattleOutcome.PLAYER1, BattleOutcome.PLAYER1),
                Arguments.of(60,30, BattleOutcome.PLAYER1, BattleOutcome.PLAYER1),
                Arguments.of(60,30, BattleOutcome.PLAYER2, BattleOutcome.PLAYER2),
                Arguments.of(120,30, BattleOutcome.PLAYER2, BattleOutcome.DRAW),
                Arguments.of(60,40, BattleOutcome.PLAYER2, BattleOutcome.PLAYER2),
                Arguments.of(20,40, BattleOutcome.PLAYER2, BattleOutcome.PLAYER2),
                Arguments.of(60,20, BattleOutcome.PLAYER2, BattleOutcome.PLAYER2),
                Arguments.of(90,20, BattleOutcome.PLAYER2, BattleOutcome.PLAYER1)
        );

    }



    @DisplayName("Test: elementFight")
    @ParameterizedTest()
    @MethodSource("provideElementFight")
    void testElementFight(CardElementType type1, CardElementType type2, BattleOutcome expected) {
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);
        Card card1 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", 20, type1, CardMonsterType.DRAGON);
        Card card2 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", 30, type2, CardMonsterType.WIZZARD);

        battle.setPlayCardOne(card1);
        battle.setPlayCardTwo(card2);

        assertThat(battle.elementFight()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideElementFight() {
        return Stream.of(
                Arguments.of(CardElementType.FIRE, CardElementType.WATER, BattleOutcome.PLAYER2),
                Arguments.of(CardElementType.FIRE, CardElementType.NORMAL, BattleOutcome.PLAYER1),
                Arguments.of(CardElementType.FIRE, CardElementType.FIRE, BattleOutcome.DRAW),
                Arguments.of(CardElementType.WATER, CardElementType.WATER, BattleOutcome.DRAW),
                Arguments.of(CardElementType.WATER, CardElementType.NORMAL, BattleOutcome.PLAYER2),
                Arguments.of(CardElementType.WATER, CardElementType.FIRE, BattleOutcome.PLAYER1),
                Arguments.of(CardElementType.NORMAL, CardElementType.WATER, BattleOutcome.PLAYER1),
                Arguments.of(CardElementType.NORMAL, CardElementType.NORMAL, BattleOutcome.DRAW),
                Arguments.of(CardElementType.NORMAL, CardElementType.FIRE, BattleOutcome.PLAYER2)


        );

    }

    @DisplayName("Test: oneRoundFight")
    @ParameterizedTest()
    @MethodSource("provideOneRoundFight")
    void testOneRoundFight(int damage1, int damage2, CardElementType type1, CardElementType type2, CardMonsterType monster1, CardMonsterType monster2, BattleOutcome expected) {
        Card card1 = new Card(UUID.fromString("95f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", damage1, type1, monster1);
        Card card1_2 = new Card(UUID.fromString("99f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", damage1, type1, monster1);
        Card card1_3 = new Card(UUID.fromString("94f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", damage1, type1, monster1);
        Card card1_4 = new Card(UUID.fromString("93f8f8dc-e25e-4a95-aa2c-782fb352fe2b"), "Fire Dragon", damage1, type1, monster1);
        Card card2 = new Card(UUID.fromString("98f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", damage2, type2, monster2);
        Card card2_2 = new Card(UUID.fromString("97f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", damage2, type2, monster2);
        Card card2_3 = new Card(UUID.fromString("91f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", damage2, type2, monster2);
        Card card2_4 = new Card(UUID.fromString("29f8f8dc-e25e-4a95-aa2c-723fcf66662b"), "Dumb Wizzard", damage2, type2, monster2);

        deckOne = List.of(new Card[]{card1, card1_2, card1_3, card1_4});
        deckTwo = List.of(new Card[]{card2, card2_2, card2_3, card2_4});
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);

        battle.setPlayCardOne(card1);
        battle.setPlayCardTwo(card2);

        assertThat(battle.oneRoundFight()).isEqualTo(expected);
    }
    public static Stream<Arguments> provideOneRoundFight() {
        return Stream.of(
                // draw is upredictable because of special feature
                // about damage
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.DRAGON , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.DRAGON ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.NORMAL , CardElementType.NORMAL , CardMonsterType.SPELL , CardMonsterType.KNIGHT , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.FIREELF , CardMonsterType.WIZZARD ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.FIREELF , CardMonsterType.FIREELF , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.FIRE, CardMonsterType.ORK , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.KNIGHT , CardMonsterType.KRAKEN , BattleOutcome.PLAYER2 ),
                Arguments.of(75, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.KRAKEN , CardMonsterType.WIZZARD ,  BattleOutcome.PLAYER1),
                Arguments.of(75, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.WIZZARD , CardMonsterType.KNIGHT , BattleOutcome.PLAYER1 ),
                Arguments.of(75, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.FIREELF , CardMonsterType.ORK ,  BattleOutcome.PLAYER1),
                Arguments.of(75, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.DRAGON , CardMonsterType.WIZZARD , BattleOutcome.PLAYER1 ),
                Arguments.of(75, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.GOBLIN , CardMonsterType.ORK ,  BattleOutcome.PLAYER1),
                Arguments.of(75, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.GOBLIN , CardMonsterType.GOBLIN , BattleOutcome.PLAYER1 ),
                Arguments.of(75, 50, CardElementType.FIRE, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.ORK ,  BattleOutcome.PLAYER1),
                // about element
                Arguments.of(25, 25, CardElementType.WATER, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER1),
                Arguments.of(25, 25, CardElementType.WATER , CardElementType.NORMAL , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 25, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 25, CardElementType.FIRE, CardElementType.NORMAL, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER1),
                Arguments.of(25, 25, CardElementType.NORMAL , CardElementType.WATER , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER1 ),
                Arguments.of(25, 25, CardElementType.NORMAL, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),

                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE , CardElementType.FIRE , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.NORMAL , CardElementType.NORMAL , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),

                Arguments.of(25, 150, CardElementType.WATER, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),
                Arguments.of(150, 25, CardElementType.WATER , CardElementType.NORMAL , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER1 ),
                Arguments.of(150, 25, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER1),
                Arguments.of(25, 150, CardElementType.FIRE, CardElementType.NORMAL, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 150, CardElementType.NORMAL , CardElementType.WATER , CardMonsterType.SPELL , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(150, 25, CardElementType.NORMAL, CardElementType.FIRE, CardMonsterType.SPELL , CardMonsterType.SPELL ,  BattleOutcome.PLAYER1),
                // about special cases
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.GOBLIN , CardMonsterType.DRAGON , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.DRAGON , CardMonsterType.GOBLIN ,  BattleOutcome.PLAYER1),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.WIZZARD , CardMonsterType.ORK , BattleOutcome.PLAYER1 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.ORK , CardMonsterType.WIZZARD ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.FIREELF , CardMonsterType.DRAGON , BattleOutcome.PLAYER1 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.DRAGON , CardMonsterType.FIREELF ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.WATER , CardMonsterType.KRAKEN , CardMonsterType.SPELL , BattleOutcome.PLAYER1 ),
                Arguments.of(25, 50, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.SPELL , CardMonsterType.KRAKEN ,  BattleOutcome.PLAYER2),
                Arguments.of(25, 50, CardElementType.WATER , CardElementType.FIRE , CardMonsterType.KNIGHT , CardMonsterType.SPELL , BattleOutcome.PLAYER1 ),
                Arguments.of(25, 10, CardElementType.FIRE, CardElementType.WATER, CardMonsterType.KNIGHT , CardMonsterType.SPELL ,  BattleOutcome.PLAYER2),
                Arguments.of(55, 50, CardElementType.NORMAL , CardElementType.WATER , CardMonsterType.KNIGHT , CardMonsterType.SPELL , BattleOutcome.PLAYER2 ),
                Arguments.of(25, 50, CardElementType.FIRE , CardElementType.FIRE , CardMonsterType.KNIGHT , CardMonsterType.SPELL , BattleOutcome.PLAYER2 )


        );

    }

    @DisplayName("Test: battlePostprocessing")
    @ParameterizedTest()
    @MethodSource("provideBattlePostprocessing")
    void testBattlePostprocessing(BattleOutcome battleOutcome) {
        List deckOne = new ArrayList(this.deckOne);
        List deckTwo = new ArrayList(this.deckTwo);
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);
        // to set the cards
        battle.oneRoundFight();
        battle.setBattleOutcome(battleOutcome);

        battle.battlePostprocessing();
        deckOne = battle.getDeckOne();
        deckTwo = battle.getDeckTwo();

        switch(battleOutcome) {
            case DRAW -> {
                assertThat(deckOne.size()).isEqualTo(4);
                assertThat(deckTwo.size()).isEqualTo(4);
                break;
            }
            case PLAYER1 -> {
                assertThat(deckOne.size()).isEqualTo(5);
                assertThat(deckTwo.size()).isEqualTo(3);
                break;
            }
            case PLAYER2 -> {
                assertThat(deckOne.size()).isEqualTo(3);
                assertThat(deckTwo.size()).isEqualTo(5);
                break;
            }

        }
    }

    public static Stream<Arguments> provideBattlePostprocessing() {
        return Stream.of(
                Arguments.of(BattleOutcome.DRAW),
                Arguments.of(BattleOutcome.PLAYER1),
                Arguments.of(BattleOutcome.PLAYER2)
        );

    }

    @Test
    @DisplayName("Test: isGameOver")
    void testIsGameOver_bothEmpty_true() {
        List deckOne = new ArrayList();
        List deckTwo = new ArrayList();
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);

        assertThat(battle.isGameOver()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test: testIsGameOver_deckOneEmpty_true")
    void testIsGameOver_deckOneEmpty_true() {
        List deckOne = new ArrayList();
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);

        assertThat(battle.isGameOver()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test: testIsGameOver_deckTwoEmpty_true")
    void testIsGameOver_deckTwoEmpty_true() {
        List deckTwo = new ArrayList();
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);

        assertThat(battle.isGameOver()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test: testIsGameOver_noneEmpty_false")
    void testIsGameOver_noneEmpty_false() {
        Battle battle = new Battle(playerOne, deckOne, playerTwo, deckTwo);

        assertThat(battle.isGameOver()).isEqualTo(false);
    }


}
