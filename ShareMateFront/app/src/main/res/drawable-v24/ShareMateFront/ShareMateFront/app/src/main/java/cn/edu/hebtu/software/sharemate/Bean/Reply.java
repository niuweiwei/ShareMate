package cn.edu.hebtu.software.sharemate.Bean;

public class Reply {
    private int replyId;
    private int commentId;
    private int image;
    private String name;
    private String content;
    private String time;
    private int countZan;

    public Reply() {
    }

    public Reply(int replyId, int commentId, int image, String name, String content, String time, int countZan) {
        this.replyId=replyId;
        this.commentId = commentId;
        this.image = image;
        this.name = name;
        this.content = content;
        this.time = time;
        this.countZan = countZan;
    }

    public int getReplyId() { return replyId; }

    public void setReplyId(int replyId) { this.replyId = replyId; }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountZan() {
        return countZan;
    }

    public void setCountZan(int countZan) {
        this.countZan = countZan;
    }
}
