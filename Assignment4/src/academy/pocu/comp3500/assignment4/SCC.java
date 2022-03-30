package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class SCC {
    static ArrayList<Task> taskListTranposed;


    public static ArrayList<LinkedList<Task>> findSCC(ArrayList<Task> taskList) {
        ArrayList<ArrayList<String>> adjMatrix = makeAdjacencyMatrix(taskList);
        ArrayList<ArrayList<String>> trMatrix = transposeAdjacencyMatrix(adjMatrix, taskList);
        taskListTranposed = createTransposedTasks(trMatrix, taskList);


        LinkedList<Task> sortedList = sortTopologically(taskListTranposed);
        ArrayList<LinkedList<Task>> SCCList = new ArrayList<>();


        LinkedList<Task> sortedListOriginalTask = new LinkedList<>();
        for (Task task : sortedList) {
            sortedListOriginalTask.add(taskList.get(indexOfTitle(taskList, task.getTitle())));
        }


        HashMap<String, Task> discovered = new HashMap<>();
        for (Task task : sortedListOriginalTask) {
            if (discovered.containsKey(task.getTitle())) {
                continue;
            }
            LinkedList<Task> SCCElement = new LinkedList<>();
            topologicalSortRecursive(task, discovered, SCCElement);
            if (SCCElement.size() > 1)
                SCCList.add(SCCElement);
        }

        return SCCList;
    }

    public static ArrayList<ArrayList<String>> makeAdjacencyMatrix(ArrayList<Task> tasks) {
        ArrayList<ArrayList<String>> adjacencyMatrix = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            adjacencyMatrix.add(new ArrayList<String>());
        }

        Stack<Task> stack = new Stack<>();
        HashMap<String, Task> discovered = new HashMap<>();

        // do dfs on every node
        for (Task task : tasks) {
            if (!discovered.containsKey(task.getTitle())) {
                stack.push(task);
                discovered.put(task.getTitle(), task);
            }
            while (!stack.isEmpty()) {
                Task next = stack.pop();

                for (Task predecessor : next.getPredecessors()) {
                    if (!discovered.containsKey(predecessor.getTitle())) {
                        // add to adjMatrix
//                        adjacencyMatrix[tasks.indexOf(next)].add(predecessor.getTitle());
                        adjacencyMatrix.get(tasks.indexOf(next)).add(predecessor.getTitle());

                        // dfs logic
                        stack.push(predecessor);
                        discovered.put(predecessor.getTitle(), predecessor);
                    } else {    // if predecessor is discovered
                        // just add to adj matrix
//                        adjacencyMatrix[tasks.indexOf(next)].add(predecessor.getTitle());
                        adjacencyMatrix.get(tasks.indexOf(next)).add(predecessor.getTitle());
                    }
                }
            }
        }


        return adjacencyMatrix;
    }

    public static ArrayList<ArrayList<String>> transposeAdjacencyMatrix(ArrayList<ArrayList<String>> adjMatrix, ArrayList<Task> taskList) {
        ArrayList<ArrayList<String>> transposedMatrix = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            transposedMatrix.add(new ArrayList<String>());
        }


        for (int i = 0; i < adjMatrix.size(); i++) {
            for (int j = 0; j < adjMatrix.get(i).size(); j++) {
                transposedMatrix.get(indexOfTitle(taskList, adjMatrix.get(i).get(j))).add(taskList.get(i).getTitle());
            }
        }

        return transposedMatrix;
    }

    public static int indexOfTitle(ArrayList<Task> tasks, String title) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equals(title))
                return i;
        }
        return -1;
    }


    public static ArrayList<Task> createTransposedTasks(ArrayList<ArrayList<String>> trMatrix, ArrayList<Task> tasks) {
        ArrayList<Task> trTasks = new ArrayList<>();
        for (Task task : tasks) {
            trTasks.add(new Task(task.getTitle(), task.getEstimate()));
        }
        for (int i = 0; i < tasks.size(); i++) {
            for (String matrix : trMatrix.get(i)) {
                trTasks.get(i).addPredecessor(trTasks.get(indexOfTitle(trTasks, matrix)));
            }
        }
        return trTasks;
    }

    public static LinkedList<Task> sortTopologically(ArrayList<Task> taskList) {
        HashMap<String, Task> discovered = new HashMap<>();
        LinkedList<Task> sortedList = new LinkedList<>();
        for (Task task : taskList) {
            if (discovered.containsKey(task.getTitle())) {
                continue;
            }

            topologicalSortRecursive(task, discovered, sortedList);
        }

        return sortedList;
    }

    public static void topologicalSortRecursive(Task task, HashMap<String, Task> discovered, LinkedList<Task> linkedList) {
        discovered.put(task.getTitle(), task);

        for (Task predecessor : task.getPredecessors()) {
            if (discovered.containsKey(predecessor.getTitle())) {
                continue;
            }

            topologicalSortRecursive(predecessor, discovered, linkedList);
        }

        linkedList.addFirst(task);
    }
}
