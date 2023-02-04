package todo;

import java.util.List;
import java.util.Random;

public interface Player {
    String getName();
    Move getMove(Board board);
    int getWinCount();
    void setWinCount(int winCount);
}

abstract class PlayerAbstract implements Player{
    
    private int winCount = 0;
    private String name;
    
    @Override
    public int getWinCount() {
        return this.winCount;
    }

    @Override
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Human extends PlayerAbstract {
    
    private Value value;
    private Input input;

    public Human(String name, Value value, Input input) {
        this.value = value;
        this.input = input;
        this.setName(name);
    }

    public Move getMove(Board board) {
        Move move = input.getMove();
        move.setValue(this.value);
        return move;
    }
}

class Computer extends PlayerAbstract {

    private Value value;

    public Computer(String name, Value value) {
        this.value = value;
        this.setName(name);
    }
    
    public Move getMove(Board board) {
        List<Pair> validPositions = board.getValidPositions();
        Random rand = new Random();
        Pair pair = validPositions.get(rand.nextInt(validPositions.size()));
        return new Move(pair.row, pair.column, this.value);
    }

}