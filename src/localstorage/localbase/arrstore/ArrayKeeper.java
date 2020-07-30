package localbase.arrstore;

import localbase.FileOprUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  用于把程序运行中临时的数组保存到本地
 *  同时在需要的时候把这些被保存下来的数组
 *  引入运行内存中
 */
public class ArrayKeeper {
    /**
     * 将二维数组写入本地
     * @param name 此二维数组的标识名
     * @param arr 被写入本地的二维数组的数据
     */
    public static void writeTwoDim(String name, String[][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += twoDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += oneDimStart + arr[i][j] + oneDimEnd;
            }
            formArr += twoDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }

    /**
     * 将二维数组写入本地
     * @param name 此二维数组的标识名
     * @param arr 被写入本地的二维数组的数据
     */
    public static void writeTwoDim(String name, int[][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += twoDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += oneDimStart + arr[i][j] + oneDimEnd;
            }
            formArr += twoDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }

    /**
     * 将二维数组写入本地
     * @param name 此二维数组的标识名
     * @param arr 被写入本地的二维数组的数据
     */
    public static void writeTwoDim(String name, double[][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += twoDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += oneDimStart + arr[i][j] + oneDimEnd;
            }
            formArr += twoDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }


    /**
     * 将一维数组写入本地
     * @param name 此一维数组的标识名
     * @param arr 被写入本地的一维数组的数据
     */
    public static void writeOneDim(String name, String[] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += oneDimStart + arr[i] + oneDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }

    /**
     * 将一维数组写入本地
     * @param name 此一维数组的标识名
     * @param arr 被写入本地的一维数组的数据
     */
    public static void writeOneDim(String name, int[] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += oneDimStart + arr[i] + oneDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }


    /**
     * 将一维数组写入本地
     * @param name 此一维数组的标识名
     * @param arr 被写入本地的一维数组的数据
     */
    public static void writeOneDim(String name, double[] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += oneDimStart + arr[i] + oneDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }


    /**
     * 将三维数组写入本地
     * @param name 此三维数组的标识名
     * @param arr 被写入本地的三维数组的数据
     */
    public static void writeThreeDim(String name, String[][][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //二维上的数据分隔符
        String ThreeDimStart = "qzjs5bskkoa3"; //三维上的数据分隔符
        String ThreeDimEnd= "qzjs5bskkoaend3"; //三维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += ThreeDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += twoDimStart;
                for(int k=0; k<arr[i][j].length; k++){
                    formArr += oneDimStart + arr[i][j][k] + oneDimEnd;
                }
                formArr += twoDimEnd;
            }
            formArr += ThreeDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }

    /**
     * 将三维数组写入本地
     * @param name 此三维数组的标识名
     * @param arr 被写入本地的三维数组的数据
     */
    public static void writeThreeDim(String name, int[][][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //二维上的数据分隔符
        String ThreeDimStart = "qzjs5bskkoa3"; //三维上的数据分隔符
        String ThreeDimEnd= "qzjs5bskkoaend3"; //三维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += ThreeDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += twoDimStart;
                for(int k=0; k<arr[i][j].length; k++){
                    formArr += oneDimStart + arr[i][j][k] + oneDimEnd;
                }
                formArr += twoDimEnd;
            }
            formArr += ThreeDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }

    /**
     * 将三维数组写入本地
     * @param name 此三维数组的标识名
     * @param arr 被写入本地的三维数组的数据
     */
    public static void writeThreeDim(String name, double[][][] arr) {
        //记录有关此二维数组的信息至本地
        FileOprUtil.createFile(name);
        //根据数组创建出格式化的数组信息
        String oneDimStart = "qzjs5bskkoa1"; //一维上的数据分隔符
        String oneDimEnd= "qzjs5bskkoaend1"; //一维上的数据分隔符
        String twoDimStart = "qzjs5bskkoa2"; //二维上的数据分隔符
        String twoDimEnd = "qzjs5bskkoaend2"; //二维上的数据分隔符
        String ThreeDimStart = "qzjs5bskkoa3"; //三维上的数据分隔符
        String ThreeDimEnd= "qzjs5bskkoaend3"; //三维上的数据分隔符
        String formArr = ""; //格式化的数组数据
        for(int i=0; i<arr.length; i++) {
            formArr += ThreeDimStart;
            for(int j=0; j<arr[i].length; j++) {
                formArr += twoDimStart;
                for(int k=0; k<arr[i][j].length; k++){
                    formArr += oneDimStart + arr[i][j][k] + oneDimEnd;
                }
                formArr += twoDimEnd;
            }
            formArr += ThreeDimEnd;
        }
        //最后把格式化后的信息写入
        FileOprUtil.writeFile(".", name, formArr);
    }



    /**
     * 用于获取本地的str三维数组
     * @param name 选择的数组名称
     * @return
     */
    public static String[][][] getThreeDimStrArr(String name) {
        String formArr = FileOprUtil.readFile(".", name);
        //先解析出二维数组 使其产生一个关于一维数组格式化字符串的数组
        String[] twoDimArr = unpackDimStr(formArr, 3);
        String[][][] result = new String[twoDimArr.length][][];
        /*
         *   把每一维的数据填入
         */
        String[] oneDimArr; //用来三维数组下每个二维数组的数据
        for(int i=0; i<twoDimArr.length; i++) {
            oneDimArr = unpackDimStr(twoDimArr[i], 2);
            result[i] = new String[oneDimArr.length][];
            for(int j=0; j<oneDimArr.length; j++) {
                result[i][j] = unpackDimStr(oneDimArr[j], 1);
            }
        }

        return result;
    }

