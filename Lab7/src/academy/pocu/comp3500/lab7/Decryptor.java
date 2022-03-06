package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {

    private String[] codeWords;
    private HashMap<String, ArrayList<String>> wordHash = new HashMap<String, ArrayList<String>>();

    public Decryptor(String[] codeWords) {
        this.codeWords = codeWords;
        codeWordsToHashMap(this.codeWords);
    }


    public static String selectionSort(String word) {
        char[] codeArray = word.toLowerCase().toCharArray();

        for (int i = 0; i < codeArray.length - 1; i++) {
            int minIndex = i;
            for (int j = i; j < codeArray.length; j++) {
                if (codeArray[minIndex] > codeArray[j]) {
                    minIndex = j;
                }
            }
            // swap
            char tmp = codeArray[i];
            codeArray[i] = codeArray[minIndex];
            codeArray[minIndex] = tmp;
        }

        String str = new String(codeArray);
        System.out.println(str);
        return str;
    }

    public void codeWordsToHashMap(String[] codeWords) {
        for (String codeWord : codeWords) {
            String sortedCode = selectionSort(codeWord);
            ArrayList<String> hashValue = wordHash.get(sortedCode);
            if (hashValue == null) {
                ArrayList<String> tmp = new ArrayList<String>();
                tmp.add(codeWord.toLowerCase());
                wordHash.put(sortedCode, tmp);
            } else {
                hashValue.add(codeWord.toLowerCase());
            }
        }
        System.out.println(wordHash);
    }

    public String[] findCandidates(String word) {
        String sortedWord = selectionSort(word);
        ArrayList<String> hashValue = wordHash.get(sortedWord);
        if (hashValue == null) {
            return new String[0];
        }

        String[] result = hashValue.toArray(new String[0]);
        return result;
    }

}
