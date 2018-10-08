SELECT oid FROM user_order;

SELECT order_item.oid, order_item.iid, item.iname, order_item.iqty, item_category.category, item_prc.prc
FROM order_item
JOIN item ON order_item.iid = item.iid
JOIN item_category ON order_item.iid = item_category.iid
JOIN item_prc ON order_item.iid = item_prc.iid
WHERE order_item.oid = 1