//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem.Container.NUM_OF_MAX_CONTAINERS;

public class WarehouseClerk {
    WarehouseKeeper warehouseKeeper;
    List<Tag> tagList;
    static String STOCK_LIST_FILE_PATH="stock-list.csv";
    static String NEW_STOCK_LIST_FILE_PATH="new-stock-list.csv";
    static String OUT_OF_STOCK_LIST_FILE_PATH="out-of-stock-list.csv";

    int firstFlag=1;
    WarehouseClerk(WarehouseKeeper warehouseKeeper){
        this.warehouseKeeper=warehouseKeeper;
        tagList=new ArrayList<>();
    }

    void addTag(Tag tag){
        tagList.add(tag);
        try{
            List<Container> usedContainerList=new ArrayList<Container>();
            for(Tag t:tagList){
                usedContainerList.add(t.container);
            }
            if(usedContainerList.size()>NUM_OF_MAX_CONTAINERS){
                throw new Exception();
            }
        }catch(Exception e){
            System.out.println("コンテナの最大数を超えています");
        }
    }

    List<Tag> getTagList(){
        System.out.println(tagList.size());
        return tagList;
    }

    boolean makeRequest(Order order){
        List requestRecordList =new ArrayList<RequestRecord>();
        int numberOfBottlesOfOrder=order.getNumberOfBottles();
        int tl=0;
        int tlnum=0;
        int listSize=0;
        int og=order.getNumberOfBottles();
        for(int i=0;i<tagList.size();i++){
            listSize=tagList.size();
            Tag t=tagList.get(i);
            tlnum=t.takeLiquor(order.getBrand(), og);
            tl+=tlnum;
            og-=tlnum;
            if(tl>0){
                requestRecordList.add(new RequestRecord(t.getContainer(),order.getOrderId(),order.getSendingCustomerName(),order.getBrand(),tlnum));

            }

            try {
                warehouseKeeper.shipLiquors(requestRecordList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(tlnum==order.getNumberOfBottles()){
                break;
            }
            if(listSize!=tagList.size()){
                i--;
            }
        }

        if(tl<order.getNumberOfBottles()){
            reportOufOfStockBottles(order, og);
            return false;
        }


        return true;
    }

    void reportOufOfStockBottles(Order order, int numOfOutOfStockBottles){
        try {
            File f = new File(OUT_OF_STOCK_LIST_FILE_PATH);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(order.getOrderId()+","+order.getSendingCustomerName()+","+order.getBrand()+","+numOfOutOfStockBottles+"\n");
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    void writeStockList(String stockListFilePath){
        try {
            File f = new File(stockListFilePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            for(Tag t:tagList){
                List<StockRecord> stockRecordList=new ArrayList<>();
                stockRecordList=t.getStockRecordList();
                for(StockRecord sr:stockRecordList){
                    bw.write(t.getContainerId()+","+t.getBroughtDate()+","+sr.getBrand()+","+sr.getNumberOfBottles()+"\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
