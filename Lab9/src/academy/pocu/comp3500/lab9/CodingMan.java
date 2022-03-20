package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {


    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (time == 0)
            return -1;
        boolean[] timeArray = new boolean[time];
//        Comparator<VideoClip> clipComparator = (VideoClip clip1, VideoClip clip2) -> Integer.compare(clip1.getStartTime(), clip2.getStartTime());
//        Arrays.sort(clips, clipComparator);
        int currPoint = 0;
        int clipCount = 0;
        while (currPoint < time) {
            ArrayList<VideoClip> currPointIncludingClip = new ArrayList<>();
            findPointIncludingClips(clips, currPointIncludingClip, currPoint);
            if (currPointIncludingClip.isEmpty())   // currPoint를 커버할 클립이 없으면 -1 반환
                return -1;
            VideoClip selectedVideoClip = findMaxOccupyClips(currPointIncludingClip, timeArray, currPoint);
            for (int j = currPoint; j < selectedVideoClip.getEndTime(); j++) {
                if (j > timeArray.length - 1)
                    break;
                timeArray[j] = true;
            }
            currPoint = selectedVideoClip.getEndTime();
            clipCount++;
        }
        return clipCount;
    }

    public static void findPointIncludingClips(VideoClip[] clips, ArrayList<VideoClip> result, int point) {
        for (VideoClip clip : clips) {
            if (clip.getStartTime() <= point && point < clip.getEndTime())
                result.add(clip);
        }
    }

    public static VideoClip findMaxOccupyClips(ArrayList<VideoClip> clips, boolean[] timeArray, int currPoint) {
        VideoClip maxOccupyClip = clips.get(0);
        int maxOccupyTime = 0;
        for (VideoClip clip : clips) {
            int occupyTime = 0;
            for (int i = currPoint; i < clip.getEndTime(); i++) {
                if (i > timeArray.length - 1)
                    break;
                if (!timeArray[i])
                    occupyTime++;
            }
            if (maxOccupyTime < occupyTime) {
                maxOccupyClip = clip;
                maxOccupyTime = occupyTime;
            }
        }
        return maxOccupyClip;
    }
}