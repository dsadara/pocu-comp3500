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

//    public void setRating(Player player) {
//        this.data = data;
//    }

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

    public static void deleteRecursive(Node2 node, Player player) {

        if (player.getRating() < node.getRating()) {
            deleteRecursive(node.left, player);
        } else if (player.getRating() > node.getRating()){    // data >= node.data
            deleteRecursive(node.right, player);
        } else {
            if (node.getLeft() == null && node.getRight() == null) {    // 삭제하는 노드가 리프 노드인 경우
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(null);
                else
                    node.getParent().setRight(null);
            } else if (node.getLeft() == null) {                // 삭제하는 노드 왼쪽자식이 없을 때
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(node.getRight());
                else
                    node.getParent().setRight(node.getRight());
            } else if (node.getRight() == null) {               // 삭제하는 노드 오른쪽자식이 없을 때
                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(node.getLeft());
                else
                    node.getParent().setRight(node.getLeft());
            } else {                                            // 삭제하는 노드 두 자식이 존재할 때
                Node2 predecessor = Node2.findMax(node.getLeft());
                node.setPlayer(predecessor.getPlayer());
                predecessor.getParent().setRight(null);
            }

        }

        return;
    }

    public static Node2 findMax(Node2 node) {
        if (node.right == null)
            return node;

        return findMax(node);
    }

//    public static boolean deleteRecursive(Node2 node, Player player, Node2 parent) {
//        if (node == null)       // 삭제할 노드가 없으면 null 반환
//            return false;
//
//        Boolean isNodeFinded = true;
//
//        if (player.getRating() < node.getRating()) {
//            isNodeFinded = deleteRecursive(node.left, player, node);
//        } else if (player.getRating() == node.getRating()) {
//            //  삭제할 노드의 자식이 0인경우
//            if (node.left == null && node.right == null) {
//                if (parent.left == node)
//                    parent.left = null;
//                else
//                    parent.right = null;
//            } else if (node.left == null) {     // 삭제할 노드의 자식이 1인 경우
//                if (parent.left == node)
//                    parent.left = node.right;
//                else
//                    parent.right = node.right;
//            } else if (node.right == null) {
//                if (parent.left == node)
//                    parent.left = node.left;
//                else
//                    parent.right = node.left;
//            } else {     // 삭제할 노드의 자식이 2인경우
//                Node2 priorNodeParent = findPriorParentRecursive(node.left, node);    // 왼쪽 하위 트리의 가장 오른쪽 리프 찾기
//                System.out.println("prior: " + priorNodeParent.getRight().getRating() + " node: " + node.getRating());
////                node.setData(priorNodeParent.getRight().getRating());   // 두 노드 교환 후 리프 노트 삭제
////                priorNodeParent.right = null;
//                // 노드 교환
//                if (parent.left == node) {
//                    priorNodeParent.right.right = node.right;
//                    priorNodeParent.right.left = node.left;
//                    parent.left = priorNodeParent.right;
//                    priorNodeParent.right = null;
//                } else {
//                    priorNodeParent.right.right = node.right;
//                    priorNodeParent.right.left = node.left;
//                    parent.right = priorNodeParent.right;
//                    priorNodeParent.right = null;
//                }
//            }
//        } else {
//            isNodeFinded = deleteRecursive(node.right, player, node);
//        }
//
//        return isNodeFinded;
//    }

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
