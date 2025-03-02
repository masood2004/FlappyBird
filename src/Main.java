import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardwidth = 360;
        int boardheight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardwidth, boardheight);
    }
}