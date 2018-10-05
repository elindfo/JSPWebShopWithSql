package webshop.bl.accounttype;

public class AdminAccount extends Account{

    public AdminAccount(int uid) {
        super(uid);
    }

    @Override
    public String getUserInfo() {
        return String.format("AdminAccount[userId:%d]", getUid());
    }
}
