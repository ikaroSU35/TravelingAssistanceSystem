//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

public class StockRecord {
    String brand;
    int numberOfBottles;

    StockRecord(String brand,int numberOfBottles){
        this.brand=brand;
        this.numberOfBottles=numberOfBottles;
    }

    public String getBrand(){
        return brand;
    }

    public int getNumberOfBottles(){
        return numberOfBottles;
    }

    void setNumberOfBottles(int numberOfBottles){
        this.numberOfBottles=numberOfBottles;
    }
}
