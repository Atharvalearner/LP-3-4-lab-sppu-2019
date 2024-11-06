import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ISR5 {
    public static void main(String[] args) {
        String[] Rq = {"d3", "d5", "d9", "d25", "d39", "d44", "d56", "d71", "d89", "d123"};
        String[] A = {"d123", "d84", "d56", "d6", "d8", "d9", "d511", "d129", "d187", "d25", "d38", "d48", "d250", "d113", "d3"};

        float modRq = Rq.length;
        String[] Ra = new String[A.length];
        float[] P = new float[A.length];
        float[] R = new float[A.length];
        float modRa = 0;
        float modA = 0;
        double precision;
        double recall;

        try (PrintWriter write = new PrintWriter(new FileWriter("Recall_Precision_Evaluation_output.txt"))) {
  
            System.out.printf("%-98s | %-8s | %-8s | %-15s | %-10s |\n", "Documents", "|Ra|", "|A|", "Precision(%)", "Recall(%)");
            write.printf("%-98s | %-8s | %-8s | %-15s | %-10s |\n", "Documents", "|Ra|", "|A|", "Precision(%)", "Recall(%)");

            System.out.println("-".repeat(45 * 3 + 11));
            write.println("-".repeat(45 * 3 + 11));
            for (int i = 0; i < A.length; i++) {
                Ra[i] = A[i];
                modA++;
                for (int j = 0; j < Rq.length; j++) {
                    if (A[i].equals(Rq[j])) {
                        modRa++;
                        break;
                    }
                }
                precision = (modRa / modA) * 100;
                P[i] = (float) (precision / 100);
                recall = (modRa / modRq) * 100;
                R[i] = (float) (recall / 100);

                String docs = printDocs(Ra, Ra.length);
                System.out.printf("%-98s | %-10.2f | %-10.2f | %-13.2f | %-10.2f |\n", docs, modRa, modA, precision, recall);
                write.printf("%-98s | %-10.2f | %-10.2f | %-13.2f | %-10.2f |\n", docs, modRa, modA, precision, recall);
            }

            System.out.println("-".repeat(45 * 3 + 11));
            write.println("-".repeat(45 * 3 + 11));

            Scanner sc = new Scanner(System.in);
            int j;
            do {
                System.out.printf("Harmonic mean and E-value\nEnter value of j (0 - %d) to find F(j) and E(j):\n", A.length - 1);
                j = sc.nextInt();
            } while (j >= Ra.length);

            float Fj = (2 * P[j] * R[j]) / (P[j] + R[j]);
            System.out.println("-".repeat(30));
            System.out.printf("| Harmonic mean (F%d) is: | %.2f |\n", j, Fj);
            write.println("-".repeat(30));
            write.printf("| Harmonic mean (F%d) is: | %.2f |\n", j, Fj);
            write.println("-".repeat(30));

            System.out.println("-".repeat(34));
            System.out.printf("| %-32s |\n", "E-Value");
            write.println("-".repeat(34));
            write.printf("| %-32s |\n", "E-Value");

            System.out.printf("| %-10s | %-10s | %-10s |\n", "b>1", "b=0", "b<1");
            write.printf("| %-10s | %-10s | %-10s |\n", "b>1", "b=0", "b<1");

            System.out.printf("| %-10.2f | %-10.2f | %-10.2f |\n", E_value(1.1f, R[j], P[j]), E_value(0, R[j], P[j]), E_value(0.9f, R[j], P[j]));
            write.printf("| %-10.2f | %-10.2f | %-10.2f |\n", E_value(1.1f, R[j], P[j]), E_value(0, R[j], P[j]), E_value(0.9f, R[j], P[j]));

            System.out.println("-".repeat(34));
            write.println("-".repeat(34));
        } catch (IOException e) {
            System.out.println("An error occurred while writing the output file.");
        }
    }
    public static String left(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
    public static String printDocs(String[] state, int size) {
        StringBuilder result = new StringBuilder();
        result.append("| ");
        for (int i = 0; i < size; i++) {
            if (state[i] != null && !state[i].isEmpty()) {
                result.append(state[i]);
                if (i + 1 < size && state[i + 1] != null && !state[i + 1].isEmpty()) {
                    result.append(", ");
                }
            }
        }
        return left(result.toString(), 98);
    }
    public static float E_value(float b, float rj, float pj) {
        return 1 - (((1 + b * b) * rj * pj) / (b * b * pj + rj));
    }
}
