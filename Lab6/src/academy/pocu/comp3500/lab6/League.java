package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;

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

        Node2 sudoRoot = new Node2(new Player(Integer.MAX_VALUE, "???", Integer.MAX_VALUE));
        sudoRoot.setLeft(this.node);

        Node2 playerNode = Node2.findRecursiveReturnNode(this.node, player);
        Node2 playerNodeParent = Node2.findParentRecursive(sudoRoot, player, null);
        Node2 playerNodeGrandParent = Node2.findParentRecursive(sudoRoot, playerNodeParent.getPlayer(), null);
        Node2 priorNode = null;
        Node2 nextNode = null;
        if (playerNode.getLeft() != null)
            priorNode = Node2.findPriorRecursive(playerNode.getLeft());
        if (playerNode.getRight() != null)
            nextNode = Node2.findNextRecursive(playerNode.getRight());

        if (priorNode == null && nextNode == null) {
            int parentDist = player.getRating() - playerNodeParent.getRating();
            int grandParentDist = player.getRating() - playerNodeGrandParent.getRating();
            if (Math.abs(parentDist) < Math.abs(grandParentDist))
                return playerNodeParent.getPlayer();
            else if (Math.abs(parentDist) > Math.abs(grandParentDist))
                return playerNodeGrandParent.getPlayer();
            else {  // parentDist == grandParentDist
                if (parentDist < 0)
                    return playerNodeParent.getPlayer();
                else
                    return playerNodeGrandParent.getPlayer();
            }
        } else if (priorNode == null) {
            int parentDist = player.getRating() - playerNodeParent.getRating();
            int nextDist = Math.abs(player.getRating() - nextNode.getRating());
            if (Math.abs(parentDist) < Math.abs(nextDist))
                return playerNodeParent.getPlayer();
            else if (Math.abs(parentDist) > Math.abs(nextDist))
                return nextNode.getPlayer();
            else {  // nextDist == grandParentDist
                if (parentDist < 0)
                    return playerNodeParent.getPlayer();
                else
                    return nextNode.getPlayer();
            }
        } else if (nextNode == null) {
            int parentDist = player.getRating() - playerNodeParent.getRating();
            int priorDist = Math.abs(player.getRating() - priorNode.getRating());
            if (Math.abs(parentDist) < Math.abs(priorDist))
                return playerNodeParent.getPlayer();
            else if (Math.abs(parentDist) > Math.abs(priorDist))
                return priorNode.getPlayer();
            else {  // priorDist == grandParentDist
                if (parentDist < 0)
                    return playerNodeParent.getPlayer();
                else
                    return priorNode.getPlayer();
            }
        } else {    // priorNode != null && nextNode != null
            int nextDist = Math.abs(player.getRating() - nextNode.getRating());
            int priorDist = Math.abs(player.getRating() - priorNode.getRating());
            if (nextDist <= priorDist)
                return nextNode.getPlayer();
            else
                return priorNode.getPlayer();
        }

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

        Node2 sudoparent = new Node2(new Player(Integer.MAX_VALUE, "^-^", Integer.MAX_VALUE));
        sudoparent.setLeft(node);
        node.setParent(sudoparent);
        Node2.deleteRecursive(sudoparent, player);

        return true;
    }


}
