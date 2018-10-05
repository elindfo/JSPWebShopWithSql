package webshop.bl.accounttype;

public class UserAccount extends Account{

    public UserAccount(int uid) {
        super(uid);
    }

    @Override
    public String getUserInfo() {
        return String.format("UserAccount[userId:%d]", getUid());
    }
}
