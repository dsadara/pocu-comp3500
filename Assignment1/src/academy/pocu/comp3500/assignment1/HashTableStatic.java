package academy.pocu.comp3500.assignment1;

public class HashTableStatic {

    static void initHashTable(int[] values, int bucketSize) {
        for (int i = 0; i < bucketSize; i++) {
            values[i] = Integer.MIN_VALUE;
        }
    }

    static boolean hasValue(String[] keys, int[] values, int value, int bucketSize) {
        int hashID = keys.hashCode();
        int startIndex = hashID % bucketSize;
        if (startIndex < 0) {
            startIndex += bucketSize;
        }
        int i = startIndex;

        do {
            if (values[i] == value) {
                return true;
            } else if (values[i] == Integer.MIN_VALUE) {
                return false;
            }

            i = (i + 1) % bucketSize;
        } while (i != startIndex);

        return false;
    }

    static boolean add(String[] keys, int[] values, String key, int value, int bucketSize) {
        int hashID = key.hashCode();
        int stardIndex = hashID % bucketSize;
        if (stardIndex < 0) {
            stardIndex += bucketSize;
        }
        int i = stardIndex;

        do {
            if (keys[i] == null || keys[i].equals(key) == true) {
                // 새 키 값을 삽입
                keys[i] = key;
                values[i] = value;
                return true;
            }

//            if (keys[i].equals(key) == true) {
//                return true;
//            }

            i = (i + 1) % bucketSize;
        } while (i != stardIndex);

        return false;
    }

    static int get(String[] keys, int[] values, String key, int bucketSize) {
        int hashID = key.hashCode();
        int stardIndex = hashID % bucketSize;
        if (stardIndex < 0) {
            stardIndex += bucketSize;
        }
        int i = stardIndex;
        int value = Integer.MIN_VALUE;

        do {
            // 없으면 어떡할까

            if (keys[i] == null || keys[i].equals(key) == true) {
                value = values[i];
                break;
            }

            i = (i + 1) % bucketSize;
        } while (i != stardIndex);


        return value;
    }

//    public static void main(String[] args) {
//        int bucketSize = 23;
//        int[] values = new int[23];
//        String[] keys = new String[23];
//
//        initHashTable(values, bucketSize);
//
//        add(keys, values, "Chaehyun", 97, bucketSize);
//        add(keys, values, "Chaehyun", 96, bucketSize);
//        add(keys, values, "Chaehyu", 100, bucketSize);
//        System.out.println(get(keys, values, "Chaehyun", bucketSize));
//        System.out.println(get(keys, values, "Chaehyu", bucketSize));
//
//    }
}
