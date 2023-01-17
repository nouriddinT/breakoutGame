import java.awt.*;
import java.util.*;
public class Level{
    private static int levelNum;
    private ArrayList<Wall> layout = new ArrayList<Wall>();
    public Level(){
        levelNum = 1;
        int x = 0;
        int y = 0;
        Random rand = new Random();
        String colour;
        //level layouts
        if (levelNum == 1){
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 5; j++){
                    if (j == 0){
                        colour = "silver";
                    }
                    else{
                        int random = rand.nextInt(Constants.COLOURS.length-2);
                        colour = Constants.COLOURS[random];
                    }
                    layout.add(new Wall(x+80,y+60,colour));
                    y += Constants.WALL_HEIGHT;
                }
                x += Constants.WALL_WIDTH;
                y = 0;
            }
        }
        else if(levelNum == 2){
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 8; j++){
                    int random = rand.nextInt(Constants.COLOURS.length-2);
                    colour = Constants.COLOURS[random];
                    if ((i+j) % 2 == 0){
                        layout.add(new Wall(x+80,y+60,colour));
                    }
                    y += Constants.WALL_HEIGHT;
                }
                x += Constants.WALL_WIDTH;
                y = 0;
            }
        }
    }
    public static int getLevelNum(){
        return levelNum;
    }
    public static void setLevelNum(int num){
        levelNum = num;
    }
    public void updateLayout(){
        int x = 0;
        int y = 0;
        Random rand = new Random();
        String colour;
        if (levelNum == 1){
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 5; j++){
                    if (j == 0){
                        colour = "silver";
                    }
                    else{
                        int random = rand.nextInt(Constants.COLOURS.length-2);
                        colour = Constants.COLOURS[random];
                    }
                    layout.add(new Wall(x+80,y+60,colour));
                    y += Constants.WALL_HEIGHT;
                }
                x += Constants.WALL_WIDTH;
                y = 0;
            }
        }
        else if(levelNum == 2){
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 8; j++){
                    int random = rand.nextInt(Constants.COLOURS.length-2);
                    colour = Constants.COLOURS[random];
                    if ((i+j) % 2 == 0){
                        layout.add(new Wall(x+80,y+60,colour));
                    }
                    y += Constants.WALL_HEIGHT;
                }
                x += Constants.WALL_WIDTH;
                y = 0;
            }
        }
    }

    public ArrayList<Wall> getLayout(){
        return layout;
    }
    public void draw(Graphics g){
        for (int i = 0; i < layout.size(); i++){
            layout.get(i).draw(g);
        }

    }
}
