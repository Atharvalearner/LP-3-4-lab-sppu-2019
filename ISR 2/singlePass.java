import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class singlePass {
    static Map<String, Float>[] documents = new HashMap[5];
    static Map<String, Float>[] cluster = new HashMap[5];
    static int[][] clusters = new int[5][5];
    static int noOfClusters = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader stdInpt = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number of Documents:");
        int noOfDocuments = Integer.parseInt(stdInpt.readLine());
        System.out.println("Enter the threshold:");
        float threshold = Float.parseFloat(stdInpt.readLine());

        String[] doc = new String[noOfDocuments];
        for (int i = 0; i < noOfDocuments; i++) {
            System.out.println("Enter the Document Name:");
            doc[i] = stdInpt.readLine();
        }

        for (int i = 0; i < noOfDocuments; i++) {
            documents[i] = new HashMap<>();
            cluster[i] = new HashMap<>();
            try (BufferedReader fin = new BufferedReader(new FileReader("E:\\atharva\\LP-3\\ISR 2\\" + doc[i] + ".txt"))) {
                String line;
                while ((line = fin.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line);
                    while (st.hasMoreTokens()) {
                        String str = st.nextToken();
                        float value = Float.parseFloat(st.nextToken());
                        documents[i].put(str, value);
                    }
                }
            }
        }

        singlePassAlgorithm(noOfDocuments, threshold);
        search(stdInpt);
    }

    private static void search(BufferedReader stdInpt) throws IOException {
        System.out.println("\nDo you want to enter any query? (yes/no)");
        String ch = stdInpt.readLine();

        if ("yes".equalsIgnoreCase(ch)) {
            System.out.println("Enter the query:");
            String query = stdInpt.readLine();

            for (int i = 0; i < noOfClusters; i++) {
                if (cluster[i].containsKey(query)) {
                    System.out.println("Query found in cluster " + (i + 1));
                }
            }
        }
    }

    private static void singlePassAlgorithm(int noOfDocuments, float threshold) {
        cluster[0] = new HashMap<>(documents[0]);
        clusters[0][0] = 1;
        clusters[0][1] = 0;

        for (int i = 1; i < noOfDocuments; i++) {
            float maxSimilarity = -1;
            int bestCluster = -1;

            for (int j = 0; j < noOfClusters; j++) {
                float similarity = calculateSimilarity(documents[i], cluster[j]);
                if (similarity > threshold && similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    bestCluster = j;
                }
            }

            if (bestCluster == -1) {
                clusters[noOfClusters][0] = 1;
                clusters[noOfClusters][1] = i;
                noOfClusters++;
                cluster[noOfClusters - 1] = new HashMap<>(documents[i]);
            } else {
                clusters[bestCluster][0]++;
                clusters[bestCluster][clusters[bestCluster][0]] = i;
                updateClusterRepresentative(documents[i], cluster[bestCluster]);
            }
        }

        printClusters();
    }

    private static void printClusters() {
        for (int i = 0; i < noOfClusters; i++) {
            System.out.println("Cluster no " + i);
            for (Map.Entry<String, Float> entry : cluster[i].entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }

        for (int i = 0; i < noOfClusters; i++) {
            System.out.print("\n" + i + "\t");
            for (int j = 1; j <= clusters[i][0]; j++) {
                System.out.print(" " + clusters[i][j]);
            }
        }
    }

    private static void updateClusterRepresentative(Map<String, Float> doc, Map<String, Float> clust) {
        for (Map.Entry<String, Float> entry : doc.entrySet()) {
            String term = entry.getKey();
            float freq = entry.getValue();

            clust.merge(term, freq, (oldFreq, newFreq) -> (oldFreq + newFreq) / 2);
        }
    }
    private static float calculateSimilarity(Map<String, Float> doc, Map<String, Float> clust) {
        int commonTerms = 0;
        for (String term : doc.keySet()) {
            if (clust.containsKey(term)) {
                commonTerms++;
            }
        }
        return 2 * commonTerms;
    }
}
