package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;

public class League {

    private Node2 node;

    public League() {
    }

    public League(Player[] players) {
        for (Player player : players) {
            this.node = Node2.insertRecursive(this.node, player);
        }
    }

    public Player findMatchOrNull(final Player player) {
        ArrayList<Player> players = new ArrayList<>();
        Node2.traverseInOrderRecursive3(node, players);

        players.remove(player);
        int matchPlayerIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            if (Math.abs(players.get(i).getRating() - player.getRating()) <= Math.abs(players.get(matchPlayerIndex).getRating() - player.getRating())) {
                matchPlayerIndex = i;
            }
        }

        return players.get(matchPlayerIndex);
    }

    public Player[] getTop(final int count) {
        if (node == null) {
            return new Player[0];
        }
        ArrayList<Player> topPlayers = new ArrayList<>();
        Node2.traverseInOrderRecursive(node, topPlayers);

        return topPlayers.subList(0, count).toArray(new Player[0]);
    }

    public Player[] getBottom(final int count) {
        if (node == null) {
            return new Player[0];
        }
        ArrayList<Player> topPlayers = new ArrayList<>();
        Node2.traverseInOrderRecursive3(node, topPlayers);

        return topPlayers.subList(0, count).toArray(new Player[0]);
    }

    public boolean join(final Player player) {
        if (Node2.findRecursive(node, player))  // 이미 리그에 참여중인 선수면 false 반환
            return false;

        Node2.insertRecursive(node, player);

        return true;
    }

    public boolean leave(final Player player) {
        if (!Node2.findRecursive(node, player))  // 이미 리그에 참여중인 선수가 아니라면 false 반환
            return false;

        Node2 sudoparent = new Node2();
        sudoparent.setLeft(node);
        Node2.deleteRecursive(node, player, sudoparent);

        node = sudoparent.getLeft();
        return true;
    }


}
