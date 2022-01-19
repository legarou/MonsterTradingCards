import at.technikum.Battle.Battle;
import at.technikum.Battle.BattleOutcome;
import at.technikum.Cards.Card;
import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import at.technikum.Server.TokenHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTokenHandler {

    @DisplayName("Test: verifyToken")
    @ParameterizedTest()
    @MethodSource("provideVerifyToken")
    void testVerifyToken(String token, boolean expected) {
        TokenHandler tokenHandler = new TokenHandler();
        assertThat(tokenHandler.verifyToken(token)).isEqualTo(expected);
    }
    public static Stream<Arguments> provideVerifyToken() {
        return Stream.of(
                Arguments.of("Basic username-mtcgToken", true),
                Arguments.of("Basic matilda-mtcgToken", true),
                Arguments.of("Basic clara-mtcgToken", true),
                Arguments.of("Basic username-mcgToken", false),
                Arguments.of("Basic username-mtcgtoken", false),
                Arguments.of("username-mtcgToken", false),
                Arguments.of("mtcgToken", false)

        );

    }

    @DisplayName("Test: getUsername")
    @ParameterizedTest()
    @MethodSource("provideGetUsername")
    void testGetUsername(String token, String expected) {
        TokenHandler tokenHandler = new TokenHandler();
        assertThat(tokenHandler.getUsername(token)).isEqualTo(expected);
    }
    public static Stream<Arguments> provideGetUsername() {
        return Stream.of(
                Arguments.of("Basic username-mtcgToken", "username"),
                Arguments.of("Basic matilda-mtcgToken", "matilda"),
                Arguments.of("Basic clara-mtcgToken", "clara"),
                Arguments.of("Basic username-mcgToken", "username"),
                Arguments.of("Basic username-mtcgtoken", "username"),
                Arguments.of("username-mtcgToken", ""),
                Arguments.of("mtcgToken", "")

        );

    }

}