    /**
     * 用于获取本地的double三维数组
     * @param name 选择的数组名称
     * @return
     */
    public static double[][][] getThreeDimDoubleArr(String name) {
        return transStrArrToDouble(getThreeDimStrArr(name));
    }


    /**
     * 用于获取本地的三维数组
     * @param name 选择的数组名称
     * @return
     */
    public static int[][][] getThreeDimIntArr(String name) {
        return transStrArrToInt(getThreeDimStrArr(name));
    }


    /**
     * 用于获取本地的str二维数组
     * @param name 选择的数组名称
     * @return
     */
    public static String[][] getTwoDimStrArr(String name) {
        String formArr = FileOprUtil.readFile(".", name);
        //先解析出二维数组 使其产生一个关于一维数组格式化字符串的数组
        String[] oneDimArr = unpackDimStr(formArr,2);
        String[][] result = new String[oneDimArr.length][];

        //把每一行的数据填入
        for(int i=0; i<oneDimArr.length; i++) {
            result[i] = unpackDimStr(oneDimArr[i], 1);
        }

        return result;
    }


    /**
     * 用于获取本地的int二维数组
     * @param name 选择的数组名称
     * @return
     */
    public static int[][] getTwoDimIntArr(String name) {
        return transStrArrToInt(getTwoDimStrArr(name));
    }

    /**
     * 用于获取本地的double二维数组
     * @param name 选择的数组名称
     * @return
     */
    public static double[][] getTwoDimDoubleArr(String name) {
        return transStrArrToDouble(getTwoDimStrArr(name));
    }


    /**
     * 用于获取一维数组
     * @param name 需要被解析的数组名称
     * @return
     */
    public static String[] getOneDimStrArr(String name) {
        String formArr = FileOprUtil.readFile(".", name);
        return unpackDimStr(formArr, 1);
    }


    /**
     * 用于获取本地的int一维数组
     * @param name 选择的数组名称
     * @return
     */
    public static int[] getOneDimIntArr(String name) {
        return transStrArrToInt(getOneDimStrArr(name));
    }

    /**
     * 用于获取本地的double一维数组
     * @param name 选择的数组名称
     * @return
     */
    public static double[] getOneDimDoubleArr(String name) {
        return transStrArrToDouble(getOneDimStrArr(name));
    }



    /**
     * 用于解析格式化数组
     * @param str 需要被处理的格式化一维数组
     * @return
     */
    private static String[] unpackDimStr(String str, int dimen) {
        ArrayList<String> temp = new ArrayList<String>();
        String[] data; //一维数组字符串解析成为数组
        //先进行对数组每行数据的解析
        Pattern transer = Pattern.compile("(?<=qzjs5bskkoa" + dimen +")(.*?)(?=qzjs5bskkoaend"+ dimen + ")",Pattern.DOTALL);
        Matcher mat  = transer.matcher(str);
        int count = 0;
        while (mat.find()) {
            temp.add(mat.group());
            count++;
        }
        data = new String[count];
        for(int i=0; i<count; i++) {
            data[i] = temp.get(i);
        }
        return data;
    }


    /**
     * 用于把一个字符串三维数组转化成int三维数组
     * @param arr 需要被转化的字符串三维数组
     * @return 生成的int三维数组
     */
    public static int[][][] transStrArrToInt(String[][][] arr) {
        int[][][] result = new int[arr.length][][];
        for(int i=0; i<arr.length; i++) {
            result[i] = new int[arr[i].length][];
            for(int j=0; j<arr[i].length; j++) {
                result[i] = transStrArrToInt(arr[i]);
            }
        }
        return result;
    }

    /**
     * 用于把一个字符串一维数组在可以转化的情况下转化成double一维数组
     * @param arr 需要被转化的字符串数组
     * @return 转化的结果
     */
    public static int[] transStrArrToInt(String[] arr) {
        int[] result = new int[arr.length];
        for(int i=0; i<arr.length; i++) {
            result[i] = Integer.parseInt(arr[i]);
        }
        return result;
    }


    /**
     * 用于把一个字符串二维数组在可以转化的情况下转化成int二维数组
     * @param arr 需要被转化的字符串数组
     * @return 转化的结果
     */
    public static int[][] transStrArrToInt(String[][] arr) {
        int[][] result = new int[arr.length][];
        for(int i=0; i<arr.length; i++) {
            result[i] = transStrArrToInt(arr[i]);
        }
        return result;
    }


    /**
     * 用于把一个字符串三维数组在可以转化的情况下转化成double三维数组
     * @param arr 需要被转化的字符串数组
     * @return 转化的结果
     */
    public static double[][][] transStrArrToDouble(String[][][] arr) {
        double[][][] result = new double[arr.length][][];
        for(int i=0; i<arr.length; i++) {
            result[i] = transStrArrToDouble(arr[i]);
        }
        return result;
    }

    /**
     * 用于把一个字符串三维数组在可以转化的情况下转化成double二维数组
     * @param arr 需要被转化的字符串数组
     * @return 转化的结果
     */
    public static double[][] transStrArrToDouble(String[][] arr) {
        double[][] result  = new double[arr.length][];
        for(int i=0; i<arr.length; i++) {
            result[i] = transStrArrToDouble(arr[i]);
        }
        return result;
    }


    /**
     * 用于把一个字符串三维数组在可以转化的情况下转化成double一维数组
     * @param arr 需要被转化的字符串数组
     * @return 转化的结果
     */
    public static double[] transStrArrToDouble(String[] arr) {
        double[] result = new double[arr.length];
        for(int i=0; i<arr.length; i++) {
            result[i] = Double.parseDouble(arr[i]);
        }
        return result;
    }
}
