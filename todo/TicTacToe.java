package todo;

public class TicTacToe {

    public static void main(String[] args) {
        
        Input input = new CommandLineInput();
        Output output = new CommandLineOutput();
        Board board = new Board();

        Game game = new Game(input, output, board);
        
        game.start();

        input.close();
    }
}
