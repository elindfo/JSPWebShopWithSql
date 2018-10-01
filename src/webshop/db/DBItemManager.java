package webshop.db;

import webshop.bl.Item;

public class DBItemManager {

    private DBManager dbManager;

    public DBItemManager(){
        this.dbManager = DBManager.getInstance();
    }

    public Item findByCategory(){
        return null;
    }

    public Item findById(){
        return null;
    }
}
