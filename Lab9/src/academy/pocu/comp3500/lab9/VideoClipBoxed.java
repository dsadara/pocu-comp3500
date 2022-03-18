package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

public class VideoClipBoxed {
    private VideoClip videoClip;
    private int occupyTime;


    public VideoClipBoxed(VideoClip videoClip, int time) {
        if (time < videoClip.getEndTime() - 1) {
            this.occupyTime = time - videoClip.getStartTime();
            this.videoClip = new VideoClip(videoClip.getStartTime(), time);
        } else {
            this.occupyTime = videoClip.getEndTime() - videoClip.getStartTime();
            this.videoClip = videoClip;
        }
    }

    public void setOccupyTime(int occupyTime) {
        this.occupyTime = occupyTime;
    }

    public void setVideoClip(VideoClip videoClip) {
        this.videoClip = videoClip;
    }

    public int getOccupyTime() {
        return occupyTime;
    }

    public VideoClip getVideoClip() {
        return videoClip;
    }

}
