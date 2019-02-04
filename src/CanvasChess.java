import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasChess {
    private Color background;
    private Color walkable;
    private final JPanel gui = new JPanel(new BorderLayout(3,3));
    private JButton[][] canvasSquares = new JButton[8][8];
    private JPanel CANvas;
    private JLabel message = new JLabel("Test");
    private static final String COLS = "ABCDEFGH";
    CanvasChess(){
        background = Color.black;
        walkable = Color.gray;
        initialiseGUI();
    }
    public final void initialiseGUI(){
        //set up the main GUI
        gui.setBorder(new EmptyBorder(5,5,5,5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools,BorderLayout.PAGE_START);
        tools.add(new JButton("New")); //notfin
        tools.add(new JButton("save"));
        tools.add(new JButton("Restore"));
        tools.addSeparator();
        tools.add(new JButton("Resign"));
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        CANvas = new JPanel(new GridLayout(0,9));
        CANvas.setBorder(new LineBorder(Color.BLACK));
        gui.add(CANvas);

        //create board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < canvasSquares.length; ii++) {
            for (int jj=0; jj<canvasSquares[ii].length;jj++){
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                //our chess pieces are 64x64 px in size so we'll
                //"fill this in" using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if((jj %2 ==1 && ii%2 == 1)|| (jj%2 ==0 && ii%2==0)){
                    b.setBackground(walkable);
                }else{
                    b.setBackground(background);
                }
                canvasSquares[jj][ii] = b;
            }
        }
        //fill the board
        CANvas.add(new JLabel(""));
        //fill in top row
        for (int ii = 0; ii<8; ii++){
            CANvas.add(
                    new JLabel(COLS.substring(ii,ii+1),SwingConstants.CENTER));
        }
        //fill the black non-pawn piece row
        for (int ii = 0; ii<8;ii++){
            for (int jj=0;jj<8;jj++){
                switch(jj){
                    case 0:
                        CANvas.add(new JLabel("" +(ii +1), SwingConstants.CENTER));
                    default:
                        CANvas.add(canvasSquares[jj][ii]);
                }
            }
        }
    }
    public final JComponent getChessBoard(){
        return CANvas;
    }
    public final JComponent getGui(){
        return gui;
    }
    public static void main(String[] args){
        Runnable r = new Runnable() {
            @Override
            public void run(){
                CanvasChess cb = new CanvasChess();
                JFrame f = new JFrame("ChessChamp");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

}
