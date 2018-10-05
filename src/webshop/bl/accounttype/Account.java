package webshop.bl.accounttype;

public abstract class Account {

    private int uid;

    protected Account(int uid){
        this.uid = uid;
    }

    public int getUid(){
        return uid;
    }

    public abstract String getUserInfo();

}
