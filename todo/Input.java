package todo;

import java.util.Scanner;

public interface Input {
    int getNumberOfRounds();
    GameMode getGameMode();
    Move getMove();
    void close();
}

class CommandLineInput implements Input {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public int getNumberOfRounds() {
        int numberOfRounds = sc.nextInt();

        if(numberOfRounds < 1) {
            throw new IllegalArgumentException("Number of rounds cannot be negative or zero");
        }

        return numberOfRounds;
    }

    @Override
    public GameMode getGameMode() {
        int modeID = sc.nextInt();
        GameMode gameMode = GameMode.getGameModeByID(modeID);

        if (gameMode == null) {
            throw new IllegalArgumentException("Game mode can only be [0/1]");
        }

        return gameMode;
    }

    @Override
    public Move getMove() {      
        String inputString = sc.nextLine();
        if(inputString.length() == 0) {
            inputString = sc.nextLine();
        }
        int col = inputString.charAt(0) - 'A';
        int row = inputString.charAt(1) - '0' - 1;

        return new Move(row, col, null);
    }

    @Override
    public void close() {
        this.sc.close();        
    }

}
