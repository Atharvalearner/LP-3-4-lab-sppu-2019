import java.io.*;
import java.util.*;

public class Conflation {
    public static void main(String[] args) {
        try {
            File inputFile = new File("ISR 1\\Input.txt");
            Scanner fileScanner = new Scanner(inputFile);
            Scanner inputScanner = new Scanner(System.in);
            int choice;

            do {
                displayMenu();
                choice = inputScanner.nextInt();

                switch (choice) {
                    case 1:
                        displayFileContents(fileScanner);
                        break;
                    case 2:
                        removePunctuationAndStopWords(inputFile);
                        break;
                    case 3:
                        suffixStripping();
                        break;
                    case 4:
                        frequencyCount();
                        break;
                }
            } while (choice != 0);
            inputScanner.close();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println(
                " 1. Display the file \n 2. Remove Stop Words \n 3. Suffix Stripping \n 4. Count Frequency \n 0. Exit \n Enter your choice: ");
    }

    private static void displayFileContents(Scanner scanner) {
        while (scanner.hasNext()) {
            System.out.print(scanner.next() + " ");
        }
        System.out.println();
    }

    private static void removePunctuationAndStopWords(File file) throws IOException {
        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "is", "and", "of", "are", "for", "in", "can", "be",
                "as", "a", "with", "an", "that", "on", "from", "into"));
        try (
        Scanner fileScanner = new Scanner(file);
        BufferedWriter writer = new BufferedWriter(new FileWriter("without_punctuation_and_stopwords.txt"))) {
            while (fileScanner.hasNext()) {
                String word = fileScanner.next().replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
                if (!stopWords.contains(word) && !word.isEmpty()) {
                    writer.write(word + " ");
                }
            }
        }

        System.out.println("File after punctuation and stopwords:");
        displayFile("without_punctuation_and_stopwords.txt");
    }

    private static void suffixStripping() throws IOException {
        try (Scanner scanner = new Scanner(new File("without_punctuation_and_stopwords.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("suffix_stripping2.txt"))) {

            while (scanner.hasNext()) {
                String word = scanner.next();
                word = stripSuffix(word);
                writer.write(word + " ");
            }
        }

        System.out.println("File after suffix stripping:");
        displayFile("suffix_stripping2.txt");
    }

    private static String stripSuffix(String word) {
        // Add suffix stripping rules here
        String[] suffixes = { "ier", "ied", "iage", "iest", "ies", "iful", "ify", "iness", "ness", "ily", "yer", "ying",
                "ys", "yable", "yful", "al", "ly", "ing", "ed", "es", "s", "is", "ment", "eing", "led", "lex", "ling" };
        for (String suffix : suffixes) {
            if (word.endsWith(suffix)) {
                word = word.substring(0, word.length() - suffix.length());
                break;
            }
        }
        return word.trim();
    }

    private static void frequencyCount() throws IOException {
        Map<String, Integer> wordCount = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("suffix_stripping.txt"))) {
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void displayFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}