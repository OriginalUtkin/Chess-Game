package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotationParser {
    private List<String> loadedNotations;

    public NotationParser(){
        loadedNotations = new ArrayList<>();
    }

    public String getLine(final int index){
        return this.loadedNotations.get(index);
    }

    public List<Turn> fileReader(String path){
        List<Turn> turns = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                turns.addAll(this.parseNotation(line));

            }

        }catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return turns;
    }
//((J|K|D|V|S|)([a-h])([1-8])([a-h])([1-8])\w+)
    private List<Turn> parseNotation(final String notationString){
//        transform check regex
        Pattern pattern = Pattern.compile("((J|K|D|V|S|)([a-h])([1-8])x(J|K|D|V|S|)([a-h])([1-8]))|((J|K|D|V|S|)([a-h])([1-8])([a-h])([1-8]))");
        Matcher matcher = pattern.matcher(notationString);

        List<Turn> turns = new ArrayList<>();

        while (matcher.find()) {
            // If Group 1 is empty -> common movement; piece was beaten otherwise
            if (matcher.group(1) != null){
                // Group 1 - full match
                // Group 2  - Chess piece
                // Group 3 - start column coordinate
                // Group 4 - start row coordinate
                // Group 5 - beaten chess piece
                // Group 6 - destination column coordinate
                // Group 7 - destination row coordinate

                String piece_name = matcher.group(2);

                if (piece_name.isEmpty()){
                    piece_name = "P";
                }

                int start_column = transformCoordinate(matcher.group(3));
                int start_row = Integer.valueOf(matcher.group(4)) - 1;

                String beaten_piece_name = matcher.group(5);

                if (beaten_piece_name.isEmpty()){
                    beaten_piece_name = "P";
                }

                int dst_column = transformCoordinate(matcher.group(6));
                int dst_row = Integer.valueOf(matcher.group(7)) - 1;

                turns.add(new Turn(start_row, start_column, dst_row, dst_column, piece_name, beaten_piece_name));
                this.loadedNotations.add(matcher.group(1));

            }else{
                // Group 8  - full match
                // Group 9 - name of the piece
                // Group 10 - start column coordinate
                // Group 11 - start row coordinate
                // Group 12 - destination column coordinate
                // Group 13 - destination row coordinate

                String piece_name = matcher.group(9);

                if (piece_name.isEmpty()){
                    piece_name = "P";
                }

                int start_column = transformCoordinate(matcher.group(10));
                int start_row = Integer.valueOf(matcher.group(11)) - 1;

                int dst_column = transformCoordinate(matcher.group(12));
                int dst_row = Integer.valueOf(matcher.group(13)) - 1;

                turns.add(new Turn(start_row, start_column, dst_row, dst_column, piece_name, ""));
                this.loadedNotations.add(matcher.group(8));
            }
        }

        return turns;
    }


    Turn parseSingleNotation(final String notationString){
        // This method should prolly be refactored with previous

        Turn turn = null;

        // Try to use regex for pawn transformation. If something found -> create turn and return it to the game
        Pattern transformPattern = Pattern.compile("(([a-h])([1-8])([a-h])([1-8])(J|D|V|S))|((([a-h])([1-8]))x(J|K|D|V|S|)([a-h])([1-8])(J|D|V|S))");
        Matcher transformMatcher = transformPattern.matcher(notationString);

        //((([a-h])([1-8]))x(J|K|D|V|S|)([a-h])([1-8])(J|D|V|S)) transform and beat other piece during the turn

        if (transformMatcher.find()){

            if (transformMatcher.group(1) != null){
                // Group 1 - full match
                // Group 2 - start column
                // Group 3 - start row
                // Group 4 - destination column
                // Group 5 - destination row
                // Group 6 - new chess abbreviation
                System.out.println("[DEBUG][Notation parser] Transform pawn turn found");

                int start_column = transformCoordinate(transformMatcher.group(2));
                int start_row = Integer.valueOf(transformMatcher.group(3)) - 1;

                int dst_column = transformCoordinate(transformMatcher.group(4));
                int dst_row = Integer.valueOf(transformMatcher.group(5)) - 1;

                turn = new Turn(start_row, start_column, dst_row, dst_column, "P", "");

                turn.setTransform();
                turn.setTransformTo(transformMatcher.group(6));

                return turn;
            }else{
                // Group 7 - full match
                // Group 9 - start column
                // Group 10 - start row
                // Group 11 - beaten piece
                // Group 12 - destination column
                // Group 13 - destination row
                // Group 14 - new chess abbreviation
                System.out.println("[DEBUG][Notation parser] Transform pawn turn found and other piece was beaten");

                int start_column = transformCoordinate(transformMatcher.group(9));
                int start_row = Integer.valueOf(transformMatcher.group(10)) - 1;

                int dst_column = transformCoordinate(transformMatcher.group(12));
                int dst_row = Integer.valueOf(transformMatcher.group(13)) - 1;

                String beatenPiece = transformMatcher.group(11);

                if (beatenPiece.isEmpty())
                    beatenPiece = "P";


                turn = new Turn(start_row, start_column, dst_row, dst_column, "P", beatenPiece);
                turn.setTransform();
                turn.setTransformTo(transformMatcher.group(14));

                return turn;
            }
        }

        Pattern pattern = Pattern.compile("((J|K|D|V|S|)([a-h])([1-8])x(J|K|D|V|S|)([a-h])([1-8]))|((J|K|D|V|S|)([a-h])([1-8])([a-h])([1-8]))");
        Matcher matcher = pattern.matcher(notationString);

        while (matcher.find()) {
            // If Group 1 is empty -> common movement; piece was beaten otherwise
            if (matcher.group(1) != null){
                // Group 1 - full match
                // Group 2  - Chess piece
                // Group 3 - start column coordinate
                // Group 4 - start row coordinate
                // Group 5 - beaten chess piece
                // Group 6 - destination column coordinate
                // Group 7 - destination row coordinate

                String piece_name = matcher.group(2);

                if (piece_name.isEmpty()){
                    piece_name = "P";
                }

                int start_column = transformCoordinate(matcher.group(3));
                int start_row = Integer.valueOf(matcher.group(4)) - 1;

                String beaten_piece_name = matcher.group(5);

                if (beaten_piece_name.isEmpty()){
                    beaten_piece_name = "P";
                }

                int dst_column = transformCoordinate(matcher.group(6));
                int dst_row = Integer.valueOf(matcher.group(7)) - 1;

                turn = new Turn(start_row, start_column, dst_row, dst_column, piece_name, beaten_piece_name);

            }else{
                // Group 8  - full match
                // Group 9 - name of the piece
                // Group 10 - start column coordinate
                // Group 11 - start row coordinate
                // Group 12 - destination column coordinate
                // Group 13 - destination row coordinate

                String piece_name = matcher.group(9);

                if (piece_name.isEmpty()){
                    piece_name = "P";
                }

                int start_column = transformCoordinate(matcher.group(10));
                int start_row = Integer.valueOf(matcher.group(11)) - 1;

                int dst_column = transformCoordinate(matcher.group(12));
                int dst_row = Integer.valueOf(matcher.group(13)) - 1;

                turn = new Turn(start_row, start_column, dst_row, dst_column, piece_name, "");
            }

        }

        return turn;
    }


    private static int transformCoordinate(String s){
        int result;
        switch (s){
            case "a": result=0;break; case "b": result=1; break; case "c":result=2; break;
            case "d": result=3;break; case "e":result=4; break; case "f":result=5; break;
            case "g": result = 6;break; case "h": result=7;break;
            default:
                return -1;
        }
        return result;
    }

}
