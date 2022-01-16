package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    private Node stackList;
    private int stackSize;

    public Stack() {
        stackSize = 0;
    }

    public void push(final int data) {
        if (stackList == null) {
            stackList = new Node(data);
            stackSize++;
            return;
        }
        stackList = LinkedList.prepend(stackList, data);
        stackSize++;
    }

    public int peek() {
        return stackList.getData();
    }

    public int pop() {
        int removedData = stackList.getData();
        stackList = LinkedList.removeAt(stackList, 0);
        stackSize--;
        return removedData;
    }


    public int getSize() {
        return stackSize;
    }
}