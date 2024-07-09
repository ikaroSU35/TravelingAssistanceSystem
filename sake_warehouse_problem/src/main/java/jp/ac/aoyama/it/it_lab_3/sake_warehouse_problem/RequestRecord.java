//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

public class RequestRecord {
    Container container;
    String orderId;
    String sendingCustomerName;
    String brand;
    int numberOfBottles;

    RequestRecord(Container container, String orderId, String sendingCustomerName, String brand, int numberOfBottles){
        this.container=container;
        this.orderId=orderId;
        this.sendingCustomerName=sendingCustomerName;
        this.brand=brand;
        this.numberOfBottles=numberOfBottles;
    }

    String getBrand(){
        return brand;
    }

    Container getContainer(){
        return container;
    }

    int getNumberOfBottles(){
        return numberOfBottles;
    }

    public String toString(){
        return orderId+","+container.id+","+sendingCustomerName+","+brand+","+numberOfBottles;
    }
}
