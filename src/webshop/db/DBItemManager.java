package webshop.db;

import webshop.bl.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBItemManager {

    private DBManager dbManager;

    public DBItemManager(){
        this.dbManager = DBManager.getInstance();
    }

    public boolean addItem(Item item){
        try{
            dbManager.getConnection().setAutoCommit(false); //Init transaction

            String insertItemQuery = "INSERT INTO item (iname) VALUES(?);";
            String insertItemPrcQuery = "INSERT INTO item_prc (iid, prc) VALUES(LAST_INSERT_ID(), ?);";
            String insertItemQtyQuery = "INSERT INTO item_qty (iid, qty) VALUES(LAST_INSERT_ID(), ?);";
            String insertItemCategoryQuery = "INSERT INTO item_category (iid, category) VALUES(LAST_INSERT_ID(), ?)";

            PreparedStatement itemQuery = dbManager.getConnection().prepareStatement(insertItemQuery);
            PreparedStatement itemPrcQuery = dbManager.getConnection().prepareStatement(insertItemPrcQuery);
            PreparedStatement itemQtyQuery = dbManager.getConnection().prepareStatement(insertItemQtyQuery);
            PreparedStatement itemCategoryQuery = dbManager.getConnection().prepareStatement(insertItemCategoryQuery);

            itemQuery.setString(1, item.getName());
            itemPrcQuery.setDouble(1, item.getPrice());
            itemQtyQuery.setInt(1, item.getQty());
            itemCategoryQuery.setString(1, item.getCategory().toString());

            itemQuery.execute();
            itemPrcQuery.execute();
            itemQtyQuery.execute();
            itemCategoryQuery.execute();

            dbManager.getConnection().commit();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }finally{
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Item> findByCategory(){
        return null;
    }

    public Item findById(){
        return null;
    }

    public static void main(String[] args) {
        DBItemManager itemManager = new DBItemManager();
        itemManager.addItem(new Item("Biljardbord", 5000.0, 3, Item.Category.SPORTS));
    }
}
