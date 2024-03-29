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

/**
 * Project: Chess game IJA project
 * File: Game.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Controller class which used for cooperate graphical interface and game logic
 */

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
    private int period;

    private List<String> singleTurnNotations;
    private List<String> cancelledNotations;

    private int lastLoadedTurn;

    private boolean deleteGUINotations;
    private boolean transformPawn;

    /**
     * Main game object constructor.
     *
     * @param initFlag define if pieces will be added to the board. If false any pieces won't be created
     */
    public Game(boolean initFlag) {
        this.gameBoard = new Board(initFlag);

        this.selectedPiece = null;
        this.selectedCell = null;
        this.destinationCell = null;

        this.singleTurnNotations = new ArrayList<>();
        this.cancelledNotations = new ArrayList<>();

        this.period = 5000;
        this.turnNumber = 1;
        this.currentTurn = Color.WHITE;

        this.selectedTurnNumber = 0;

        this.deleteGUINotations = false;
        this.transformPawn = false;

        this.lastLoadedTurn = -1;

    }

    public void setLastLoadedTurn(){
        this.lastLoadedTurn = this.selectedTurnNumber;
    }

    public List<String> getTurnNotations(){
        return this.singleTurnNotations;
    }

    public void setDeleteGUINotations(final boolean value){
        this.deleteGUINotations = value;
    }


    /**
     * Define if notation entries on gui part should be deleted
     *
     * @return true if notations should be deleted, false otherwise
     */
    public boolean isDeleteGUINotations(){
        return this.deleteGUINotations;
    }


    /**
     * Getter method for transformPawn flag which signalize if pawn should be changed to the other chess piece.
     *
     * @return value of transformPawn field. True, if pawn was moved to the last row on the gme board, false oterwise
     */
    public boolean isTransformPawn(){
        return this.transformPawn;
    }


    /**
     * Set selectedCell and selectedPiece variables to particular values (Set selection of src cell).
     *
     * @param selectedCell is a cell from gui which was selected
     * @param selectedPiece chess piece which is staying on selected cell
     */
    public void setSelected(gui.Cell selectedCell, ChessPiece selectedPiece) {
        this.selectedCell = selectedCell;
        this.selectedPiece = selectedPiece;
    }


    /**
     *
     * @param newPeriodValue
     */
    public void setPeriod(final int newPeriodValue){
        this.period = newPeriodValue;
    }


    public int getPeriod(){
        return this.period;
    }

    /**
     *
     * @return
     */
    public int getSelectedTurnNumber(){
        return this.selectedTurnNumber;
    }


    /**
     * Set selectedCell and selectedPiece variables to null (Drop selection). Is used after turn is done or turn
     * isn't even possible.
     */
    public void dropSelected() {
        this.selectedCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.selectedCell.repaint();

        this.selectedPiece = null;
        this.selectedCell = null;
    }



    /**
     * Set destinationCell variable to (gui) Cell object from GUI (Set selection of dst cell).
     */
    public void setDestinationCell(gui.Cell dstCell) {
        this.destinationCell = dstCell;
    }



    /**
     * Set destinationCell variable tu null (Drop selection). Is used after turn is done or turn
     * isn't even possible.
     */
    public void dropDestinationCell() {
        this.destinationCell = null;
    }



    /**
     * Change a turn and set color for pieces which could be selected during current turn.
     */
    private void changeTurn() {
        this.currentTurn = Color.getOppositeColor(this.currentTurn);
    }



    /**
     * Calculate all possible movements for selected cell. Calculation method depends on object (polimorphic method).
     *
     * @return list of all possible movements. One movement is represented by Movement object
     */
    public List<Movement> getPossibleMovements() {
        List<Movement> possibleMovements;

        possibleMovements = this.selectedPiece.calculatePossibleMovements();
        this.applyRules(possibleMovements, this.selectedPiece.getColor());

        return possibleMovements;
    }



    /**
     * Getter for currentTurn field.
     *
     * @return Color object which represents which pieces are movable in the current turn
     */
    public Color getCurrentTurn(){
        return this.currentTurn;
    }



    /**
     * Check if any cell is selected.
     *
     * @return true if cell is selected, false otherwise
     */
    public boolean isCellSelected() {
        return this.selectedPiece != null && this.selectedCell != null;
    }



    /**
     * Check if any destination cell is selected.
     *
     * @return true if cell is selected, false otherwise
     */
    public boolean destinationSelected() {
        return this.destinationCell != null;
    }

    /**
     * Return chess piece that is staying on cell with coordinates x and y.
     *
     * @param row - row coordinate; array index 0 - 7
     * @param column - column coordinate; array index 0 - 7
     *
     * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
     */
    public ChessPiece getBoardPiece(final int row, final int column) {
        return this.gameBoard.gameBoard[row][column].getPiece();
    }



    /**
     * set chess piece object to particular position
     *
     * @param piece which will be set to the board
     * @param row where piece will be set
     * @param column where piece will be set
     */
    public void setPiece(ChessPiece piece, int row, int column) {
        this.gameBoard.gameBoard[row][column].setPiece(piece);
    }



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
     * @param pieceColor - color of chess piece
     *
     * @return true if chess piece movement is possible, false otherwise
     */
    private boolean isPossibleMovement(Movement movement, final Color pieceColor) {

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



    /**
     * Check if after movement selected piece enemy peace was beaten.
     *
     * @param movement possible movement of selected chess piece
     * @param pieceColor color of selected chess piece
     *
     * @return true is enemy piece was beaten, else otherwise
     */
    private boolean beatEnemy(final Movement movement, final Color pieceColor) {
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



    /**
     * !!!---------------------WARNING----------------------------------------!!!
     *            This method is changing allPossibleMovements list parameter
     * !!!--------------------------------------------------------------------!!!
     * Recalculate possible piece moves on chess board;
     *
     * @param allPossibleMovements all possible movements list of selected chess piece. This list will be changed
     *                             after applying rules
     *
     * @param pieceColor selected board piece. Moves are calculated depends on selected piece color
     *
     * return void but input list will be changed in result
     */
    private void applyRules(List<Movement> allPossibleMovements, final Color pieceColor) {
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



    /**
     * Calculate possible movements for currently selected chess piece and check if destination cell
     * is possible movement for selected piece.
     *
     * @param dstRow destination row coordinate
     * @param dstColumn destination column coordinate
     *
     * @return true in case if destination cell is a possible movement and piece could be moved; False otherwise
     */
    public boolean isPossibleDestination(int dstRow, int dstColumn) {
        for (Movement movement : this.getPossibleMovements()) {
            if (movement.getRow() == dstRow && movement.getColumn() == dstColumn)
                return true;
        }

        return false;
    }



    /**
     * Move selected piece from source selected cell to the destination selected cell.
     *
     * @return turn notation in string form
     */
    public String movePiece() {

        if (this.selectedPiece.getStartedPosition()){
            this.selectedPiece.changeStartedPosition();
        }

        String turnNotation = this.getFullNotation();

        if((this.selectedPiece instanceof Pawn) && (this.destinationCell.getRow() == 7 || this.destinationCell.getRow() == 0)){
            System.out.println("[DEBUG][movePiece] Pawn movement to the last row on the game board");
            this.transformPawn = true;
        }

        if (!this.transformPawn){
            this.destinationCell.setAbbreviation(this.selectedPiece.getColor().toString() + this.selectedPiece.toString());
            this.setPiece(this.selectedPiece, this.destinationCell.getRow(), this.destinationCell.getColumn());

            this.selectedCell.setAbbreviation("");

            this.setPiece(null, this.selectedCell.getRow(), this.selectedCell.getColumn());

            this.dropSelected();
            this.dropDestinationCell();
            this.changeTurn();

            // TODO: if turn with transform pawn is done, this part is going to work incorrect
            this.updateTurnNotations(turnNotation);

            this.selectedTurnNumber += 1;
        }

        return turnNotation;
    }


    /**
     * Get chess piece object using the abbreviation
     * @param newPieceAbbreviation abbreviation of the chess piece which will be used for creating a new chess object
     */
    public String transformPawn(final String newPieceAbbreviation){

        ChessPiece newPiece;
        Color pieceColor = this.currentTurn;

        switch (newPieceAbbreviation) {
            case "P":
                newPiece = new Pawn(pieceColor);
                break;
            case "J":
                newPiece = new Knight(pieceColor);
                break;
            case "S":
                newPiece = new Bishop(pieceColor);
                break;
            case "V":
                newPiece = new Rook(pieceColor);
                break;
            case "D":
                newPiece = new Queen(pieceColor);
                break;
            default:
                newPiece = new King(pieceColor);
                break;
        }

        String turnNotation = this.getFullNotation() + newPieceAbbreviation;

        // update src cell
        this.setPiece(null, this.selectedCell.getRow(), this.selectedCell.getColumn());
        this.selectedCell.setAbbreviation("");

        // update destination cell
        this.setPiece(newPiece, this.destinationCell.getRow(),this.destinationCell.getColumn());
        this.destinationCell.setAbbreviation(this.selectedPiece.getColor().toString() + newPieceAbbreviation);

        this.updateTurnNotations(turnNotation);

        // drop selected
        this.dropSelected();
        this.dropDestinationCell();
        this.changeTurn();

        this.transformPawn = false;
        this.selectedTurnNumber += 1;

        return turnNotation;

    }

    /**
     * Update turn notations in the right panel. In case that new turn was done, add notation to list. Update existing
     * selected notation and remove others otherwise.
     * @param turnNotation string representation of the turn notation
     */
    private void updateTurnNotations(final String turnNotation){

        if (this.selectedTurnNumber >= this.singleTurnNotations.size())
            this.singleTurnNotations.add(this.selectedTurnNumber, turnNotation);
        else{

            this.singleTurnNotations.set(this.selectedTurnNumber, turnNotation);

            if(this.selectedTurnNumber < this.singleTurnNotations.size() - 1){
                this.singleTurnNotations.subList(this.selectedTurnNumber + 1, this.singleTurnNotations.size()).clear();
            }

            this.deleteGUINotations = true;
            this.cancelledNotations.clear();

            // add possibility use redo and undo operations
            this.lastLoadedTurn = this.selectedTurnNumber;
        }
    }

    /**
     * Create full turn notation.
     *
     * @return string which represent full turn notation
     */
    private String getFullNotation() {
        String check = "";
        String dstPart;
        String srcPart;

        if (this.isCheck()) {

            check = "+";

            if(this.isMate())
                check = "#";
        }

        // Set first part of notation
        srcPart = this.notationAbbreviate(this.selectedPiece.toString()) +
                this.gameBoard.gameBoard[selectedCell.getRow()][selectedCell.getColumn()].toString();

        if (this.beatEnemy()){

            dstPart = "x" +
                    this.notationAbbreviate(this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].getPiece().toString()) +
                    this.gameBoard.gameBoard[this.destinationCell.getRow()][this.destinationCell.getColumn()].toString();

        }else
            dstPart = this.gameBoard.gameBoard[destinationCell.getRow()][destinationCell.getColumn()].toString();

        return  srcPart + dstPart + check;
    }


    /**
     * Return particular abbreviate for chess piece.
     *
     * @param abbreviate input abbreviate
     *
     * @return empty string if pawn, abbreviate for particular chess piece otherwise
     */
    private String notationAbbreviate(final String abbreviate){
        return abbreviate.equals("p") ? "" : abbreviate;
    }



    /**
     * Check if under moving chess piece king is under the check.
     *
     * @return true if king under the check after moving chess piece, false otherwise
     */
    private boolean isCheck() {
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

    /**
     * Check if piece will be beaten by other piece with opposite color after move to new cell with coordinates
     * row and cell.
     *
     * @param row new row position of piece
     * @param column new column position of piece
     * @param pieceColor color of piece which will be moved to new position
     */
    private boolean isBeatenByEnemy ( final int row, final int column, final Color pieceColor){
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


    /**
     * Apply turn on the game board after redo or undo operation.
     * @param turn object which represents turn
     * @param turnNotation string representation of turn
     * @param redo specify which operation was used
     */
    public void applyTurn(final Turn turn, final String turnNotation, final boolean redo){
        if (turn.isTransform()){

            ChessPiece transformedPiece;
            Color pieceColor = this.currentTurn;

            switch (turn.getTransformTo()) {

                case "J":
                    transformedPiece = new Knight(pieceColor);
                    break;
                case "S":
                    transformedPiece = new Bishop(pieceColor);
                    break;
                case "V":
                    transformedPiece = new Rook(pieceColor);
                    break;
                case "D":
                    transformedPiece = new Queen(pieceColor);
                    break;
                default:
                    transformedPiece = new King(pieceColor);
                    break;
            }

            this.gameBoard.gameBoard[turn.getDestinationRow()][turn.getDestinationColumn()].setPiece(transformedPiece);

        }else{
            ChessPiece movedPiece = this.gameBoard.gameBoard[turn.getSourceRow()][turn.getSourceColumn()].getPiece();
            this.gameBoard.gameBoard[turn.getDestinationRow()][turn.getDestinationColumn()].setPiece(movedPiece);
        }


        this.gameBoard.gameBoard[turn.getSourceRow()][turn.getSourceColumn()].setPiece(null);

        this.selectedTurnNumber += 1;
        this.changeTurn();

        if (!redo)
            this.singleTurnNotations.add(turnNotation);
    }



    /**
     * Get all turn notations and transform them to correct file notation format (<turn_num>. <white_turn> <black_turn>).
     *
     * If some turns were cancelled (was used undo operation and redo wasn't used after it) this turns won't be
     * loaded.
     *
     * @return List of string which contains all turn notations
     */
    public List<String> saveGame(){
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



    /**
     * Redo one player turn.
     *
     * @return Turn object which represents a following turn if number of turns is less than all turns number; null
     * otherwise
     */
    public Turn redo(boolean checkRequired){

        if (checkRequired)
            if (this.selectedTurnNumber + 1 <= this.lastLoadedTurn)
                return null;

        if (this.selectedTurnNumber <= this.singleTurnNotations.size() - 1){
            NotationParser notationParser = new NotationParser();
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



    /**
     * Undo one player turn.
     *
     * @return Turn object which represents a previous turn if number of turns is greater than 0; null otherwise
     */
    public Turn undo(boolean checkRequired){

        if (checkRequired)
            if (this.selectedTurnNumber - 1 < this.lastLoadedTurn)
                return null;

        if (this.selectedTurnNumber > 0){
            NotationParser notationsParser = new NotationParser();

            String previousTurnNotation = this.singleTurnNotations.get(this.selectedTurnNumber - 1);
            Turn previousTurn = notationsParser.parseSingleNotation(previousTurnNotation);

            Color movedPieceColor;

            // Pawn piece reached the last row on the game board
            if (previousTurn.isTransform()){
                System.out.println("[DEBUG][UNDO] Transform piece to pawn");

                Color pieceColor = this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].getPiece().getColor();
                this.gameBoard.gameBoard[previousTurn.getSourceRow()][previousTurn.getSourceColumn()].setPiece(new Pawn(pieceColor));

                movedPieceColor = pieceColor;

            }

            // Common chess piece movement
            else{
                ChessPiece piece = this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].getPiece();
                this.gameBoard.gameBoard[previousTurn.getSourceRow()][previousTurn.getSourceColumn()].setPiece(piece);

                movedPieceColor = piece.getColor();
            }

            // After turn is done, any piece was beaten
            if (previousTurn.getBeaten().isEmpty())
                this.gameBoard.gameBoard[previousTurn.getDestinationRow()][previousTurn.getDestinationColumn()].setPiece(null);

            // Some chess piece was beaten during the previous turn
            else{

                ChessPiece beatenPiece;
                Color beatenColor = Color.getOppositeColor(movedPieceColor);

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

            this.currentTurn = movedPieceColor;
            previousTurn.setColor(movedPieceColor);

            this.selectedTurnNumber -= 1;
            this.cancelledNotations.add(this.singleTurnNotations.get(this.selectedTurnNumber));

            return previousTurn;
        }

        else
            return null;
    }

    public boolean isRedo(final String turnNotation){
        int index = this.singleTurnNotations.indexOf(turnNotation) + 1;

        return index > selectedTurnNumber;
    }


    /**
     * Get game board state for particular notation.
     *
     * @param notation turn notation selected on the frontend
     */
    public List<Turn> getGameboardState(final String notation){

        int index = this.singleTurnNotations.indexOf(notation) + 1;

        List<Turn> turns = new ArrayList<>();

        if (index != this.selectedTurnNumber){
            System.out.println("[DEBUG][CONTROLLER][getGameboardState] Calculating state");


            boolean redo = this.isRedo(notation);
            int max_index = index - selectedTurnNumber;

            int counter = 0;

            if (max_index < 0) max_index = Math.abs(max_index);

            while(counter < max_index){
                turns.add(redo? this.redo(false) : this.undo(false));
                counter+=1;
            }
        }else
            System.out.println("[DEBUG][CONTROLLER][getGameboardState] Current turn is selected");

        return turns;
    }
}
