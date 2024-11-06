import java.util.HashSet;
import java.util.Set;

public class PrecisionRecallCalculator {

    public static void main(String[] args) {

        Set<String> relevantDocs = new HashSet<>();

        relevantDocs.add("d123");

        relevantDocs.add("d84");

        relevantDocs.add("d56");

        relevantDocs.add("d6");

        relevantDocs.add("d8");

        relevantDocs.add("d9");

        relevantDocs.add("d511");

        relevantDocs.add("d129");

        relevantDocs.add("d187");

        relevantDocs.add("d25");

        relevantDocs.add("d38");

        relevantDocs.add("d48");

        relevantDocs.add("d250");

        relevantDocs.add("d0");


        String[] retrievedDocsSequence = {"d123", "d84", "d56", "d6", "d8", "d9", "d511","d129", "d187", "d25", "d38", "d48", "d250", "d0"};

        Set<String> retrievedDocs = new HashSet<>();  

        System.out.printf("%-60s | %-5s | %-5s | %-22s | %-20s%n", "Documents", "|R ∩ A|", "|A|", "Precision = R ∩ A / A", "Recall = R ∩ A / R");

        System.out.println("-----------------------------------------------------------------------------------------------");


        for (int i = 0; i < retrievedDocsSequence.length; i++) {

            retrievedDocs.add(retrievedDocsSequence[i]);


            Set<String> intersection = new HashSet<>(retrievedDocs);

            intersection.retainAll(relevantDocs);


            int numRelevantRetrieved = intersection.size(); // |R ∩ A|

            int numRetrieved = retrievedDocs.size(); // |A|

            int numRelevant = relevantDocs.size(); // |R|

            double precision = numRetrieved > 0 ? (double) numRelevantRetrieved / numRetrieved * 100 : 0;

            double recall = numRelevant > 0 ? (double) numRelevantRetrieved / numRelevant * 100 : 0;
            System.out.printf("%-60s | %-5d | %-5d | %-22.2f | %-20.2f%n", String.join(",", retrievedDocs), numRelevantRetrieved, numRetrieved, precision, recall);

        }

    }

}