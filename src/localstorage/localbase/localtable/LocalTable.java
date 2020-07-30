package localbase.localtable;

import localbase.FileOprUtil;
import localbase.localmap.LocalMap;

import java.io.File;
import java.util.*;

public class LocalTable {
//    private static Stack<String> result;
//    private static HashMap<String, String> column;
//    //当前操作的文件夹的路径
//    private static String currentPath = "";
//    //当前操作的LocalMap对象的名称
//    private static String currentTableName = ""
    //用来保存此表格的基本信息
    private static TableInform config = null;
    //用来保存一次查询之后得到的结果集
    private static QueryResult queryResult = null;


    /**
     * 在指定的路径下创建一个新的表格
     * @param pathname 需要生成表格的路径 ps:如果想要在当前目录中的base文件夹下创建一个名为 clothes的表格
     *                 那么可以输入其相对路径 base/clothes 也可以使用绝对路径
     * @param cols 表格中的列 输入字符串作为列的名字
     *             <h2>
     *                 其中列的类型分为普通列和关键字列
     *                 普通列不能被作为检索的条件 而关键字列可以
     *                 若指定某列为关键字列 则在列名后加"$"
     *                 如创建一个表格 book 有属性 name ID price
     *                 现制定ID和name作为关键字 则应这么输入
     *                 LocalTable.createTable("book", "name$", "ID$", "price");
     *             </h2>
     */
    public static void createTable(String pathname, String ... cols) {
        //先检测输入的数据是否符合规范
        if(TableChecker.colsValid(cols) && TableChecker.pathnameValid(pathname)
        && !FileOprUtil.existDir(pathname)) {
            //再根据输入数据创建列对象以生成对应的TableInform对象
            TableColumn[] columns = new TableColumn[cols.length];
            for(int i=0; i<columns.length; i++) {
                columns[i] = new TableColumn(cols[i]);
            }
            TableInform newTableInfo = new TableInform(pathname, columns);
            FileOprUtil.createDir(pathname);
            //生成本地表格操作器以进行写入操作
            TableTerminal newTableTer = new TableTerminal(newTableInfo);
            newTableInfo.writeConfig();
            newTableTer.updateAddCol();
        } else {
            //打出提示语
            System.out.println("创建表格失败 可能原因:1.表格的输入数据不规范(鼠标悬浮在LocalTable.createTable(..);上以查阅输入说明)" +
                    " 2.已经存在对应此路径的表格文件");
        }
    }


    /**
     * 切换当前操作对象至某个表格
     * @param pathname 所选择表格的路径名
     */
    public static void switchToTable(String pathname) {
        if(TableChecker.pathnameValid(pathname) && FileOprUtil.existDir(pathname)) {
            config = TableInform.readConfig(pathname);
            queryResult = null;
        }
        else {
            //打出提示语
            System.out.println("切换表格失败 可能原因:1.不存在对应路径的表格"
            + "2.输入的路径不规范");
        }
    }


    /**
     * 给此表格输入一行新的数据
     * @param inputInform 新一行的数据
     *                    <h2>ps: 如有表格有列 name ID price
     *                    那么输入一条新的数据时 需要一一对应
     *                    如 "name", "ball", "ID", "013", "price", "3"
     *                    即前为列名后为数据</h2>
     */

