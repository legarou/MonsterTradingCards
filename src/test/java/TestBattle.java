import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBattle {
    @BeforeAll
    static void initAll() {}

    @BeforeEach
    void init() {}

    @Test
    void succeedingTest() {}

    @Test
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @Test
    void abortedTest() {
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    /*
    @DisplayName("Test: elementFight")
    @ParameterizedTest()
    @MethodSource("provideElementFight")
    void testElemetFight() {
        if (DamageType.IMMUNE == type)
            assertThat(cardBattle.checkForImmunity(type)).isTrue();
        else
            assertThat(cardBattle.checkForImmunity(type)).isFalse();
    }

    public static Stream<Arguments> provideDamageTypesForImmunity() {
        return Stream.of(
                Arguments.of(DamageType.IMMUNE),
                Arguments.of(DamageType.DOUBLE),
                Arguments.of(DamageType.FULL),
                Arguments.of(DamageType.HALVED),
                Arguments.of(DamageType.UNKNOWN)
        );
    }*/

    @AfterEach
    void tearDown() {}

    @AfterAll
    static void tearDownAll() {}
}