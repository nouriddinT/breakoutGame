import java.awt.*;

public class Score extends Rectangle{
    private int lives = 3;
    private int score = 0;
    private Font font = new Font("Times New Roman",Font.PLAIN, Constants.FONT_SIZE);
    Score(){
        super();
    }
    public void addToScore(int s){
        score += s;
    }
    public int getLives(){
        return lives;
    }
    public void setLives(int l){
        lives = l;
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(font);
        g2.setColor(Color.RED);
        g2.drawString(String.valueOf(score),Constants.SCORE_X,Constants.SCORE_Y);
        g2.drawString(String.valueOf(lives),Constants.LIVES_X,Constants.LIVES_Y);
        g2.drawString(String.valueOf(Level.getLevelNum()),Constants.LIVES_X,Constants.LIVES_Y-100);
        if (lives <= 0){
            Font newFont = new Font("Times New Roman",Font.PLAIN, 40);
            g2.setFont(newFont);
            g2.drawString("GAME OVER",220,396);
        }
        if (ArkanoidPanel.win == true){
            Font newFont = new Font("Times New Roman",Font.PLAIN, 40);
            g2.setColor(Color.GREEN);
            g2.setFont(newFont);
            g2.drawString("WINNER!",220,396);
        }
    }
}
