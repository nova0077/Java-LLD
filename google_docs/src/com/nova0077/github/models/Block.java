package models;

import enums.BlockType;

public class Block {
  private String blockId;
  private BlockType type;
  private String content;

  public Block(String blockId, BlockType type, String content) {
    this.blockId = blockId;
    this.type = type;
    this.content = content;
  }

  // Getters and setters
  public String getBlockId() {
    return blockId;
  }
  public BlockType getType() {
    return type;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public void setType(BlockType type) {
    this.type = type;
  }
}
