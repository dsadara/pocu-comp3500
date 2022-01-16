package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node queueList;
    private int queueSize;

    public Queue() {
        queueSize = 0;
    }

    public void enqueue(final int data) {
        if (queueList == null) {
            queueList = new Node(data);
            queueSize++;
            return;
        }
        queueList = LinkedList.append(queueList, data);
        queueSize++;
    }

    public int peek() {
        return queueList.getData();
    }

    public int dequeue() {
        int removedData = queueList.getData();
        queueList = LinkedList.removeAt(queueList, 0);
        queueSize--;
        return removedData;
    }

    public int getSize() {
        return queueSize;
    }
}