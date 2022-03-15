package academy.pocu.comp3500.lab9;

import java.util.ArrayList;
import java.util.Arrays;

public class PyramidBuilder {
    public static int findMaxHeight(final int[] widths, int statue) {
        // 정렬
        Arrays.sort(widths);
        ArrayList<Integer> widthList = new ArrayList<>();   // widths 배열을 복사할 리스트
        ArrayList<Integer> pyramid = new ArrayList<>(); // 각 level의 너비 총합을 저장하는 리스트, 0번 인덱스에 조각상 위치
        pyramid.add(statue);    // 제일 위층에 조각상 올려놓기

        // widths 배열 리스트에 복사
        for (int width : widths) {
            widthList.add(width);
        }

        // pyramid 리스트 채우는 반복문, 한번 반복시마다 level 하나가 채워짐
Loop1:      while (!widthList.isEmpty()) {
            int minStoneNum = pyramid.size() + 1;
            int sumOfLevel = 0;
            ArrayList<Integer> level = new ArrayList<>();
            int priorLevelWidths = pyramid.get(pyramid.size() - 1);

            // widthList에 남아있는 원소가 minStoneNum보다 작을때
            if (minStoneNum > widthList.size()) {
                // 조각상만 있을 때
//                if (pyramid.size() == 1) {
//                    break;
//                }
//                int levelSum = 0;
//                for (Integer element : widthList) {
//                    levelSum += element;
//                }
//                if (levelSum > pyramid.get(pyramid.size() - 1))
//                    pyramid.add(levelSum);
                break;
            }

            // 슬라이딩 윈도우로 level 조건에 맞는 원소 조합 찾기
            int window_start = 0;
            while (sumOfLevel <= priorLevelWidths) {
                if (widthList.size() - 1 < window_start + minStoneNum - 1)  // 슬라이딩 윈도우가 리스트의 범위를 벗어나면 break
                    break Loop1;
                level.clear();
                sumOfLevel = 0;
                for (int i = 0; i < minStoneNum; i++) {
                    level.add(widthList.get(window_start + i));
                    sumOfLevel += widthList.get(window_start + i);
                }
                window_start++;
            }
            // widthList에서 level에 들어간 원소를 제거, pyramid 리스트에 합 추가
            int levelSum = 0;
            for (Integer element : level) {
                levelSum += element;
                widthList.remove(element);
            }
            pyramid.add(levelSum);

        }

        // pyramid print
        for (Integer element : pyramid) {
            System.out.println(element);
        }

        return pyramid.size() - 1;
    }
}