package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {

    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (time == 0)
            return -1;
        Comparator<VideoClip> clipComparator = (VideoClip clip1, VideoClip clip2) -> Integer.compare(clip1.getStartTime(), clip2.getStartTime());
        Arrays.sort(clips, clipComparator);
        int[] lastOccupiedTime = new int[2];
        int currPoint = 0;
        int clipCount = 0;
        while (currPoint < time) {
            ArrayList<VideoClip> currPointIncludingClip = new ArrayList<>();
            findPointIncludingClips(clips, currPointIncludingClip, currPoint);
            if (currPointIncludingClip.isEmpty())   // currPoint를 커버할 클립이 없으면 -1 반환
                return -1;
            VideoClip selectedVideoClip = findMaxOccupyClips(currPointIncludingClip, currPoint, lastOccupiedTime, time);
            // lastOccupiedTime 채우기
            lastOccupiedTime[0] = currPoint;
            lastOccupiedTime[1] = selectedVideoClip.getEndTime() - 1;

            currPoint = selectedVideoClip.getEndTime();
            clipCount++;
        }
        return clipCount;
    }

    public static void findPointIncludingClips(VideoClip[] clips, ArrayList<VideoClip> result, int point) {
        for (VideoClip clip : clips) {
            if (clip.getStartTime() > point)
                return;
            if (clip.getStartTime() <= point && point < clip.getEndTime())
                result.add(clip);
        }
    }

    public static VideoClip findMaxOccupyClips(ArrayList<VideoClip> clips, int currPoint, int[] lastOccupiedTime, int time) {
        VideoClip maxOccupyClip = clips.get(0);
        int maxOccupyTime = 0;
        for (VideoClip clip : clips) {
            int occupyTime = 0;
            if (lastOccupiedTime[0] == 0 && lastOccupiedTime[1] == 0) {
                occupyTime = clip.getEndTime() - clip.getStartTime();
            } else {
                occupyTime = clip.getEndTime() - lastOccupiedTime[1];
            }

            if (maxOccupyTime < occupyTime) {
                maxOccupyClip = clip;
                maxOccupyTime = occupyTime;
            }
        }
        return maxOccupyClip;
    }
}