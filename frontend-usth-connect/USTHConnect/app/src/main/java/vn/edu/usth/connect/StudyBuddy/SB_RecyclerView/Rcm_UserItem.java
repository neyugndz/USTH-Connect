package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView;

public class Rcm_UserItem {

    private String studentId;
    private String name;
    private String gender;
    private String major;
    private String looking_for;

    private int image;

    public Rcm_UserItem(String studentId, String name, String gender, String major, String looking_for, int image) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.looking_for = looking_for;
        this.image = image;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
