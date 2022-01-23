package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

import static academy.pocu.comp3500.assignment1.BianarySearch.binarysearchPoint;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        QuickSortGameStat.quicksort(gameStats);
        for (int i = 0; i < gameStats.length; i++) {
            System.out.println(gameStats[i].getPlayerName());
        }

        int playerIndex = 0;
        int priorPlayer = gameStats[0].getPlayerName().hashCode();
        int currPlayer;
        int sumOfPoints = 0;
        int sumOfAssists = 0;
        int sumOfPasses = 0;
        int sumOfGoals = 0;
        int sumOfGoalAttempts = 0;
        int numOfPlayerGame = 0;

        for (int i = 0; i < gameStats.length; i++) {
            currPlayer = gameStats[i].getPlayerName().hashCode();
            if (currPlayer != priorPlayer) {
                priorPlayer = currPlayer;

                outPlayers[playerIndex].setPointsPerGame(sumOfPoints / numOfPlayerGame);
                outPlayers[playerIndex].setAssistsPerGame(sumOfAssists / numOfPlayerGame);
                outPlayers[playerIndex].setPassesPerGame(sumOfPasses / numOfPlayerGame);
                outPlayers[playerIndex].setShootingPercentage((int) ((double) 100 * sumOfGoals / sumOfGoalAttempts));
                numOfPlayerGame = 0;
                sumOfPoints = 0;
                sumOfAssists = 0;
                sumOfPasses = 0;
                sumOfGoals = 0;
                sumOfGoalAttempts = 0;
                playerIndex++;

            }
            outPlayers[playerIndex].setName(gameStats[i].getPlayerName());
            sumOfPoints = sumOfPoints + gameStats[i].getPoints();
            sumOfAssists = sumOfAssists + gameStats[i].getAssists();
            sumOfPasses = sumOfPasses + gameStats[i].getNumPasses();
            sumOfGoals = sumOfGoals + gameStats[i].getGoals();
            sumOfGoalAttempts = sumOfGoalAttempts + gameStats[i].getGoalAttempts();
            numOfPlayerGame++;
        }
        // update last player
        outPlayers[playerIndex].setPointsPerGame(sumOfPoints / numOfPlayerGame);
        outPlayers[playerIndex].setAssistsPerGame(sumOfAssists / numOfPlayerGame);
        outPlayers[playerIndex].setPassesPerGame(sumOfPasses / numOfPlayerGame);
        outPlayers[playerIndex].setShootingPercentage((int) ((double) 100 * sumOfGoals / sumOfGoalAttempts));

    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        int playerIndex = BianarySearch.binarysearchPoint(players, targetPoints);
        return players[playerIndex];
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        int playerIndex = BianarySearch.binarysearchShootingPercentage(players, targetShootingPercentage);
        return players[playerIndex];
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        return findDreamTeam(players, 3, outPlayers, scratch);
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        if (k == 0)
            return -1;
        QuickSortPlayer.quicksortAssists(players);

        long maxTeamwork;
        long currTeamWork;
        int currPassesSum = 0;
        int currMinAssist = players[k - 1].getAssistsPerGame();

        for (int i = 0; i < k; i++) {
            scratch[i] = players[i];
            currPassesSum += players[i].getPassesPerGame();
        }
        maxTeamwork = (long) currPassesSum * currMinAssist;
        for (int j = 0; j < k; j++) {
            outPlayers[j] = scratch[j];
        }

        int maxTeamworkIndex = -1;
        for (int i = k; i < players.length; i++) {
//            QuickSortPlayer.quicksortPasses(scratch, k);
            // find min at scratch array
            int indexOfminPassesInScratch = 0;
            int minPassesInScratch = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                if (scratch[j].getPassesPerGame() < minPassesInScratch) {
                    indexOfminPassesInScratch = j;
                    minPassesInScratch = scratch[j].getPassesPerGame();
                }
            }


            currPassesSum -= scratch[indexOfminPassesInScratch].getPassesPerGame();
            scratch[indexOfminPassesInScratch] = players[i];
            currPassesSum += players[i].getPassesPerGame();
            currMinAssist = players[i].getAssistsPerGame();
            currTeamWork = (long) currPassesSum * currMinAssist;

            if (currTeamWork > maxTeamwork) {
                maxTeamworkIndex = i;
                maxTeamwork = currTeamWork;
//                for (int j = 0; j < k; j++) {
//                    outPlayers[j] = scratch[j];
//                }
            }
        }
        // 스크래치 초기화
        for (int i = 0; i < k; i++) {
            scratch[i] = players[i];
        }
        // outPlayer에 선수들을 담기위해 반복문을 다시 돌리기
        for (int i = k; i <= maxTeamworkIndex; i++) {
//            QuickSortPlayer.quicksortPasses(scratch, k);
            // find min at scratch array
            int indexOfminPassesInScratch = 0;
            int minPassesInScratch = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                if (scratch[j].getPassesPerGame() < minPassesInScratch) {
                    indexOfminPassesInScratch = j;
                    minPassesInScratch = scratch[j].getPassesPerGame();
                }
            }
            scratch[indexOfminPassesInScratch] = players[i];
        }
        // outPlayer에 scratch 값 대입
        for (int j = 0; j < k; j++) {
            outPlayers[j] = scratch[j];
        }

        return maxTeamwork;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        int bestTeamSize = 1;
        long bestTeamworkScore = Integer.MIN_VALUE;
        long currTeamworkScore;

        for (int i = 1; i <= players.length; i++) {
            currTeamworkScore = findDreamTeam(players, i, scratch, scratch);

            if (bestTeamworkScore < currTeamworkScore) {
                bestTeamSize = i;
                bestTeamworkScore = currTeamworkScore;
            }
        }

        return bestTeamSize;
    }
}