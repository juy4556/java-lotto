package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import lotto.domain.Lotto;
import org.junit.jupiter.api.Test;
import lotto.exception.Exception;
import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";
    Exception exception = new Exception();

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 구매금액_천단위_입력_안할_때_예외테스트() {
        String input = "1001";
        assertSimpleTest(() ->
                assertThatThrownBy(() -> exception.validateIsThousandUnit(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR]천 단위로 입력해주세요.")
        );
    }

    @Test
    void 구매금액_1000보다_적을_때_예외테스트() {
        String input = "0";
        assertSimpleTest(() ->
                assertThatThrownBy(() -> exception.validateIsLessThanThousand(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR]구매금액은 1000 이상이어야 합니다.")
        );
    }

    @Test
    void 보너스번호_문자_입력될_때_예외처리_예외테스트() {
        String input = "C";
        assertSimpleTest(() ->
                assertThatThrownBy(() -> exception.validateIsNumeric(input))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR]숫자만 입력해주세요.")
        );
    }

    @Test
    void 보너스번호_당첨번호와_중복되는지_예외테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() ->
                        exception.validateIsDuplicatedWithWinningLotto(new Lotto(List.of(1,2,3,4,5,6)),1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR]보너스번호가 우승 번호와 중복됩니다.")
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
