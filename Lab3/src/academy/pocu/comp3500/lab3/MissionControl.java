package academy.pocu.comp3500.lab3;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
//        int maxAltitude = Integer.MIN_VALUE;
//        int maxAltitudeIndex = -1;
//        for (int i = 0; i < altitudes.length; i++) {
//            int currAltitude = altitudes[i];
//            if (currAltitude > maxAltitude) {
//                maxAltitudeIndex = i;
//                maxAltitude = currAltitude;
//            }
//
//        }
//        return maxAltitudeIndex;
        int searchResult = -1;
        if (altitudes.length != 2) {    // altitudes 원소의 개수가 2개일때는 최댓값 search 하지않음
            searchResult = BinarySearch.binarySearch(altitudes);    // 올라갔다 내려가는경우 최대고도 찾기
            if (searchResult != -1)
                return searchResult;
        }
        int flightTime = altitudes.length;
        if (altitudes[0] + (flightTime - 1) <= altitudes[flightTime - 1] ) {   // 고도가 올라가기만 하는 경우
            return flightTime - 1;
        }
        if (altitudes[0] - (flightTime - 1) >= altitudes[flightTime - 1]) {    // 고도가 내려가기만 하는 경우
            return 0;
        }

        return searchResult;
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> altitudeTimes = new ArrayList<Integer>();
        for (int i = 0; i < altitudes.length; i++) {
            if (altitudes[i] == targetAltitude) {
                altitudeTimes.add(i);
            }
        }
        return altitudeTimes;
    }
}