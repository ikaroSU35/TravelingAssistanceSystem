//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.util.ArrayList;
import java.util.List;

public class Container {
    static int NUM_OF_MAX_CONTAINERS=6;
    int id;
    Tag tag;
    List<StockUnit> stockList;


    public Container(int id){
        this.id=id;
        tag=new Tag(this);
        stockList=new ArrayList<>();
    }

    void carryOut(){
        System.out.printf("コンテナ番号=%dのコンテナは空になったため、 搬出されました。\n", id);
    }

    int getId(){
        return id;
    }

    List<StockUnit> getStockList(){
        return stockList;
    }

    public Tag getTag(){
        return tag;
    }

    public int getTotalNumberOfBottles(){
        int totalNumberOfBottles=0;
        for(StockUnit su:stockList){
            totalNumberOfBottles+=su.getNumberOfBottles();
        }
        return totalNumberOfBottles;
    }

    public void putLiquor(Liquor liquor, int numOfBottles) {
        try {
            stockList.add(new StockUnit(liquor, numOfBottles));
        }catch(Exception e){

        }
        StockRecord sr=new StockRecord(liquor.getBrand(), numOfBottles);
        tag.addStockRecord(sr);
    }

    int takeLiquor(String brand, int numOfBottlesToBeShipped){
        int numOfBottlesToBeReturned=0;
        for(int i=0;i<stockList.size();i++) {
            StockUnit su=stockList.get(i);
            if (su.getBrand().equals(brand)) {
                if (su.getNumberOfBottles() >= numOfBottlesToBeShipped) {
                    su.setNumberOfBottles(su.getNumberOfBottles() - numOfBottlesToBeShipped);
                    numOfBottlesToBeReturned += numOfBottlesToBeShipped;
                    if (su.getNumberOfBottles() == 0) {
                        stockList.remove(su);
                        i--;
                    }
                    return numOfBottlesToBeReturned;
                } else {
                    numOfBottlesToBeShipped -= su.getNumberOfBottles();
                    numOfBottlesToBeReturned += su.getNumberOfBottles();
                    su.setNumberOfBottles(0);
                    stockList.remove(su);
                    i--;
                }
            }
        }
        return numOfBottlesToBeReturned;
    }
}
