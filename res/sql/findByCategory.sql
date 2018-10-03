SELECT item.iname, item_prc.prc, item_qty.qty, item_category.category
FROM item
INNER JOIN item_prc
ON item.iid = item_prc.iid
INNER JOIN item_qty
ON item_prc.iid = item_qty.iid
INNER JOIN item_category
ON item_qty.iid = item_category.iid
WHERE item_category.category = 'SPORTS';


