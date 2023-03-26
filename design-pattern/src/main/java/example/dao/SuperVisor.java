package example.dao;

/**
 * @author kana-cr
 * @date 2023/3/18
 */

public class SuperVisor {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    private String username;

    private String userId;

    private String userInfo;

}
