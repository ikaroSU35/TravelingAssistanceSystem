//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

public class Order {
    String orderId;
    String brand;
    int numberOfBottles;
    String sendingCustomerName;

    Order(String orderId, String brand, int numberOfBottles, String sendingCustomerName){
        this.orderId=orderId;
        this.brand=brand;
        this.numberOfBottles=numberOfBottles;
        this.sendingCustomerName=sendingCustomerName;
    }

    String getBrand(){
        return brand;
    }

    int getNumberOfBottles(){
        return numberOfBottles;
    }

    String getOrderId(){
        return orderId;
    }

    String getSendingCustomerName(){
        return sendingCustomerName;
    }

    public String toString(){
        return orderId+","+brand+","+numberOfBottles+","+sendingCustomerName;
    }


}
