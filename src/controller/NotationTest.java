package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotationTest {
    private List<String> lines;

    public NotationTest(){
        lines = new ArrayList<>();
    }

    public String getLine(final int index){
        return this.lines.get(index);
    }

    public List<Turn> fileReader(String path){
        List<Turn> turns = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(path));
            Pattern pattern = Pattern.compile("((J|K|D|V|S){0,1}([a-h])([1-8])([a-h])([1-8]))");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    // Group 1  - full match
                    // Group 2 - name of the piece
                    // Group 3 - start column coordinate
                    // Group 4 - start row coordinate
                    // Group 5 - destination column coordinate
                    // Group 6 - destination row coordinate
                    String piece_name = matcher.group(2);

                    if (piece_name == null){
                        piece_name = "P";
                    }

                    int start_column = transformCoordinate(matcher.group(3));
                    int start_row = Integer.valueOf(matcher.group(4)) - 1;

                    int dst_column = transformCoordinate(matcher.group(5));
                    int dst_row = Integer.valueOf(matcher.group(6)) - 1;

                    Turn turn = new Turn(start_row, start_column, dst_row, dst_column, piece_name);
                    turns.add(turn);
                    this.lines.add(line);
                }

            }

        }catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return turns;
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