    public static void addRow(String ... inputInform) {
        if(config != null) {
            if (new TableChecker(config).rowDataValid(inputInform)) {
                TableTerminal terminal = new TableTerminal(config);
                terminal.addRowData(inputInform);
            } else {
                //打出提示语
                System.out.println("输入的数据不规范 鼠标悬停于LocalTable.addRow(..);上查阅说明");
            }
        } else {
            //打出提示语
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /**
     * 根据输入的条件检索对应的数据行 并保存到当前操作对象的结果集中
     * 结果集的操作如下：
     * <li>可以通过LocalTable.next(), LocalTable.before, LocalTable.first
     * 把结果集中的指针移到不同的位置中</li>
     * <li>可以通过LocalTable.getRowData(String colName);来获取当前结果集指针指向位置的
     * 一行记录中某列下的数据</li>
     * <li>可以通过LocalTable.setRowData(String colName, String colData);
     * 来设置指针所在记录在某一列下的数据</li>
     * <li>可以通过LocalTable.deleteRowData();
     * 来删除此条记录</li>
     * @param conditions 需要被检索的条件 只能以关键字列作为作条件搜索
     *                   格式如下:
     *                   <h2>
     *                       如有表格有列 name ID price
     *                       假如在创建表格的时候name和ID作为关键字
     *                       需要检索的是name为"ball" ID为"32"的
     *                       数据 那么可以这么输入:
     *                       LocalTable.queryRow("name", "ball", "ID", "32");
     *                       检索完毕后 会将所有符合条件的记录更新至结果集
     *                   </h2>
     */
    public static void queryRow(String ... conditions) {
        if(config !=null) {
            if(new TableChecker(config).queryDataValid(conditions)) {
                String[] serialArray = new TableTerminal(config).queryRow(conditions);
                queryResult = new QueryResult(serialArray, config);
                //搜索成功 打出提示语
            } else {
                //打出提示语
                System.out.println("输入的信息不规范 鼠标悬停于LocalTable.addRow(..);上查阅说明");
            }
        } else {
            //打出提示语
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }

    }


    /**
     * 获取查询获得的结果集中一条记录对应列下的数据
     * @param colName 需求数据所在的列
     * @return 此条记录此列下的对应数据
     */
    public static String getRowData(String colName) {
        if(config != null) {
            if(queryResult != null) {
                if(config.existCol(colName)) {
                    return queryResult.getData(colName);
                } else {
                    System.out.println("无法获取行数据 原因:不存在此列!");
                }
            } else {
                //打印提示语
                System.out.println("不存在结果集 使用LocalTable.queryRow(String ... conditions);" +
                        "获取查询结果后才能获取其中的数据");
            }
        } else {
            //打印提示语
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }

        //如果查询失败 返回空
        return null;
    }

    /**
     * 用于删除当前结果集中指针指向的一条记录
     */
    public static void deleteRowData() {
        if(config != null) {
            if(queryResult != null) {
                new TableTerminal(config).deleteRow(queryResult.getCurrentRow());
            } else {
                //打印提示语
                System.out.println("不存在结果集 使用LocalTable.queryRow(String ... conditions);" +
                        "获取查询结果后才能获取其中的数据");
            }
        } else {
            //打印提示语
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /**
     * 用于设置结果集中当前指针指向的数据
     * @param colName 需要被修改的数据所在的列
     * @param colData 修改后的数据
     */
    public static void setRowData(String colName, String colData) {
        if(config != null) {
            if(queryResult != null) {
                if(config.existCol(colName)) {
                    TableChecker checker = new TableChecker(config);
                    if(checker.colDataValid(colData)) {
                        new TableTerminal(config).setRowData(queryResult.getCurrentRow(),
                                colName, colData);
                    } else {
                        //提示语
                        System.out.println("输入的列数据不符合规范 请避免使用一些特殊字符");
                    }
                } else {

                }
            } else {
                //打印提示语
                System.out.println("不存在结果集 使用LocalTable.queryRow(String ... conditions);" +
                        "获取查询结果后才能获取其中的数据");
            }
        } else {
            //打印提示语
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /**
     * 用于移动结果集的指针至下一条记录
     * 如果存在下一条记录 就返回true 否则返回false
     * @return 是否存在下一条记录
     */
    public static boolean next() {
        if(queryResult != null) {
            return queryResult.next();
        } else {
            //打出提示语
            System.out.println("当前结果集为空 " +
                    "使用LocalTable.queryRow" +
                    "(String ... conditions)以获取结果集");

            return false;
        }
    }


    /**
     * 用于移动结果集的指针至上一条记录
     * 如果存在上一条记录 就返回true 否则返回false
     * @return 是否存在上一条记录
     */
    public static boolean before() {
        if(queryResult != null) {
            return queryResult.before();
        } else {
            //打出提示语
            System.out.println("当前结果集为空 " +
                    "使用LocalTable.queryRow" +
                    "(String ... conditions)以获取结果集");

            return false;
        }
    }


    /**
     * 用于将结果集的指针移向结果集中的第一条记录
     * @return 结果集中是否存在记录
     */
    public static boolean first() {
        if(queryResult != null) {
            return queryResult.first();
        } else {
            //打出提示语
            System.out.println("当前结果集为空 " +
                    "使用LocalTable.queryRow" +
                    "(String ... conditions)以获取结果集");

            return false;
        }
    }


    /**
     * 删除此表格中的某一列
     * @param colName 要被删除的列的名称
     */
    public static void deleteCol(String colName) {
        if(config != null) {
            if(config.existCol(colName)) {
                config.deleteCol(colName);
            } else {
                System.out.println("删除列操作失败" +
                        "不存在名称为" + colName + "的列" );
            }
        } else {
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /**
     * 添加一个新的列至此表格中
     * @param colName 新增的列名称 若需要指定其为关键字列
     *                可在输入的列名后加$ 如添加一个名叫
     *                ID的关键字列则输入 "ID$"
     */
    public static void addCol(String colName) {
        if(config != null) {
            if(TableChecker.colsValid(colName)) {
                config.addCol(colName);
            } else {
                System.out.println("添加列操作失败 原因:" +
                        "添加的新列" + colName + "的命名不规范");
            }
        } else {
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /**
     * 删除当前作为操作对象的Table在本地的缓存
     */
    public static void deleteTable() {
        if(config != null) {
            FileOprUtil.dropFileOrDir(config.getPathname());
            config = null;
        } else {
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }

    }


    /**
     * 在控制台打印出表格的图象
     */
    public static void showTable() {
        if(config != null) {
            String[] rowList = new TableTerminal(config).getRowList();
            printTableHead();
            for (int i = 0; i < rowList.length; i++) {
                printRow(rowList[i]);
            }
        } else {
            //tip
            System.out.println("使用LocalTable.switchToTable(String pathname)以指定操作的表格对象");
        }
    }


    /*
     * 打印表格图象时的支持方法 负责打印一行表格数据
     */
    private static void printRow(String rowNumber) {
        TableTerminal dataGetter = new TableTerminal(config);
        String[] cols = config.getColArrCopy();
        HashMap<String, TableColumn> colsMap = config.getColumnMap();
        TableColumn currCol;
        String rowContent = "";
        for(int i=0; i<cols.length; i++) {
            currCol = colsMap.get(cols[i]);
            rowContent += "|" + dataGetter.getRowData(rowNumber, cols[i]) ;
            for(int j=0; j<currCol.getColWidth() - dataGetter.getRowData(rowNumber, cols[i]).length()
                    ;j++) {
                rowContent += " ";
            }
            rowContent += "|";
        }
        System.out.println(rowContent);
    }


    /*
     * 打印出表格头部描述每一列的部分
     */
    private static void printTableHead() {
        String[] cols = config.getColArrCopy();
        HashMap<String, TableColumn> colsMap = config.getColumnMap();
        TableColumn currCol;
        String headContent = "";
        for(int i=0; i<cols.length; i++) {
            currCol = colsMap.get(cols[i]);
            headContent += "|" + cols[i] ;
            for(int j=0; j<currCol.getColWidth() - cols[i].length()
                    ;j++) {
                headContent += " ";
            }
            headContent += "|";
        }
        System.out.println(headContent);
    }


}


/*
 * 支持类 用于检查输入数据是否正确
 */
class TableChecker {

    private TableInform config;

    public TableChecker(TableInform config) {
        this.config = config;
    }

    public static boolean pathnameValid(String pathname) {
        String[] pathCell = pathname.split("/");
        if(LocalMap.fileNameIsValid(pathCell[pathCell.length-1])) {
            return true;
        } else {
            return false;
        }
    }


    public boolean queryDataValid(String... queryData) {
        HashMap<String, TableColumn> inform = config.getColumnMap();
        for(int i=0; i<queryData.length/2; i++) {
            if(!inform.containsKey(queryData[2*i])) {
                //tip
                System.out.println("搜索信息输入错误 不含名为" +
                        queryData[2*i] + "列");
                return false;
            }
            if(!inform.get(queryData[2*i]).getIsKey()) {
                //tip
                return false;
            }
        }
        return true;
    }

    public boolean rowDataValid(String... rowData) {
        HashMap<String, TableColumn> inform = config.getColumnMap();
        boolean valid = false;
        for(int i=0; i<rowData.length/2; i++) {
            if(!inform.containsKey(rowData[2*i])) {
                //tip
                System.out.println("行信息输入错误 不含名为" +
                        rowData[2*i] + "列");
                return false;
            }
            if(!LocalMap.fileNameIsValid(rowData[2*i+1])) {
                //tip
                System.out.println("输入的行数据不符合规范 不能包含某些特殊字符" +
                        " 造成错误的数据:" + rowData[2*i + 1]);
                return false;
            }
        }
        return true;
    }

    public static boolean colsValid(String... cols) {
        for(int i=0; i<cols.length; i++) {
            if(!LocalMap.fileNameIsValid(cols[i])) {
                System.out.println("输入的列名称不符合规范" +
                        "造成错误的列名称:" + cols[i]);
                return false;
            }
        }
        return true;
    }

    public  boolean colDataValid(String colData) {
        return LocalMap.fileNameIsValid(colData);
    }




    public boolean colExist(String col) {
        if(config.getColumnMap().containsKey(col)) {
            return true;
        } else {
            return false;
        }
    }
}


/*
 * 表格的列抽象出的类
 */
class TableColumn {
    private String name;
    private boolean isKey = false;
    private int colWidth;

    public TableColumn(String name) {
        if(name.charAt(name.length()-1) == '$') {
            this.name = name.substring(0, name.length()-1);
            isKey = true;
            this.colWidth = name.length()-1;
        } else {
            this.name = name;
            isKey = false;
            this.colWidth = name.length();
        }
    }

    public TableColumn(String name, int colWidth, boolean isKey) {
        this.name = name;
        this.colWidth = colWidth;
        this.isKey = isKey;
    }

    public String getName() {
        return name;
    }

    public boolean getIsKey() {
        return isKey;
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int width) {this.colWidth = width;}
}



/*
 * 表格的信息文件 作为支持类 目的是实现存储表格的信息 并读写本地对等的信息文件
 */
class TableInform {
    //用于存储表格中列名和列数据的映射
    private HashMap<String, TableColumn> columnMap = new HashMap<String, TableColumn>();
    private String pathname = "";
    private String[] cols = null;
    static final String copiedDirName = "-copy";
    static final String configFileName = "-config";

    public TableInform(String pathname, TableColumn ... columns) {
        this.pathname = pathname;
        this.cols = new String[columns.length];
        for(int i=0; i<columns.length; i++) {
            columnMap.put(columns[i].getName(), columns[i]);
            cols[i] = columns[i].getName();
        }
    }
    public HashMap<String, TableColumn> getColumnMap() {
        return columnMap;
    }
    public String getPathname() {return pathname;}

    public void writeConfig() {
        String configContent = "";
        TableColumn column = null;
        for(int i=0; i<cols.length; i++) {
            column = columnMap.get(cols[i]);
            /*配置文件中关于一列有三样信息(列的名字,绘制列表格时的宽度(最长数据的长度),列是否是关键字)
             * 三样信息之间以“:”间隔
             */
            configContent += column.getName() + ":" + column.getColWidth() + ":";
            if(column.getIsKey()) {
                configContent += "1";
            } else {
                configContent += "0";
            }
            //每列数据之间以"<"作为间隔符
            if(i != cols.length-1) { configContent += "<"; }
        }
        FileOprUtil.writeFile(pathname, configFileName, configContent);
        //FileOprUtil.createDirInPath(pathname, TableTerminal.rowListFileName);
        if(!FileOprUtil.existDir(pathname, copiedDirName)) {
            FileOprUtil.createDir(pathname, copiedDirName);
        }
        FileOprUtil.writeFile(pathname + "/" + copiedDirName, configFileName, configContent);
    }


    //用于解析配置文本形成配置对象
    public static TableInform readConfig(String pathname) {
        if(FileOprUtil.existFile(pathname, configFileName)) {
            String configContent = FileOprUtil.readFile(pathname, configFileName);
            //先把每列的信息给分出来
            String[] colsInfo = configContent.split("<");
            String[] colsCell = null;
            TableInform result = null;
            //用于暂存从本地配置文件中获取信息而生成的列对象
            TableColumn[] columns = new TableColumn[colsInfo.length];
            try {
                boolean isKey; //是否是关键字
                for (int i = 0; i < colsInfo.length; i++) {
                    isKey = false;
                    colsCell = colsInfo[i].split(":");
                    if(colsCell[2].equals("1")){
                        isKey = true;
                    }
                    columns[i] = new TableColumn(colsCell[0], Integer.parseInt(colsCell[1]),
                            isKey);
                }
                result = new TableInform(pathname, columns);
            } catch (ArrayIndexOutOfBoundsException e) {
                if(fixConfig(pathname)) {
                    result = readConfig(pathname);
                }
            } finally {
                return result;
            }
        } else {
            System.out.println("指定表格" + pathname + "的配置文件丢失");
            return null;
        }
    }

    public void addCol(String colName) {
        TableColumn newCol = new TableColumn(colName);
        if(!new TableChecker(this).colExist(newCol.getName())) {
            if(TableChecker.colsValid(colName)) {
                String[] colsArray = new String[cols.length + 1];
                for(int i=0; i<cols.length; i++) {
                    colsArray[i] = cols[i];
                }
                colsArray[cols.length] = newCol.getName();
                cols = colsArray;
                //因为有时候用户输入的列名是关键字列 后
                columnMap.put(newCol.getName(), newCol);
                writeConfig();
                new TableTerminal(this).updateAddCol();
            } else {

            }
        } else {

        }


    }

    public void deleteCol(String colName) {
        if(new TableChecker(this).colExist(colName)) {
                String[] colsArray = new String[cols.length - 1];
                int arrCount = 0;
                for(int i=0; i<cols.length; i++) {
                    if(!cols[i].equals(colName)) {
                        colsArray[arrCount++] = cols[i];
                    }
                }
                cols = colsArray;
                columnMap.remove(colName);
                writeConfig();
                new TableTerminal(this).updateDelCol();
        } else {

        }
    }

    public String[] getColArrCopy() {
        String[] copy = new String[cols.length];
        for(int i=0; i<cols.length; i++) {
            copy[i] = cols[i];
        }
        return copy;
    }
    public boolean existCol(String colName) {
        return columnMap.containsKey(colName);
    }
    public boolean colIsKey(String colName) {return columnMap.get(colName).getIsKey();}

    private static boolean fixConfig(String pathname) {
        String configContent = FileOprUtil.readFile(pathname, configFileName);
        String fixContent = FileOprUtil.readFile(pathname + "/" + copiedDirName, configFileName);
        if(configContent.equals(fixContent)) {
            return false;
        } else {
            FileOprUtil.writeFile(pathname, configFileName, fixContent);
            return true;
        }
    }
}


/*
 * 用于完成对本地表格信息的操作
 */
class TableTerminal {

    private TableInform config;
    public static final String rowListFileName = "-rowList";

    public TableTerminal(TableInform config) {
        this.config = config;
    }

    public String[] queryRow(String ... condition) {
        //先获得符合第一个条件的结果数组 再筛去第一个条件数组中不存在其它结果数组中的结果 以获得这些结果数组的交集
        String[] firstResult = queryByOneCondition(condition[0], condition[1]);
        //如果只有一个条件 那么返回
        if(condition.length == 2) {
            return firstResult;
        }
        HashSet<String>[] otherResult = new HashSet[condition.length/2 - 1];
        for(int i=1; i<condition.length/2; i++ ) {
            otherResult[i-1] = arrToHash(queryByOneCondition(condition[2*i], condition[2*i+1]));
        }
        //最终结果数组的长度
        int finalLength = firstResult.length;
        //筛去不存在其它结果数组中的数据
        for(int i=0; i<otherResult.length; i++) {
            for(int j=0; j<firstResult.length; j++) {
                if(!firstResult[j].equals("X") && !otherResult[i].contains(firstResult[j])) {
                    //给不符合者做记号
                    firstResult[j] = "X";
                    finalLength--;
                }
            }
        }
        String[] finalResult = new String[finalLength];
        //再根据上一步做的记号拷贝一份最终的结果数组
        for(int i=0, j=0; i<firstResult.length; i++) {
            if(!firstResult[i].equals("X")) {
                finalResult[j++] = firstResult[i];
            }
        }
        return finalResult;
    }

    public String[] getRowList() {
        if(FileOprUtil.existFile(config.getPathname(), rowListFileName)) {
            return new File(config.getPathname() + "/" + rowListFileName).list();
        } else {
            //tip
            return new String[0];
        }
    }

    public void setRowData(String rowNumber,String colName, String data) {
            FileOprUtil.writeFile(config.getPathname() + "/" + colName, rowNumber, data);
    }

    public void deleteRow(String rowNumber) {
        //获取列数据 来作为删除行信息的根据
        //nowCols--当前的列名称表
        String[] nowCols = config.getColArrCopy();
        //columnMap 当前的列数据映射集
        HashMap<String, TableColumn> columnMap = config.getColumnMap();
        String delePath = "";
        //此行关键字列的内容 删除关键字列对应数据时使用
        String rowContent = "";
        for(int i=0; i<nowCols.length; i++) {
            //当前要进行删除的行对应的某列数据的目录
            delePath = config.getPathname() + "/" + nowCols[i];
            //删除对应列文件夹下的对应此行数据文件
            rowContent = FileOprUtil.readFile(delePath,rowNumber);
            FileOprUtil.dropFileOrDir(delePath, rowNumber);
            FileOprUtil.dropFileOrDir(config.getPathname() + "/" + TableTerminal.rowListFileName,
                    rowNumber);
            if(columnMap.get(nowCols[i]).getIsKey()) {
                delePath = config.getPathname() + "/" + nowCols[i] + "-key/" + rowContent;
                FileOprUtil.dropFileOrDir(delePath, rowNumber);
            }
        }
//            if(FileOprUtil.existFile(delePath, rowNumber)) {
//                FileOprUtil.dropFile(delePath, rowNumber);
//            }

    }

    public String getRowData(String rowNumber, String colName) {
        if(FileOprUtil.existFile(config.getPathname() + "/" + colName + "/" + rowNumber)) {
            return FileOprUtil.readFile(config.getPathname() + "/" + colName, rowNumber);
        } else {
            return "";
            //tip
        }
    }

    public void addRowData(String ... rowData) {
        if(!FileOprUtil.existFile(config.getPathname(), rowListFileName)) {
            generateRowList();
        }
        //为了标识每一行 给每一行设置一个唯一的序号
        long serialNumber = System.currentTimeMillis();
        FileOprUtil.writeFile(config.getPathname() + "/" + rowListFileName,serialNumber + "", "" );
        String colName;   //所要添加的数据对应列的名称
        String colData;   //将要被装载的数据
        TableColumn currCol = null;
        //把输入数据按一定格式存储在本地
        for(int i=0; i<rowData.length/2; i++) {
            colName = rowData[2 * i];
            colData = rowData[2 * i + 1];
            if (!FileOprUtil.existFile(config.getPathname(), colName)) {
                FileOprUtil.createDir(config.getPathname(), colName);
            }

            //创建本行对应的索引文件
            FileOprUtil.createFile(config.getPathname() + "/" + colName, serialNumber + "");
            FileOprUtil.writeFile(config.getPathname() + "/" + colName, serialNumber + "",
                    colData);
            currCol = config.getColumnMap().get(colName);
            if(colData.length() > currCol.getColWidth()) {
                currCol.setColWidth(colData.length());
                //更新本地的配置文件
                config.writeConfig();
            }
            boolean debug = config.colIsKey(colName);
            if (config.colIsKey(colName)) {
                createKeyIndex(colName, colData, serialNumber+"");
            }
        }

        //等待1ms以防止行序列号重合
        waitOneMs();

    }


    //用于更新表格中增加一列的信息
    public void updateAddCol() {
        String[] currentCols = new File(config.getPathname()).list();
        String[] nowCols = config.getColArrCopy();
        HashMap<String, TableColumn> columnMap = config.getColumnMap();
        HashSet<String> currColHash = arrToHash(currentCols);

        for(int i=0; i<nowCols.length; i++) {
            if(!currColHash.contains(nowCols[i])) {
                addOneCol(columnMap.get(nowCols[i]));
            }
        }

    }

    //用于把删除表格的信息更新到本地
    public void updateDelCol() {
        String[] currentCols = new File(config.getPathname()).list();
        String[] nowCols = config.getColArrCopy();
        HashSet<String> nowColHash = arrToHash(nowCols);
        for(int i=0; i<currentCols.length; i++) {
            if(!currentCols[i].equals(TableInform.configFileName) && !currentCols[i].equals(TableInform.copiedDirName)
                    && !currentCols[i].equals(rowListFileName)) {
                if(!nowColHash.contains(currentCols[i])) {
                    delOneCol(currentCols[i]);
                }
            }
        }
    }


    private void addOneCol(TableColumn col) {
        FileOprUtil.createDir(config.getPathname(), col.getName());
        if(col.getIsKey()) {
            FileOprUtil.createDir(config.getPathname(), col.getName() + "-key");
        }
    }

    private void delOneCol(String colName) {
        FileOprUtil.dropFileOrDir(config.getPathname(), colName);
        if(FileOprUtil.existFile(config.getPathname(), colName + "-key")) {
            FileOprUtil.dropFileOrDir(config.getPathname(), colName + "-key");
        }
    }

    private String[] queryByOneCondition(String colName, String data) {
        if(FileOprUtil.existFile(config.getPathname() + "/" + colName + "-key/" + data)) {
            return new File(config.getPathname() + "/" + colName + "-key/" + data).list();
        } else{
            return new String[0];
        }
    }

    private void generateRowList() {
        FileOprUtil.createDir(config.getPathname(), rowListFileName);
    }

    private void createKeyIndex(String colName, String colData, String serialNumber) {
        if(!FileOprUtil.existFile(config.getPathname(), colName + "-key")) {
            FileOprUtil.createDir(config.getPathname(), colName + "-key");
        }
        if(!FileOprUtil.existFile(config.getPathname() + "/" + colName + "-key", colData)) {
            FileOprUtil.createDir(config.getPathname() + "/" + colName + "-key/", colData);
        }
//        LocalMap.switchMapByPath(config.getPathname() + "/" + colName + "-key/" + colData);
        FileOprUtil.createFile(config.getPathname() + "/" + colName + "-key/" + colData, serialNumber);
//        LocalMap.createReflect(serialNumber, "");
    }

    private HashSet<String> arrToHash(String[] arr) {
        HashSet<String> hash = new HashSet<String>();
        for(int i=0; i<arr.length; i++) {
            hash.add(arr[i]);
        }
        return hash;
    }

    private synchronized void waitOneMs() {
        try {
            this.wait(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class QueryResult {
    String[] result;
    TableInform config;
    int rowPoint = 0;

    public QueryResult(String[] result, TableInform config) {
        this.result = result;
        this.config = config;
    }

    public boolean before() {
        if(rowPoint > 0) {
            rowPoint--;
            return true;
        } else {
            return false;
        }
    }

    public boolean next() {
        if(rowPoint < result.length-1) {
            rowPoint++;
            return true;
        } else {
            return false;
        }
    }

    public boolean first() {
        if(result.length >= 1) {
            rowPoint = 0;
            return true;
        } else {
            return false;
        }
    }

    public String getData(String colunmName) {
        return new TableTerminal(config).getRowData(result[rowPoint], colunmName);
    }


    public String getCurrentRow() {
        return result[rowPoint];
    }
}

