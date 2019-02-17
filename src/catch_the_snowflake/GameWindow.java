package catch_the_snowflake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
    private static long last_frame_time;
    private static Image background;
    private static Image game_over;
    private static Image snowflake;
    private static float snowflake_left = 200;
    private static float snowflake_top = -100;
    private static float snowflake_v=200;
    private static int score;



    public static void main(String[] args) throws IOException {
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        snowflake = ImageIO.read(GameWindow.class.getResourceAsStream("snowflake.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float snowflake_right = snowflake_left + snowflake.getWidth(null);
                float snowflake_bottom = snowflake_top + snowflake.getHeight(null);
                boolean is_snowflake = x >= snowflake_left && x <= snowflake_right && y >= snowflake_top && y <= snowflake_bottom;
                if (is_snowflake) {
                    snowflake_top = -100;
                    snowflake_left = (int)(Math.random() * (game_field.getWidth() - snowflake.getWidth(null)));
                    snowflake_v = snowflake_v +10;
                    score++;
                    game_window.setTitle("Score: " + score);
                }
            }
        });
        game_window.add(game_field);
        game_window.setVisible(true);
    }
    private static void Repaint (Graphics g) {
        long corrent_time = System.nanoTime();
        float delta_time = (corrent_time - last_frame_time) * 0.000000001f;
        last_frame_time = corrent_time;
        snowflake_top = snowflake_top + snowflake_v * delta_time;
//        snowflake_left = snowflake_left + snowflake_v * delta_time;
        g.drawImage(background, 0, 0, null);
        g.drawImage(snowflake, (int)snowflake_left, (int)snowflake_top, null);
        if (snowflake_top > game_window.getHeight()) g.drawImage(game_over, 320, 140, null);
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            Repaint(g);
            repaint();
        }
    }
}
