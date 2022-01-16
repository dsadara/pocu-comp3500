package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

import static academy.pocu.comp3500.assignment1.BianarySearch.binarysearchPoint;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        QuickSort.quicksort(gameStats);
        for (int i = 0; i < gameStats.length; i++) {
            System.out.println(gameStats[i].getPlayerName());
        }

        int playerIndex = 0;
        int priorPlayer = gameStats[0].getPlayerName().hashCode();
        int currPlayer;
        int sumOfPoints = 0, sumOfAssists = 0, sumOfPasses = 0, sumOfGoals = 0, sumOfGoalAttempts = 0, numOfPlayerGame = 0;
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
        return -1;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        return -1;
    }
}