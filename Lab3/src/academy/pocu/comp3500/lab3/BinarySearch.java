package academy.pocu.comp3500.lab3;

public class BinarySearch {
    public static int binarysearchRecursiveMaxAlt(int[] alts, int l, int r) {
        if (l > r) {    // 찾기 실패한 경우 -1 반환
            return -1;
        }
        if (l == r) {
            if (l == 0)
                return -1;
            if (l == alts.length - 1)
                return -1;
        }
        int m;

        if (l < alts.length / 2 && r < alts.length / 2)     // {4, 5, 4, 3, 2 } 와 같은 케이스 -> l = 0, r = 1인 경우 nullIndexError
            m = (int) Math.ceil((double) (l + r) / 2);
        else
            m = (l + r) / 2;    // 평균구한 후 내림

        if (alts[m - 1] < alts[m] && alts[m] > alts[m + 1]) { // 최고 고도면 m 반환!
            return m;
        } else if (alts[m - 1] > alts[m]) {  // alts[m - 1]이 alts[m]보다 크면 왼쪽으로 고고
            return binarysearchRecursiveMaxAlt(alts, l, m - 1);
        } else {    // 작으면 오른쪽으로 고고
            return binarysearchRecursiveMaxAlt(alts, m + 1, r);
        }
    }

    public static int binarySearchMaxAlt(int[] alts) {
        return binarysearchRecursiveMaxAlt(alts, 0, alts.length - 1);
    }

    public static int binarysearchRecursive(int[] alts, int l, int r, int value) {
//        if (alts.length == 1) { // 원소가 1인경우
//            if (alts[0] == value)
//                return 0;
//            else
//                return -1;
//        }
        if (l > r) {    // 찾기 실패한 경우 - value와 가장 가까운 값을 반환
            return -1;
        }

        int m = (l + r) / 2;    // 내림

        if (alts[m] < value) {  // value가 m보다 오른쪽에 있으면
            return binarysearchRecursive(alts, m + 1, r, value);
        } else if (alts[m] == value) {
            return m;
        } else {    // players[m] > value (value가 m보다 왼쪽에 있으면)
            return binarysearchRecursive(alts, l, m - 1, value);
        }
    }

    public static int binarysearchRecursiveDescend(int[] alts, int l, int r, int value) {
//        if (alts.length == 1) { // 원소가 1인경우
//            if (alts[0] == value)
//                return 0;
//            else
//                return -1;
//        }
        if (l > r) {    // 찾기 실패한 경우 - value와 가장 가까운 값을 반환
            return -1;
        }

        int m = (l + r) / 2;    // 내림

        if (alts[m] > value) {  // value가 m보다 오른쪽에 있으면
            return binarysearchRecursiveDescend(alts, m + 1, r, value);
        } else if (alts[m] == value) {
            return m;
        } else {    // players[m] > value (value가 m보다 왼쪽에 있으면)
            return binarysearchRecursiveDescend(alts, l, m - 1, value);
        }
    }

    public static int binarySearch(int[] alts, int value) {
        return binarysearchRecursive(alts, 0, alts.length - 1, value);
    }
}
