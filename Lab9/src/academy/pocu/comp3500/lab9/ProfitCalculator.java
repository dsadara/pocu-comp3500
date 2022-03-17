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
            // find max difficulty task and max profit and Difficulty ratio
            int i = 0;
            int difficulty = tasks[i].getDifficulty();
            if (skillLevel < difficulty)
                continue;
            double maxProfitDiffRatio = profitDiffRatio.get(0);
            int maxProfitDiffRatioIndex = 0;
            int maxDiffIndex = 0;
            while (i < tasks.length - 1)  {
                i++;
                difficulty = tasks[i].getDifficulty();
                if (difficulty <= skillLevel) {
                    maxDiffIndex = i;
                }
                if (skillLevel >= difficulty && maxProfitDiffRatio < profitDiffRatio.get(i)) {
                    maxProfitDiffRatio = profitDiffRatio.get(i);
                    maxProfitDiffRatioIndex = i;
                }

            }
//            if (i > 0)
//                i--;
            int maxDiff = tasks[maxDiffIndex].getProfit();
            int maxProfit = tasks[maxProfitDiffRatioIndex].getProfit();
            sumOfProfit += maxProfit > maxDiff ? maxProfit : maxDiff;
        }
        return sumOfProfit;
    }
}