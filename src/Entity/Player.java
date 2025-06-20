package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public final int health;
    public int score;



    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(8, 16, 32, 32);
        health = 100;
        score = 0;

        setDefualtValues();
        getPlayerImage();

    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefualtValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 3;
        direction = "down";


    }

    public void update() {
        if (keyH.downPressed || keyH.rightPressed || keyH.leftPressed || keyH.upPressed) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            //check tile colision
            collisionOn = false;
            gp.collisionDetector.checkTile(this);
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;//goes up
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
//       g2.setColor(Color.white);//componets color
//       g2.fillRect(x,y,gp.tileSize,gp.tileSize);//our
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawString("Score:"+score,0,0);
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
    double delta = 0;

    public void scoreCalculator(){
        if((keyH.rightPressed||keyH.leftPressed|| keyH.downPressed|| keyH.upPressed) && collisionOn == false) {

            delta +=Math.sqrt(worldX * worldX + worldY * worldY)/1000;
            while(delta>=20){
               this.score +=  1;
               delta = 0;

            }

        }
    }

}
