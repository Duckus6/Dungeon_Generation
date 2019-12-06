import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
//import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
public  class ConstantsCanvas {
    //dimensions of the canvas
    private int canvas;
    //min width of a room
    private int minWidth;
    //max width of a room
    private int maxWidth;
    //min height of a room
    private int minHeight;
    //max height of a room
    private int maxHeight;
    //color of walls
    private Color walls;
    //color of floor/ interactable areas
    private Color floor;
    private Color corridor;
    private int minCorridor;
    private int maxCorridor;
    private int totalRooms;
    public ConstantsCanvas() {
        canvas = 100;
        minWidth = 3;
        maxWidth = 8;
        minHeight = 3;
        maxHeight = 8;
        walls = Color.black;
        floor = Color.white;
        corridor = Color.yellow;
        minCorridor = 1;
        maxCorridor = 8;
        totalRooms = 8;
    }
    //bunch of getters/accessors
    public int getCanvasDimensions(){
        return canvas;
    }
    public int getMinWidth(){
        return minWidth;
    }
    public int getMaxWidth(){
        return maxWidth;
    }
    public int getMinHeight(){
        return minHeight;
    }
    public int getMaxHeight(){
        return maxHeight;
    }
    public Color getWalls(){
        return walls;
    }
    public Color getFloor(){
        return floor;
    }
    public Color getCorridor(){return corridor;}
    public int getMinCorridor(){
        return minCorridor;
    }
    public int getMaxCorridor(){return maxCorridor;}
    public int getTotalRooms(){
        return totalRooms;
        }
}
