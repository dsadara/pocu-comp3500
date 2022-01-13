package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;

public class QuickSort {
    public static void swap(GameStat[] arr, int a, int b) {
        GameStat tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static int partition(GameStat[] arr, int left, int right) {
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j].getPlayerName().hashCode() < arr[right].getPlayerName().hashCode()) {
                i++;
                swap(arr, i, j);
            }
        }

        int pivotpos = i + 1;
        swap(arr, pivotpos, right);

        return pivotpos;
    }

    public static void quicksortRecursive(GameStat[] arr, int left, int right) {
        // 종료조건 left와 right가 같거나 left가 right를 넘어가면 종료
        if (left >= right)
            return;

        int pivot = partition(arr, left, right);

        quicksortRecursive(arr, left, pivot - 1);
        quicksortRecursive(arr, pivot + 1, right);
    }

    public static void quicksort(GameStat[] arr) {
        quicksortRecursive(arr, 0, arr.length - 1);
    }

    public static void printArr(GameStat[] arr) {
        System.out.println("arr = " + java.util.Arrays.toString(arr));
    }
}
