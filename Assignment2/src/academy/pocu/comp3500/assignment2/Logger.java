package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public final class Logger {
    public static LinkedList<ListElement> list = new LinkedList<>();
    static int currIndentLevel = 0;
    static int maxIndentLevel = 0;

    public static void log(final String text) {
        int indentLevel = currIndentLevel;
        list.addLast(new ListElement(text, indentLevel));
    }

    public static void printTo(final BufferedWriter writer) {
        Iterator<ListElement> iter = list.iterator();
        try {
            while (iter.hasNext()) {
                ListElement element = iter.next();
                // indent 출력하기
                for (int i = 0; i < element.indentLevel; i++) {
                    writer.write("  ");
                }
                // text 출력하기
                writer.write(element.text + System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
        Iterator<ListElement> iter = list.iterator();
        try {
            while (iter.hasNext()) {
                ListElement element = iter.next();
                if (element.text.contains(filter)) {
                    // indent 출력하기
                    for (int i = 0; i < element.indentLevel; i++) {
                        writer.write("  ");
                    }
                    // text 출력하기
                    writer.write(element.text + System.lineSeparator());
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        list.clear();
    }

    public static Indent indent() {
        currIndentLevel++;

        if (currIndentLevel > maxIndentLevel)
            maxIndentLevel = currIndentLevel;

        return new Indent(currIndentLevel);
    }

    public static void unindent() {
        if (currIndentLevel == 0)
            return;
        currIndentLevel--;
    }
}