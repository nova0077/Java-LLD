package models;

public class EditRequest {
  private Document document;
  private User user;
  private String content;
  private Integer lineNumber;

  // Note: EditRequest object cannot be edited after creation, so no setters are provided. This is to ensure that the edit request remains immutable once created.
  public EditRequest(Document document, User user, String content, Integer lineNumber) {
    this.document = document;
    this.user = user;
    this.content = content;
    this.lineNumber = lineNumber;
  }

  // Getters
  public Document getDocument() {
    return document;
  }

  public User getUser() {
    return user;
  }

  public String getContent() {
    return content;
  }

  public Integer getLineNumber() {
    return lineNumber;
  } 
}