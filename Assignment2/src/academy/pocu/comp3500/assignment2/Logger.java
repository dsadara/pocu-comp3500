package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public final class Logger {
    public static LinkedList<Indent> indentList = new LinkedList<>();
//    public static ArrayList<LinkedList<Indent>> parentList = new ArrayList<>();
    static int currIndentLevel = 0;

    public static void log(final String text) {
        if (indentList.getSize() == 0) {    // indentList가 비어있으면
            indentList.add(new Indent(currIndentLevel));
        }

        indentList.getLast().add(text);
    }

    public static void printTo(final BufferedWriter writer) {
        // indent iterator
        Iterator<Indent> indentIter = indentList.iterator();
        try {
            while (indentIter.hasNext()) {
                Indent indent = indentIter.next();
                // text iterator
                Iterator<String> textIter = indent.textList.iterator();
                while (textIter.hasNext()) {
                    writer.write(textIter.next() + System.lineSeparator());
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
//        Iterator<ListElement> iter = list.iterator();
//        try {
//            while (iter.hasNext()) {
//                ListElement element = iter.next();
//                if (element.text.contains(filter)) {
//                    // indent 출력하기
//                    for (int i = 0; i < element.indentLevel; i++) {
//                        writer.write("  ");
//                    }
//                    // text 출력하기
//                    writer.write(element.text + System.lineSeparator());
//                }
//            }
//            writer.flush();
//            currIndentLevel = 0;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // indent iterator
        Iterator<Indent> indentIter = indentList.iterator();
        try {
            while (indentIter.hasNext()) {
                Indent indent = indentIter.next();
                // text iterator
                Iterator<String> textIter = indent.textList.iterator();
                while (textIter.hasNext()) {
                    String text = textIter.next();
                    if (text.contains(filter))
                        writer.write(text + System.lineSeparator());
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        indentList = new LinkedList<>();
        currIndentLevel = 0;
    }

    public static Indent indent() {
        currIndentLevel++;
        Indent newIndent = new Indent(currIndentLevel);
        indentList.addLast(newIndent);

        return newIndent;
    }

    public static void unindent() {
        if (currIndentLevel == 0)
            return;
        currIndentLevel--;
        Indent newIndent = new Indent(currIndentLevel);
        indentList.addLast(newIndent);
    }
}