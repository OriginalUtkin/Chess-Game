package controller;

import backend.Abstracts.ChessPiece;
import backend.Figures.Movement;
import gui.Tab;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notation{

     private int turnNumber;

     private String shortNotation;
     private String fullNotation;
     private Matcher m;
     private Tab loadedTab;

     public Notation(JTabbedPane tabPane, JFrame frame){
         loadedTab = new Tab(tabPane, frame, "Loaded Game");
     }

    private int transformCoordinate(String s){
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

    private Matcher matchPattern(String str, String pattern){
        Pattern urlPattern = Pattern.compile(pattern);
        m = urlPattern.matcher(str);

        return m;
    }


    private Matcher replaceStrWithoutNumeration(String movement, String strWithoutNumeration){
        movement =  strWithoutNumeration.replace(m.group(),"");
        m = this.matchPattern(movement, "^[a-h]{1}[1-7]{1}");
        return m;
    }

    private StringBuilder readFile(){
        BufferedReader file = null;
        StringBuilder fileData = new StringBuilder();

        try {
            file = new BufferedReader(new FileReader("src/controller/notation.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String notationRow = "";
        try{
            while((notationRow = file.readLine()) != null){
                fileData.append(notationRow );
                fileData.append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return fileData;
    }

    public void parseNotation(){
        String movement = "";
        String source = "";
        String destination = "";
        Integer column, destColumn = 0;
        String destRow = "";
        String row = "";

        ChessPiece gottenPiece;
        boolean flag = false;


        StringBuilder fileData = this.readFile();

        m = this.matchPattern(fileData.toString(), "^\\d{1,2}[\\.]{1}\\s*");

        if (m.find()){
            String strWithoutNumeration = fileData.toString().replace(m.group(), "");
            m = this.matchPattern(strWithoutNumeration,"^[\"K\",\"J\",\"D\", \"V\",\"J\",\"p\"]{1}" );

            if (m.find()){
                String abbeviation = m.group();
                movement =  strWithoutNumeration.replace(m.group(),"");
                m = this.matchPattern(movement, "^[a-h]{1}[1-8]{1}");
                if (m.find()){
                    source = m.group();
                    m = this.matchPattern(source, "^[a-h]{1}");
                    if (m.find()){
                        column = this.transformCoordinate(m.group());
                        m = this.matchPattern(source, "[1-8]{1}");
                        if (m.find()){ row = m.group();
                             gottenPiece = loadedTab.getBoardPiece(Integer.parseInt(row)-1, column);
                            if (gottenPiece.toString().equals(abbeviation)){
                                destination = movement.replace(source,"");
                                System.out.println(destination);

                                m = this.matchPattern(destination, "^[a-h]{1}");
                                if (m.find()){
                                    destColumn = this.transformCoordinate(m.group());
                                    m = this.matchPattern(destination, "[1-8]{1}");
                                    if (m.find()){
                                        destRow = m.group();
                                        System.out.println("Row " + destRow + "Column " + destColumn);
                                    }
                                }
                                List<Movement> possibleMovements =  gottenPiece.calculatePossibleMovements();
                                for (int i = 0; i< possibleMovements.size(); i++){
                                    if ( (possibleMovements.get(i).getRow() == Integer.parseInt(destRow)-1)
                                            && (possibleMovements.get(i).getColumn() == destColumn)
                                    ){
                                        System.out.println("True");
                                        loadedTab.setPieceToTheGame(gottenPiece, Integer.parseInt(row)-1,
                                                column,Integer.parseInt(destRow)-1,destColumn);
                                        flag = true;
                                    }
                                }
                                if (!flag){
                                    System.out.println("Impossible movement for this figure");
                                    System.exit(1);
                                }
                            }
                        }
                    }
                }
            }else{
                System.out.println("Notation format error");
            }
        }
    }



}
