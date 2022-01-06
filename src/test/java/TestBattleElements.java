import at.technikum.Battle.BattleElements;
import at.technikum.Cards.CardElementType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.util.stream.Stream;


public class TestBattleElements {

    @DisplayName("Test: checkForTrump")
    @ParameterizedTest()
    @MethodSource("provideCheckForTrump")
    void testCheckForTrump(CardElementType one, CardElementType two, int expected) {

        BattleElements battleClass = new BattleElements(one, two);
        assertThat(battleClass.checkForTrump()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideCheckForTrump() {
        return Stream.of(
                Arguments.of(CardElementType.WATER, CardElementType.WATER, 0),
                Arguments.of(CardElementType.FIRE, CardElementType.FIRE, 0),
                Arguments.of(CardElementType.NORMAL, CardElementType.NORMAL, 0),
                Arguments.of(CardElementType.WATER, CardElementType.FIRE, 1),
                Arguments.of(CardElementType.FIRE, CardElementType.WATER, 2),
                Arguments.of(CardElementType.WATER, CardElementType.NORMAL, 2),
                Arguments.of(CardElementType.NORMAL, CardElementType.WATER, 1),
                Arguments.of(CardElementType.FIRE, CardElementType.NORMAL, 1),
                Arguments.of(CardElementType.NORMAL, CardElementType.FIRE, 2)
        );

    }

}
