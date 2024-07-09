//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

public class LiquorSalesCompany {
    WarehouseKeeper warehouseKeeper;
    WarehouseClerk warehouseClerk;

    LiquorSalesCompany(){
        warehouseKeeper=new WarehouseKeeper();
        warehouseClerk=new WarehouseClerk(this.warehouseKeeper);
        warehouseKeeper.setWarehouseClerk(warehouseClerk);
    }

    WarehouseClerk getWarehouseClerk(){
        return warehouseClerk;
    }

    WarehouseKeeper getWarehouseKeeper(){
        return warehouseKeeper;
    }
}
