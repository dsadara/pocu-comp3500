package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        int gameStatsLength = gameStats.length;
        int emptyIndex = 0;
        int playersLength = outPlayers.length;

        HashTable htGameCount = new HashTable(playersLength);    // 각 플레이어에 대한 경기 횟수를 count 하기 위한 해시 테이블
        HashTable htGoals = new HashTable(playersLength);       // 각 플레이어의 골 횟수를 count하는 해시 테이블
        HashTable htGoalAttempts = new HashTable(playersLength);    // 각 플레이어의 골 시도횟수를 count하는 해시 테이블

        outerLoop:
        for (int i = 0; i < gameStatsLength; i++) {     // gameStats 한바퀴 도는 반복문
            String playerNameFromStats = gameStats[i].getPlayerName();

            if (htGameCount.get(playerNameFromStats) == Integer.MIN_VALUE) {    // 해당 String이 해시테이블에 available한 경우
                htGameCount.put(playerNameFromStats, 1);
                htGoals.put(playerNameFromStats, gameStats[i].getGoals());
                htGoalAttempts.put(playerNameFromStats, gameStats[i].getGoalAttempts());
            } else {                                                            // 해당 String에 대한 value가 이미 존재하는 경우
                // update game count
                int tmp1 = htGameCount.get(playerNameFromStats);
                htGameCount.put(playerNameFromStats, ++tmp1);
                // update goals
                int tmp2 = htGoals.get(playerNameFromStats);
                htGoals.put(playerNameFromStats, tmp2 + gameStats[i].getGoals());
                // update goal attempts
                int tmp3 = htGoalAttempts.get(playerNameFromStats);
                htGoalAttempts.put(playerNameFromStats, tmp3 + gameStats[i].getGoalAttempts());

            }

            for (int j = 0; j < emptyIndex; j++) {     // emptyIndex 전까지 도는 반복문 (outPlayers 배열에 이미 넣은 player가 있는지 확인하기 위함)
                String playerNameFromPlayers = outPlayers[j].getName();

                if (playerNameFromStats.equals(playerNameFromPlayers)) {     // OutPlayers 배열에 playerName이 있으면 (이미 있는 값을 업데이트)
//                    outPlayers[j].setName(gameStats[i].getPlayerName());     doesn't have to rename again
                    // Points 업데이트
                    int priorVal = outPlayers[j].getPointsPerGame();
                    outPlayers[j].setPointsPerGame(priorVal + gameStats[i].getPoints());
                    // Assists 업데이트
                    priorVal = outPlayers[j].getAssistsPerGame();
                    outPlayers[j].setAssistsPerGame(priorVal + gameStats[i].getAssists());
                    // Passes 업데이트
                    priorVal = outPlayers[j].getPassesPerGame();
                    outPlayers[j].setPassesPerGame(priorVal + gameStats[i].getNumPasses());
                    continue outerLoop;
                }
            }
            // OutPlayers 배열에 playerName이 없으면 (처음 대입하는 경우)
            // name 초기화
            outPlayers[emptyIndex].setName(gameStats[i].getPlayerName());
            // Points 초기화
            outPlayers[emptyIndex].setPointsPerGame(gameStats[i].getPoints());
            // Assists 초기화
            outPlayers[emptyIndex].setAssistsPerGame(gameStats[i].getAssists());
            // Passes 초기화
            outPlayers[emptyIndex].setPassesPerGame(gameStats[i].getNumPasses());
            emptyIndex++;


        }

        // 평균 구하는 반복문
        for (int i = 0; i < outPlayers.length; i++) {
            String name = outPlayers[i].getName();

            // update points average
            int sum = outPlayers[i].getPointsPerGame();
            outPlayers[i].setPointsPerGame(sum / htGameCount.get(name));
            // update Assists average
            sum = outPlayers[i].getAssistsPerGame();
            outPlayers[i].setAssistsPerGame(sum / htGameCount.get(name));
            // update Passes average
            sum = outPlayers[i].getPassesPerGame();
            outPlayers[i].setPassesPerGame(sum / htGameCount.get(name));
            // update shooting Percentage
            double shootingPercent = 100 * (Double.valueOf(htGoals.get(name)) / htGoalAttempts.get(name));
            outPlayers[i].setShootingPercentage((int) shootingPercent);
        }

//        System.out.println("print game count in HT");
//        for (int i = 0; i < outPlayers.length ; i++) {
//            System.out.println(htGameCount.get(outPlayers[i].getName()));
//        }
//
//        System.out.println("print goals in HT");
//        for (int i = 0; i < outPlayers.length ; i++) {
//            System.out.println(htGoals.get(outPlayers[i].getName()));
//        }
//
//        System.out.println("print goal attemps in HT");
//        for (int i = 0; i < outPlayers.length ; i++) {
//            System.out.println(htGoalAttempts.get(outPlayers[i].getName()));
//        }
    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        return null;
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        return null;
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