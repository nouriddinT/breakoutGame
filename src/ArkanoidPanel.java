
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//game logic goes in this class          //interface
class ArkanoidPanel extends JPanel implements ActionListener, MouseListener, KeyListener {
    private static Timer myTimer;
    private static Image background;
    private static Paddle paddle;
    private static Score score;
    private static Ball startBall;
    private static Level level;
    private static int currentLevel;
    private static Graphics graphics;
    boolean leftBoundaryHit = false;
    boolean rightBoundaryHit = false;
    boolean topBoundaryHit = false;
    static boolean win;
    static boolean gameOver = false;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    public static void main(String[] args){
        new ArkanoidFrame();
    }

    ArkanoidPanel(){
        super();
        newPaddle();
        newBall();
        level = new Level();
        win = false;
        currentLevel = 1;
        score = new Score();
        myTimer = new Timer(20, this);
        myTimer.start();
        setPreferredSize(new Dimension(Constants.PANEL_WIDTH,Constants.GAME_HEIGHT));
        addMouseListener(this);
        setFocusable(true);
        addKeyListener(this);
        requestFocus();

    }
    public boolean getWin(){
        return win;
    }
    public void newPaddle(){
        paddle = new Paddle(Constants.GAME_WIDTH/2- Paddle.PADDLE_WIDTH/2, Constants.GAME_HEIGHT-100);
    }
    public void newBall(){
        startBall = new Ball((Constants.GAME_WIDTH/2) - (Constants.BALL_DIAMETER/2), (Constants.GAME_HEIGHT-100) - Constants.BALL_DIAMETER, Constants.BALL_DIAMETER, Constants.BALL_DIAMETER,true);
        balls.add(startBall);
    }
    public void newRandomBall(Ball mainBall){
        Ball randomBall = new Ball(mainBall.x, mainBall.y, Constants.BALL_DIAMETER,Constants.BALL_DIAMETER,false);
        balls.add(randomBall);
    }
    public void move(){
        paddle.move();
        for (int i = 0; i < balls.size(); i++){
            balls.get(i).move();
        }
    }
    public void checkCollision(){
        //stops paddles from going off screen
        if (paddle.x+ Paddle.PADDLE_WIDTH>= Constants.GAME_WIDTH-22){
            paddle.x = Constants.GAME_WIDTH- Paddle.PADDLE_WIDTH-22;
        }
        if (paddle.x<= 22){
            paddle.x = 22;
        }
        for (int i = 0; i < balls.size(); i++){
            //stops ball from going off screen and bounces it instead
            //top boundary
            if (balls.get(i).y <= 24 &&
                    topBoundaryHit == false){
                balls.get(i).setYVelocity(-balls.get(i).getYVelocity());
                balls.get(i).setBallHit(false);
                topBoundaryHit = true;
                leftBoundaryHit = false;
                rightBoundaryHit = false;
            }
            //left boundary
            if (balls.get(i).x <= 22 &&
                    leftBoundaryHit == false){
                balls.get(i).setXVelocity(-balls.get(i).getXVelocity());
                balls.get(i).setBallHit(false);
                topBoundaryHit = false;
                leftBoundaryHit = true;
                rightBoundaryHit = false;
            }
            //right boundary
            if (balls.get(i).x + Constants.BALL_DIAMETER >= Constants.GAME_WIDTH-22 &&
                    rightBoundaryHit == false){
                balls.get(i).setXVelocity(-balls.get(i).getXVelocity());
                balls.get(i).setBallHit(false);
                topBoundaryHit = false;
                leftBoundaryHit = false;
                rightBoundaryHit = true;
            }
            //bottom boundary
            //reduce amount of lives left by -1
            if (balls.get(i).y + Constants.BALL_DIAMETER >= Constants.GAME_HEIGHT){
                if (balls.size()<=0){
                    score.setLives(score.getLives()-1);
                    newPaddle();
                    newBall();
                }
            }
            //bounce ball off paddle
            if (balls.get(i).getYVelocity()>0){
                if (balls.get(i).getY()+Constants.BALL_DIAMETER >= paddle.getY() &&
                        balls.get(i).getX()+Constants.BALL_DIAMETER/2 >= paddle.getX() && balls.get(i).getX()+Constants.BALL_DIAMETER/2 <= paddle.getX() + paddle.width &&
                        balls.get(i).getBallHit() == false &&
                        balls.get(i).getY() + Constants.BALL_DIAMETER < paddle.y + paddle.height){
                    balls.get(i).setBallHit(true);
                    topBoundaryHit = false;
                    leftBoundaryHit = false;
                    rightBoundaryHit = false;
                    double theta = getBallVelocityAngle(paddle, balls.get(i));
                    //using SOH CAH TOA to determine new velocities
                    double newXVelocity = -(Math.sin(theta)) * Constants.BALL_SPEED;
                    double newYVelocity = (Math.cos(theta)) * Constants.BALL_SPEED;
                    //tells us if our velocity is positive or negative, then change the sign
                    double oldSign = Math.signum(balls.get(i).getYVelocity());
                    balls.get(i).setYVelocity(newYVelocity * (-1.0 * oldSign));
                    balls.get(i).setXVelocity(newXVelocity);

                }
                // if the ball hits the side of the paddle, just change the sign of the velocities

                if (balls.get(i).getX()+Constants.BALL_DIAMETER >= paddle.x && balls.get(i).getX() <= paddle.x + paddle.width &&
                        balls.get(i).getY()+Constants.BALL_DIAMETER/2 > paddle.y && balls.get(i).getY()+Constants.BALL_DIAMETER/2 <= (paddle.y + paddle.height) &&
                        balls.get(i).getBallHit() == false){
                    topBoundaryHit = false;
                    leftBoundaryHit = false;
                    rightBoundaryHit = false;
                    balls.get(i).setBallHit(true);
                    balls.get(i).setYVelocity(-balls.get(i).getYVelocity());
                    balls.get(i).setXVelocity(-balls.get(i).getXVelocity());
                }

            }
            for (int j = 0; j < level.getLayout().size(); j++){
                checkBallWallCollision(balls.get(i),level.getLayout().get(j));
            }
        }

    }
    //getting our angle dependant on where the ball collides with the paddle, leftmost point: -1, middle: 0, rightmost point: 1
    public double getBallVelocityAngle(Rectangle paddle, Rectangle ball){
        double relativeXIntersect = (paddle.getX() + paddle.width / 2.0) - (ball.x + (ball.width / 2.0));
        double normalXIntersect = relativeXIntersect / (paddle.width / 2.0);
        double theta = normalXIntersect * Constants.BALL_MAX_BOUNCE_ANGLE;
        return Math.toRadians(theta);
    }
    public void checkBallWallCollision(Ball ball, Wall wall){
        int wallPointValue = wall.getColourPointValue();
        // top of ball hits bottom of wall
        if (ball.getYVelocity() < 0 &&
            ball.getY() <= wall.y + Constants.WALL_HEIGHT &&
            ball.getX()+ Constants.BALL_DIAMETER/2 >= wall.x && ball.getX()+ Constants.BALL_DIAMETER/2 <= wall.x + Constants.WALL_WIDTH &&
            ball.getY() + Constants.BALL_DIAMETER > wall.y &&
            wall.getWallHit() == false){
                level.getLayout().remove(wall);
                topBoundaryHit = false;
                leftBoundaryHit = false;
                rightBoundaryHit = false;
                score.addToScore(wallPointValue);
                ball.setBallHit(false);
                wall.setWallHitTrue();
                ball.setYVelocity(-ball.getYVelocity());
                newRandomBall(ball);
        }
        //bottom of ball hits top of wall
        if (ball.getYVelocity() > 0 &&
           ball.getY() + Constants.BALL_DIAMETER >= wall.y &&
           ball.getX()+ Constants.BALL_DIAMETER/2 >= wall.x && ball.getX()+ Constants.BALL_DIAMETER/2 <= wall.x + Constants.WALL_WIDTH &&
           ball.getY() + Constants.BALL_DIAMETER < wall.y + Constants.WALL_HEIGHT &&
           wall.getWallHit() == false){
                level.getLayout().remove(wall);
                topBoundaryHit = false;
                leftBoundaryHit = false;
                rightBoundaryHit = false;
                score.addToScore(wallPointValue);
                ball.setBallHit(false);
                wall.setWallHitTrue();
                ball.setYVelocity(-ball.getYVelocity());
                newRandomBall(ball);

        }
        // left side of ball hits right side of wall
        if (ball.getXVelocity() < 0 &&
           (ball.getY() + Constants.BALL_DIAMETER/2) > wall.y & (ball.getY() + Constants.BALL_DIAMETER/2) < (wall.y + Constants.WALL_HEIGHT) &&
            ball.getX() <= (wall.x + Constants.WALL_WIDTH) && ball.getX() > wall.x &&
            wall.getWallHit() == false){
                level.getLayout().remove(wall);
                topBoundaryHit = false;
                leftBoundaryHit = false;
                rightBoundaryHit = false;
                score.addToScore(wallPointValue);
                ball.setBallHit(false);
                wall.setWallHitTrue();
                ball.setXVelocity(-ball.getXVelocity());
                newRandomBall(ball);

        }
        //right side of ball hits left side of wall
        if (ball.getXVelocity() > 0 &&
           (ball.getY() + Constants.BALL_DIAMETER/2) > wall.y && (ball.getY() + Constants.BALL_DIAMETER/2) < (wall.y + Constants.WALL_HEIGHT) &&
            ball.getX() + Constants.BALL_DIAMETER >= wall.x && ball.getX() + Constants.BALL_DIAMETER < wall.x + Constants.WALL_WIDTH &&
            wall.getWallHit() == false){
                level.getLayout().remove(wall);
                topBoundaryHit = false;
                leftBoundaryHit = false;
                rightBoundaryHit = false;
                score.addToScore(wallPointValue);
                ball.setBallHit(false);
                wall.setWallHitTrue();
                ball.setXVelocity(-ball.getXVelocity());
        }
    }
    public void checkLives(){
        if (score.getLives() <=0){
            gameOver = true;
        }
    }
    public void checkBallCount(){
        for (int i = 0; i < balls.size(); i++){
            Ball b = balls.get(i);
            if (b.x + b.width >= Constants.GAME_WIDTH + 100 || b.x <= -100 ||
                b.y + b.height >= Constants.GAME_HEIGHT + 100 || b.y <= -100){
                balls.remove(b);
            }
        }
    }
    public void checkWallCount(){
        if (level.getLayout().size() <= 0 && currentLevel <=1){
            level.setLevelNum(Level.getLevelNum()+1);
            currentLevel+=1;
            balls.removeAll(balls);
            level.updateLayout();
            newPaddle();
            newBall();
        }
        if (level.getLayout().size() <= 0 && currentLevel==2){
            win = true;
        }
    }
    @Override
    public void paint(Graphics g){
        background = createImage(getWidth(),getHeight());
        Image field = new ImageIcon("./textures/field.png").getImage();
        graphics = background.getGraphics();
        graphics.drawImage(field,0,0,this);
        draw(graphics);
        g.drawImage(background,0,0,this);
    }
    public void draw(Graphics g){
        paddle.draw(g);
        level.draw(g);
        score.draw(g);
        for (int i = 0; i < balls.size(); i++){
            balls.get(i).draw(g);
        }
    }
    public void checkWin(){
        if (Level.getLevelNum() > 2){
            win = true;
        }
    }
    public void playGame(){
        if (!gameOver){
            checkStart();
            move();
            checkBallCount();
            checkCollision();
            checkLives();
            checkWallCount();
            checkWin();
            repaint();
        }
    }
    public void checkStart(){
        if (startBall.start == false){
            startBall.x = paddle.x + paddle.width/2 - Constants.BALL_DIAMETER/2;
            startBall.y = paddle.y - Constants.BALL_DIAMETER;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        playGame();
    }
    @Override
    public void	mouseClicked(MouseEvent e){
    }

    @Override
    public void	mouseEntered(MouseEvent e){}
    @Override
    public void	mouseExited(MouseEvent e){}

    @Override
    public void	mousePressed(MouseEvent e){}

    @Override
    public void	mouseReleased(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e){
        paddle.keyPressed(e);
        startBall.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        paddle.keyReleased(e);
    }

}
