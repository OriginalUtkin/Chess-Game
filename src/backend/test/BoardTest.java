package backend.test;

import org.junit.Test;
import static org.junit.Assert.*;
import backend.Board.Board;


/**
 *
 * @author xutkin00, xpolis03
 */

public class BoardTest {

    @Test
    public void BoardWithoutFigureTest(){
        Board gameBoard = new Board();
        assertEquals("1aB", gameBoard.gameBoard[0][0].getFullString());
        assertEquals("1dW", gameBoard.gameBoard[0][3].getFullString());
        assertEquals("2aW", gameBoard.gameBoard[1][0].getFullString());
        assertEquals("8aW", gameBoard.gameBoard[7][0].getFullString());
    }
}
