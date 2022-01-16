package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;

public class BianarySearch {
    public static int binarysearchRecursiveP(Player[] players, int l, int r, int value) {
        if (l > r) {    // 찾기 실패한 경우 - value와 가장 가까운 값을 반환
            if (Math.abs(players[l].getPointsPerGame() - value) <= Math.abs(players[r].getPointsPerGame() - value))
                return l;
            else
                return r;
        }

        int m = (l + r) / 2;    // 내림

        if (players[m].getPointsPerGame() < value) {  // value가 m보다 오른쪽에 있으면
            return binarysearchRecursiveP(players, m + 1, r, value);
        } else if (players[m].getPointsPerGame() == value) {
            return m;
        } else {    // players[m] > value (value가 m보다 왼쪽에 있으면)
            return binarysearchRecursiveP(players, l, m - 1, value);
        }
    }

    public static int binarysearchRecursiveSP(Player[] players, int l, int r, int value) {
        if (l > r) {    // 찾기 실패한 경우 - value와 가장 가까운 값을 반환
            if (Math.abs(players[l].getShootingPercentage() - value) <= Math.abs(players[r].getShootingPercentage() - value))
                return l;
            else
                return r;
        }

        int m = (l + r) / 2;    // 내림

        if (players[m].getShootingPercentage() < value) {  // value가 m보다 오른쪽에 있으면
            return binarysearchRecursiveSP(players, m + 1, r, value);
        } else if (players[m].getShootingPercentage() == value) {
            return m;
        } else {    // players[m] > value (value가 m보다 왼쪽에 있으면)
            return binarysearchRecursiveSP(players, l, m - 1, value);
        }
    }

    public static int binarysearchPoint(Player[] players, int value) {
        return binarysearchRecursiveP(players, 0, players.length, value);
    }

    public static int binarysearchShootingPercentage(Player[] players, int value) {
        return binarysearchRecursiveSP(players, 0, players.length, value);
    }

//    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4, 6, 7, 8, 9, 10};
//
//        int result = binarysearch(nums, 5);
//        System.out.println(result);
//    }
}
