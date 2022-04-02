package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;
import java.util.List;


public final class Project {
    private ArrayList<Task> tasks;
    private ArrayList<Task> trTasks;
    private Task[] taskArray;

    public Project(final Task[] tasks) {
        this.taskArray = tasks;
//        this.tasks = new ArrayList<Task>(Arrays.asList(tasks));
//        excludeMaintenanceCycle(tasks);
    }

    private void excludeMaintenanceCycle(Task[] taskArray) {

        // find Strongly Connected Component
        ArrayList<LinkedList<Task>> sccList = SCC.findSCC(tasks);
        trTasks = SCC.taskListTranposed;

        // make SCC hashMap
        HashMap<String, Task> sccHashMap = new HashMap<>();
        for (LinkedList<Task> scc : sccList) {
            for (Task element : scc) {
                sccHashMap.put(element.getTitle(), element);
            }
        }

        // remove SCC
        Iterator<Task> iter = tasks.iterator();
        while (iter.hasNext()) {
            Task next = iter.next();
            if (sccHashMap.containsKey(next.getTitle()))
                iter.remove();
        }

//                for (int i = 0; i < tasks.size(); i++) {
//                    if (element.getTitle().equals(tasks.get(i).getTitle())) {
//                        tasks.remove(i);
//                        break;
//                    }
//                }

//        // also remove SCC in transposed tasks
//        for (LinkedList<Task> SCC : SCCList) {
//            for (Task element : SCC) {
//                for (int i = 0; i < trTasks.size(); i++) {
//                    if (element.getTitle().equals(trTasks.get(i).getTitle())) {
//                        trTasks.remove(i);
//                        break;
//                    }
//                }
//            }
//        }
//
//        // replace transposed taskList to new instance without SCC
//        ArrayList<Task> trTasksNoSCC = new ArrayList<>();
//        for (Task task : trTasks) {
//             trTasksNoSCC.add(new Task(task.getTitle(), task.getEstimate()));
//        }
//        HashMap<String, Task> trTaskHashMap = new HashMap<>();
//        for (Task task : trTasksNoSCC) {
//            trTaskHashMap.put(task.getTitle(), task);
//        }
//
//        ListIterator<Task> trTaskListIterator = trTasks.listIterator();
//        while (trTaskListIterator.hasNext()) {
//            Task task = trTaskListIterator.next();
//            Iterator<Task> predIter = task.getPredecessors().iterator();
//            LinkedList<String> predecessorTitles = new LinkedList<>();
//            while (predIter.hasNext()) {
//                Task pred = predIter.next();
//                predecessorTitles.add(pred.getTitle());
//            }
//            Iterator<String> titleIter = predecessorTitles.iterator();
//            while (titleIter.hasNext()) {
//                String pred = titleIter.next();
//                for (LinkedList<Task> SCC : SCCList) {
//                    for (Task element : SCC) {
//                        if (element.getTitle().equals(pred)) {
//                            titleIter.remove();
//                        }
//                    }
//                }
//            }
//            Task taskNoSCCPred = trTaskHashMap.get(task.getTitle());
//            for (String predecessorTitle : predecessorTitles) {
//                taskNoSCCPred.addPredecessor(trTaskHashMap.get(predecessorTitle));
//            }
//        }
//        trTasks = trTasksNoSCC;
//
//        // replace taskList to new instance without SCC
//        ArrayList<Task> tasksNoSCC = new ArrayList<>();
//        for (Task task : tasks) {
//            tasksNoSCC.add(new Task(task.getTitle(), task.getEstimate()));
//        }
//        HashMap<String, Task> taskHashMap = new HashMap<>();
//        for (Task task : tasksNoSCC) {
//            taskHashMap.put(task.getTitle(), task);
//        }
//
//        ListIterator<Task> taskListIterator = tasks.listIterator();
//        while (taskListIterator.hasNext()) {
//            Task task = taskListIterator.next();
//            Iterator<Task> predIter = task.getPredecessors().iterator();
//            LinkedList<String> predecessorTitles = new LinkedList<>();
//            while (predIter.hasNext()) {
//                Task pred = predIter.next();
//                predecessorTitles.add(pred.getTitle());
//            }
//            Iterator<String> titleIter = predecessorTitles.iterator();
//            while (titleIter.hasNext()) {
//                String pred = titleIter.next();
//                for (LinkedList<Task> SCC : SCCList) {
//                    for (Task element : SCC) {
//                        if (element.getTitle().equals(pred)) {
//                            titleIter.remove();
//                        }
//                    }
//                }
//            }
//            Task taskNoSCCPred = taskHashMap.get(task.getTitle());
//            for (String predecessorTitle : predecessorTitles) {
//                taskNoSCCPred.addPredecessor(taskHashMap.get(predecessorTitle));
//            }
//        }
//        tasks = tasksNoSCC;
    }

    public int findTotalManMonths(final String task) {
        int[] totalManMonth = {0};

        Task mileStone = null;
        for (int i = 0; i < taskArray.length; i++) {
            if (taskArray[i].getTitle().equals(task)) {
                mileStone = taskArray[i];
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
            // visit
            totalManMonth[0] += next.getEstimate();
            List<Task> predecessors = next.getPredecessors();
            for (Task predecessor : predecessors) {
                if (!discovered.containsKey(predecessor.getTitle())) {
                    stack.push(predecessor);
                    discovered.put(predecessor.getTitle(), next);
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
        Task mileStone = null;
        for (int i = 0; i < taskArray.length; i++) {
            if (taskArray[i].getTitle().equals(task)) {
                mileStone = taskArray[i];
            }
        }
        if (mileStone == null)
            return -1;

        List<Task> predecessors = mileStone.getPredecessors();
        HashMap<String, Task> discovered = new HashMap<>();
        int[] duration = {mileStone.getEstimate()};
        int[] maxDuration = {mileStone.getEstimate()};
        System.out.println("start");
        for (Task predecessor : predecessors) {
            duration[0] = mileStone.getEstimate();

            dfsRecursive(predecessor, duration, maxDuration, discovered);
            System.out.println("duration : " + duration[0]);
        }

        return maxDuration[0];
    }

    public void dfsRecursive(Task node, int[] duration, int[] maxDuration, HashMap<String, Task> discovered) {
        duration[0] += node.getEstimate();
        if (duration[0] > maxDuration[0])
            maxDuration[0] = duration[0];

        List<Task> predecessors = node.getPredecessors();
        for (Task predecessor : predecessors) {
            dfsRecursive(predecessor, duration, maxDuration, discovered);
            duration[0] -= predecessor.getEstimate();
        }
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }
}