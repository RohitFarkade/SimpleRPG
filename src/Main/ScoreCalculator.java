package Main;

import Entity.Player;

import java.awt.*;

public class ScoreCalculator{

    public  void draw(Graphics2D g2,Player player){
            g2.drawString("Score: "+ player.score,10,30);

    }
}
