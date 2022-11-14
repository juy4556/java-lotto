package domain;

import java.util.ArrayList;
import java.util.List;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class User {
    private LottoGame lottoGame;
    private Lotto winningLotto;
    private int buyPrice;
    private int bonusNumber;

    public void startLottoGame() {
        lottoGame = new LottoGame();
        System.out.println("구입금액을 입력해 주세요.");
        inputBuyPrice();
        System.out.println();

        System.out.println((buyPrice / 1000) + "개를 구매했습니다.");
        lottoGame.createLotto(buyPrice/1000);
        lottoGame.printLotteries(buyPrice/1000);
        System.out.println();

        System.out.println("당첨 번호를 입력해 주세요.");
        inputWinning();
        System.out.println();

        System.out.println("보너스 번호를 입력해 주세요.");
        inputBonusNumber();
        System.out.println();

        lottoGame.confirmLotteriesPrized(winningLotto, bonusNumber);

        lottoGame.printWinningStatistics();
    }

    public void inputBuyPrice() {
        buyPrice = Integer.parseInt(readLine());
    }

    private void inputWinning() {
        String[] numbers = readLine().split(",");
        List<Integer> winningNumbers = new ArrayList<>();

        for (String number : numbers) {
            winningNumbers.add(Integer.valueOf(number));
        }

        winningLotto = new Lotto(winningNumbers);
    }

    private void inputBonusNumber() {
        bonusNumber = Integer.parseInt(readLine());
    }

}
