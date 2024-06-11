import javax.swing.*;
import java.awt.*;

//Main game loop with extra funnies
public class Main extends JFrame {

    private static final int WIDTH = 1200;//Widht
    private static final int HEIGHT = 600;//Height



    public Main() {
        setTitle("Mark-2C 'Calender', Mod. 2024");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(227,174,87));
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);

            }
        });
    }
}