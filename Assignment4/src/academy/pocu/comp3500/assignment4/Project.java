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
                        if (element.getTitle().equals(predecessors.get(i).getTitle())) {
//                            task.deletePredecessor(i);
                            task.getPredecessors().remove(i);
                        }
                    }
                }
            }
        }

        for (LinkedList<Task> SCC : SCCList) {
            for (Task element : SCC) {
                for (Task task : tasks) {
                    List<Task> predecessors = task.getPredecessors();
                    for (int i = 0; i < predecessors.size(); i++) {
                        if (element.getTitle().equals(predecessors.get(i).getTitle())) {
//                            task.deletePredecessor(i);
                            task.getPredecessors().remove(i);
                        }
                    }
                }
            }
        }
    }

    public int findTotalManMonths(final String task) {
        // task를 만나면 재귀를 종료하는 위상정렬 실시
        int[] totalManMonth = {0};

//        for (Task node : trTasks) {
//            if (discovered.containsKey(node.getTitle())) {
//                continue;
//            }
//
//            topologicalSortRecursive(discovered, node, totalManMonth, task);
//            discovered.remove(task);
//        }

        Task mileStone = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equals(task)) {
                mileStone = tasks.get(i);
            }
        }
        if (mileStone == null)
            return -1;


        // milestone에서부터 DFS
        Stack<Task> stack = new Stack<>();
        stack.push(mileStone);
        HashMap<String, Task> discovered = new HashMap<>();
        while (!stack.isEmpty()) {
            Task next = stack.pop();
            discovered.put(next.getTitle(), next);
            // visit
            totalManMonth[0] += next.getEstimate();
            List<Task> predecessors = next.getPredecessors();
            for (Task predecessor : predecessors) {
                if (!discovered.containsKey(predecessor.getTitle())) {
                    stack.push(predecessor);
                }
            }
        }

        return totalManMonth[0];
    }

//    public static boolean topologicalSortRecursive(HashMap<String, Task> discovered, Task node, int[] totalManMonth, String task) {
//        discovered.put(node.getTitle(), node);
//
//        for (Task predecessor : node.getPredecessors()) {
//            if (discovered.containsKey(predecessor.getTitle())) {
//                continue;
//            }
//
//            if (node.getTitle().equals(task) || topologicalSortRecursive(discovered, predecessor, totalManMonth, task)) {
//                totalManMonth[0] += node.getEstimate();
//                return true;
//            }
//        }
//
//        return false;
//    }

    public int findMinDuration(final String task) {
        int[] maxDuration = {0};
        Task mileStone = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equals(task)) {
                mileStone = tasks.get(i);
            }
        }
        if (mileStone == null)
            return -1;
        
        List<Task> predecessors = mileStone.getPredecessors();
        HashMap<String, Task> discovered = new HashMap<>();
        int[] duration = {0};
        System.out.println("start");
        for (Task predecessor : predecessors) {
             duration[0] = mileStone.getEstimate();

            dfsRecursive(predecessor, duration, maxDuration, discovered);
            System.out.println("duration : " + duration[0]);
//            if (duration[0] > maxDuration)
//                maxDuration = duration[0];
        }
        
        return maxDuration[0];
    }
    
    public void dfsRecursive(Task node, int[] duration, int[] maxDuration, HashMap<String, Task> discovered) {
//        discovered.put(node.getTitle(), node);
        duration[0] += node.getEstimate();
        if (duration[0] > maxDuration[0])
            maxDuration[0] = duration[0];

        List<Task> predecessors = node.getPredecessors();
        for (Task predecessor : predecessors) {
//            if (!discovered.containsKey(predecessor.getTitle())) {
                dfsRecursive(predecessor, duration, maxDuration, discovered);
                duration[0] -= predecessor.getEstimate();
//            }
        }
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }
}