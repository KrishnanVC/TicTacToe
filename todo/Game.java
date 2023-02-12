package todo;

import java.util.HashMap;
import java.util.Map;

enum GameMode {
    SINGLE_PLAYER(1),
    DOUBLE_PLAYER(2);

    private static final Map<Integer, GameMode>  instanceByID = new HashMap<>();
    final int modeID;
    GameMode(int modeID) {
        this.modeID = modeID;
    }

    static {
        for (GameMode gameMode : GameMode.values()){
            instanceByID.put(gameMode.modeID, gameMode);
        }
    }

    public static GameMode getGameModeByID(int modeID) {
        return instanceByID.get(modeID);
    }
}

enum Value {
    X('X'),
    O('O');

    char ch;
    Value(char ch) {
        this.ch = ch;
    }
    public char toChar(){
        return this.ch;
    }
};

class Move {

    int row;
    int column;
    private Value value;
    
    public Move(int row, int column, Value value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getValue() {
        /*if(this.value == Value.X) {
            return 'X';
        } else {
            return 'O';
        }*/
        return this.value.toChar();
    }
    
    public void setValue(Value value) {
        this.value = value;
    }
}

public class Game {
    
    private int numberOfRounds;
    private final Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final Input input;
    private final Output output;

    public Game(Input input, Output output, Board board) {
        this.input = input;
        this.output = output;
        this.board = board;
    }

    public void start() {
        
        this.intializeGame();

        for(int round = 0; round < this.numberOfRounds; round++) {
            this.runRound();
        }

        this.printWinnerTo(this.output);
    }

    private void intializeGame() {
        this.printGameModesTo(this.output);
        
        GameMode gameMode;

        try {
            gameMode = this.input.getGameMode();
        } catch (IllegalArgumentException e) {
            //TODO: Why do we need to catch this ?
            throw e;
        }
        if(gameMode == GameMode.SINGLE_PLAYER) {
            this.player1 = new Human("Player 1", Value.X, this.input);
            this.player2 = new Computer("Computer", Value.O);
        } else if(gameMode == GameMode.DOUBLE_PLAYER) {
            this.player1 = new Human("Player 1", Value.X, this.input);
            this.player2 = new Human("Player 2", Value.O, this.input);
        }

        this.currentPlayer = player1;
        
        this.output.println("Please enter the number of rounds you wish to play : ");

        try {
            this.numberOfRounds = this.input.getNumberOfRounds();
        } catch (IllegalArgumentException e) {
            //TODO: Why do we need to catch this ?
            throw e;
        }
    }

    private void printGameModesTo(Output output) {
        output.println("Please select the game mode");
        output.println("1) Single player: Play against Computer");
        output.println("2) Double player: Play against Friend");
        output.println("Enter game mode: ");
    }

    private void runRound() {
        boolean isDone = false;
        do {
            this.output.println("");
            this.output.println(this.currentPlayer.getName() + "'s turn");
            this.board.printTo(this.output);
            this.output.println("Enter your move [position like A2]: ");

            Move move = this.currentPlayer.getMove(this.board);
            this.board.makeMove(move);
            isDone = this.board.getIsDone();
            
            if(isDone) {
                this.currentPlayer.setWinCount(
                    this.currentPlayer.getWinCount() + 1
                );

                this.output.println("This Round's Winner " + this.currentPlayer.getName());

            } else {
                if(this.board.getMoveCount() == 9) {
                    isDone = true;
                    this.output.println("This Round resulted in a draw");
                }
            }

            this.changeCurrentPlayer();
        } while(!isDone);
    }

    private void printWinnerTo(Output output) {
        if(player1.getWinCount() > player2.getWinCount()) {
            output.println("\\_^_^_/ The Winner is : " + player1.getName() + " \\_^_^_/");
        } else if(player1.getWinCount() < player2.getWinCount()) {
            output.println("\\_^_^_/ The Winner is : " + player2.getName() + " \\_^_^_/");
        } else {
            output.println("DRAW");
        }
    }

    private void changeCurrentPlayer() {
        if(this.currentPlayer == player1) {
            this.currentPlayer = player2;
        } else {
            this.currentPlayer = player1;
        }
    }

}
