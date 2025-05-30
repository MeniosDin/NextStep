import java.time.LocalDate;

public class Review {
    private int reviewID;
    private User user;
    private Program program;
    private int rating;
    private String comment;
    private LocalDate date;

    public Review(int reviewID, User user, Program program, int rating, String comment) {
        this.reviewID = reviewID;
        this.user = user;
        this.program = program;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDate.now();
    }

    public int getReviewID() { return reviewID; }
    public User getUser() { return user; }
    public Program getProgram() { return program; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDate getDate() { return date; }
}