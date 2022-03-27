package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.lang.reflect.Array;
import java.util.*;

public class Project {
    public static List<String> findSchedule(Task[] tasks, boolean includeMaintenance) {
        ArrayList<Task> taskList = new ArrayList<>(Arrays.asList(tasks));
        ArrayList<String>[] adjMatrix = makeAdjacencyMatrix(taskList);
//        printAdjacency(adjMatrix, tasks);
        ArrayList<String>[] trMatrix = transposeAdjacencyMatrix(adjMatrix, taskList);
//        printAdjacency(trMatrix, tasks);
        ArrayList<Task> trTaskList = createTransposedTasks(trMatrix, taskList);

        // SCC 찾기
        ArrayList<LinkedList<Task>> stronglyConnectedComponents = findSCC(taskList, trTaskList);
        // SCC를 간단히 표현한 새로운 tasks 생성

        // 위상정렬수행 1.includeMaintenance == true 2. false 인 경우
        ArrayList<String> schedule;
        if (includeMaintenance == false) {
            schedule = sortTopologicallyMFalse(trTaskList, stronglyConnectedComponents);
        } else {    // includeMaintenance == true
            schedule = sortTopologicallyMTrue(trTaskList, stronglyConnectedComponents);
//            for (LinkedList<Task> SCC : stronglyConnectedComponents) {
//                for (int i = 0; i < schedule.size(); i++) {
//                    if (SCC.getFirst().getTitle().equals(schedule.get(i))) {
//
//                    }
//                }
//            }
        }
        // 결과 schedule에 대입

        return schedule;
    }

