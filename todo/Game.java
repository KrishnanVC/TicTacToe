package todo;

enum GameMode {
    SINGLE_PLAYER,
    DOUBLE_PLAYER
}

enum Value {
    X,
    O
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
        if(this.value == Value.X) {
            return 'X';
        } else {
            return 'O';
        }
    }
    
    public void setValue(Value value) {
        this.value = value;
    }
}

public class Game {
    
    private int numberOfRounds;
    private boolean isDone = false;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Input input;
    private Output output;

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
        do {
            this.output.println("");
            this.output.println(this.currentPlayer.getName() + "'s turn");
            this.board.printTo(this.output);
            this.output.println("Enter your move [position like A2]: ");

            Move move = this.currentPlayer.getMove(this.board);
            this.board.makeMove(move);
            this.isDone = this.board.getIsDone();
            
            if(this.isDone == true) {
                this.currentPlayer.setWinCount(
                    this.currentPlayer.getWinCount() + 1
                );

                this.output.println("This Round's Winner " + this.currentPlayer.getName());

            } else {
                if(this.board.getMoveCount() == 9) {
                    this.isDone = true;
                    this.output.println("This Round resulted in a draw");
                }
            }

            this.changeCurrentPlayer();
        } while(! this.isDone);
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
