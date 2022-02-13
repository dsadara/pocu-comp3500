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

    public static Node2 deleteRecursive(Node2 node, Player player) {
        if (node == null)
            return node;

        if (player.getRating() < node.getRating()) {
            node.left = deleteRecursive(node.left, player);
        } else if (player.getRating() > node.getRating()) {
            node.right = deleteRecursive(node.right, player);
        } else {    // player.getRating() == node.getRating()
            if (node.left == null && node.right == null) {      // 자식이 없을 때
                node.setParent(null);
                return null;
            } else if (node.left == null) {     // 오른쪽 자식만 있을 때
                node.setParent(null);
                node.right.setParent(node.getParent());
                return node.right;
            } else if (node.right == null) {     // 왼쪽 자식만 있을 때
                node.setParent(null);
                node.left.setParent(node.getParent());
                return node.left;
            }
            // 자식이 둘 있을 때
            Node2 predecessor = findPredecessor(node.left, null, player);

            if (predecessor.getParent() == node) {
                node.left = predecessor.left;
            } else {
                predecessor.getParent().right = predecessor.left;
            }
            predecessor.setParent(null);
            node.setPlayer(predecessor.getPlayer());
        }
        return node;
    }

    public static void traverseInOrderRecursiveDescending(final Node2 node, ArrayList<Player> players, final int count) {
        if (node == null)
            return;

        traverseInOrderRecursiveDescending(node.right, players, count);
        if (players.size() == count)
            return;
        players.add(node.player);
        if (players.size() == count)
            return;
        traverseInOrderRecursiveDescending(node.left, players, count);
        if (players.size() == count)
            return;
    }

    public static void traverseInOrderRecursiveAscending(final Node2 node, ArrayList<Player> players, final int count) {
        if (node == null)
            return;

        traverseInOrderRecursiveAscending(node.left, players, count);
        if (players.size() == count)
            return;
        players.add(node.player);
        if (players.size() == count)
            return;
        traverseInOrderRecursiveAscending(node.right, players, count);
        if (players.size() == count)
            return;
    }

}
