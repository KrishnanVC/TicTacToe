package todo;

public interface Output {
    void print(String string);
    void println(String string);
}

class CommandLineOutput implements Output {

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public void println(String string) {
        System.out.println(string);
    }

}
