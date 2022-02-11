package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;

import static academy.pocu.comp3500.lab6.Node2.findPredecessor;
import static academy.pocu.comp3500.lab6.Node2.findSuccessor;

public class League {

    private Node2 node;

    public League() {
    }

    public League(Player[] players) {
        for (Player player : players) {
            this.node = Node2.insertRecursiveStatic(this.node, player, null);
        }
    }

    public Player findMatchOrNull(final Player player) {
        if (node == null)   // 리그에 참여중인 선수가 없는 경우
            return null;
        if (node.getLeft() == null && node.getRight() == null)  // 리그에 참여중인 선수가 한명인 경우
            return null;
        if (!Node2.findRecursive(node, player))     // 매칭할 선수가 리그에 없는 경우
            return null;

        Node2 predecessor = findPredecessor(this.node, null, player);
        Node2 successor = findSuccessor(this.node, null, player);

        int predDist;
        int succrDist;

        if (predecessor == null)
            predDist = Integer.MAX_VALUE;
        else
            predDist = player.getRating() - predecessor.getRating();
        if (successor == null)
            succrDist = Integer.MAX_VALUE;
        else
            succrDist = successor.getRating() - player.getRating();

        if (predDist > succrDist)
            return successor.getPlayer();
        else if (predDist < succrDist)
            return predecessor.getPlayer();
        else    // predecessor == sucessor
            return successor.getPlayer();
    }

    public Player[] getTop(final int count) {
        if (node == null) {
            return new Player[0];
        }
        ArrayList<Player> topPlayers = new ArrayList<>();
        Node2.traverseInOrderRecursiveDescending(node, topPlayers);

        if (topPlayers.size() < count)
            return topPlayers.toArray(new Player[0]);

        return topPlayers.subList(0, count).toArray(new Player[0]);
    }

    public Player[] getBottom(final int count) {
        if (node == null) {
            return new Player[0];
        }
        ArrayList<Player> topPlayers = new ArrayList<>();
        Node2.traverseInOrderRecursiveAscending(node, topPlayers);

        if (topPlayers.size() < count)
            return topPlayers.toArray(new Player[0]);

        return topPlayers.subList(0, count).toArray(new Player[0]);
    }

    public boolean join(final Player player) {
        if (Node2.findRecursive(node, player))  // 이미 리그에 참여중인 선수면 false 반환
            return false;

        node = Node2.insertRecursiveStatic(node, player, null);

        return true;
    }

    public boolean leave(final Player player) {
        if (!Node2.findRecursive(node, player))  // 이미 리그에 참여중인 선수가 아니라면 false 반환
            return false;

        // 원소 한개인 경우 제거
        if (node.getLeft() == null && node.getRight() == null) {
            this.node = null;
            return true;
        }

        Node2 sudoparent = new Node2(new Player(Integer.MAX_VALUE, "^-^", Integer.MAX_VALUE));
        sudoparent.setLeft(node);
        node.setParent(sudoparent);

        Node2.deleteRecursive(sudoparent, player);
        node = sudoparent.getLeft();
        sudoparent.setPlayer(null);
        node.setParent(null);
        return true;
    }


}
