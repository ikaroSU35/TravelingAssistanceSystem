//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

public class StockUnit {
    Liquor liquor;
    int numberOfBottles;
    static int NUM_OF_MAX_BOTTLES=20;

    public StockUnit(Liquor liquor, int numberOfBottles)throws Exception{

        if(numberOfBottles>NUM_OF_MAX_BOTTLES) {
            throw new Exception("最大ボトル数を超えています");
        }
        else {
                this.liquor = liquor;
                this.numberOfBottles = numberOfBottles;
        }
    }

    String getBrand(){
        return liquor.brand;
    }

    int getNumberOfBottles(){
        return numberOfBottles;
    }

    void setNumberOfBottles(int numberOfBottles){
        this.numberOfBottles=numberOfBottles;
    }
}
