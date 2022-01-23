package academy.pocu.comp3500.lab3;

import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
        int maxAltitude = Integer.MIN_VALUE;
        int maxAltitudeIndex = -1;
        for (int i = 0; i < altitudes.length; i++) {
            int currAltitude = altitudes[i];
            if (currAltitude > maxAltitude) {
                maxAltitudeIndex = i;
                maxAltitude = currAltitude;
            }

        }
        return maxAltitudeIndex;
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> altitudeTimes = new ArrayList<Integer>();
        for (int i = 0; i < altitudes.length ; i++) {
            if (altitudes[i] == targetAltitude) {
                altitudeTimes.add(i);
            }
        }
        return altitudeTimes;
    }
}