import Abstracts.ChessPiece;
//import Enums.Color;
import Figures.King;
import Figures.Movement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;

public class IJAProject {

    public static void main(String args[]) {

        /**
         * PSEUDO CODE:
         * Create chess piece (just for debugging. Pieces will be created during board init)
         * Get possible moves
         * Remove from possible moves variable cells which has been already obtained by other piece with the same color or add new possible movements in case of pawn
         * Call move function with new position parameters
         * Render frontend when it is done
         */

        JFrame frame= new JFrame("IJA Project");
        frame.setSize(1000,850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        Font font = new Font("Verdana", Font.PLAIN, 14);

        // Tabs
        Tab tabs = new Tab();


        /*Menu*/
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setFont(font);

        JMenuItem newGame = new JMenuItem("New");
        newGame.setFont(font);
        menuGame.add(newGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabs.addNewTab(frame, "Game 2");
            }
        });

        JMenuItem refreshGame = new JMenuItem("Restart");
        menuGame.add(refreshGame);
        refreshGame.setFont(font);

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setFont(font);
        menuGame.add(exitGame);

        exitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JMenu menuActions = new JMenu("Actions");
        menuActions.setFont(font);
        JMenuItem unDo = new JMenuItem("Undo");
        unDo.setFont(font);
        menuActions.add(unDo);
        JMenuItem reDo = new JMenuItem("Redo");
        reDo.setFont(font);
        menuActions.add(reDo);

        JMenu menuView = new JMenu("View");
        menuView.setFont(font);

        frame.setJMenuBar(menuBar);
        menuBar.add(menuGame);
        menuBar.add(menuActions);
        menuBar.add(menuView);

        // Makes new Tab
        // testing
        tabs.addNewTab(frame, "Game 1");

        frame.setVisible(true);



        Game chessGame = new Game();

        /** TODO seems as fuck. refactor it. Coordinates will be set right from Cell */
        chessGame.setPiece(new King(Enums.Color.BLACK), 0, 0);

        // Simulate mouse button click
        ChessPiece boardPiece = chessGame.getBoardCell(0, 0);

        // Get all possible statements of current piece and show them on GUI
        List<Movement> possibleMovements = new ArrayList<>(boardPiece.calculatePossibleMovements()) ;

        if (possibleMovements.size() == 0){
            System.out.println("Imma so stoned and cannot move. Help me pls");
        }else{
            System.out.println("Move me");
        }

        /**
         * Move string:
         * getBoardCell -> to string (src)
         * if returned piece not Pawn -> To string
         * Before calling setPiece get dst cell and call toString (dst)
         */
        System.out.println();
    }
}
