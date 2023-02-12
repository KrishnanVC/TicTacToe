package todo;

import java.util.ArrayList;
import java.util.List;


class Pair {
    int row;
    int column;
    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }
}

public class Board {

    char[][] board = this.initializeBoard();
    boolean isDone = false;
    int moveCount = 0;

    public int getMoveCount() {
        return moveCount;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    private char[][] initializeBoard() {
        
        char[][] board = new char[3][3];
        
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                board[row][col] = '-';
            }
        }

        return board;
    }

    public void makeMove(Move move) {
        if(this.isValidMove(move)) {
            this.moveCount++;
            this.board[move.getRow()][move.getColumn()] = move.getValue();
            this.isDone = this.checkDone(move);
        } else {
            throw new IllegalArgumentException("The row or column value is invalid");
        }
    }

    private boolean checkDone(Move move) {
        int row = move.getRow();
        int column = move.getColumn();
        char value = move.getValue();

        return this.isRowDone(row, value) || this.isColumnDone(column, value) || this.isDiagonalDone(row, column, value);
    }

    private boolean isRowDone(int row, char value) {
        for(int j = 0; j < 3; j++) {
            if(this.board[row][j] != value) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnDone(int column, char value) {
        for(int i = 0; i < 3; i++) {
            if(this.board[i][column] != value) {
                return false;
            }
        }
        
        return true;
    }

    private boolean isDiagonalDone(int row, int column, char value) {

        if((row == 1 || column == 1) && (row != 1 && column != 1)) {
            return false;
        }

        int count = 0;

        for(int i=0; i<3;i++) {
            if(this.board[i][i] == value) {
                count++;
            }
        }

        if(count == 3) {
            return true;
        }

        count = 0;

        for(int i=2; i>=0;i--) {
            if(this.board[2-i][i] == value) {
                count++;
            }
        }

        return count == 3;
    }

    private boolean isValidMove(Move move) {
        return this.board[move.getRow()][move.getColumn()] == '-';
    }

    public void printTo(Output output) {
        output.println("_________GAME BOARD__________");
        output.println("");
        output.println("  A B C");

        for(int row = 0; row < 3; row++) {
            output.print(row + 1 + " ");
            for(int col = 0; col < 3; col++) {
                output.print(this.board[row][col] + " ");
            }
            output.println("");
        }
    }

    public List<Pair> getValidPositions() {
        List<Pair> validPositions = new ArrayList<>();

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(this.board[i][j] == '-') {
                    validPositions.add(new Pair(i, j));
                }
            }
        }
        return validPositions;
    }
    
}
