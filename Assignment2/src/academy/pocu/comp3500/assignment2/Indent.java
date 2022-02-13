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
//        Iterator<ListElement> iter = Logger.list.iterator();
//
//        while (iter.hasNext()) {
//            ListElement element = iter.next();
//            for (int i = indentLevel; i <= Logger.maxIndentLevel ; i++) {
//                if (element.indentLevel == i) {
//                    iter.remove();
//                }
//            }
//        }
//        if (indentLevel == 0)
//            Logger.maxIndentLevel = 0;
//        Logger.maxIndentLevel = indentLevel - 1;

        Iterator<Indent> indentIter = Logger.indentList.iterator();
        Indent currIndent;
        while (indentIter.hasNext()) {
            currIndent = indentIter.next();
            if (currIndent == this) {
                break;
            }
        }

        // 해당 indent 삭제
        indentIter.remove();

        // 삭제한 노드의 자식이 있으면 자식들도 삭제
        Indent nextIndent;
        if (indentIter.hasNext()) {     // 다음 노드 확인
            nextIndent = indentIter.next();
        }
        else {
            return;
        }

        if (nextIndent.indentLevel > this.indentLevel ) {   // 다음노드가 자식이라면
            indentIter.remove();
            while (indentIter.hasNext()) {
                Indent indent = indentIter.next();
                if (indent.indentLevel == this.indentLevel)
                    break;
                indentIter.remove();
            }
        }


    }
}