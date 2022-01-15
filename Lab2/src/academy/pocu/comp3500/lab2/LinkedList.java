package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() { }

    public static Node append(final Node rootOrNull, final int data) {
        // if root node is null
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node root = rootOrNull;
        Node curr = rootOrNull;

        while(curr.getNextOrNull() != null) {
            curr = curr.getNextOrNull();
        }

        curr.setNext(new Node(data));
        return root;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node prevRoot = rootOrNull;
        Node newRoot = new Node(data);

        newRoot.setNext(prevRoot);

        return newRoot;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        // early return
        if (index < 0)  // invalid index
            return rootOrNull;
        if (index == 0) {
            if (rootOrNull == null)
                return new Node(data);
            else
                return prepend(rootOrNull, data);
        }

        Node root = rootOrNull;
        Node curr = rootOrNull;

        for (int i = 0; i < index - 1; i++) {   // go to prev node of index node
            if (curr.getNextOrNull() == null)
                return root;
            curr = curr.getNextOrNull();
        }

        Node newNode = new Node(data);
        newNode.setNext(curr.getNextOrNull());
        curr.setNext(newNode);

        return root;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        // early return
        if (index < 0)  // invalid index
            return rootOrNull;
        if (rootOrNull == null)
            return rootOrNull;
        if (index == 0) {
            return rootOrNull.getNextOrNull();
        }

        Node root = rootOrNull;
        Node curr = rootOrNull;

        for (int i = 0; i < index - 1; i++) {   // go to prev node of index node
            if (curr.getNextOrNull().getNextOrNull() == null)
                return root;
            curr = curr.getNextOrNull();
        }

        curr.setNext(curr.getNextOrNull().getNextOrNull());

        return root;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        if (rootOrNull == null)
            return -1;

        Node curr = rootOrNull;
        int index = 0;

        while (curr.getNextOrNull() != null) {
            if (curr.getData() == data)
                return index;
            curr = curr.getNextOrNull();
            index++;
        }

        return index;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        // early return
        if (index < 0)  // invalid index
            return null;
        if (rootOrNull == null)
            return null;
//        if (index == 0) {
//            return ;
//        }

        Node curr = rootOrNull;

        for (int i = 0; i < index; i++) {
            if (curr == null)
                return null;
            curr = curr.getNextOrNull();
        }

        return curr;
    }

    public static Node reverse(final Node rootOrNull) {
        if (rootOrNull == null)
            return null;

        Node curr = rootOrNull;
        Node next;
        Node newRoot = null;
        Node prevRoot;
        while (curr != null) {
            prevRoot = newRoot;
            next = curr.getNextOrNull();
            newRoot = curr;
            newRoot.setNext(prevRoot);

            curr = next;
        }


        return newRoot;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null && root1OrNull == null)
            return null;
        if (root0OrNull == null)
            return root1OrNull;
        if (root1OrNull == null)
            return root0OrNull;

        Node curr1 = root0OrNull;
        Node next1 = root0OrNull.getNextOrNull();
        Node curr2 = root1OrNull;
        Node next2 = root0OrNull.getNextOrNull();
        Node newRoot = curr1;
        curr1 = next1;
        Node currNew = newRoot;
        while (curr1 != null && curr2 != null) {
            currNew.setNext(curr2);
            next2 = curr2.getNextOrNull();
            currNew = currNew.getNextOrNull();
            currNew.setNext(curr1);
            next1 = curr1.getNextOrNull();
            currNew = currNew.getNextOrNull();

            curr1 = next1;
            curr2 = next2;
        }

        // add remaining list
        while (curr1 != null) {
            currNew.setNext(curr1);
            currNew = currNew.getNextOrNull();

            curr1 = curr1.getNextOrNull();
        }
        while (curr2 != null) {
            currNew.setNext(curr2);
            currNew = currNew.getNextOrNull();

            curr2 = curr2.getNextOrNull();
        }

        return newRoot;
    }
}
