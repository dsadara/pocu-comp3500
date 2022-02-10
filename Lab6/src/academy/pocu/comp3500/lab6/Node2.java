package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;
import java.util.Stack;

public class Node2 {
    private Player player;
    private Node2 left;
    private Node2 right;

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
        left = node;
    }

    public void setRight(Node2 node) {
        right = node;
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

    public static Node2 findRecursive2(final Node2 node, Player player) {
        if (player.getRating() < node.getRating()) {
            return findRecursive2(node.left, player);
        } else if (player.getRating() > node.getRating()) {
            return findRecursive2(node.right, player);
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

//    public static Node2 findRecursive2(final Node2 node, Player player, Node2 parent) {
//        if (node == null) {
//
//        }
//            return parent;
//
//        if (player.getRating() < node.getRating()) {
//            node.left = findRecursive2(node.left, player, parent);
//        } else if (player.getRating() > node.getRating()) {
//            node.right =  findRecursive2(node.right, player, parent);
//        }
//
//        return node;
//    }

    public static Node2 insertRecursive(final Node2 node, Player player) {
        if (node == null) {
            return new Node2(player);
        }

        if (player.getRating() < node.getRating()) {
            node.left = insertRecursive(node.left, player);
        } else {    // data >= node.data
            node.right = insertRecursive(node.right, player);
        }

        return node;
    }

    public static boolean deleteRecursive(Node2 node, Player player, Node2 parent) {
        if (node == null)       // 삭제할 노드가 없으면 null 반환
            return false;

        Boolean isNodeFinded = true;

        if (player.getRating() < node.getRating()) {
            isNodeFinded = deleteRecursive(node.left, player, node);
        } else if (player.getRating() == node.getRating()) {
            //  삭제할 노드의 자식이 0인경우
            if (node.left == null && node.right == null) {
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
                Node2 priorNodeParent = findPriorParentRecursive(node.left, node);    // 왼쪽 하위 트리의 가장 오른쪽 리프 찾기
                System.out.println("prior: " + priorNodeParent.getRight().getRating() + " node: " + node.getRating());
//                node.setData(priorNodeParent.getRight().getRating());   // 두 노드 교환 후 리프 노트 삭제
//                priorNodeParent.right = null;
                // 노드 교환
                if (parent.left == node) {
                    priorNodeParent.right.right = node.right;
                    priorNodeParent.right.left = node.left;
                    parent.left = priorNodeParent.right;
                    priorNodeParent.right = null;
                } else {
                    priorNodeParent.right.right = node.right;
                    priorNodeParent.right.left = node.left;
                    parent.right = priorNodeParent.right;
                    priorNodeParent.right = null;
                }
            }
        } else {
            isNodeFinded = deleteRecursive(node.right, player, node);
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

    public static void traverseInOrderRecursive(final Node2 node, ArrayList<Player> players, int count) {
        if (players.size() == count)
            return;
        if (node == null)
            return;

        traverseInOrderRecursive(node.right, players, count);
        players.add(node.player);
        traverseInOrderRecursive(node.left, players, count);
    }

    public static void traverseInOrderRecursive3(final Node2 node, ArrayList<Player> players, int count) {
        if (players.size() == count)
            return;
        if (node == null)
            return;

        traverseInOrderRecursive3(node.left, players, count);
        players.add(node.player);
        traverseInOrderRecursive3(node.right, players, count);
    }

}
