package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Node {
    private char value;
    private ArrayList<Node> childs = new ArrayList<>();

    public Node(char value) {
        this.value = value;
    }

    public void addChild(char value) {
        this.childs.add(new Node(value));
    }

    public int getNumOfChild() {
        return this.childs.size() - 1;
    }

    public Node getChild(int index) {
        return this.childs.get(index);
    }

    public char getValue() {
        return this.value;
    }

    public static void putWordInTrie(String word) {

    }

//    public static Node findLastLetterNode(String word) {
//        char letter = word.charAt(0);
//
//        for (int i = 0; i < word.length() - 1; i++) {
//        }
//    }
}
