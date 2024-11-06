import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndexSearch {

    static class DocumentReader {
        public static List<String> readDocuments(List<String> filePaths) {
            List<String> documents = new ArrayList<>();
            for (String filePath : filePaths) {
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        documents.add(line.trim());
                    }
                } catch (IOException e) {
                    System.err.println("Error: The file at path " + filePath + " was not found.");
                }
            }
            return documents;
        }
    }

    static class Tokenizer {
        public static List<String> tokenize(String text) {
            return Arrays.asList(text.toLowerCase().split("\\s+"));
        }
    }

    static class InvertedIndex {
        private final Map<String, Set<Integer>> index = new HashMap<>();

        public void addDocument(int docId, String content) {
            List<String> tokens = Tokenizer.tokenize(content);
            for (String token : tokens) {
                index.computeIfAbsent(token, k -> new HashSet<>()).add(docId);
            }
        }

        public Set<Integer> search(Set<String> queries) {
            if (queries.isEmpty()) {
                return Collections.emptySet();
            }
            Set<Integer> resultDocIds = null;
            for (String query : queries) {
                Set<Integer> docIds = index.get(query);
                if (docIds == null) {
                    return Collections.emptySet();
                }
                if (resultDocIds == null) {
                    resultDocIds = new HashSet<>(docIds);
                } else {
                    resultDocIds.retainAll(docIds);
                }
            }
            return resultDocIds != null ? resultDocIds : Collections.emptySet();
        }
    }

    static class DocumentRetriever {
        private final InvertedIndex invertedIndex;
        private final List<String> documents;

        public DocumentRetriever(InvertedIndex invertedIndex, List<String> documents) {
            this.invertedIndex = invertedIndex;
            this.documents = documents;
        }

        public void retrieveDocuments(Set<String> queries) {
            Set<Integer> docIds = invertedIndex.search(queries);
            System.out.println("Documents matching queries " + queries + ":");
            for (int docId : docIds) {
                System.out.println("Document " + docId + ": " + documents.get(docId));
            }
        }
    }

    public static void main(String[] args) {
        List<String> filePaths = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        List<String> documents = DocumentReader.readDocuments(filePaths);
        if (documents.isEmpty()) {
            System.out.println("No documents to process.");
            return;
        }
        InvertedIndex invertedIndex = new InvertedIndex();
        for (int i = 0; i < documents.size(); i++) {
            invertedIndex.addDocument(i, documents.get(i));
        }
        DocumentRetriever retriever = new DocumentRetriever(invertedIndex, documents);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your query terms separated by spaces:");
        String input = scanner.nextLine();
        Set<String> queries = new HashSet<>(Tokenizer.tokenize(input));
        retriever.retrieveDocuments(queries);
    }
}
