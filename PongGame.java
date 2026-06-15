import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements ActionListener, KeyListener {

    private int ballX = 300, ballY = 200;
    private int ballDX = 2, ballDY = 2;
    private final int ballSize = 20;

    private int playerY = 150;
    private int aiY = 150;
    private final int paddleWidth = 10;
    private final int paddleHeight = 80;

    private int playerScore = 0;
    private int aiScore = 0;

    private Timer timer;

    public PongGame() {
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.BLACK);

        timer = new Timer(10, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(20, playerY, paddleWidth, paddleHeight);
        g.fillRect(610, aiY, paddleWidth, paddleHeight);

        // Draw ball
        g.fillOval(ballX, ballY, ballSize, ballSize);

        // Draw scores
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(String.valueOf(playerScore), 250, 40);
        g.drawString(String.valueOf(aiScore), 380, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move ball
        ballX += ballDX;
        ballY += ballDY;

        // Top and bottom wall collision
        if (ballY <= 0 || ballY >= getHeight() - ballSize) {
            ballDY = -ballDY;
        }

        // Player paddle collision
        if (ballX <= 30 &&
            ballY + ballSize >= playerY &&
            ballY <= playerY + paddleHeight) {
            ballDX = -ballDX;
        }

        // AI paddle collision
        if (ballX + ballSize >= 610 &&
            ballY + ballSize >= aiY &&
            ballY <= aiY + paddleHeight) {
            ballDX = -ballDX;
        }

        // AI movement
        if (ballY > aiY + paddleHeight / 2) {
            aiY += 2;
        } else {
            aiY -= 2;
        }

        // Score
        if (ballX < 0) {
            aiScore++;
            resetBall();
        }

        if (ballX > getWidth()) {
            playerScore++;
            resetBall();
        }

        repaint();
    }

    private void resetBall() {
        ballX = 300;
        ballY = 200;
        ballDX = -ballDX;
        ballDY = (Math.random() > 0.5) ? 3 : -3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && playerY > 0) {
            playerY -= 20;
        }

        if (key == KeyEvent.VK_DOWN &&
            playerY < getHeight() - paddleHeight) {
            playerY += 20;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
