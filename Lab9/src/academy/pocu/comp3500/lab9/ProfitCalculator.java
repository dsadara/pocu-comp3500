package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ProfitCalculator {
    public static int findMaxProfit(final Task[] tasks, final int[] skillLevels) {
        // early return if task is empty
        if (tasks.length == 0 || tasks == null) {
            return 0;
        }
        // tasks difficulty 기준으로 정렬
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getDifficulty() - o2.getDifficulty();
            }
        };
        Arrays.sort(tasks, comparator);
        // profit / difficulty 리스트
        ArrayList<Double> profitDiffRatio = new ArrayList<>();
        for (Task task : tasks) {
            profitDiffRatio.add((double) task.getProfit() / task.getDifficulty());
        }

        int sumOfProfit = 0;
        for (int skillLevel : skillLevels) {
            int i = 0;
            int difficulty = tasks[i].getDifficulty();

            // index 0에서 difficulty를 충족하지 못하면, 이 employee는 아무것도 안시킴
            if (skillLevel < difficulty)
                continue;

            // 현재 skillLevel이 감당 가능한 최대 Difficulty  찾기
            int maxDiffIndex = 0;
            while (i < tasks.length)  {
                difficulty = tasks[i].getDifficulty();
                if (skillLevel < difficulty) {  // 종료조건: 해당 직원이 해당 task의 difficulty를 감당 못하면
                    break;
                }
                maxDiffIndex = i;
                i++;
            }

            // 최대 Profit Difficulty Ratio 찾기
            double maxProfitDiffRatio = profitDiffRatio.get(0);
            int maxProfitDiffRatioIndex = 0;
            i = 0;
            while (i < tasks.length) {
                difficulty = tasks[i].getDifficulty();
                if (skillLevel < difficulty) {  // 종료조건: 해당 직원이 해당 task의 difficulty를 감당 못하면
                    break;
                }
                if (maxProfitDiffRatio < profitDiffRatio.get(i)) {
                    maxProfitDiffRatio = profitDiffRatio.get(i);
                    maxProfitDiffRatioIndex = i;
                }
                i++;
            }

            // 찾은 max값들 비교
            int maxDiff = tasks[maxDiffIndex].getProfit();
            int maxProfit = tasks[maxProfitDiffRatioIndex].getProfit();
            sumOfProfit += maxProfit > maxDiff ? maxProfit : maxDiff;
        }
        return sumOfProfit;
    }
}