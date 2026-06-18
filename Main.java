import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame f=new JFrame("Matrix Run");

        GamePanel p=new GamePanel();

        f.add(p);

        f.pack();

        f.setLocationRelativeTo(null);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setResizable(false);

        f.setVisible(true);

    }
}