package academy.pocu.comp3500.lab3;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
        int searchResult = -1;
        if (altitudes.length != 2) {    // altitudes 원소의 개수가 2개일때는 최댓값 search 하지않음
            searchResult = BinarySearch.binarySearchMaxAlt(altitudes);    // 올라갔다 내려가는경우 최대고도 찾기
            if (searchResult != -1)
                return searchResult;
        }
        int flightTime = altitudes.length;
        if (altitudes[0] + (flightTime - 1) <= altitudes[flightTime - 1]) {   // 고도가 올라가기만 하는 경우
            return flightTime - 1;
        }
        if (altitudes[0] - (flightTime - 1) >= altitudes[flightTime - 1]) {    // 고도가 내려가기만 하는 경우
            return 0;
        }

        return searchResult;
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> altitudeTimes = new ArrayList<Integer>();
        int searchResult = -1;
        if (altitudes.length != 2) {    // altitudes 원소의 개수가 2개일때는 최댓값 search 하지않음
            searchResult = BinarySearch.binarySearchMaxAlt(altitudes);    // 올라갔다 내려가는경우 최대고도 찾기
        }
        if (searchResult == -1) {   // 오르거나 내려가기만 하는 경우
            int targetAltitudeTime;
            if (altitudes[0] - (altitudes.length - 1) >= altitudes[altitudes.length - 1]) {    // 고도가 내려가기만 하는 경우
                targetAltitudeTime = BinarySearch.binarysearchRecursiveDescend(altitudes, 0, altitudes.length - 1, targetAltitude);
            } else {    // 고도 올라가는경우
                targetAltitudeTime = BinarySearch.binarySearch(altitudes, targetAltitude);
            }
            if (targetAltitudeTime != -1)
                altitudeTimes.add(targetAltitudeTime);
        } else {    // 오르다가 내리는경우
            if (altitudes[searchResult] == targetAltitude)
                altitudeTimes.add(searchResult);
            int targetAltitudeTime = BinarySearch.binarysearchRecursive(altitudes, 0, searchResult - 1, targetAltitude);
            if (targetAltitudeTime != -1)
                altitudeTimes.add(targetAltitudeTime);
            targetAltitudeTime = BinarySearch.binarysearchRecursiveDescend(altitudes, searchResult + 1, altitudes.length - 1, targetAltitude);
            if (targetAltitudeTime != -1)
                altitudeTimes.add(targetAltitudeTime);
        }

        return altitudeTimes;
    }
}