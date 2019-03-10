package controller;

import java.util.List;

public class testovani {

    public static void main(String args[]) {
        NotationTest reader = new NotationTest();
        List<Turn> turns = reader.fileReader("/Users/kirill/IdeaProjects/IJA/src/controller/notation.txt");
    }
}
