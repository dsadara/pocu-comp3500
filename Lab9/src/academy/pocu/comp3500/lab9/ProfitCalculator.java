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

            ArrayList<Task> availTasks = new ArrayList<>();

            // 현재 skillLevel이 수행 가능한 tasks들 리스트에 담기
            int maxDiffIndex = 0;
            while (i < tasks.length) {
                difficulty = tasks[i].getDifficulty();
                if (skillLevel < difficulty) {  // 종료조건: 해당 직원이 해당 task의 difficulty를 감당 못하면
                    break;
                }
                availTasks.add(tasks[i]);
                i++;
            }

            // 수행 가능한 task 중 profit이 가장 큰 것 찾기
            int maxProfit = availTasks.get(0).getProfit();
            for (i = 0; i < availTasks.size(); i++) {
                if (maxProfit < availTasks.get(i).getProfit())
                    maxProfit = availTasks.get(i).getProfit();
            }

            // sumOfProfit에 추가
            sumOfProfit += maxProfit;
        }
        return sumOfProfit;
    }
}