//15820062 戸田空伽
package jp.ac.aoyama.it.it_lab_3.sake_warehouse_problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 酒倉庫問題のメインクラス
 */
public class Main {

    /**
     * 搬入年月日
     */
    private static Date broughtDate;

    /**
     * 指定したファイルを削除する．
     *
     * @param filePath ファイルのパス
     */
    private static void removeFile(String filePath) {
        if (Files.exists(Paths.get(filePath))) {
            try {
                Files.delete(Paths.get(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 出力されたファイル（在庫リスト，出庫後の在庫リスト，在庫不足リスト，出庫依頼リスト）を削除する．
     */
    public static void removeOutputFiles() {
        removeFile(WarehouseClerk.STOCK_LIST_FILE_PATH);
        removeFile(WarehouseClerk.NEW_STOCK_LIST_FILE_PATH);
        removeFile(WarehouseClerk.OUT_OF_STOCK_LIST_FILE_PATH);
        removeFile(WarehouseKeeper.REQUEST_LIST_FILE_PATH);
    }

    /**
     * コンテナリストファイルを元に，酒類販売会社の倉庫にコンテナを搬入する．
     *
     * @param liquorSalesCompany    酒類販売会社
     * @param containerListFilePath コンテナリストファイルパス
     */
    public static void bringContainersFromFile(LiquorSalesCompany liquorSalesCompany, String containerListFilePath) {
        Map<Integer, Container> idContainerMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(containerListFilePath));
            // コンテナリストファイルを元に，酒類販売会社の倉庫にコンテナを搬入
            // コンテナ番号をキーとして，idContainerMapに，Containerクラスのインスタンスを値として格納
            for(String line:lines){
                String[] words=line.split(",",3);
                Liquor liquor=new Liquor(words[1]);
                if(idContainerMap.containsKey(Integer.parseInt(words[0]))==false){
                    Container container=new Container(Integer.parseInt(words[0]));
                    container.putLiquor(liquor,Integer.parseInt(words[2]));
                    Tag tag=container.getTag();
                    tag.setBroughtDate(broughtDate);
                    idContainerMap.put(Integer.parseInt(words[0]),container);
                }
                else{
                    idContainerMap.get(Integer.parseInt(words[0])).putLiquor(liquor,Integer.parseInt(words[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Container c : idContainerMap.values()) {
            try {
                liquorSalesCompany.getWarehouseKeeper().bringContainers(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 出庫依頼ファイルから顧客リストを作成する．
     *
     * @param liquorSalesCompany      酒類販売会社
     * @param shippingRequestFilePath 出庫依頼ファイルパス
     * @return 顧客リスト
     */
    public static Collection<Customer> createCustomers(LiquorSalesCompany liquorSalesCompany, String shippingRequestFilePath) {
        Map<String, Customer> customerNameMap = new HashMap<>();
        List<String> customerNameList = Arrays.asList(Customer.CUSTOMER_NAME_LIST);
        int flag=0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(shippingRequestFilePath));
            // 出庫依頼ファイルから注文番号，顧客名（配達先），銘柄，本数を取得
            // 顧客名が顧客リストに含まれている場合，Customerクラスを作成し，注文を作成（CustomerクラスのcreateOrderメソッドを実行）
            // 顧客名をキーとして，customerNameMapに，Customerクラスのインスタンスを値として格納
            // customerNameMapの値のセットを返す
            for(String line:lines){
                flag=0;
                String[] word=line.split(",");
                if(customerNameList.contains(word[1])){
                    for(Customer c:customerNameMap.values()) {
                        if(c.getName().equals(word[1])){
                            c.createOrder(word[0], word[2], Integer.parseInt(word[3]));
                            flag=1;
                        }
                    }
                    if(flag!=1) {
                        Customer customer = new Customer(word[1], liquorSalesCompany);
                        customer.createOrder(word[0], word[2], Integer.parseInt(word[3]));
                        customerNameMap.put(word[1], customer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerNameMap.values();
    }

    private static void printUsage() {
        System.err.println("Usage: \njava jp.ac.aoyama.it.sake_warehouse_problem.Main yyyy年MM月dd日 出庫依頼ファイルパス コンテナリストファイルパス");
    }

    /**
     * メインメソッド
     * Usage: java jp.ac.aoyama.it.sake_warehouse_problem.Main yyyy年MM月dd日 出庫依頼ファイルパス コンテナリストファイルパス
     *
     * @param args args[0]: yyyy年MM月dd日, args[1]: 出庫依頼ファイルパス, args[2]: コンテナリストファイルパス
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            System.exit(-1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            broughtDate = sdf.parse(args[0]);
        } catch (ParseException e) {
            printUsage();
            e.printStackTrace();
            System.exit(-1);
        }

        String shippingRequestFilePath = args[1];
        if (!Files.exists(Paths.get(shippingRequestFilePath))) {
            System.err.printf("ERROR: Cannot open file '%s'.", shippingRequestFilePath);
            printUsage();
            System.exit(-1);
        }

        String containerListFilePath = args[2];
        if (!Files.exists(Paths.get(containerListFilePath))) {
            System.err.printf("ERROR: Cannot open file '%s'.", containerListFilePath);
            printUsage();
            System.exit(-1);
        }

        // 出力ファイルを削除する
        removeOutputFiles();

        // 酒類販売会社インスタンスの生成
        LiquorSalesCompany liquorSalesCompany = new LiquorSalesCompany();

        // ファイルから在庫搬入
        bringContainersFromFile(liquorSalesCompany, containerListFilePath);

        // 出庫前の在庫リストを出力
        liquorSalesCompany.getWarehouseClerk().writeStockList(WarehouseClerk.STOCK_LIST_FILE_PATH);

        // 顧客リストの作成
        Collection<Customer> customerList = createCustomers(liquorSalesCompany, shippingRequestFilePath);

        // 出庫依頼する
        for (Customer c : customerList) {
            c.order();
        }
        // 出庫後の在庫リストを出力
        liquorSalesCompany.getWarehouseClerk().writeStockList(WarehouseClerk.NEW_STOCK_LIST_FILE_PATH);
    }
}

