package Main;

public class FPSCounter {
    private long lastTime;
    private int frames;
    private int fps;

    public FPSCounter() {
        lastTime = System.nanoTime();
        frames = 0;
        fps = 0;
    }

    public void update() {
        frames++;
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) { // One second
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }
    }

    public int getFPS() {
        return fps;
    }
}