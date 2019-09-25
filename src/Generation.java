import java.util.Random;
public class Generation {
    private int[] roomWidth;
    private int[] roomHeight;
    private ConstantsCanvas constants;
    //0 = nothing, 1 = corridor, 2 = room
    private int[][] canvas;
    private int corridorSize;
    private Random random;
    private int totalRooms;

    public Generation(){
        constants = new ConstantsCanvas();
        canvas = new int[constants.getCanvasDimensions()][constants.getCanvasDimensions()];
        corridorSize = constants.getCorridorSize();
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
    public int[] createCorridor(int x,int y,int direction){
        int size  = random.nextInt(corridorSize);
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
                canvas[x+i][y] = 1;
                if (size -i == 1){
                    coords[0]= x+i;
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
            int cornerx  = x - random.nextInt(sizeWidth);
            for (i =0; i<sizeHeight;i++){
                for (j =0; j<sizeWidth;j++){
                    canvas[cornerx+j][y-(i+1)] = 2;
                }
            }
            coords[0]=cornerx +j -1;
            coords[1]=y -(i+2);
        }
        else if(orientation == 1){
            int i=0;
            int j=0;
            int cornery = y+random.nextInt(sizeHeight);
            for (i=0; i<sizeHeight;i++){
                for (j=0; j<sizeWidth;j++){
                    canvas[x+j+1][cornery-i]= 2;
                }
            }
            coords[0]=x+j;
            coords[1]=cornery-i-1;
        }
        else if(orientation==2){
            int i=0;
            int j=0;
            int cornerx = x- random.nextInt(sizeWidth);
            for (i=0;i<sizeHeight;i++){
                for (j =0; j<sizeWidth;j++){
                    canvas[cornerx+j][y+i+1]=2;
                }
            }
            coords[0]=cornerx +(j-1);
            coords[1]=y +i;

        }
        else {
            int i=0;
            int j=0;
            int cornery = y+random.nextInt(sizeHeight);
            for ( i=0; i<sizeHeight;i++){
                for (j=0;j<sizeWidth;j++){
                    canvas[x-(j+1)][cornery-i]=2;
                }
            }
            coords[0]=x-(j+2);
            coords[1]=cornery-i-1;
        }
        return coords;
    }
    /*public boolean checkCorridor(int x, int y, int direction, int size){
        int width = roomWidth[1];
        int height = roomHeight[1];
        switch (direction){
            case 0:
                y-=(size+height);
                x+=width;
                if ((x>100)||(y<0)){
                    return false;
                }
                return true;
            case 1:
                x+=(size+width);
                y-=height;
                if ((x>100)||(y<0)){
                    return false;
                }
                return true;
            case 2:
                y+=(size+height);
                x+=width;
                if ((x>100)||(y>100)){
                    return false;
                }
                return true;
            case 3:
                y-=height;
                x-=(width+size);
                if ((x>0)||(y>0)){
                    return false;
                }
                return true;
        }
        /*if (direction == 0){
            y-=(size+height);
            x+=width;
            if ((x>100)||(y<0)){
                return false;
            }
            return true;
        }
        else if (direction ==1){
            x+=(size+width);
            y-=height;
            if ((x>100)||(y<0)){
                return false;
            }
            return true;
        }
        else if(direction ==2){
            y+=(size+height);
            x+=width;
            if ((x>100)||(y>100)){
                return false;
            }
            return true;
        }
        else{
            y-=height;
            x-=(width+size);
            if ((x>0)||(y>0)){
                return false;
            }
            return true;
        }
    }*/
    /*public boolean checkRoom(int x, int y, int direction, int size, int size2){
        //size = width size
        //size 2 = height size

    }*/
    public int[][] createDungeon(){
        //will need to add a 2nd point for if/when a rectangular canvas is made
        int centerpoint = (int) Math.ceil(constants.getCanvasDimensions()/2.0);
        int orientation = random.nextInt(4);
        int[] coords = new int[2];
        int[] corCoords = new int[2];
        int[] roomCoords = new int[2];
        coords[0] = centerpoint;
        coords[1] = centerpoint;
        corCoords = createCorridor(coords[0],coords[1],orientation);
        roomCoords = createRoom(corCoords[0],corCoords[1],orientation);
        for (int i=0; i<(totalRooms-1);i++){
            orientation = random.nextInt(4);
            corCoords = createCorridor(roomCoords[0],roomCoords[1],orientation);
            roomCoords = createRoom(corCoords[0],corCoords[1],orientation);
        }
        return canvas;
    }
}

