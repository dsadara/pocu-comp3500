package academy.pocu.comp3500.assignment2;

import java.util.Iterator;

public final class Indent {
    final int indentLevel;

    public Indent(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public void discard() {
        Iterator<ListElement> iter = Logger.list.iterator();

        while (iter.hasNext()) {
            ListElement element = iter.next();
            if (element.indentLevel == this.indentLevel) {
                iter.remove();
            }
        }
    }
}