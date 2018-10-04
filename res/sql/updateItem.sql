UPDATE item
SET item.iname = "Test"
WHERE item.iid = 1;

UPDATE item_prc
SET item_prc.prc = 23000.0
WHERE item_prc.iid = 1;

UPDATE item_qty
SET item_qty.qty = 233
WHERE item_qty.iid = 1;

UPDATE item_category
SET item_category.category = "MUSIC"
WHERE item_category.iid = 1;