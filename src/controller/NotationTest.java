package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotationTest {
    private List<String> notations;

    public NotationTest(){
        notations = new ArrayList<>();
    }

    public String getLine(final int index){
        return this.notations.get(index);
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

    public List<Turn> parseNotation(final String notationString){

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
                this.notations.add(matcher.group(1));

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
                this.notations.add(matcher.group(8));
            }

        }

        return turns;
    }


    public Turn parseSingleNotation(final String notationString){
        // This method should prolly be refactored with previous
        Pattern pattern = Pattern.compile("((J|K|D|V|S|)([a-h])([1-8])x(J|K|D|V|S|)([a-h])([1-8]))|((J|K|D|V|S|)([a-h])([1-8])([a-h])([1-8]))");
        Matcher matcher = pattern.matcher(notationString);

        Turn turn = null;
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
