import javax.swing.*;
import java.awt.*;

<<<<<<< HEAD
//Main game loop with extra funnies
public class Main extends JFrame {
=======
//Main program loop with extra funnies
public class Main extends Canvas implements Runnable {
>>>>>>> 6a20ea1a3efae6ed17aaa6e1913361df966e1311

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