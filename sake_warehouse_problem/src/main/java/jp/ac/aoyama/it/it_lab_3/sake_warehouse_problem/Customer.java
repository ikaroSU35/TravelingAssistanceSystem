//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    String name;
    List<Order> orderList;
    LiquorSalesCompany supplier;
    static String[] CUSTOMER_NAME_LIST={"A-sake-shop","B-sake-shop","C-sake-shop","D-sake-shop","E-sake-shop","F-sake-shop","G-sake-shop","H-sake-shop","I-sake-shop","J-sake-shop","K-sake-shop"};

    Customer(String name, LiquorSalesCompany liquorSalesCompany){
        this.name=name;
        this.supplier=liquorSalesCompany;
        orderList=new ArrayList<>();
    }

    void createOrder(String orderId, String brand, int numberOfBottles){
        orderList.add(new Order(orderId, brand, numberOfBottles, this.name));
    }

    String getName(){
        return name;
    }

    int getNumberOfOrders(){
        return orderList.size();
    }

    void order(){
        for(Order order:orderList){
            supplier.getWarehouseClerk().makeRequest(order);
        }
    }

}
