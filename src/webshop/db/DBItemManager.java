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

    public boolean addItem(String name, double price, int qty, Item.Category category){
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

            itemQuery.setString(1, name);
            itemPrcQuery.setDouble(1, price);
            itemQtyQuery.setInt(1, qty);
            itemCategoryQuery.setString(1, category.toString());

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

    public boolean update(int id, String name, double price, int qty, Item.Category category){
        try{
            dbManager.getConnection().setAutoCommit(false); //Init transaction

            String updateItemQuery = "UPDATE item SET item.iname = ? WHERE item.iid = ?;";
            String updateItemPrcQuery = "UPDATE item_prc SET item_prc.prc = ? WHERE item_prc.iid = ?;";
            String updateItemQtyQuery = "UPDATE item_qty SET item_qty.qty = ? WHERE item_qty.iid = ?;";
            String updateItemCategoryQuery = "UPDATE item_category SET item_category.category = ? WHERE item_category.iid = ?;";

            PreparedStatement updateItem = dbManager.getConnection().prepareStatement(updateItemQuery);
            PreparedStatement updateItemPrc = dbManager.getConnection().prepareStatement(updateItemPrcQuery);
            PreparedStatement updateItemQty = dbManager.getConnection().prepareStatement(updateItemQtyQuery);
            PreparedStatement updateItemCategory = dbManager.getConnection().prepareStatement(updateItemCategoryQuery);

            updateItem.setString(1, name);
            updateItem.setInt(2, id);
            updateItemPrc.setDouble(1, price);
            updateItemPrc.setInt(2, id);
            updateItemQty.setInt(1, qty);
            updateItemQty.setInt(2, id);
            updateItemCategory.setString(1, category.toString());
            updateItemCategory.setInt(2, id);

            updateItem.execute();
            updateItemPrc.execute();
            updateItemQty.execute();
            updateItemCategory.execute();

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

    public Item findByName(String iname){
        try{
            dbManager.getConnection().setAutoCommit(false); //Init transaction

            String findByIidQuery =
                    "SELECT item.iid, item.iname, item_prc.prc, item_qty.qty, item_category.category\n" +
                            "FROM item\n" +
                            "INNER JOIN item_prc\n" +
                            "ON item.iid = item_prc.iid\n" +
                            "INNER JOIN item_qty\n" +
                            "ON item_prc.iid = item_qty.iid\n" +
                            "INNER JOIN item_category\n" +
                            "ON item_qty.iid = item_category.iid\n" +
                            "WHERE item.iname = ?;";

            PreparedStatement itemByCategoryQuery = dbManager.getConnection().prepareStatement(findByIidQuery);

            itemByCategoryQuery.setString(1, iname);

            ResultSet result = itemByCategoryQuery.executeQuery();

            Item foundItem = null;

            if(result.next()){
                Item.Category cat = null;
                for(Item.Category c : Item.Category.values()){
                    if(c.toString().equals(result.getString("category"))){
                        cat = c;
                        break;
                    }
                }
                foundItem = new Item(result.getInt("iid"), result.getString("iname"), result.getDouble("prc"), result.getInt("qty"), cat);
            }

            dbManager.getConnection().commit();
            return foundItem;
        }catch(SQLException e){
            e.printStackTrace();
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
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

    public List<Item> findAllItems(){
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
                            "ON item_qty.iid = item_category.iid;";

            PreparedStatement findAllItemsQuery = dbManager.getConnection().prepareStatement(findByCategoryQuery);

            ResultSet result = findAllItemsQuery.executeQuery();

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

    public Item findById(int iid){
        try{
            dbManager.getConnection().setAutoCommit(false); //Init transaction

            String findByIidQuery =
                    "SELECT item.iid, item.iname, item_prc.prc, item_qty.qty, item_category.category\n" +
                            "FROM item\n" +
                            "INNER JOIN item_prc\n" +
                            "ON item.iid = item_prc.iid\n" +
                            "INNER JOIN item_qty\n" +
                            "ON item_prc.iid = item_qty.iid\n" +
                            "INNER JOIN item_category\n" +
                            "ON item_qty.iid = item_category.iid\n" +
                            "WHERE item.iid = ?;";

            PreparedStatement itemByCategoryQuery = dbManager.getConnection().prepareStatement(findByIidQuery);

            itemByCategoryQuery.setInt(1, iid);

            ResultSet result = itemByCategoryQuery.executeQuery();

            Item foundItem = null;

            if(result.next()){
                Item.Category cat = null;
                for(Item.Category c : Item.Category.values()){
                    if(c.toString().equals(result.getString("category"))){
                        cat = c;
                        break;
                    }
                }
                foundItem = new Item(result.getInt("iid"), result.getString("iname"), result.getDouble("prc"), result.getInt("qty"), cat);
            }

            dbManager.getConnection().commit();
            return foundItem;
        }catch(SQLException e){
            e.printStackTrace();
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
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

    public static void main(String[] args) {
        DBItemManager itemManager = new DBItemManager();
        itemManager.update(3, "Gitarr", 28000.0, 3, Item.Category.MUSIC);
        /*itemManager.addItem("Biljardbord", 10000.0, 10, Item.Category.SPORTS);
        itemManager.addItem("Biljardkö", 500.0, 20, Item.Category.SPORTS);
        itemManager.addItem("Gitarr", 15000.0, 3, Item.Category.MUSIC);
        itemManager.addItem("Plektrum", 10.0, 300, Item.Category.MUSIC);*/

    }
}
