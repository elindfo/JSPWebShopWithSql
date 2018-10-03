package webshop.db;

import webshop.bl.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public boolean addItem(int id, String name, double price, int qty, Item.Category category){
        return addItem(new Item(id, name, price, qty, category));
    }

    public List<Item> findByCategory(Item.Category category){
        try{
            dbManager.getConnection().setAutoCommit(false); //Init transaction

            String findByCategoryQuery =
                    "SELECT item.iid, item.iname, item_prc.prc, item_qty.qty, item_category.category\n" +
                            "FROM item\n" +
                            "INNER JOIN item_prc\n" +
                            "ON item.iid = item_prc.iid\n" +
                            "INNER JOIN item_qty\n" +
                            "ON item_prc.iid = item_qty.iid\n" +
                            "INNER JOIN item_category\n" +
                            "ON item_qty.iid = item_category.iid\n" +
                            "WHERE item_category.category = ?;";

            PreparedStatement itemByCategoryQuery = dbManager.getConnection().prepareStatement(findByCategoryQuery);

            itemByCategoryQuery.setString(1, category.toString());

            ResultSet result = itemByCategoryQuery.executeQuery();

            List<Item> foundItems = new ArrayList<>();

            while(result.next()){
                Item.Category cat = null;
                for(Item.Category c : Item.Category.values()){
                    if(c.toString().equals(result.getString("category"))){
                        cat = c;
                        break;
                    }
                }
                foundItems.add(new Item(result.getInt("iid"), result.getString("iname"), result.getDouble("prc"), result.getInt("qty"), cat));
            }

            dbManager.getConnection().commit();
            return foundItems;
        }catch(SQLException e){
            e.printStackTrace();
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return new ArrayList<>();
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

    public Item findById(){
        return null;
    }

    public static void main(String[] args) {
        DBItemManager itemManager = new DBItemManager();
        List<Item> items = itemManager.findByCategory(Item.Category.SPORTS);
        for(Item i : items){
            System.out.println(i.toString());
        }
    }
}
