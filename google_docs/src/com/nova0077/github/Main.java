import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import models.Document;
import models.EditRequest;
import models.User;
import services.DocumentService;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    // Create users
    User user1 = new User("alice", "Alice");
    User user2 = new User("jane_smith", "Jane Smith");

    // Create a document
    DocumentService documentService = new DocumentService();
    String docId = documentService.createDocument("My First Document", user1);

    // Retrieve the document from the store
    Document document = documentService.getDocumentById(docId);

    // Add a collaborator
    documentService.addCollaborator(document, user2);

    int numberOfConcurrentUsers = 2;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfConcurrentUsers);
    CountDownLatch latch = new CountDownLatch(1);

    executorService.submit(() -> {
      try {
        System.out.println("User1 (Alice) is ready to edit.");
        latch.await();
        EditRequest requestA = new EditRequest(document, user1, "Alice's Override!", 0);
        EditRequest requestB = new EditRequest(document, user2, "Alice's second line", 2);
        documentService.addEditRequest(requestB);
        documentService.addEditRequest(requestA);
        System.out.println("User1 (Alice) submitted an edit request.");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    executorService.submit(() -> {
      try {
        System.out.println("User2 (Jane) is ready to edit.");
        latch.await();
        EditRequest requestB = new EditRequest(document, user2, "Jane's Override!", 0);
        documentService.addEditRequest(requestB);
        System.out.println("User2 (Jane) submitted an edit request.");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread.sleep(1000); // Simulate some delay before starting edits
    System.out.println("Both users are ready. Starting concurrent edits...");
    latch.countDown();

    executorService.shutdown();
    executorService.awaitTermination(2, TimeUnit.SECONDS);

    // Print the updated content of the document
    documentService.waitForAllEditsToProcess(); // Ensure all edits are processed before rendering
    System.out.println("\n--- Final Document State ---");
    System.out.println(documentService.renderDocument(document));
  }
}