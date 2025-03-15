package controller;
import model.*;

public class AIPlayer extends User {
    private NoNetGameController gameController;

    public AIPlayer(NoNetGameController gameController) {
        super();
        setUsername("AI");
        this.gameController = gameController;
    }


    public Step generateMove() {
        Step step = gameController.getChessboard().getMostValueStep(gameController.currentPlayer, gameController.getGameController().getTurn());
        return step;
    }
}