import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class Ball extends Rectangle{
    private double xVelocity;
    private double yVelocity;
    private boolean ballHit = true;
    boolean start = false;
    boolean startBall;
    public Ball(int x, int y, int width, int height, boolean starter){
        super(x, y, width, height);
        startBall = starter;
        if (starter == false){
            setYVelocity(-Constants.BALL_SPEED);
            setXVelocity(Constants.BALL_SPEED);
            move();
        }
    }
    public void setXVelocity(double newVelocity){
        xVelocity = newVelocity;
    }
    public void setYVelocity(double newVelocity){
        yVelocity = newVelocity;
    }
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }
    public void setBallHit(boolean tf){ballHit = tf;}
    public boolean getBallHit(){
        return ballHit;
    }
    public double getXVelocity(){return xVelocity;}
    public double getYVelocity(){return yVelocity;}
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x , y, width, height);
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_SPACE && start == false && startBall == true){
            start = true;
            setYVelocity(-Constants.BALL_SPEED);
            setXVelocity(0);
        }
    }
}
