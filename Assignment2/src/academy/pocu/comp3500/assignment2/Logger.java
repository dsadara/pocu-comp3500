package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public final class Logger {
    public static LinkedList<Indent> indentList = new LinkedList<>();
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