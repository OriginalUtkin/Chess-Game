package controller;

import backend.Figures.*;
import gui.Tab;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            case "a": result=1;break; case "b": result=2; break; case "c":result=3; break;
            case "d": result=4;break; case "e":result=5; break; case "f":result=6; break;
            case "h": result=7;break;
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

    public void parseNotation(){
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

        m = this.matchPattern(fileData.toString(), "^\\d{1,2}[\\.]{1}\\s*");

        if (m.find()){
            String strWithoutNumeration = fileData.toString().replace(m.group(), "");
            System.out.println(strWithoutNumeration);
            m = this.matchPattern(strWithoutNumeration,"^[\"K\",\"J\",\"D\", \"V\",\"J\",\"p\"]{1}" );

            if (m.find()){
                switch (m.group()){
                    case "K":
                        System.out.println("It's a King");
                        String movement =  strWithoutNumeration.replace(m.group(),"");
                        m = this.matchPattern(movement, "^[a-h]{1}[1-7]{1}");
                        if (m.find()){
                            String source = m.group();
                            m = this.matchPattern(source, "^[a-h]{1}");
                            if (m.find()){
                                Integer row = this.transformCoordinate(m.group());
                                m = this.matchPattern(source, "[1-7]{1}");
                                if (m.find()){ String column = m.group();
                                    loadedTab.setPieceToTheGame(new King(backend.Enums.Color.BLACK),row,Integer.parseInt(column));
                                }

                            }
                        }

                        break;
                }
            }else{
                System.out.println("Doesn't match, must have figure abbreviation");
            }

        }
    }



}
