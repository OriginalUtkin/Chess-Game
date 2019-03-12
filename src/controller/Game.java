package controller;

import backend.Abstracts.ChessPiece;
import backend.Board.Board;
import backend.Enums.Color;
import backend.Enums.Direction;
import backend.Figures.*;
import backend.Board.Cell;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    // Game backend
    private Board gameBoard;

    // Game board interaction specification
    private ChessPiece selectedPiece;
    private gui.Cell selectedCell;
    private gui.Cell destinationCell;

    //TODO: initialise obj for right tab selection
    //Game right tab interaction specification

    // Game turn specification
    private Color currentTurn;
    private int turnNumber;
    private int selectedTurnNumber;
    private List<String> singleTurnNotations;
    private List<String> cancelledNotations;

    private char identifier;

    // Game temporary variables
    private String whiteTurnNotation;


    public Game(boolean initFlag) {
        this.gameBoard = new Board(initFlag);

        this.selectedPiece = null;
        this.selectedCell = null;
        this.destinationCell = null;

        this.singleTurnNotations = new ArrayList<>();
        this.cancelledNotations = new ArrayList<>();

        this.turnNumber = 1;
        this.currentTurn = Color.WHITE;

        this.selectedTurnNumber = 0;

    }

    public void setSelected(gui.Cell selectedCell, ChessPiece selectedPiece) {
        /**
         * Set selectedCell and selectedPiece variables to particular values (Set selection of src cell).
         *
         * @param selectedCell is a cell from gui which was selected
         * @param selectedPiece chess piece which is staying on selected cell
         */

        this.selectedCell = selectedCell;
        this.selectedPiece = selectedPiece;
    }

    public void dropSelected() {
        /**
         * Set selectedCell and selectedPiece variables to null (Drop selection). Is used after turn is done or turn
         * isn't even possible.
         */

        this.selectedCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.selectedCell.repaint();

        this.selectedPiece = null;
        this.selectedCell = null;
    }

    public void setDestinationCell(gui.Cell dstCell) {
        /**
         * Set destinationCell variable to (gui) Cell object from GUI (Set selection of dst cell).
         */

        this.destinationCell = dstCell;
    }

    public void dropDestinationCell() {
        /**
         * Set destinationCell variable tu null (Drop selection). Is used after turn is done or turn
         * isn't even possible.
         */

        this.destinationCell = null;
    }

    private void changeTurn() {
        /**
         * Change a turn and set color for pieces which could be selected during current turn.
         *
         * @return void
         */

        if (this.currentTurn == Color.WHITE)
            this.currentTurn = Color.BLACK;

        else
            this.currentTurn = Color.WHITE;
    }

    public List<Movement> getPossibleMovements() {
        /**
         * Calculate all possible movements for selected cell. Calculation method depends on object (polimorphic method).
         *
         * @see applyRules
         * @see calculatePossibleMovements calculation movements method for particular chess piece
         *
         * @return list of all possible movements. One movement is represented by Movement object
         */

        List<Movement> possibleMovements;

        possibleMovements = this.selectedPiece.calculatePossibleMovements();
        this.applyRules(possibleMovements, this.selectedPiece.getColor());

        return possibleMovements;
    }

    public Color getCurrentTurn(){
        return this.currentTurn;
    }

    public boolean isCellSelected() {
        /**
         * Check if any cell is selected
         *
         * @return true if cell is selected, false otherwise
         */

        return this.selectedPiece != null && this.selectedCell != null;
    }

    public boolean destinationSelected() {
        /**
         * Check if any destination cell is selected
         *
         * @return true if cell is selected, false otherwise
         */

        return this.destinationCell != null;
    }

    public ChessPiece getBoardPiece(final int row, final int column) {
        /**
         * Return chess piece that is staying on cell with coordinates x and y.
         *
         * @param row - row coordinate; array index 0 - 7
         * @param column - column coordinate; array index 0 - 7
         *
         * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
         */

        return this.gameBoard.gameBoard[row][column].getPiece();
    }

    public void setPiece(ChessPiece piece, int row, int column) {
        /**
         * set chess piece object to particular position
         *
         * @param piece which will be set to the board
         * @param row where piece will be set
         * @param column where piece will be set
         */

        this.gameBoard.gameBoard[row][column].setPiece(piece);
    }

    private boolean isPossibleMovement(Movement movement, final Color pieceColor) {
        /**
         * Check if movement of chess piece is possible.
         *
         * Chess piece can move to destination cell in case:
         * 1) Destination cell is free
         * 2) Destination cell isn't free but contains enemy player chess piece
         *
         * Check if additionalCheck flag is set. If so, that means that this movement should be
         * processed using other rules (pawn diagonal movement, pawn vertical movement):
         *
         * Diagonal:
         * 1) Destination cell shouldn't be free
         * 2) Same as 2 point above
         *
         * Vertical:
         * 1) Destination cell SHOULD be free
         *
         * @param movement - possible movement of chess piece
         * @param color - color of chess piece
         *
         * @return true if chess piece movement is possible, false otherwise
         */

        final Cell dstCell = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()];

        if (movement.getAdditionalCheck()) {

            if ((pieceColor == Color.WHITE) && (movement.getDirection() == Direction.DIAGONAL_UP_LEFT || movement.getDirection() == Direction.DIAGONAL_UP_RIGHT))
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;


            if ((pieceColor == Color.BLACK) && (movement.getDirection() == Direction.DIAGONAL_DOWN_LEFT || movement.getDirection() == Direction.DIAGONAL_DOWN_RIGHT))
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;

            if (movement.getDirection() == Direction.VERTICAL_UP || movement.getDirection() == Direction.VERTICAL_DOWN)
                return dstCell.isFree();

            return false;
        }

        return dstCell.isFree() || dstCell.getPiece().getColor() != pieceColor;
    }

    private boolean beatEnemy(final Movement movement, final Color pieceColor) {
        /**
         * Check if after movement selected piece enemy peace was beaten.
         *
         * @param movement possible movement of selected chess piece
         * @param pieceColor color of selected chess piece
         *
         * @return true is enemy piece was beaten, else otherwise
         */

        final Cell dstCell = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()];

        if (dstCell.isFree())
            return false;

        else {

            if (dstCell.getPiece().getColor() != pieceColor)
                return true;
        }

        return false;
    }


    private boolean beatEnemy(){
        if (this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].isFree())
            return false;

        return this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].getPiece().getColor() !=
                this.gameBoard.gameBoard[this.selectedCell.getRow()][this.selectedCell.getColumn()].getPiece().getColor();
    }

    // TODO: this method could be refactored somehow :/
    private void applyRules(List<Movement> allPossibleMovements, final Color pieceColor) {
        /**
         * !!!---------------------WARNING----------------------!!!
         * This method is changing allPossibleMovements list parameter
         * !!!--------------------------------------------------!!!
         *
         * Recalculate possible piece moves on chess board;
         *
         * @see beatEnemy
         * @see isPossible
         *
         * @param allPossibleMovements all possible movements list of selected chess piece. This list will be changed
         *                             after applying rules.
         * @param boardPiece selected board piece. Moves are calculated depends on selected piece color
         *
         * @return void but input list will be changed in result
         */

        boolean horizontalLeft = false;
        boolean horizontalRight = false;

        boolean verticalTop = false;
        boolean verticalDown = false;

        boolean diagonalTopRight = false;
        boolean diagonalTopLeft = false;
        boolean diagonalDownRight = false;
        boolean diagonalDownLeft = false;

        Iterator<Movement> movementIterator = allPossibleMovements.iterator();

        while (movementIterator.hasNext()) {
            Movement currentMovement = movementIterator.next();

            // Rules for all type of pieces except Knight
            if (currentMovement.getDirection() != null) {
                switch (currentMovement.getDirection()) {

                    case VERTICAL_UP:
                        if (!verticalTop) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                verticalTop = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    verticalTop = true;
                            }
                        } else {
                            if (verticalTop)
                                movementIterator.remove();
                        }
                        break;

                    case VERTICAL_DOWN:
                        if (!verticalDown) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                verticalDown = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    verticalDown = true;
                            }

                        } else {
                            if (verticalDown)
                                movementIterator.remove();
                        }
                        break;

                    case HORIZONTAL_RIGHT:
                        if (!horizontalRight) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                horizontalRight = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    horizontalRight = true;
                            }

                        } else {
                            if (horizontalRight)
                                movementIterator.remove();
                        }
                        break;

                    case HORIZONTAL_LEFT:
                        if (!horizontalLeft) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                horizontalLeft = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    horizontalLeft = true;
                            }

                        } else {
                            if (horizontalLeft)
                                movementIterator.remove();
                        }
                        break;

                    case DIAGONAL_UP_LEFT:
                        if (!diagonalTopLeft) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                diagonalTopLeft = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    diagonalTopLeft = true;
                            }

                        } else {
                            if (diagonalTopLeft)
                                movementIterator.remove();
                        }
                        break;

                    case DIAGONAL_DOWN_RIGHT:
                        if (!diagonalDownRight) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                diagonalDownRight = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    diagonalDownRight = true;
                            }

                        } else {
                            if (diagonalDownRight)
                                movementIterator.remove();
                        }
                        break;

                    case DIAGONAL_DOWN_LEFT:
                        if (!diagonalDownLeft) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                diagonalDownLeft = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    diagonalDownLeft = true;
                            }

                        } else {
                            if (diagonalDownLeft)
                                movementIterator.remove();
                        }
                        break;

                    case DIAGONAL_UP_RIGHT:
                        if (!diagonalTopRight) {

                            if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                                diagonalTopRight = true;
                                movementIterator.remove();
                            } else {
                                if (this.beatEnemy(currentMovement, pieceColor))
                                    diagonalTopRight = true;
                            }

                        } else {
                            if (diagonalTopRight)
                                movementIterator.remove();
                        }
                        break;
                }
            } else {
                if (!this.isPossibleMovement(currentMovement, pieceColor))
                    movementIterator.remove();
            }
        }
    }

    public boolean isPossibleDestination(int dstRow, int dstColumn) {
        /**
         * Calculate possible movements for currently selected chess piece and check if destination cell
         * is possible movement for selected piece
         *
         * @param srcX destination row coordinate
         * @param srcY destination column coordinate
         *
         * @return true in case if destination cell is a possible movement and piece could be moved. False otherwise
         */

        for (Movement movement : this.getPossibleMovements()) {
            if (movement.getRow() == dstRow && movement.getColumn() == dstColumn)
                return true;
        }

        return false;
    }

    public String movePiece() {

        if (this.selectedPiece.getStartedPosition()){
            this.selectedPiece.changeStartedPosition();
        }
        String turnNotation = this.getFullNotation();


        this.destinationCell.setAbbreviation(this.selectedPiece.getColor().toString() + this.selectedPiece.toString());
        this.setPiece(this.selectedPiece, this.destinationCell.getRow(), this.destinationCell.getColumn());

        this.selectedCell.setAbbreviation("");


        this.setPiece(null, this.selectedCell.getRow(), this.selectedCell.getColumn());

        this.dropSelected();
        this.dropDestinationCell();

        this.changeTurn();

        this.singleTurnNotations.add(this.selectedTurnNumber, turnNotation);

        this.selectedTurnNumber += 1;


        return turnNotation;
    }

    private String getFullNotation() {
        /**
         * Create full turn notation.
         *
         * @return string which represent full turn notation
         */

        String check = "";
        String dstPart;

        if (this.isCheck()) {

            check = "+";

            if(this.isMate())
                check = "#";
        }

        if (this.beatEnemy()){

            dstPart = "x" +
                    this.notationAbbreviate(this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].getPiece().toString()) +
                    this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].toString();

        }else
            dstPart = this.gameBoard.gameBoard[destinationCell.getRow()][destinationCell.getColumn()].toString();

        return  this.notationAbbreviate(this.selectedPiece.toString()) +
                this.gameBoard.gameBoard[selectedCell.getRow()][selectedCell.getColumn()].toString() +
                dstPart + check;
    }

    private String notationAbbreviate(final String abbreviate){

        return abbreviate.equals("p") ? "" : abbreviate;

    }


    private boolean isCheck() {
        /**
         * Check if under moving chess piece king is under the check
         *
         * @return true if king under the check after moving chess piece, false otherwise
         */
        List<Movement> possibleMovements = this.getPossibleMovements();

        for (Movement movement : possibleMovements) {

            // King with opposite color in possible movement for piece which was moved
            ChessPiece pieceOnBoard = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].getPiece();

            if (pieceOnBoard == null)
                continue;

            if (pieceOnBoard.getColor() != this.selectedPiece.getColor() && pieceOnBoard.toString().equals("K")) {
                return true;
            }
        }
        return false;
    }

    public String getLastWhiteNotation(){
        /**
         * Return last notation of the white player turn. Used in case when game is saved before black player turn.
         *
         * @return notation string for last white player turn in format [turn_num]. [turn_notation]
         */

        return this.whiteTurnNotation != null ?
                Integer.valueOf(this.turnNumber).toString() + ". " +this.whiteTurnNotation :
                null;
    }

    private boolean isMate() {
        List<Movement> selectedPossibleMovements = this.getPossibleMovements();
        ChessPiece kingPiece = null;

        int currentKingRow = 0;
        int currentKingColumn = 0;

        final int currentSelectedRow = this.destinationCell.getRow();
        final int currentSelectedColumn = this.destinationCell.getColumn();


        // looking for king piece
        for (Movement movement : selectedPossibleMovements) {

            // King with opposite color in possible movement for piece which was moved
            ChessPiece pieceOnBoard = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].getPiece();

            if (pieceOnBoard == null)
                continue;

            if (pieceOnBoard.getColor() != this.selectedPiece.getColor() && pieceOnBoard.toString().equals("K")) {
                kingPiece = pieceOnBoard;

                currentKingRow = movement.getRow();
                currentKingColumn = movement.getColumn();

                break;
            }
        }

        if (kingPiece != null) {

            // Check if King could beat the chess piece which mate him / move to other cell and save your life
            if (kingPiece.calculatePossibleMovements().size() != 0) {
                List<Movement> possibleKingMovements = kingPiece.calculatePossibleMovements();

                for (Movement movement : possibleKingMovements) {

                    // King with opposite color in possible movement for piece which was moved
                    ChessPiece pieceOnBoard = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].getPiece();

                    if (pieceOnBoard == null)
                        continue;

                    // TODO: Implement hashcode and equals methods for chess piece

                    // Currently selected piece which mate king can be beaten by this king
                    if (pieceOnBoard.equals(this.selectedPiece)) {

                        // temporary set king to selected piece position
                        this.gameBoard.gameBoard[currentSelectedRow][currentSelectedColumn].setPiece(kingPiece);

                        // King can't be beaten after beat chess piece which try to mate him
                        if (!this.isBeatenByEnemy(currentSelectedRow, currentSelectedColumn, kingPiece.getColor())) {
                            System.out.println("[DEBUG][MATE][Success] King could kill dangerous piece");

                            this.gameBoard.gameBoard[currentKingRow][currentKingColumn].setPiece(kingPiece);
                            this.gameBoard.gameBoard[currentSelectedRow][currentSelectedColumn].setPiece(this.selectedPiece);

                            return false;
                        } else { // Just move king piece to his previous position and reset selected piece
                            System.out.println("[DEBUG][MATE][Fail] King couldn't kill dangerous piece");

                            this.gameBoard.gameBoard[currentKingRow][currentKingColumn].setPiece(kingPiece);
                            this.gameBoard.gameBoard[currentSelectedRow][currentSelectedColumn].setPiece(this.selectedPiece);
                        }
                    }

                }

                // Try to move King piece from current position cell to other and check if it is safe position
                for (Movement movement : possibleKingMovements) {
                    ChessPiece pieceOnBoard = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].getPiece();

                    // temporary move king to this position and check if this position is safe
                    this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].setPiece(kingPiece);

                    if (!this.isBeatenByEnemy(movement.getRow(), movement.getColumn(), kingPiece.getColor())) {
                        System.out.println("[DEBUG][MATE][Success] King could move to other safe place from current one");

                        this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].setPiece(pieceOnBoard);
                        this.gameBoard.gameBoard[currentKingRow][currentKingColumn].setPiece(kingPiece);

                        return false;

                    } else {
                        System.out.println("[DEBUG][MATE][Fail] King couldn't move to safe place");

                        this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()].setPiece(pieceOnBoard);
                        this.gameBoard.gameBoard[currentKingRow][currentKingColumn].setPiece(kingPiece);
                    }
                }
            }
        }

        return true;
    }


    private void setFlagForTheShortNotation ( char identifier){
        this.identifier = identifier;
    }

    private String representBriefNotation () {

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < this.gameBoard.gameBoard[i].length; j++) {

                ChessPiece currentPiece = getBoardPiece(i, j);
                if ((this.selectedCell.getRow() == i && (selectedCell.getColumn() != j))) {
                    if (currentPiece != null && selectedPiece.toString().equals(currentPiece.toString())) {
                        setFlagForTheShortNotation('s');
                    }
                } else if ((this.selectedCell.getColumn() == j && (this.selectedCell.getRow() != i))) {
                    if (currentPiece != null && selectedPiece.toString().equals(currentPiece.toString())) {
                        setFlagForTheShortNotation('n');
                    }
                }
            }
        }

        String mate = "";
        String turnNotation = "";
        String abbreviation = this.selectedPiece.toString();

        if (abbreviation.equals("p"))
            abbreviation = "";

        if (this.isCheck()) {
            mate = "+";
        }

        if (identifier == 's') {
            turnNotation = "[short] " + Integer.valueOf(this.turnNumber).toString() + ". " + abbreviation +
                    this.gameBoard.gameBoard[selectedCell.getRow()][selectedCell.getColumn()].returnLetter() +
                    this.gameBoard.gameBoard[destinationCell.getRow()][destinationCell.getColumn()].toString() +
                    mate;
        } else {
            turnNotation = "[short] " + Integer.valueOf(this.turnNumber).toString() + ". " + abbreviation +
                    this.gameBoard.gameBoard[selectedCell.getRow()][selectedCell.getColumn()].returnNumber() +
                    this.gameBoard.gameBoard[destinationCell.getRow()][destinationCell.getColumn()].toString() +
                    mate;
        }

        return turnNotation;
    }


    private boolean isBeatenByEnemy ( final int row, final int column, final Color pieceColor){
        /**
         * Check if piece will be beaten by other piece with opposite color after move to new cell with coordinates
         * row and cell.
         *
         * @param row new row position of piece
         * @param column new column position of piece
         * @param pieceColor color of piece which will be moved to new position
         */

        for (int currRow = 0; currRow < 8; currRow++) {
            for (int currColumn = 0; currColumn < 8; currColumn++) {

                ChessPiece boardPiece = this.gameBoard.gameBoard[currRow][currColumn].getPiece();

                if (boardPiece == null)
                    continue;

                if (boardPiece.getColor() == pieceColor)
                    continue;

                final List<Movement> boardPieceMovements = boardPiece.calculatePossibleMovements();

                for (Movement movement : boardPieceMovements) {

                    if (movement.getRow() == row && movement.getColumn() == column) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void applyTurn(final Turn turn, final String turnNotation, final boolean redo){
        ChessPiece movedPiece = this.gameBoard.gameBoard[turn.getSourceRow()][turn.getSourceColumn()].getPiece();
        this.gameBoard.gameBoard[turn.getDestinationRow()][turn.getDestinationColumn()].setPiece(movedPiece);
        this.gameBoard.gameBoard[turn.getSourceRow()][turn.getSourceColumn()].setPiece(null);

        this.selectedTurnNumber += 1;
        this.changeTurn();

        if (!redo)
            this.singleTurnNotations.add(turnNotation);
    }


    public List<String> saveGame(){
        /**
         * Get all turn notations and transform them to correct file notation format (<turn_num>. <white_turn> <black_turn>).
         *
         * If some turns were cancelled (was used undo operation and redo wasn't used after it) this turns won't be
         * loaded.
         *
         * @return List of string which contains all turn notations.
         */
        List<String> notations = new ArrayList<>();

        this.singleTurnNotations.removeAll(this.cancelledNotations);

        if (this.singleTurnNotations.size() == 0){
            return notations;
        }

        int max_counter = this.singleTurnNotations.size() % 2 == 0 ? this.singleTurnNotations.size() - 1 : this.singleTurnNotations.size() - 2;

        int start_index = 0;
        int end_index = 1;
        int turn = 1;

        while(start_index < max_counter){
            notations.add(turn + ". " + this.singleTurnNotations.get(start_index) + " " + this.singleTurnNotations.get(end_index) + "\n");

            start_index = end_index + 1;
            end_index += 2;
            turn += 1;
        }

        if (max_counter == singleTurnNotations.size() - 2){
            notations.add(turn + ". " + this.singleTurnNotations.get(this.singleTurnNotations.size() - 1));
        }

        return notations;
    }

    public Turn redo(){
        /**
         *
         */
        if (this.selectedTurnNumber <= this.singleTurnNotations.size() - 1){
            NotationTest notationParser = new NotationTest();
            String followingTurnNotation = this.singleTurnNotations.get(this.selectedTurnNumber);
            Turn followingTurn = notationParser.parseSingleNotation(followingTurnNotation);
            followingTurn.setColor(this.gameBoard.gameBoard[followingTurn.getSourceRow()][followingTurn.getSourceColumn()].getPiece().getColor());

            this.applyTurn(followingTurn, followingTurnNotation, true);

            this.cancelledNotations.remove(followingTurnNotation);

            return followingTurn;
        }

        else
            return null;
    }

    public Turn undo(){
        /**
         * Undo one player turn.
         *
         * @see singleTurnNotations
         * @see cancelledNotations
         * @see selectedTurnNumber
         * @see Turn
         *
         * @return Turn object which represents a previous turn if number of turns is greater than 0. null otherwise
         */

        if (this.selectedTurnNumber > 0){
            NotationTest notationsParser = new NotationTest();

            String previousTurnNotation = this.singleTurnNotations.get(this.selectedTurnNumber - 1);
            Turn previousTurn = notationsParser.parseSingleNotation(previousTurnNotation);

            // Get moved piece
            ChessPiece piece = this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].getPiece();

            this.gameBoard.gameBoard[previousTurn.getSourceRow()][previousTurn.getSourceColumn()].setPiece(piece);

            if (previousTurn.getBeaten().isEmpty())
                this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].setPiece(null);

            else{

                ChessPiece beatenPiece;
                Color beatenColor = Color.getOppositeColor(piece.getColor());

                String beatenPieceAbbr = previousTurn.getBeaten();

                switch (beatenPieceAbbr) {
                    case "P":
                        beatenPiece = new Pawn(beatenColor);
                        break;
                    case "J":
                        beatenPiece = new Knight(beatenColor);
                        break;
                    case "S":
                        beatenPiece = new Bishop(beatenColor);
                        break;
                    case "V":
                        beatenPiece = new Rook(beatenColor);
                        break;
                    case "D":
                        beatenPiece = new Queen(beatenColor);
                        break;
                    default:
                        beatenPiece = new King(beatenColor);
                        break;
                }

                this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].setPiece(beatenPiece);
            }

            this.currentTurn = piece.getColor();
            previousTurn.setColor(piece.getColor());

            this.selectedTurnNumber -= 1;
            this.cancelledNotations.add(this.singleTurnNotations.get(this.selectedTurnNumber));

            return previousTurn;
        }

        else
            return null;
    }
}
