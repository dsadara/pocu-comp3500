package academy.pocu.comp3500.lab3;

public class BinarySearch {
    public static int binarysearchRecursive(int[] alts, int l, int r) {
        if (l > r) {    // 찾기 실패한 경우 -1 반환
            return -1;
        }
        if (l == r) {
            if (l == 0)
                return -1;
            if (l == alts.length - 1)
                return -1;
        }

        int m = (l + r) / 2;    // 평균구한 후 내림

        if (alts[m - 1] < alts[m] && alts[m] > alts[m + 1]) { // 최고 고도면 m 반환!
            return m;
        }
        else if (alts[m - 1] > alts[m]) {  // alts[m - 1]이 alts[m]보다 크면 왼쪽으로 고고
            return binarysearchRecursive(alts, l, m - 1);
        } else {    // 작으면 오른쪽으로 고고
            return binarysearchRecursive(alts, m + 1, r);
        }
    }

    public static int binarySearch(int[] alts) {
        return binarysearchRecursive(alts, 0, alts.length - 1);
    }
}