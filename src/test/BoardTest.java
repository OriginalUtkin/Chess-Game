package test;

import org.junit.Test;
import static org.junit.Assert.*;
import Board.Board;


/**
 *
 * @author xutkin00, xpolis03
 */

public class BoardTest {

    @Test
    public void BoardWithoutFigureTest(){
        Board gameBoard = new Board();
        assertEquals("1aB", gameBoard.gameBoard[0][0].toString());
        assertEquals("1dW", gameBoard.gameBoard[0][3].toString());
        assertEquals("2aW", gameBoard.gameBoard[1][0].toString());
        assertEquals("8aW", gameBoard.gameBoard[7][0].toString());
    }
}
