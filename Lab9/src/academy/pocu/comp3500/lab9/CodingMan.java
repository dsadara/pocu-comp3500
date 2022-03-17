package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {
    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        // time만큼의 원소를 갖는 배열 생성
        boolean[] timeArray = new boolean[time];

        // time 안에서 가장 많은 시간을 차지하는 순으로 정렬
        Comparator<VideoClip> comparator = new Comparator<VideoClip>() {
            @Override
            public int compare(VideoClip o1, VideoClip o2) {
                int o1Period = o1.getEndTime() - o1.getStartTime();
                int o2Period = o2.getEndTime() - o2.getStartTime();
                if (o1.getEndTime() > time) {
                    o1Period = time - o1.getStartTime();
                    System.out.println("o1Period : " + o1Period);
                }
                if (o2.getEndTime() > time) {
                    o2Period = time - o2.getStartTime();
                    System.out.println("o2Period : " + o2Period);
                }

                if (o1Period > o2Period) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        Arrays.sort(clips, comparator);

        System.out.println("time :" + time);
        for (VideoClip clip : clips) {
            System.out.println("(" + clip.getStartTime() + ", " + clip.getEndTime() + ")");
        }



        return -1;
    }
}