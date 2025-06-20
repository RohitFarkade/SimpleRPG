package Main;

import Entity.Player;
import Tile.TileManager;

import javax.swing.*;//jpannel
import java.awt.*;//dimension

public class GamePanel extends JPanel implements Runnable {
    //Screen Setting
    public final int originalTileSize = 16;//character ,npc default size,tile
    public final int scale = 3;//for scaling according to display resolution
    public final int tileSize = originalTileSize * scale;//new scaled resolution
    public final int maxScreenCol = 16;//game display 16 horizontally
    public final int maxScreenRow = 12;// game display vertically
    public final int screenWidth = tileSize * maxScreenCol;//768 pixel
    public final int screenHeight = tileSize * maxScreenRow;//576 pixel //total gameScreen Size
// world Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public  final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize* maxWorldRow;
    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    KeyHandler keyH = new KeyHandler();
   public Player player = new Player(this, keyH);
    //set Players defualt postion
    private FPSCounter fpsCounter;
    ScoreCalculator scoreCalc = new ScoreCalculator();


    public GamePanel() {
        fpsCounter = new FPSCounter();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//drawing will be done in offscreen (simply rendering will be Improved)
        this.addKeyListener(keyH);//reconize key events
        this.setFocusable(true);//game panel focused to receive key inputs
    }

    Thread gameThread;//thread is used to implement the concept of time means a continues runing process

    //with ,thread  we will be able to animate our player with continuos static images displaying continuosly
    //for it out gamePanel implemented Runable whic needs to in order to use Threads and for Runable we need void run method
    public void startGameThread() {
        gameThread = new Thread(this);//this means we are passing our GamePanel class in as Parameter
        gameThread.start();//starting the Thread
    }
//----------------------------------------------------------
    //this will be automaticaly called when thread is started
//    @Override
//    public void run(){
//        double drawInterval = 1000000000/FPS;//1sec 1bilion nanosecond
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while(gameThread!=null){

    /// /            System.out.println("Game Loop Is Running");fps == 30 per second drawing
    /// /            long currentTime = System.nanoTime();
    /// /            long currentTimeInMS = System.currentTimeMillis();
    /// /          System.out.println("Game Loop Is Running" +currentTimeInMS);//fps == 30 per second drawing
    /// /
//             //sleep mthod------------start painting -------------------
//            //step 1 update c\info such as char position
//            update();
//            //step 2 draw the screen with updatd infro(position)
//
//            repaint();//thats how we call paintComponent
//            try {
//                double remainingTime = nextDrawTime-System.nanoTime();
//                remainingTime /=1000000;//nano to milisecond
//                if(remainingTime<0){
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);//pause the loop until loop is over
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            //------------------------------ nextDraw(Start)---------------
//        }
//
//    }
//
//
//--------------------------------------------------------------
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        //for fps counting
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS= " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }
    }


    public void update() {
        player.update();
        player.scoreCalculator();
        System.out.println("Player's Score is: "+player.score);
    }

    //draw thigs standart component
    public void paintComponent(Graphics g) {

        super.paintComponent(g);//always defualt must use
        Graphics2D g2 = (Graphics2D) g;//Graphics2D extends Graphics to get better control over geometry position colour layputs etc
        tileM.draw(g2);
        player.draw(g2);
        Font myFont = new Font("Courier New", Font.BOLD, 30);
        g2.setFont(myFont);
        Color myColor = new Color( 255, 255, 255); // Red color (RGB values))
        g2.setColor( myColor); // Red color (RGB values));
        scoreCalc.draw(g2,player);
        //goes to player class draw method
        g2.dispose();// realeases resourse when done

    }


}
