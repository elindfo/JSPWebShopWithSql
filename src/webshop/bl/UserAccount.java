package webshop.bl;

public class UserAccount {

    private int uid;
    private String uname;
    private int level;

    public UserAccount(int uid, String uname, int level){
        this.uid = uid;
        this.uname = uname;
        this.level = level;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString(){
        return String.format("User[uid:%d, uname:%s, level:%d]", uid, uname, level);
    }
}
