import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    private BufferedImage backgroundBuffered;
    private BufferedImage birdBuffered;
    private BufferedImage topPipeBuffered;
    private BufferedImage bottomPipeBuffered;

    // Bird
    int BirdX = boardWidth / 8;
    int BirdY = boardHeight / 2;
    int BirdWidth = 34;
    int BirdHeight = 24;


    class Bird {
        int x = BirdX;
        int y = BirdY;
        int width = BirdWidth;
        int height = BirdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //    PIPES
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }


    //    GAME LOGIC
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;

    //    TIMER LOOP
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load Images
        try {
            backgroundBuffered = ImageIO.read(Objects.requireNonNull(getClass().getResource("images/flappybirdbg.png")));
            birdBuffered = scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("images/flappybird.png"))), BirdWidth, BirdHeight);

            // Scale pipes once during initialization
            topPipeBuffered = scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("images/toppipe.png"))), pipeWidth, pipeHeight);
            bottomPipeBuffered = scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("images/bottompipe.png"))), pipeWidth, pipeHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Bird Object Initialization
        bird = new Bird(birdBuffered);
        pipes = new ArrayList<>();

        // Timer for Placing Pipes
        placePipesTimer = new Timer(1500, _ -> placePipes());

        placePipesTimer.start();

//        Game Timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    private BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        return scaled;
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - (double) pipeHeight / 4 - Math.random() * ((double) pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeBuffered);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeBuffered);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundBuffered, 0, 0, boardWidth, boardHeight, null);

//        Drawing Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // Draw visible pipes only
        for (Pipe pipe : pipes) {
            if (pipe.x + pipe.width > 0) { // Only draw pipes that are visible
                g.drawImage(pipe.img == topPipeBuffered  ? topPipeBuffered : bottomPipeBuffered,
                        pipe.x, pipe.y, null);
            }
        }

        // Writing Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over: " + (int) score, 10, 35);
        }
        else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        // Bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Pipe management using iterator for safe removal
        Iterator<Pipe> iterator = pipes.iterator();
        while (iterator.hasNext()) {
            Pipe pipe = iterator.next();
            pipe.x += velocityX;

            // Remove pipes that are completely off-screen
            if (pipe.x + pipe.width < 0) {
                iterator.remove();
                continue;
            }

            // Keep existing collision and scoring logic
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
                break; // Early exit on collision
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird bird, Pipe pipe) {
        // Check if bird is SAFELY outside the pipe
        boolean notTouchingLeft   = bird.x + bird.width < pipe.x;  // Bird is left of pipe
        boolean notTouchingRight  = bird.x > pipe.x + pipe.width; // Bird is right of pipe
        boolean notTouchingTop    = bird.y + bird.height < pipe.y; // Bird is above pipe
        boolean notTouchingBottom = bird.y > pipe.y + pipe.height;// Bird is below pipe

        // If ANY of these are true → NO collision (bird is safe!)
        boolean isSafe = notTouchingLeft || notTouchingRight || notTouchingTop || notTouchingBottom;

        // If NOT safe → Collision!
        return !isSafe;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
        if (gameOver){
            // Restart the game
            bird.y = BirdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            placePipesTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
