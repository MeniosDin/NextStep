import java.util.ArrayList;
import java.util.List;

public class Program {
    private int programID;
    private String title;
    private String location;
    private String duration;
    private int tuition;
    private int opportunityScore;
    private String type;
    private List<String> reviews = new ArrayList<>(); // ✅ ΝΕΟ

    public Program(int programID, String title, String location, String duration,
                   int tuition, int opportunityScore, String type) {
        this.programID = programID;
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.tuition = tuition;
        this.opportunityScore = opportunityScore;
        this.type = type;
    }

    public int getProgramID() { return programID; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDuration() { return duration; }
    public int getTuition() { return tuition; }
    public int getOpportunityScore() { return opportunityScore; }
    public String getType() { return type; }

    public int getStarRating() {
        int rating = Math.round(opportunityScore / 20.0f);
        if (rating < 1) rating = 0;
        if (rating > 5) rating = 5;
        return rating;
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public List<String> getReviews() {
        return reviews;
    }

 

    
}

