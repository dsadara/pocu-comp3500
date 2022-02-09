package academy.pocu.comp3500.lab6;

public class Node {
    private int data;
    private Node left;
    private Node right;

    public Node() {}

    public Node(final int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }

    public void setData(final int data) {
        this.data = data;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public static Node insertRecursive(final Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left =  insertRecursive(node.left, data);
        } else {    // data >= node.data
            node.right =  insertRecursive(node.right, data);
        }

        return node;
    }

    public static boolean deleteRecursive(Node node, final int data, Node parent) {
        if (node == null)       // 삭제할 노드가 없으면 null 반환
            return false;

        Boolean isNodeFinded = true;

        if (data < node.data) {
            isNodeFinded = deleteRecursive(node.left, data, node);
        } else if (data == node.data){
            //  삭제할 노드의 자식이 0인경우
            if (node.left == null && node.right == null)  {
                if (parent.left == node)
                    parent.left = null;
                else
                    parent.right = null;
            } else if (node.left == null) {     // 삭제할 노드의 자식이 1인 경우
                if (parent.left == node)
                    parent.left = node.right;
                else
                    parent.right = node.right;
            } else if (node.right == null) {
                if (parent.left == node)
                    parent.left = node.left;
                else
                    parent.right = node.left;
            } else {     // 삭제할 노드의 자식이 2인경우
                Node priorNodeParent = findPriorParentRecursive(node.left, node);    // 왼쪽 하위 트리의 가장 오른쪽 리프 찾기
                System.out.println("prior: " + priorNodeParent.getRight().getData() + " node: " + node.getData() );
                node.setData(priorNodeParent.getRight().getData());   // 두 노드 교환 후 리프 노트 삭제
                priorNodeParent.right = null;
            }
        }
        else {
            isNodeFinded = deleteRecursive(node.right, data, node);
        }

        return isNodeFinded;
    }

    public static Node findPriorParentRecursive(final Node node, Node parent) {
        if (node.right == null)
            return parent;
        return findPriorParentRecursive(node.right, node);
    }

    public static void traverseInOrderRecursive(final Node node) {
        if (node == null)
            return;

        traverseInOrderRecursive(node.left);
        System.out.print(node.data + ", ");
        traverseInOrderRecursive(node.right);
    }
}
