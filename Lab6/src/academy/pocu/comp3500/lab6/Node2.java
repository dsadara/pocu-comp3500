package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;
import java.util.Stack;

public class Node2 {
    private Player player;
    private Node2 left;
    private Node2 right;
    private Node2 parent;

    public Node2() {
    }

    public Node2(final Player player) {
        this.player = player;
    }

    public int getRating() {
        return this.player.getRating();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Node2 getParent() {
        return this.parent;
    }

    public Node2 getLeft() {
        return this.left;
    }

    public Node2 getRight() {
        return this.right;
    }

    public void setLeft(Node2 node) {
        this.left = node;
    }

    public void setRight(Node2 node) {
        this.right = node;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setParent(Node2 parent) {
        this.parent = parent;
    }

    public static boolean findRecursive(final Node2 node, Player player) {
        if (node == null)
            return false;

        if (player.getRating() < node.getRating()) {
            return findRecursive(node.left, player);
        } else if (player.getRating() > node.getRating()) {
            return findRecursive(node.right, player);
        } else {    // player.getRating() == node.getRating()
            return true;
        }
    }

    public static Node2 findRecursiveReturnNode(final Node2 node, Player player) {
        if (player.getRating() < node.getRating()) {
            return findRecursiveReturnNode(node.left, player);
        } else if (player.getRating() > node.getRating()) {
            return findRecursiveReturnNode(node.right, player);
        } else {    // player.getRating() == node.getRating()
            return node;
        }
    }

    public static Node2 findParentRecursive(final Node2 node, Player player, Node2 parent) {
        if (player.getRating() < node.getRating()) {
            parent = node;
            return findParentRecursive(node.left, player, parent);
        } else if (player.getRating() > node.getRating()) {
            parent = node;
            return findParentRecursive(node.right, player, parent);
        } else {    // player.getRating() == node.getRating()
            return parent;
        }
    }

//    public static Node2 findRecursiveReturnNode(final Node2 node, Player player, Node2 parent) {
//        if (node == null) {
//
//        }
//            return parent;
//
//        if (player.getRating() < node.getRating()) {
//            node.left = findRecursiveReturnNode(node.left, player, parent);
//        } else if (player.getRating() > node.getRating()) {
//            node.right =  findRecursiveReturnNode(node.right, player, parent);
//        }
//
//        return node;
//    }

    public static Node2 insertRecursiveStatic(final Node2 node, Player player, Node2 parent) {
        if (node == null) {
            Node2 newNode = new Node2(player);
            newNode.parent = parent;
            return newNode;

        }

        if (player.getRating() < node.getRating()) {
            parent = node;
            node.left = insertRecursiveStatic(node.left, player, parent);
        } else {    // data >= node.data
            parent = node;
            node.right = insertRecursiveStatic(node.right, player, parent);
        }

        return node;
    }

//    public  Node2 insertRecursive(final Node2 node, Player player, Node2 parent) {
//        if (node == null) {
//            this.parent = parent;
//            return new Node2(player);
//        }
//
//        if (player.getRating() < node.getRating()) {
//            parent = node;
//            node.left = insertRecursive(node.left, player, parent);
//        } else {    // data >= node.data
//            parent = node;
//            node.right = insertRecursive(node.right, player, parent);
//        }
//
//        return node;
//    }

//    public static void deleteRecursive(Node2 node, Player player) {
//        if (player.getRating() < node.getRating()) {
//            deleteRecursive(node.left, player);
//        } else if (player.getRating() > node.getRating()){    // data >= node.data
//            deleteRecursive(node.right, player);
//        } else {    // 샂제할 노드를 찾았을 때
//            if (node.getLeft() == null && node.getRight() == null) {    // 삭제하는 노드가 리프 노드인 경우
//                if (node.getParent().getLeft() == node)
//                    node.getParent().setLeft(null);
//                else
//                    node.getParent().setRight(null);
//            } else if (node.getLeft() == null) {
//                Node2 successor = Node2.findMin(node.getRight());
//                if (successor == node.getRight()) { // node의 자식 노드가 successor 일 때
//                    node.setPlayer(successor.getPlayer());
//                    node.setRight(successor.getRight());
//                } else {
//                    node.setPlayer(successor.getPlayer());
//                    successor.getParent().setLeft(null);
//                }
//            } else {
//                Node2 predecessor = Node2.findMax(node.getLeft());
//                if (predecessor == node.getLeft()) {    // node의 자식 노드가 predcessor 일 때
//                    node.setPlayer(predecessor.getPlayer());
//                    node.setLeft(predecessor.getLeft());
//                } else {
//                    node.setPlayer(predecessor.getPlayer());
//                    predecessor.getParent().setRight(null);
//                }
//            }
//        }
//        return;
//    }

    public static Node2 findPredecessor(Node2 node, Node2 pred, Player player) {
        if (node == null)
            return pred;

        if (node.getRating() > player.getRating()) {
            return findPredecessor(node.left, pred, player);
        } else if (node.getRating() < player.getRating()) {
            pred = node;
            return findPredecessor(node.right, pred, player);
        } else {    // node.getRating() == player.getRating()
            if (node.left != null)
                return findMax(node.left);
        }
        return pred;
    }

    public static Node2 findSuccessor(Node2 node, Node2 succr, Player player) {
        if (node == null)
            return succr;

        if (node.getRating() > player.getRating()) {
            succr = node;
            return findSuccessor(node.left, succr, player);
        } else if (node.getRating() < player.getRating()) {
            return findSuccessor(node.right, succr, player);
        } else {         // node.getRating() == player.getRating()
            if (node.right != null)
                return findMin(node.right);
        }
        return succr;
    }

    public static Node2 findMax(Node2 node) {
        if (node.right == null)
            return node;

        return findMax(node.right);
    }

    public static Node2 findMin(Node2 node) {
        if (node.left == null)
            return node;

        return findMin(node.left);
    }

    public static boolean deleteRecursive(Node2 node, Player player) {
        if (node == null)       // 삭제할 노드가 없으면 null 반환
            return false;

        Boolean isNodeFinded = true;

        if (player.getRating() < node.getRating()) {
            isNodeFinded = deleteRecursive(node.left, player);
        } else if (player.getRating() == node.getRating()) {
            //  삭제할 노드의 자식이 0인경우
            if (node.left == null && node.right == null) {
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(null);
                else
                    node.getParent().setRight(null);
            } else if (node.left == null) {     // 삭제할 노드의 자식이 1인 경우
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(node.right);
                else
                    node.getParent().setRight(node.right);
            } else if (node.right == null) {
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(node.left);
                else
                    node.getParent().setRight(node.left);
            } else {     // 삭제할 노드의 자식이 2인경우
//                Node2 priorNodeParent = findPriorParentRecursive(node.left, node);    // 왼쪽 하위 트리의 가장 오른쪽 리프 찾기
//                System.out.println("prior: " + priorNodeParent.getRight().getRating() + " node: " + node.getRating());
                Node2 predNode = findMax(node.left);
                // 노드 교환
                if (node.getParent().getLeft() == node) {
                    predNode.getParent().getRight().setRight(node.right);
                    predNode.getParent().getRight().setLeft(node.left);
                    node.getParent().setLeft(predNode.getParent().getRight());
                    predNode.getParent().setRight(null);
                } else {
                    predNode.getParent().getRight().setRight(node.right);
                    predNode.getParent().getRight().setLeft(node.left);
                    node.getParent().setRight(predNode.getParent().getRight());
                    predNode.getParent().setRight(null);
                }
            }
        } else {
            isNodeFinded = deleteRecursive(node.right, player);
        }

        return isNodeFinded;
    }

    private static Node2 findPriorParentRecursive(final Node2 node, Node2 parent) {
        if (node.right == null)
            return parent;
        return findPriorParentRecursive(node.right, node);
    }

    public static Node2 findPriorRecursive(final Node2 node) {
        if (node.right == null)
            return node;
        return findPriorRecursive(node.right);
    }

    public static Node2 findNextRecursive(final Node2 node) {
        if (node.left == null)
            return node;
        return findNextRecursive(node.left);
    }

    public static void traverseInOrderRecursiveDescending(final Node2 node, ArrayList<Player> players) {
        if (node == null)
            return;

        traverseInOrderRecursiveDescending(node.right, players);
        players.add(node.player);
        traverseInOrderRecursiveDescending(node.left, players);
    }

    public static void traverseInOrderRecursiveAscending(final Node2 node, ArrayList<Player> players) {
        if (node == null)
            return;

        traverseInOrderRecursiveAscending(node.left, players);
        players.add(node.player);
        traverseInOrderRecursiveAscending(node.right, players);
    }

}