    public static ArrayList<String>[] makeAdjacencyMatrix(ArrayList<Task> tasks) {
        ArrayList<String>[] adjacencyMatrix = new ArrayList[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            adjacencyMatrix[i] = new ArrayList<String>();
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
                        adjacencyMatrix[tasks.indexOf(next)].add(predecessor.getTitle());

                        // dfs logic
                        stack.push(predecessor);
                        discovered.put(predecessor.getTitle(), predecessor);
                    } else {    // if predecessor is discovered
                        // just add to adj matrix
                        adjacencyMatrix[tasks.indexOf(next)].add(predecessor.getTitle());
                    }
                }
            }
        }


        return adjacencyMatrix;
    }

    public static void printAdjacency(ArrayList<String>[] adj, Task[] tasks) {
        System.out.println("###PRINT START###");
        for (int i = 0; i < adj.length; i++) {
            System.out.println("node " + tasks[i].getTitle() + "has...");
            for (String task1 : adj[i]) {
                System.out.println(task1);
            }
        }

    }

    public static ArrayList<String>[] transposeAdjacencyMatrix(ArrayList<String>[] adjMatrix, ArrayList<Task> taskList) {
        ArrayList<String>[] transposedMatrix = new ArrayList[taskList.size()];
        for (int i = 0; i < adjMatrix.length; i++) {
            transposedMatrix[i] = new ArrayList<String>();
        }


        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].size(); j++) {
                transposedMatrix[indexOfTitle(taskList, adjMatrix[i].get(j))].add(taskList.get(i).getTitle());
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

    public static int indexOfTitle2(Task[] tasks, String title) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].getTitle().equals(title))
                return i;
        }
        return -1;
    }

    public static ArrayList<Task> createTransposedTasks(ArrayList<String>[] trMatrix, ArrayList<Task> tasks) {
        ArrayList<Task> trTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            trTasks.add(new Task(tasks.get(i).getTitle(), tasks.get(i).getEstimate()));
        }
        for (int i = 0; i < tasks.size(); i++) {
            for (String matrix : trMatrix[i]) {
                trTasks.get(i).addPredecessor(trTasks.get(indexOfTitle(trTasks, matrix)));
            }
        }
        return trTasks;
    }

    //  코사라주 알고리즘을 이용하여 Strongly Connected Component들을 찾는 함수
    public static ArrayList<LinkedList<Task>> findSCC(ArrayList<Task> taskList, ArrayList<Task> taskListTranposed) {
        LinkedList<Task> sortedList = sortTopologically(taskListTranposed);
        ArrayList<LinkedList<Task>> SCCList = new ArrayList<LinkedList<Task>>();

        // taskList의 Task로 대체
//        for (Task task : sortedList) {
//            sortedList.addLast(taskList.get(indexOfTitle(taskList, task.getTitle())));
//            sortedList.remove(task);
//        }
        LinkedList<Task> sortedListOriginalTask = new LinkedList<>();
        Iterator<Task> iterator = sortedList.listIterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
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

    public static ArrayList<String> sortTopologicallyMFalse(ArrayList<Task> taskListTransposed, ArrayList<LinkedList<Task>> SCCList) {
        HashMap<String, Task> discovered = new HashMap<>();
        ArrayList<String> schedule = new ArrayList<>();

        for (Task task : taskListTransposed) {
            if (discovered.containsKey(task.getTitle()) || isInSCCList(task, SCCList)) {
                continue;
            }
            topologicalSortMFalseRecursive(task, discovered, schedule, SCCList);
        }
        return schedule;
    }

    public static void topologicalSortMFalseRecursive(Task task, HashMap<String, Task> discovered, ArrayList<String> schedule, ArrayList<LinkedList<Task>> SCCList) {
        discovered.put(task.getTitle(), task);

        for (Task predecessor : task.getPredecessors()) {
            if (discovered.containsKey(predecessor.getTitle())) {
                continue;
            }
            if (!isInSCCList(predecessor, SCCList)) {
                topologicalSortMFalseRecursive(predecessor, discovered, schedule, SCCList);
            }
        }

        schedule.add(0, task.getTitle());
    }

    public static ArrayList<String> sortTopologicallyMTrue(ArrayList<Task> taskListTransposed, ArrayList<LinkedList<Task>> SCCList) {
        HashMap<String, Task> discovered = new HashMap<>();
        ArrayList<String> schedule = new ArrayList<>();

        for (Task task : taskListTransposed) {
            if (discovered.containsKey(task.getTitle())) {
                continue;
            }
//            if (isInSCCFirstOfList(task, SCCList) || !isInSCCList(task, SCCList)) {
                topologicalSortMTrueRecursive(task, discovered, schedule, SCCList);
//            }
        }
        return schedule;
    }

    public static void topologicalSortMTrueRecursive(Task task, HashMap<String, Task> discovered, ArrayList<String> schedule, ArrayList<LinkedList<Task>> SCCList) {
        discovered.put(task.getTitle(), task);

        for (Task predecessor : task.getPredecessors()) {
            if (discovered.containsKey(predecessor.getTitle())) {
                continue;
            }
//            if (isInSCCFirstOfList(predecessor, SCCList) || !isInSCCList(predecessor, SCCList)) {
                topologicalSortMTrueRecursive(predecessor, discovered, schedule, SCCList);
//            }
        }

        schedule.add(0, task.getTitle());
    }

    public static boolean isInSCCList(Task predecessor, ArrayList<LinkedList<Task>> SCCList) {
        for (LinkedList<Task> tasks : SCCList) {
            for (Task task : tasks) {
                if (task.getTitle().equals(predecessor.getTitle()))
                    return true;
            }

        }
        return false;
    }

    public static boolean isInSCCFirstOfList(Task predecessor, ArrayList<LinkedList<Task>> SCCList) {
        for (LinkedList<Task> tasks : SCCList) {
                if (tasks.getFirst().getTitle().equals(predecessor.getTitle()))
                    return true;
        }
        return false;
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

//    public static ArrayList<Task> TransposeGraph(ArrayList<Task> taskList) {
//        ArrayList<Task> tasks = new ArrayList<>();
//
//        for (Task task : taskList) {
//
//        }
//
//        return tasks;
//    }
//
//    public static void graphTransposeRecursive() {
//
//    }


}
