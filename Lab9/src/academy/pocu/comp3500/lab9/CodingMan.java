package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {


    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (time == 0)
            return -1;
        boolean[] timeArray = new boolean[time];
        Comparator<VideoClip> clipComparator = (VideoClip clip1, VideoClip clip2) -> Integer.compare(clip1.getStartTime(), clip2.getStartTime());
        Arrays.sort(clips, clipComparator);
//        ArrayList<VideoClipBoxed> clipsBoxed = new ArrayList<>();
//        for (VideoClip clip : clips) {
//            clipsBoxed.add(new VideoClipBoxed(clip, time));
//        }
//        Comparator<VideoClipBoxed> clipComparator1 = (VideoClipBoxed clip1, VideoClipBoxed clip2) -> Integer.compare(clip1.getOccupyTime(), clip2.getOccupyTime());
//        Comparator<VideoClipBoxed> clipComparator2 = (VideoClipBoxed clip1, VideoClipBoxed clip2) -> Integer.compare(clip1.getVideoClip().getStartTime(), clip2.getVideoClip().getStartTime());
//        System.out.println("{{{{before array}}}}");
//        for (VideoClipBoxed clip : clipsBoxed) {
//            System.out.println("(" + clip.getVideoClip().getStartTime() +", "+ clip.getVideoClip().getEndTime() + ")");
//        }
//        clipsBoxed.sort(clipComparator1.reversed());
//        System.out.println("{{{{after array}}}}");
//        for (VideoClipBoxed clip : clipsBoxed) {
//            System.out.println("(" + clip.getVideoClip().getStartTime() +", "+ clip.getVideoClip().getEndTime() + ")");
//        }
//        clipsBoxed.sort(clipComparator2);
//        System.out.println("{{{{after array2}}}}");
//        for (VideoClipBoxed clip : clipsBoxed) {
//            System.out.println("(" + clip.getVideoClip().getStartTime() +", "+ clip.getVideoClip().getEndTime() + ")");
//        }
        int currPoint = 0;
        int nextPoint = 0;
        int clipCount = 0;
        int i = 0;
        while (currPoint < time) {
            ArrayList<VideoClip> currPointIncludingClip = new ArrayList<>();
            findPointIncludingClips(clips, currPointIncludingClip, currPoint);
            if (currPointIncludingClip.isEmpty())   // currPoint를 커버할 클립이 없으면 -1 반환
                return -1;
            VideoClip selectedVideoClip = findMaxOccupyClips(currPointIncludingClip, timeArray);
            for (int j = selectedVideoClip.getStartTime(); j < selectedVideoClip.getEndTime(); j++) {
                if (j > timeArray.length - 1)
                    break;
                timeArray[j] = true;
            }
            currPoint = selectedVideoClip.getEndTime() + 1;
            clipCount++;
//            if (clipsBoxed.get(i).getVideoClip().getStartTime() == currPoint) {
//                nextPoint = clipsBoxed.get(i).getVideoClip().getEndTime();
//                clipCount++;
//            }
//            else {
//                return -1;
//            }
//            while (clipsBoxed.get(i).getVideoClip().getStartTime() == currPoint) {
//                i++;
//            }
        }
        return clipCount;
    }

    public static void findPointIncludingClips(VideoClip[] clips, ArrayList<VideoClip> result, int point) {
        for (VideoClip clip : clips) {
            if (clip.getStartTime() <= point && point <= clip.getEndTime())
                result.add(clip);
        }
    }

    public static VideoClip findMaxOccupyClips(ArrayList<VideoClip> clips, boolean[] timeArray) {
        VideoClip maxOccupyClip = clips.get(0);
        int maxOccupyTime = 0;
        for (VideoClip clip : clips) {
            int occupyTime = 0;
            for (int i = clip.getStartTime(); i < clip.getEndTime(); i++) {
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