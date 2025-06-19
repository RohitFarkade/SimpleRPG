package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//STEP 1: Create A Jframe Window Named gameWindow
        JFrame gameWindow = new JFrame("MyGame");
        gameWindow.setSize(768, 576);//sets the window size
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close when clicked on 'X'

        GamePanel gamePanel = new GamePanel();//created new gamePanel to draw on the JPanel
        gameWindow.add(gamePanel);//add gamePanel to window

        gameWindow.pack();//made it fit the preferred size and its layer components

        gameWindow.setVisible(true);//sets window visible
        gameWindow.setLocationRelativeTo(null);//window opens in center
//        gameWindow.setResizable(false);
        gameWindow.setTitle("My 2D Game!");
        gamePanel.startGameThread();

    }
}
