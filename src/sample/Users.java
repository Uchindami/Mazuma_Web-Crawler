package sample;

public class Users {
    private int id;
    private String username;
    private String location;
    private String phoneNumber;
    private String job;
    private String password;

    public Users(int id, String username, String location, String phoneNumber, String job, String password) {
        this.id = id;
        this.username = username;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getJob() {
        return job;
    }

    public String getPassword() {
        return password;
    }
}