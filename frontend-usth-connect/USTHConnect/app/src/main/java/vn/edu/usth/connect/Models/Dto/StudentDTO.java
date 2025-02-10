package vn.edu.usth.connect.Models.Dto;

public class StudentDTO {
    private String id;
    private String password;
    private String phone;

    public StudentDTO(){

    }
    public StudentDTO(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
