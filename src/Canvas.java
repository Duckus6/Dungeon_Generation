import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
//import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
public class Canvas {
    private Color background;
    private ConstantsCanvas constants;
    private Generation generation;
    private Color colCorridor;
    private Color walkable;
    private final JPanel gui;
    private JPanel canvas;
    public static int SQUARECONSTANT;
    private JButton[][] canvasSquares;
    private Dimension screenSize;
     public Canvas(){
        constants = new ConstantsCanvas();
        background = constants.getWalls();
        walkable = constants.getFloor();
        generation = new Generation();
        colCorridor = constants.getCorridor();
        gui = new JPanel(new BorderLayout(3,3));
        SQUARECONSTANT = constants.getCanvasDimensions();
        canvasSquares = new JButton[SQUARECONSTANT][SQUARECONSTANT];
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(""+screenSize.getWidth()+"   "+screenSize.getHeight());
        int[][] genCanvas =  generation.createDungeon();
        initialiseGUI(genCanvas);
    }
    public final void initialiseGUI(int[][] genCanvas){
        //set up the main GUI
        gui.setBorder(new EmptyBorder(5,5,5,5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        canvas = new JPanel(new GridLayout(0,SQUARECONSTANT));
        canvas.setBorder(new LineBorder(Color.BLACK));
        gui.add(canvas);

        //create board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < canvasSquares.length; ii++) {
            for (int jj=0; jj<canvasSquares[ii].length;jj++){
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                //our chess pieces are 64x64 px in size so we'll
                //"fill this in" using a transparent icon..
                // set so it is square and not rectangular
                int imageWidth = (int)(screenSize.getHeight()/SQUARECONSTANT)/2;
                int imageHeight = (int)(screenSize.getHeight()/SQUARECONSTANT)/2;
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
//                if((jj %2 ==1 && ii%2 == 1)|| (jj%2 ==0 && ii%2==0)){
//                    b.setBackground(walkable);
//                }else{
//                    b.setBackground(background);
//                }
                if (genCanvas[jj][ii] == 2){
                    b.setBackground(walkable);
                }
                else if(genCanvas[jj][ii] == 1){
                    b.setBackground(colCorridor);
                }
                else{b.setBackground(background);}
                canvasSquares[jj][ii] = b;
            }
        }
        for (int ii = 0; ii<SQUARECONSTANT;ii++){
            for (int jj=0;jj<SQUARECONSTANT;jj++){
                canvas.add(canvasSquares[jj][ii]);
            }
        }
    }

    public final JComponent getGui(){
        return gui;
    }
    public static void main(String[] args){
        //Runnable r = new Runnable() {
        Runnable r = () -> {
            Canvas cb = new Canvas();
            JFrame f = new JFrame("Dungeon");
            f.add(cb.getGui());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);
            // ensures the frame is the minimum size it needs to be
            // in order display the components within it
            f.pack();
            // ensures the minimum size is enforced.
            f.setMinimumSize(f.getSize());
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }

}
