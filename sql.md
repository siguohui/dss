GROUP_CONCAT(
CONCAT_WS( '-', CustStoreId, CustStoreName )  
ORDER BY CustStoreId DESC
SEPARATOR '/') as Spli
