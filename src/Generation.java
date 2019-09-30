import java.util.Random;
import java.lang.Math;
public class Generation {
    private int[] roomWidth;
    private int[] roomHeight;
    private ConstantsCanvas constants;
    //0 = nothing, 1 = corridor, 2 = room
    private int[][] canvas;
    private int maxCorridorSize;
    private int minCorridorSize;
    private Random random;
    private int totalRooms;

    public Generation(){
        constants = new ConstantsCanvas();
        canvas = new int[constants.getCanvasDimensions()][constants.getCanvasDimensions()];
        maxCorridorSize = constants.getMaxCorridor();
        minCorridorSize = constants.getMinCorridor();
        random = new Random();
        roomWidth = new int[2];
        roomHeight = new int[2];
        roomWidth[0] = constants.getMinWidth();
        roomWidth[1] = constants.getMaxWidth();
        roomHeight[0] = constants.getMinHeight();
        roomHeight[1] = constants.getMaxHeight();
        totalRooms = constants.getTotalRooms();
    }
    // look up the unity c# one, start at one room then go around making a bunch of rooms.
    //
    public int[] createCorridor(int x,int y,int direction){
        int size  = minCorridorSize+random.nextInt(maxCorridorSize);
        int[] coords = new int[2];
        if (direction == 0){
            for (int i = 0; i<size; i++){
                canvas[x][y-i] = 1;
                if (size -i == 1){
                    coords[0]= x;
                    coords[1] = y-i;
                }
            }
        }
        if (direction == 1) {
            for (int i = 0; i < size; i++) {
                canvas[x+i][y] = 1;
                if (size -i == 1){
                    coords[0]= x+i;
                    coords[1] = y;
                }
            }
        }
        if (direction == 2){
            for (int i = 0; i < size; i++) {
                canvas[x][y+i] = 1;
                if (size -i == 1){
                    coords[0]= x;
                    coords[1] = y+i;
                }
            }
        }
        if (direction == 3){
            for (int i = 0; i < size; i++) {
                canvas[x-i][y] = 1;
                if (size -i == 1){
                    coords[0]= x-i;
                    coords[1] = y;
                }
            }
        }
        return coords;
    }
    public int[] createRoom (int x, int y, int orientation){
        //different orientations for where spawned
        //orientation based on orientation of corridor
        //x,y are the coords of the end of the corridor
        //creating room at the bottom left for orientation 0/1
        //top left for 2
        //bottom right for 3
        int sizeWidth = random.nextInt(roomWidth[1])+(roomWidth[0]);
        int sizeHeight = random.nextInt(roomHeight[1])+(roomHeight[0]);
        int[] coords = new int[2];
        if (orientation == 0){
            int i=0;
            int j=0;
            int midx = sizeWidth/2;
            for (i = 0; i<sizeHeight;i++){
                for (j = 0; j<sizeWidth;j++){
                    canvas[x+(j-midx)][y-(i+1)] = 2;
                }
            }
            coords[0]=x;
            coords[1]=y -((int)Math.ceil(sizeHeight/2.0);
        }
        else if(orientation == 1){
            int i=0;
            int j=0;
            int midy = sizeWidth/2;
            for (i=0; i<sizeHeight;i++){
                for (j=0; j<sizeWidth;j++){
                    canvas[x+j+1][y+midy-i]= 2;
                }
            }
            coords[0]=x+((int)Math.ceil(sizeWidth/2.0));
            coords[1]=y;
        }
        else if(orientation==2){
            int i=0;
            int j=0;
            int midx = sizeWidth/2;
            for (i=0;i<sizeHeight;i++){
                for (j =0; j<sizeWidth;j++){
                    canvas[x+(j-midx)][y+i+1]=2;
                }
            }
            coords[0]=x;
            coords[1]=y +((int)Math.ceil(sizeHeight/2.0));

        }
        else {
            int i=0;
            int j=0;
            int midy = sizeHeight/2;
            for ( i=0; i<sizeHeight;i++){
                for (j=0;j<sizeWidth;j++){
                    canvas[x-(j+1)][y+midy-i]=2;
                }
            }
            coords[0]=x-((int)Math.ceil(sizeWidth/2.0));
            coords[1]=y;
        }
        return coords;
    }
    public boolean checkCorridor(int x, int y, int orientation){
        return true;
    }
    public boolean checkRoom(int x, int y,int orientation){
        return true;
    }
    public int[][] createDungeon(){
        //will need to add a 2nd point for if/when a rectangular canvas is made
        int centerpoint = (int) Math.ceil(constants.getCanvasDimensions()/2.0);
        int orientation = random.nextInt(4);
        int[] coords = new int[2];
        int[] corCoords = new int[2];
        int[] roomCoords = new int[2];
        int oldOrientation = 0;
        coords[0] = centerpoint;
        coords[1] = centerpoint;
        corCoords = createCorridor(coords[0],coords[1],orientation);
        roomCoords = createRoom(corCoords[0],corCoords[1],orientation);
        for (int i=0; i<(totalRooms-1);i++){
            oldOrientation = orientation;
            orientation = random.nextInt(4);
            while (orientation == oldOrientation){orientation = random.nextInt(4);}
            corCoords = createCorridor(roomCoords[0],roomCoords[1],orientation);
            roomCoords = createRoom(corCoords[0],corCoords[1],orientation);
        }
        return canvas;
    }
}

