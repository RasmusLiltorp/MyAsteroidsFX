package dk.sdu.cbse.common.data;

public class GameData {

    private int displayWidth = 800;
    private int displayHeight = 800;
    private int score = 0;
    private final GameKeys keys = new GameKeys();

    public GameKeys getKeys() { return keys; }

    public int getDisplayWidth() { return displayWidth; }

    public int getDisplayHeight() { return displayHeight; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public void addScore(int points) { this.score += points; }
}
