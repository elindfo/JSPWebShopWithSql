package webshop.db;

import webshop.bl.Item;
import webshop.bl.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBItemManager {

    private DBItemManager(){}

    public static boolean addItem(String name, double price, int qty, Item.Category category){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

            String insertItemQuery = "INSERT INTO item (iname) VALUES(?);";
            String insertItemPrcQuery = "INSERT INTO item_prc (iid, prc) VALUES(LAST_INSERT_ID(), ?);";
            String insertItemQtyQuery = "INSERT INTO item_qty (iid, qty) VALUES(LAST_INSERT_ID(), ?);";
            String insertItemCategoryQuery = "INSERT INTO item_category (iid, category) VALUES(LAST_INSERT_ID(), ?)";

            PreparedStatement itemQuery = DBManager.getConnection().prepareStatement(insertItemQuery);
            PreparedStatement itemPrcQuery = DBManager.getConnection().prepareStatement(insertItemPrcQuery);
            PreparedStatement itemQtyQuery = DBManager.getConnection().prepareStatement(insertItemQtyQuery);
            PreparedStatement itemCategoryQuery = DBManager.getConnection().prepareStatement(insertItemCategoryQuery);

            itemQuery.setString(1, name);
            itemPrcQuery.setDouble(1, price);
            itemQtyQuery.setInt(1, qty);
            itemCategoryQuery.setString(1, category.toString());

            itemQuery.execute();
            itemPrcQuery.execute();
            itemQtyQuery.execute();
            itemCategoryQuery.execute();

            DBManager.getConnection().commit();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean update(int id, String name, double price, int qty, Item.Category category){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

            String updateItemQuery = "UPDATE item SET item.iname = ? WHERE item.iid = ?;";
            String updateItemPrcQuery = "UPDATE item_prc SET item_prc.prc = ? WHERE item_prc.iid = ?;";
            String updateItemQtyQuery = "UPDATE item_qty SET item_qty.qty = ? WHERE item_qty.iid = ?;";
            String updateItemCategoryQuery = "UPDATE item_category SET item_category.category = ? WHERE item_category.iid = ?;";

            PreparedStatement updateItem = DBManager.getConnection().prepareStatement(updateItemQuery);
            PreparedStatement updateItemPrc = DBManager.getConnection().prepareStatement(updateItemPrcQuery);
            PreparedStatement updateItemQty = DBManager.getConnection().prepareStatement(updateItemQtyQuery);
            PreparedStatement updateItemCategory = DBManager.getConnection().prepareStatement(updateItemCategoryQuery);

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

            DBManager.getConnection().commit();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean packOrder(int oid){
        try{
            DBManager.getConnection().setAutoCommit(false);

            String packOrderQuery = "UPDATE user_order SET packed = true WHERE oid = ?;";
            PreparedStatement packorder = DBManager.getConnection().prepareStatement(packOrderQuery);
            packorder.setInt(1, oid);
            packorder.execute();

            DBManager.getConnection().commit();
            return true;
        } catch (SQLException e) {
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } finally {
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Order getOrder(int oid){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction
            Order order = new Order();
            String getOrdersQuery = "SELECT order_item.oid, order_item.iid, item.iname, order_item.iqty, item_category.category, item_prc.prc\n" +
                    "FROM order_item\n" +
                    "JOIN item ON order_item.iid = item.iid\n" +
                    "JOIN item_category ON order_item.iid = item_category.iid\n" +
                    "JOIN item_prc ON order_item.iid = item_prc.iid\n" +
                    "WHERE order_item.oid = ?";

            PreparedStatement stmt = DBManager.getConnection().prepareStatement(getOrdersQuery);
            stmt.setInt(1,oid);

            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Item.Category c = null;
                for(Item.Category category : Item.Category.values()){
                    if(resultSet.getString("category").equals(category.toString())){
                        c = category;
                        break;
                    }
                }
                order.addItem(new Item(resultSet.getInt("iid"), resultSet.getString("iname"), resultSet.getDouble("prc"), resultSet.getInt("iqty"), c));
                order.setOid(resultSet.getInt("oid"));
            }

            DBManager.getConnection().commit();
            return order;
        }catch(SQLException e){
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Integer> getAllOrderIds(){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

            String getAllOrderIdQuery =
                    "SELECT oid FROM user_order;";

            PreparedStatement getOrderId = DBManager.getConnection().prepareStatement(getAllOrderIdQuery);

            ResultSet result = getOrderId.executeQuery();

            List<Integer> orderIds = new ArrayList<>();


            while(result.next()){
                 orderIds.add(result.getInt("oid"));
            }

            DBManager.getConnection().commit();
            return orderIds;
        }catch(SQLException e){
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return new ArrayList<>();
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean placeOrder(List<Item> items, int uid){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

            String updateItemQtyQuery = "UPDATE item_qty SET item_qty.qty = item_qty.qty - ? WHERE item_qty.iid = ?;";
            PreparedStatement updateItemQty = DBManager.getConnection().prepareStatement(updateItemQtyQuery);
            for(Item item : items){
                updateItemQty.setInt(1, item.getQty());
                updateItemQty.setInt(2, item.getId());
                updateItemQty.execute();
            }

            String addOrderQuery = "INSERT INTO user_order(uid, packed) VALUES(?, ?);";
            PreparedStatement addOrderStmnt = DBManager.getConnection().prepareStatement(addOrderQuery);
            addOrderStmnt.setInt(1, uid);
            addOrderStmnt.setBoolean(2, false);
            addOrderStmnt.execute();

            String addOrderItemQuery = "INSERT INTO order_item(oid, iid, iqty) VALUES(LAST_INSERT_ID(), ?, ?);";
            PreparedStatement addOrderItemStmnt = DBManager.getConnection().prepareStatement(addOrderItemQuery);
            for(Item item : items){
                addOrderItemStmnt.setInt(1, item.getId());
                addOrderItemStmnt.setInt(2, item.getQty());
                addOrderItemStmnt.execute();
            }

            DBManager.getConnection().commit();
            return true;
        }catch(SQLException e){
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Item findByName(String iname){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

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

            PreparedStatement itemByCategoryQuery = DBManager.getConnection().prepareStatement(findByIidQuery);

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

            DBManager.getConnection().commit();
            return foundItem;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Item> findByCategory(Item.Category category){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

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

            PreparedStatement itemByCategoryQuery = DBManager.getConnection().prepareStatement(findByCategoryQuery);

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

            DBManager.getConnection().commit();
            return foundItems;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return new ArrayList<>();
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Item> findAllItems(){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

            String findByCategoryQuery =
                    "SELECT item.iid, item.iname, item_prc.prc, item_qty.qty, item_category.category\n" +
                            "FROM item\n" +
                            "INNER JOIN item_prc\n" +
                            "ON item.iid = item_prc.iid\n" +
                            "INNER JOIN item_qty\n" +
                            "ON item_prc.iid = item_qty.iid\n" +
                            "INNER JOIN item_category\n" +
                            "ON item_qty.iid = item_category.iid;";

            PreparedStatement findAllItemsQuery = DBManager.getConnection().prepareStatement(findByCategoryQuery);

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

            DBManager.getConnection().commit();
            return foundItems;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return new ArrayList<>();
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Item findById(int iid){
        try{
            DBManager.getConnection().setAutoCommit(false); //Init transaction

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

            PreparedStatement itemByCategoryQuery = DBManager.getConnection().prepareStatement(findByIidQuery);

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

            DBManager.getConnection().commit();
            return foundItem;
        }catch(SQLException e){
            e.printStackTrace();
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //DBItemManager.fill();
        System.out.println(getAllOrderIds());
        System.out.println(getOrder(1));
        //testOrder();
    }

    private static void testOrder(){
        List<Item> itemOrderList = new ArrayList<>();
        itemOrderList.add(new Item(1,"Sockerbeta", 110.0, 5, Item.Category.FOOD));
        itemOrderList.add(new Item(2, "Kladdkaka", 110.0, 1, Item.Category.FOOD));
        itemOrderList.add(new Item(3,"Potatis", 110.0, 0, Item.Category.FOOD));
        itemOrderList.add(new Item(7,"Metallica - Ride The Lightning", 110.0, 2, Item.Category.MUSIC));
        itemOrderList.add(new Item(9,"Gitarr", 110.0, 11, Item.Category.INSTRUMENTS));
        itemOrderList.add(new Item(10,"Klarinett", 110.0, 4, Item.Category.INSTRUMENTS));
        itemOrderList.add(new Item(12,"Shure SM58", 110.0, 6, Item.Category.INSTRUMENTS));
        itemOrderList.add(new Item(13,"Tröja", 110.0, 7, Item.Category.CLOTHING));
        itemOrderList.add(new Item(15,"Basker", 110.0, 3, Item.Category.CLOTHING));
        System.out.println("placeOrder: " + placeOrder(itemOrderList, 1));
    }

    private static void fill(){
        DBItemManager.addItem("Sockerbeta", 110.0, 12, Item.Category.FOOD);
        DBItemManager.addItem("Kladdkaka", 110.0, 2, Item.Category.FOOD);
        DBItemManager.addItem("Potatis", 110.0, 1, Item.Category.FOOD);
        DBItemManager.addItem("Pulsen-korv", 110.0, 5, Item.Category.FOOD);
        DBItemManager.addItem("Biljardbord", 110.0, 23, Item.Category.SPORTS);
        DBItemManager.addItem("How to play like OSullivan - DVD", 110.0, 9999, Item.Category.SPORTS);
        DBItemManager.addItem("Metallica - Ride The Lightning", 110.0, 3, Item.Category.MUSIC);
        DBItemManager.addItem("Vårkonsert med Örebros kommunala musikskola", 110.0, 33, Item.Category.MUSIC);
        DBItemManager.addItem("Gitarr", 110.0, 14, Item.Category.INSTRUMENTS);
        DBItemManager.addItem("Klarinett", 110.0, 13, Item.Category.INSTRUMENTS);
        DBItemManager.addItem("Trummor", 110.0, 76, Item.Category.INSTRUMENTS);
        DBItemManager.addItem("Shure SM58", 110.0, 6, Item.Category.INSTRUMENTS);
        DBItemManager.addItem("Tröja", 110.0, 7, Item.Category.CLOTHING);
        DBItemManager.addItem("Skinnpaj", 110.0, 3, Item.Category.CLOTHING);
        DBItemManager.addItem("Basker", 110.0, 8, Item.Category.CLOTHING);
        DBItemManager.addItem("Hatt", 110.0, 0, Item.Category.CLOTHING);
        DBItemManager.addItem("Volvo 740", 110.0, 23, Item.Category.VEHICLES);
        DBItemManager.addItem("Gocart", 110.0, 2, Item.Category.VEHICLES);
    }
}
