package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.util.Iterator;

public final class Indent {
    LinkedList<String> textList;
    int indentLevel;

    public Indent(int indentLevel) {
        textList = new LinkedList<>();
        this.indentLevel = indentLevel;
    }

    public void add(String text) {
        for (int i = 0; i < indentLevel; i++) {
            text = "  " + text;
        }
        textList.add(text);
    }

    public void discard() {
        Iterator<Indent> indentIter = Logger.indentList.iterator();
        Indent currIndent;
        while (indentIter.hasNext()) {
            currIndent = indentIter.next();
            if (currIndent == this) {
                indentIter.remove();        // 해당 indent 삭제
                break;
            }
        }

        // 해당 indent의 자식 또는 같은 레벨의 indent 삭제
        while (indentIter.hasNext()) {
            Indent indent = indentIter.next();
            if (indent.indentLevel < this.indentLevel)
                break;
            indentIter.remove();
        }
    }
}