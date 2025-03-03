import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardwidth = 360;
        int boardheight = 640;

        // Setting up the window
        JFrame frame = new JFrame("Flappy Bird");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardwidth, boardheight);

        // Creating Instance of FlappyBird
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); // Added this so that Panel couldn't take the title bar size too.
        flappyBird.requestFocus();
        frame.setVisible(true);

    }
}