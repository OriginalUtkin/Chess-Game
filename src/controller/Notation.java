package controller;

import backend.Enums.Color;

import backend.Figures.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notation{

     private int turnNumber;

     private String shortNotation;
     private String fullNotation;
     private Matcher m;

     private List<Turn> turns;

     public Notation(){
          this.turns = new ArrayList<>();
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

     public String convertCoordinateBack(int n){
          String result = "";
          switch (n){
               case 0: result="a";break; case 1: result="b"; break; case 2:result="c"; break;
               case 3: result="d";break; case 4:result="e"; break; case 5:result="f"; break;
               case 6: result = "g";break; case 7: result="h";break;
               default:
                    break;
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

     private StringBuilder readFile(String filename){
          BufferedReader file = null;
          StringBuilder fileData = new StringBuilder();

          try {
               file = new BufferedReader(new FileReader(filename));
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

     public void parseFile(String filename){
          StringBuilder fileData = this.readFile(filename);
          this.parseNotation(fileData.toString());
     }

     private void parseNotation(String str){
          m = this.matchPattern(str, "^\\d{1,2}[\\.]{1}\\s*");
          if (m.find()){
               String strWithoutNumeration = str.replace(m.group(), "");
               this.moveFigure(strWithoutNumeration);
          }else if(str.equals("")){
               System.out.println("[END]");
          }
          else{
               System.out.println(str);
               System.exit(1);
          }
     }


     private void moveFigure(String strWithoutNumeration){
          String movement, source, destination;
          String srcRow, srcColumn, destColumn, destRow;

          m = this.matchPattern(strWithoutNumeration,"^[\"K\",\"D\",\"V\",\"S\",\"J\",\"p\"]{1}" );

          if (m.find()) {
               String abbreviation = m.group();
               movement = strWithoutNumeration.substring(1);
               m = this.matchPattern(movement, "^[a-h]{1}[1-8]{1}");
               if (m.find()) {
                    source = m.group();
                    m = this.matchPattern(source, "^[a-h]{1}");
                    if (m.find()) {
//                         column = this.transformCoordinate(m.group());
                         srcColumn = m.group();
                         m = this.matchPattern(source, "[1-8]{1}");
                         if (m.find()) {
                              srcRow = m.group();
                              destination = movement.replace(source, "");
                              m = this.matchPattern(destination, "^[a-h]{1}");
                              if (m.find()) {
//                                   destColumn = this.transformCoordinate(m.group());
                                   destColumn = m.group();
                                   movement = destination.substring(1);
                                   m = this.matchPattern(destination, "[1-8]{1}");
                                   if (m.find()) {
                                        destRow = m.group();

                                        // Add notation to the list of turns

                                        Turn chessPiece = new Turn(Integer.valueOf(srcRow)-1, this.transformCoordinate(srcColumn),
                                                Integer.valueOf(destRow)-1, this.transformCoordinate(destColumn), abbreviation, null);

                                        this.turns.add(chessPiece);

                                        movement = movement.substring(1);
                                   }
                              }

                              m = this.matchPattern(movement, "^\\s+");
                              if (!m.find()){
                                   // No spaces
                                   System.out.println("Incorrect notation");
                                   System.exit(1);
                              }else{
                                   movement = movement.substring(1);
                                   m = this.matchPattern(movement,"^[\"K\",\"D\",\"V\",\"S\",\"J\",\"p\"]{1}" );
                                   if (m.find()){
                                        this.moveFigure(movement);
                                   }else{
                                        /*The next movement*/
                                        this.parseNotation(movement);
                                   }
                              }
                         }
                    }
               }
          }else{
               System.out.println("Incorrect notation, expecting K,D,V,S,J,p");
               System.exit(1);
          }
     }


     public List<Turn> returnTurnList(){
          return this.turns;
     }
}