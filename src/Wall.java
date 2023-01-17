import javax.swing.*;
import java.awt.*;
import java.util.*;


public class Wall extends Rectangle {

    private static String wallColour;
    private static int pointValue;
    private int hitsLeft;
    private HashMap<String, Image> textures = new HashMap<String, Image>();
    String fileName;
    private boolean wallHit = false;

    public Wall(int x, int y,String colour){
        super(x, y, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);
        wallColour = colour;
        pointValue = getColourPointValue();
        loadTextures();
    }

    public void loadTextures() {
        for (int i = 0; i < Constants.COLOURS.length; i++){
            String colour = Constants.COLOURS[i];
            fileName = "./textures/" + wallColour + ".png";
            textures.put(colour, new ImageIcon(fileName).getImage());
        }
    }
    public Image getTexture(){
        return textures.get(wallColour);
    }
    public String getFileName(){
        return fileName;
    }
    public String getWallColour(){
        return wallColour;
    }
    public int getColourPointValue(){
        if (wallColour.equals("white")){
            return 50;
        }
        else if (wallColour.equals("orange")){
            return 60;
        }
        else if (wallColour.equals("light blue")){
            return 70;
        }
        else if (wallColour.equals("green")){
            return 80;
        }
        else if (wallColour.equals("red")){
            return 90;
        }
        else if (wallColour.equals("blue")){
            return 100;
        }
        else if (wallColour.equals("pink")){
            return 110;
        }
        else if (wallColour.equals("yellow")){
            return 120;
        }
        else if (wallColour.equals("silver")){
            return 50 * Level.getLevelNum();
        }
        else{
            return 0;
        }
    }
    public int getHitsRequiredToBreak(int round){
        if (wallColour.equals("white")){
            return 1;
        }
        else if (wallColour.equals("orange")){
            return 1;
        }
        else if (wallColour.equals("light blue")){
            return 1;
        }
        else if (wallColour.equals("green")){
            return 1;
        }
        else if (wallColour.equals("red")){
            return 1;
        }
        else if (wallColour.equals("blue")){
            return 1;
        }
        else if (wallColour.equals("pink")){
            return 1;
        }
        else if (wallColour.equals("yellow")){
            return 1;
        }
        else if (wallColour.equals("silver")){
            return 50 * round;
        }
        //only for indestructable gold blocks
        else{
            return -1;
        }
    }
    public boolean getWallHit(){return wallHit;};
    public void setWallHitTrue(){wallHit = true;}
    public void draw (Graphics g){
        g.drawImage(textures.get(wallColour),x,y,null);
    }
    @Override
    public String toString(){
        String colourImage = wallColour + " " + textures.get(wallColour);
        return colourImage;
    }
}
