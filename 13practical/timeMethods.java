import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class timeMethods {

    // Node
    static class Node {
        int key;
        String data;

        Node(int k, String d) {
            key = k;
            data = d;
        }
    }

    // Main
    public static void main(String[] args) throws Exception {

        Node[] array = loadFile("ulysses.numbered");
        int n = array.length;

        int repetitions = 30;
        Random rand = new Random();

        double linearTime = 0, linearTime2 = 0;
        double binaryTime = 0, binaryTime2 = 0;

        for (int r = 0; r < repetitions; r++) {

            int key = rand.nextInt(32654) + 1;

            //Linear Search
            long start = System.currentTimeMillis();
            linearSearch(array, key);
            long finish = System.currentTimeMillis();

            double time = finish - start;
            linearTime += time;
            linearTime2 += time * time;

            //Binary Search Timing
            start = System.currentTimeMillis();
            binarySearch(array, key);
            finish = System.currentTimeMillis();

            time = finish - start;
            binaryTime += time;
            binaryTime2 += time * time;
        }

        // Notable statistics
        double aveLinear = linearTime / repetitions;
        double stdLinear =
                Math.sqrt(linearTime2 - repetitions * aveLinear * aveLinear)
                        / (repetitions - 1);

        double aveBinary = binaryTime / repetitions;
        double stdBinary =
                Math.sqrt(binaryTime2 - repetitions * aveBinary * aveBinary)
                        / (repetitions - 1);

        DecimalFormat fiveD = new DecimalFormat("0.00000");
        DecimalFormat fourD = new DecimalFormat("0.0000");

        System.out.println("\n==================== RESULTS ====================");
        System.out.println("Linear Average Time (ms): " + fiveD.format(aveLinear));
        System.out.println("Linear Standard Deviation:     " + fourD.format(stdLinear));
        System.out.println();
        System.out.println("Binary Average Time (ms): " + fiveD.format(aveBinary));
        System.out.println("Binary Standard Deviation:     " + fourD.format(stdBinary));
        System.out.println("=================================================");
        System.out.println("Array size n = " + n);
    }

    //File loaded into one array
    static Node[] loadFile(String filename) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<Node> list = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ", 2);
            int key = Integer.parseInt(parts[0]);
            String data = parts[1];
            list.add(new Node(key, data));
        }

        br.close();
        return list.toArray(new Node[0]);
    }

    // Linear Search O(n)
    static Node linearSearch(Node[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].key == key)
                return arr[i];
        }
        return null;
    }

    // Binary Search O(log n)
    static Node binarySearch(Node[] arr, int key) {

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].key == key)
                return arr[mid];
            else if (arr[mid].key < key)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return null;
    }
}