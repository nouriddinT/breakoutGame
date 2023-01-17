import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Paddle extends Rectangle{

    public static int PADDLE_WIDTH = 75;
    public static int PADDLE_HEIGHT = 19;
    int xVelocity;
    int speed = 10;
    boolean isMoving;
    private Image vausTexture;
    public Paddle(int x, int y){
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        vausTexture = new ImageIcon("./textures/vaus.png").getImage();
    }
    public void move() {
        x = x + xVelocity;
    }
    public void draw (Graphics g){
        g.drawImage(vausTexture,x,y,null);
    }

    public void setXDirection(int xDirection){
        xVelocity = xDirection;
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_D){
            setXDirection(speed);
        }
        if (e.getKeyCode()==KeyEvent.VK_A) {
            setXDirection(-speed);
        }
    }
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_D){
            isMoving = false;
            setXDirection(0);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){
            isMoving = false;
            setXDirection(0);
        }
    }

}
