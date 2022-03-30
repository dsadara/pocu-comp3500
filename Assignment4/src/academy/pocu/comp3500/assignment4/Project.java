package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.*;

import academy.pocu.comp3500.assignment4.SCC;

public final class Project {
    private ArrayList<Task> tasks;
    private ArrayList<Task> trTasks;

    public Project(final Task[] tasks) {
        this.tasks = new ArrayList<Task>(Arrays.asList(tasks));
        excludeMaintenanceCycle(tasks);
    }

    private void excludeMaintenanceCycle(Task[] taskArray) {

        // find Strongly Connected Component
        ArrayList<LinkedList<Task>> SCCList = SCC.findSCC(tasks);
        trTasks = SCC.taskListTranposed;
        // remove SCC
        for (LinkedList<Task> SCC : SCCList) {
            for (Task element : SCC) {
                for (int i = 0; i < tasks.size(); i++) {
                    if (element.getTitle().equals(tasks.get(i).getTitle())) {
//                        taskArray[i] = null;
                        tasks.remove(i);
                        break;
                    }
                }
//                tasks.removeIf(n -> (n.getTitle().equals(task.getTitle())));
            }
        }
        // also remove SCC in transposed tasks
        for (LinkedList<Task> SCC : SCCList) {
            for (Task element : SCC) {
                for (int i = 0; i < trTasks.size(); i++) {
                    if (element.getTitle().equals(trTasks.get(i).getTitle())) {
//                        taskArray[i] = null;
                        trTasks.remove(i);
                        break;
                    }
                }
//                trTasks.removeIf(n -> (n.getTitle().equals(task.getTitle())));
            }
        }
        // remove SCC element predecessor
        for (LinkedList<Task> SCC : SCCList) {
            for (Task element : SCC) {
                for (Task task : trTasks) {
//                    ArrayList<Task> predecessors = new ArrayList<>(task.getPredecessors());
                    List<Task> predecessors = task.getPredecessors();
                    for (int i = 0; i < predecessors.size(); i++) {
                        if(element.getTitle().equals(predecessors.get(i).getTitle())) {
                            task.deletePredecessor(i);
                        }
                    }
                }
            }
        }
    }

    public int findTotalManMonths(final String task) {
        // task를 만나면 재귀를 종료하는 위상정렬 실시
        int[] totalManMonth = {0};
        HashMap<String, Task> discovered = new HashMap<>();

        for (Task node : trTasks) {
            if (discovered.containsKey(node.getTitle())) {
                continue;
            }

            topologicalSortRecursive(discovered, node, totalManMonth, task);
            discovered.remove(task);
        }
        
        return totalManMonth[0];
    }

    public static boolean topologicalSortRecursive(HashMap<String, Task> discovered, Task node, int[] totalManMonth, String task) {
        discovered.put(node.getTitle(), node);

        for (Task predecessor : node.getPredecessors()) {
            if (discovered.containsKey(predecessor.getTitle())) {
                continue;
            }

            if (node.getTitle().equals(task) || topologicalSortRecursive(discovered, predecessor, totalManMonth, task)) {
                totalManMonth[0] += node.getEstimate();
                return true;
            }
        }
        
        return false;
    }

    public int findMinDuration(final String task) {
        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }
}