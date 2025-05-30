public class User {
    private int userID;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String degree;
    private String department;
    private boolean verified;

    public User(int userID, String name, String lastName, String email, String phone, String country,
                String degree, String department, boolean verified) {
        this.userID = userID;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.degree = degree;
        this.department = department;
        this.verified = verified;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getDegree() {
        return degree;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
