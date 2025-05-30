public class Application {
    private User applicant;
    private Program program;
    private String motivation;

    public Application(User applicant, Program program, String motivation) {
        this.applicant = applicant;
        this.program = program;
        this.motivation = motivation;
    }

    public User getApplicant() {
        return applicant;
    }

    public Program getProgram() {
        return program;
    }

    public String getMotivation() {
        return motivation;
    }
}
