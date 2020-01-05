package module;

public class PasswordChange {
    private String userAccount;
    private String oldPassword;
    private String newPassword;

    public PasswordChange(String userAccount, String oldPassword, String newPassword) {
        this.userAccount = userAccount;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
