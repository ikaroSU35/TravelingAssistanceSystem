//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tag {
    Container container;
    List<StockRecord> stockRecordList;
    Date broughtDate;

    Tag(Container container){
        this.container=container;
        stockRecordList=new ArrayList<StockRecord>() ;
    }
    void 	addStockRecord(StockRecord stockRecord){
        stockRecordList.add(stockRecord);
    }

    String 	getBroughtDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(broughtDate);
    }

    Container getContainer(){
        return container;
    }

    int getContainerId(){
        return container.id;
    }

    public List<StockRecord> getStockRecordList(){
        return stockRecordList;
    }

    void setBroughtDate(Date broughtDate){
        this.broughtDate=broughtDate;
    }

    int takeLiquor(String brand, int numOfBottlesToBeShipped){
        int numOfBottlesToBeReturned=0;
        //System.out.println(brand+","+numOfBottlesToBeShipped);
        //for(int i=0;i<stockRecordList.size();i++) {
        //    System.out.println(stockRecordList.get(i).getBrand()+","+stockRecordList.get(i).getNumberOfBottles());
        //}
        //System.out.println();
        for(int i=0;i<stockRecordList.size();i++) {
            StockRecord sr=stockRecordList.get(i);
            if (sr.getBrand().equals(brand)) {
                if (sr.getNumberOfBottles() >= numOfBottlesToBeShipped) {
                    sr.setNumberOfBottles(sr.getNumberOfBottles() - numOfBottlesToBeShipped);
                    numOfBottlesToBeReturned += numOfBottlesToBeShipped;
                    if (sr.getNumberOfBottles() == 0) {
                        stockRecordList.remove(sr);
                        i--;
                    }
                    return numOfBottlesToBeReturned;
                } else {
                    numOfBottlesToBeShipped -= sr.getNumberOfBottles();
                    numOfBottlesToBeReturned += sr.getNumberOfBottles();
                    sr.setNumberOfBottles(0);
                    stockRecordList.remove(sr);
                    i--;
                }
            }
        }
        return numOfBottlesToBeReturned;
    }
}
