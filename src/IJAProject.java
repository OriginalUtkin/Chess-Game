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

        // Simulate mouse button click. Now we have a chess piece from selected cell
        ChessPiece boardPiece = chessGame.getBoardPiece(0, 0);

        // Get all possible statements of current piece
        List<Movement> possibleMovements = new ArrayList<>(boardPiece.calculatePossibleMovements()) ;
//        chessGame.isPossible(possibleMovements, boardPiece.getColor());

//        // Remove moves from possible moves for currently selected chess piece which couldn't be done
//        for(int counter = 0; counter < possibleMovements.size(); counter++){
//
//            if (!chessGame.isPossible(possibleMovements.get(counter), boardPiece.getColor()))
//                possibleMovements.remove(counter);
//        }

        // Return PossibleMoves to GUI and show them on board. Now we're waiting for new input from player.

        // if player choose a cell which isn't represented in PossibleMovements List as dst cell -> clear selected cell and give him option to choose another cell
        // else change x and y coordinates for chess piece , set pointer to piece in src cell to null dst pointer to chessPiece. Current player Turn is done -> return Turn string

        /**
         * Move string:
         * if returned piece not Pawn -> To string
         * Before calling setPiece get dst cell and call toString (dst)
         */
        System.out.println();
    }

}
