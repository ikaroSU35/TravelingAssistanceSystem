//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem.Container.NUM_OF_MAX_CONTAINERS;

public class WarehouseKeeper {
    WarehouseClerk warehouseClerk;
    List<Container> containerList;
    static String REQUEST_LIST_FILE_PATH="request-list.csv";

    WarehouseKeeper(){
        containerList=new ArrayList<Container>();
        warehouseClerk=new WarehouseClerk(this);
    }

    void bringContainers(Container container) throws Exception{
        if(containerList.size()==NUM_OF_MAX_CONTAINERS){
            throw new Exception("倉庫にこれ以上コンテナを搬入できません。");
        }
        else {
            containerList.add(container);
            warehouseClerk.addTag(container.getTag());
        }
    }

    int getNumberOfContainers(){
        return containerList.size();
    }

    void setWarehouseClerk(WarehouseClerk warehouseClerk){
        this.warehouseClerk=warehouseClerk;
    }

    void shipLiquors(List<RequestRecord> requestList) throws Exception{
        try {
            int frag=0;
            File f = new File(REQUEST_LIST_FILE_PATH);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            for(RequestRecord rr:requestList) {
                if(!(containerList.contains(rr.getContainer()))) {
                    throw new Exception("倉庫係が管理していないコンテナ番号が要求されました。");
                }
                bw.write(rr.toString());
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        for(int i=0;i<requestList.size();i++){
            RequestRecord rr=requestList.get(i);
            rr.getContainer().takeLiquor(rr.getBrand(), rr.getNumberOfBottles());
            if(rr.getContainer().getTotalNumberOfBottles()==0){
                rr.getContainer().carryOut();
                containerList.remove(rr.getContainer());
                warehouseClerk.getTagList().remove(rr.getContainer().getTag());
                requestList.remove(rr);
                i--;
            }
        }
    }
}
