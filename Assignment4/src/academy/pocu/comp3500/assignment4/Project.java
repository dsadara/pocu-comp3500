package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.Arrays;

public final class Project {
    private ArrayList<Task> tasks;

    public Project(final Task[] tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
        excludeMaintenanceCycle();
    }

    private void excludeMaintenanceCycle() {

        // find Strongly Connected Component

        //
    }

    public int findTotalManMonths(final String task) {
        // 유지보수 사이클 없애기

        // task를 만나면 재귀를 종료하는 위상정렬 실시

        return -1;
    }

    public int findMinDuration(final String task) {
        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }
}