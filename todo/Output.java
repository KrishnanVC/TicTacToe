package todo;

public interface Output {
    void printWinner(Player player);
    void printGameModes();
    void print(String string);
    void println(String string);
}

class CommandLineOutput implements Output {

    @Override
    public void printWinner(Player player) {
        System.out.println("\\_^_^_/ The Winner is : " + player.getName() + " \\_^_^_/");
    }

    @Override
    public void printGameModes() {
        System.out.println("Please select the game mode");
        System.out.println("1) Single player: Play against Computer");
        System.out.println("2) Double player: Play against Friend");
        System.out.println("Enter game mode: ");
    }

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public void println(String string) {
        System.out.println(string);
    }

}
