package services;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import enums.BlockType;
import models.Block;
import models.Document;
import models.EditRequest;
import models.User;

public class DocumentService {
  private BlockingQueue<EditRequest> editQueue;
  private HashMap<String, Document> documentStore;

  public DocumentService() {
    this.editQueue = new LinkedBlockingQueue<>();
    this.documentStore = new HashMap<>();
    startEditProcessingThread();
  }

  private void startEditProcessingThread() {
    Thread editProcessingThread = new Thread(() -> {
      try {
        while (true) {
          EditRequest request = editQueue.take();
          processSingleEditRequest(request);
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
    editProcessingThread.setDaemon(true); // Set as daemon so it doesn't prevent JVM shutdown
    editProcessingThread.start();
  }

  private void processSingleEditRequest(EditRequest request) {
    // Implementation for processing a single edit request
    Document document = request.getDocument();
    User user = request.getUser();
    String content = request.getContent();
    Integer lineNumber = request.getLineNumber();

    System.out.println("Processing edit request from user " + user.getUsername() + " for document "
        + document.getTitle() + " at line " + lineNumber);
    if (document.isValidCollaborator(user)) {
      // Assuming lineNumber is 0-based index
      if (lineNumber >= 0 && lineNumber < document.getContent().size()) {
        Block blockToEdit = document.getContent().get(lineNumber);
        System.out.println("Conflict!!! User " + user.getUsername() + " is editing line " + lineNumber
            + " which currently has content: '" + blockToEdit.getContent() + "'");
        blockToEdit.setContent(content);
      } else {
        // If line number is out of bounds, we can choose to add new empty spaces block
        for (int i = document.getContent().size(); i < lineNumber; i++) {
          Block emptyBlock = new Block(UUID.randomUUID().toString(), BlockType.TEXT, "");
          document.addContent(emptyBlock);
        }
        Block newBlock = new Block(UUID.randomUUID().toString(), BlockType.TEXT, content);
        document.addContent(newBlock);
      }
    } else {
      System.out
          .println("User " + user.getUsername() + " is not a valid collaborator for document " + document.getTitle());
    }
  }

  public String createDocument(String title, User owner) {
    Document document = new Document(title, owner);
    documentStore.put(document.getId(), document);
    return document.getId();
  }

  public void addCollaborator(Document document, User user) {
    // Later we can add validation here to check space or authorization access
    document.addCollaborator(user);
  }

  public void changeTitle(Document document, String newTitle, User user) {
    // Later we can add validation here to check authorization access
    if (document.getOwner() == user && document.getTitle().equals(newTitle) == false) {
      document.changeTitle(newTitle);
    }
  }

  public void addEditRequest(EditRequest editRequest) {
    editQueue.offer(editRequest);
  }

  public Document getDocumentById(String documentId) {
    return documentStore.get(documentId);
  }

  public String renderDocument(Document document) {
    StringBuilder renderedContent = new StringBuilder();
    for (Block block : document.getContent()) {
      renderedContent.append(block.getContent()).append("\n");
    }
    return renderedContent.toString();
  }

  public void waitForAllEditsToProcess() throws InterruptedException {
    // A simple polling loop to wait until the queue is fully empty
    while (!editQueue.isEmpty()) {
      Thread.sleep(100);
    }
    Thread.sleep(100);
  }
}