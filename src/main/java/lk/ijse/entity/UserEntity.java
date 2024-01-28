package lk.ijse.entity;

public class UserEntity {


    private int userId;
    private String username;

    public UserEntity(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
