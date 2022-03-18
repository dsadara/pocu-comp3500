package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {


    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        // time만큼의 원소를 갖는 배열 생성
        if (time == 0)
            return -1;
        boolean[] timeArray = new boolean[time + 1];

        int[] occupyTime = new int[clips.length];
        // videoClip에 OccupyTime 변수를 추가하여 boxing
        VideoClipBoxed[] videoClipBoxed = new VideoClipBoxed[clips.length];
        for (int i = 0; i < videoClipBoxed.length; i++) {
            videoClipBoxed[i] = new VideoClipBoxed(clips[i], time);
        }

        Comparator<VideoClipBoxed> byOccupyTime = (VideoClipBoxed clip1, VideoClipBoxed clip2) -> Integer.compare(clip1.getOccupyTime(), clip2.getOccupyTime());

        // time 안에서 가장 많은 시간을 차지하는 순으로 정렬
        Arrays.sort(videoClipBoxed, byOccupyTime.reversed());

        for (int i = 0; i < videoClipBoxed.length; i++) {
            System.out.println("(" + videoClipBoxed[i].getVideoClip().getStartTime() + ", " + videoClipBoxed[i].getVideoClip().getEndTime() + ")");
        }

        int minClipNum = 0;
        for (int i = 0; i < videoClipBoxed.length; i++) {
            for (int j = videoClipBoxed[i].getVideoClip().getStartTime(); j <= videoClipBoxed[i].getVideoClip().getEndTime(); j++) {
                timeArray[j] = true;
            }
            minClipNum++;
            if (isTimeArrayFilled(timeArray)) {
                return minClipNum;
            }
        }


        return -1;
    }

    public static boolean isTimeArrayFilled(boolean[] timeArray) {
        for (boolean b : timeArray) {
            if (!b)
                return false;
        }
        return true;
    }
}