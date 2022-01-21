package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

public class QuickSortPlayer {
    public static void swap(Player[] arr, int a, int b) {
        Player tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static int partitionAssists(Player[] arr, int left, int right) {
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j].getAssistsPerGame() > arr[right].getAssistsPerGame()) {
                i++;
                swap(arr, i, j);
            }
        }

        int pivotpos = i + 1;
        swap(arr, pivotpos, right);

        return pivotpos;
    }

    public static void quicksortRecursiveAssists(Player[] arr, int left, int right) {
        // 종료조건 left와 right가 같거나 left가 right를 넘어가면 종료
        if (left >= right) return;

        int pivot = partitionAssists(arr, left, right);

        quicksortRecursiveAssists(arr, left, pivot - 1);
        quicksortRecursiveAssists(arr, pivot + 1, right);
    }

    public static void quicksortAssists(Player[] arr) {
        quicksortRecursiveAssists(arr, 0, arr.length - 1);
    }

    public static int partitionPasses(Player[] arr, int left, int right) {
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j].getPassesPerGame() > arr[right].getPassesPerGame()) {
                i++;
                swap(arr, i, j);
            }
        }

        int pivotpos = i + 1;
        swap(arr, pivotpos, right);

        return pivotpos;
    }

    public static void quicksortRecursivePasses(Player[] arr, int left, int right) {
        // 종료조건 left와 right가 같거나 left가 right를 넘어가면 종료
        if (left >= right) return;

        int pivot = partitionPasses(arr, left, right);

        quicksortRecursivePasses(arr, left, pivot - 1);
        quicksortRecursivePasses(arr, pivot + 1, right);
    }

    public static void quicksortPasses(Player[] arr, int k) {
        quicksortRecursivePasses(arr, 0, k - 1);
    }
}
