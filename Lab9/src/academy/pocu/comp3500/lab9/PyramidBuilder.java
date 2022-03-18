package academy.pocu.comp3500.lab9;

import java.util.ArrayList;
import java.util.Arrays;

public class PyramidBuilder {
    public static int findMaxHeight(final int[] widths, int statue) {
        // 정렬
        Arrays.sort(widths);
        ArrayList<Integer> widthList = new ArrayList<>();   // widths 배열을 복사할 리스트
        ArrayList<Integer> pyramid = new ArrayList<>(); // 각 level의 너비 총합을 저장하는 리스트, 0번 인덱스에 동상 위치
        ArrayList<Integer> level = new ArrayList<>();   // 각 level에 들어가는 돌들의 인덱스를 저장하는 임시 리스트
        pyramid.add(1);    // 제일 위층에 동상 올려놓기

        // widths 배열 리스트에 복사
        for (int width : widths) {
            widthList.add(width);
        }

        // 동상 바로 밑층 돌 쌓기
        int sumOfLevel = 0;
        int minStoneNum = 2;
        int endIndex = minStoneNum - 1;
        while (sumOfLevel <= statue) {
            sumOfLevel = 0;
            if (endIndex > widthList.size() - 1) {
                return 0;
            }
            for (int i = 0; i <= endIndex; i++) {
                sumOfLevel += widthList.get(i);
            }
            endIndex++;
        }
        endIndex--;
        pyramid.add(endIndex + 1);
        for (int i = 0; i <= endIndex; i++) {
            widthList.remove(0);
        }

        // 나머지 돌 쌓기
        while (!widthList.isEmpty()) {
            minStoneNum = pyramid.get(pyramid.size() - 1) + 1;  // 위층의 돌 개수보다 많아야 함
            endIndex = minStoneNum - 1;
            if (minStoneNum > widthList.size()) {  // 남아있는 돌의 개수보다, 최소 쌓아야 하는 돌의 개수가더 많으면 쌓지않음
                break;
            }
            pyramid.add(endIndex + 1);
            for (int i = 0; i <= endIndex; i++) {
                widthList.remove(0);
            }
        }

        // pyramid print
        for (Integer element : pyramid) {
            System.out.println(element);
        }

        return pyramid.size() - 1;
    }
}