package localbase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;

/**
 * 用来完成一些基本的基于windows文件管理系统的操作
 */
public class FileOprUtil {

    /**
     * 用于在本文件所在目录下创建一个文件夹
     * @param name 创建出的文件夹的名称
     */
    public static void createDir(String name) {
        File file = new File(name);
        file.mkdir();
    }


    /**
     * 用于在指定目录下创建一个新的文件夹
     * @param pathname 需要创建文件夹的目录
     * @param dirName 需要创建的文件夹名称
     */
    public static void createDir(String pathname, String dirName) {
        File file = null;
        if(pathname.charAt(pathname.length() - 1) != '/') {
            file = new File(pathname + "/" + dirName);
        } else {
            file = new File(pathname + dirName);
        }
        file.mkdir();
    }


    /**
     * 用于创建一个指定路径的文件夹
     * @param pathname 文件夹的指定路径
     */
    public static void createDirInPath(String pathname) {
        File file = new File(pathname);
        file.mkdir();
    }



    /**
     * 用于在当前项目目录下创建一个文本
     * @param name 创建出的文本的名称
     */
    public static void createTxt(String name) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter( name + ".txt"));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 用于在指定目录下创建一个文本
     * @param pathname 需要创建文本的目录路径
     * @param name 创建出的文本的名称
     */
    public static void createTxt(String pathname, String name) {
        try {
            //完整的路径
            String totalPath = "";
            if(pathname.charAt(pathname.length() - 1) != '/') {
                totalPath = pathname + "/" + name + ".txt";
            } else {
                totalPath = pathname + name + ".txt";
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(totalPath));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用于在指定路径下创建一个空文件
     * @param pathname 需要创建的空文件的路径
     */
    public static void createFileInPath(String pathname) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathname));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用于在当前项目目录下创建一个空文件
     * @param fileName 需要创建的文件名称
     */
    public static void createFile(String fileName) {
        writeFile(".", fileName, "");
    }


    /**
     * 用于在指定目录下创建一个空文件
     * @param path 指定创建文件的目录
     * @param fileName 需要创建的文件的名称
     */
    public static void createFile(String path, String fileName) {
        writeFile(path, fileName, "");
    }


    /**
     * 用于在指定目录下删除一个文件或文件夹
     * @param pathname 指定目录的路径
     * @param fileName 要删除的文件或文件夹的名称
     */
    public static void dropFileOrDir(String pathname, String fileName) {
        /*
         * 由于的delete()方法不能删除非空文件夹 所以得进行改造
         * 具体的方法是 如果要被删除的文件不是文件夹 直接删除即可
         * 否则 先删除其内部的所有文件 然后再回来删除当前的这个文件
         */
        //完整的路径
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + fileName;
        } else {
            totalPath = pathname + fileName;
        }
        File objFile = new File(totalPath);
        if(objFile.exists()) {
            if(objFile.isDirectory()) {
                String[] fileList = objFile.list();
                for(int i=0; i<fileList.length; i++) {
                    dropFileOrDir(totalPath, fileList[i]);
                }
            }
            objFile.delete();
        }
    }


    /**
     * 用于在当前目录下删除一个文件或文件夹
     * @param fileName 要删除的文件或文件夹
     */
    public static void dropFileOrDir(String fileName) {
        File objFile = new File(fileName);
        if(objFile.exists()) {
            if(objFile.isDirectory()) {
                String[] fileList = objFile.list();
                for(int i=0; i<fileList.length; i++) {
                    dropFileOrDir(fileName, fileList[i]);
                }
            }
            objFile.delete();
        }
    }


    /**
     * 用于删除指定路径的文件或文件夹
     * @param pathname 文件或文件夹的路径
     */
    public static void dropFileOrDirInPath(String pathname) {
        File objFile = new File(pathname);
        if(objFile.exists()) {
            if(objFile.isDirectory()) {
                String[] fileList = objFile.list();
                for(int i=0; i<fileList.length; i++) {
                    dropFileOrDir(pathname, fileList[i]);
                }
            }
            objFile.delete();
        }
    }


    /**
     * 用于寻索文件所在目录下是否存在某个名称的文件
     * @param fileName 要判定是否存在的文件名
     * @return 是否存在此文件
     */
    public static boolean existFile(String fileName) {
        boolean exist = false;
        File objFile = new File(fileName);
        if(objFile.exists()) {
            exist = true;
        }
        return exist;
    }


    /**
     * 用于寻索一个目录下是否存在某个名称的文件
     * @param pathname 被检索的目录路径
     * @param fileName 要判定是否存在的文件名
     * @return 是否存在此文件
     */
    public static boolean existFile(String pathname, String fileName) {
        boolean exist = false;
        //完整的路径
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + fileName ;
        } else {
            totalPath = pathname + fileName;
        }
        File objFile = new File(totalPath);
        if(objFile.exists()) {
            exist = true;
        }
        return exist;
    }


    /**
     * 用于检索是否存在对应路径的文件
     * @param pathname 文件的路径
     * @return 是否存在此文件
     */
    public static boolean existFileInPath(String pathname) {
        boolean exist = false;
        File objFile = new File(pathname);
        if(objFile.exists()) {
            exist = true;
        }
        return exist;
    }



    /**
     * 用于寻索一个目录下是否存在某个名称的文件
     * @param dirName 要判定是否存在的文件夹名
     * @return 是否存在此文件
     */
    public static boolean existDir(String dirName) {
        boolean exist = false;
        File objFile = new File(dirName);
        if(objFile.isDirectory()) {
            exist = true;
        }
        return exist;
    }


    /**
     * 用于寻索一个目录下是否存在某个名称的文件
     * @param pathname 被检索的目录路径
     * @param dirName 要判定是否存在的文件夹名
     * @return 是否存在此文件
     */
    public static boolean existDir(String pathname, String dirName) {
        boolean exist = false;
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + dirName ;
        } else {
            totalPath = pathname + dirName;
        }
        File objFile = new File(totalPath);
        if(objFile.isDirectory()) {
            exist = true;
        }
        return exist;
    }


    /**
     * 用于通过直接输入文件夹的路径来判断其是否存在的方法
     * @param pathname 要被检索的文件夹的路径
     * @return 是否在此路径上存在此文件夹
     */
    public static boolean existDirInPath(String pathname) {
        File checked = new File(pathname);
        if(checked.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 用于读取指定目录下的文本内容
     * @param pathname 被检索的目录路径(相对路径亦可)
     * @param txtName 需要被读取的txt文件名称(不带.txt)
     * @return 此文本的内容
     */
    public static String readTxt(String pathname, String txtName) {
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + txtName ;
        } else {
            totalPath = pathname + txtName;
        }
        return readTxtInPath(totalPath);
    }

    /**
     * 用于读取当前目录下一个文本中的内容
     * @param txtName 此文本的名称(不带.txt)
     * @return 此文本的内容
     */
    public static String readTxt(String txtName) {
        return readTxtInPath(txtName);
    }


    /**
     * 用于读取指定路径的文件
     * @param pathname 指定文件的路径(不带.txt)
     * @return 此文本的内容
     */
    public static String readTxtInPath(String pathname) {
        String content = "";
        String firstLine = ""; //第一行的文件内容
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathname + ".txt"));
            firstLine = br.readLine();
            String tempContent = firstLine;  //用于暂时存储每一行的数据
            while (tempContent != null) {
                count++;
                content += tempContent + "\n";
                tempContent = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(count == 1) {
            //如果只有一行数据就返回不带换行符的一行文字
            return firstLine;
        } else {
            return content;
        }
    }




    /**
     * 用于某个指定路径的文本进行写入
     * @param pathname 需要写入的文本的所在路径(不带.txt)
     * @param content 需要写入的文本的内容
     */
    public static void writeTxtInPath(String pathname, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter( pathname + ".txt"));
            bw.write(content);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于写入存在于某个目录下的特定文本的内容
     * @param pathname 需要写入文本所在的目录
     * @param txtName 需要写入文本的名称(不带.txt)
     * @param content 需要写入文本的内容
     */
    public static void writeTxt(String pathname, String txtName, String content) {
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + txtName ;
        } else {
            totalPath = pathname + txtName;
        }
        writeTxtInPath(totalPath, content);
    }


    /**
     * 用于写入当前所在目录下某个文本的内容
     * @param txtName 此文本的名称(不带.txt)
     */
    public static void writeTxt(String txtName, String content) {
        writeTxtInPath(txtName, content);
    }

    /**
     * 用于某个特定的文本进行写入
     * @param pathname 需要写入的目录路径(相对路径亦可)
     * @param fileName 需要被写入的文件名称
     * @param content 需要写入的内容
     */
    public static void writeFile(String pathname, String fileName, String content) {
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + fileName ;
        } else {
            totalPath = pathname + fileName;
        }
        writeFileInPath(totalPath, content);
    }


    /**
     * 用于写入信息至当前目录下的的某个文件
     * @param fileName 需要被写入的文件名称
     * @param content 需要写入的内容
     */
    public static void writeFile(String fileName, String content) {
        writeFileInPath(fileName, content);
    }


    /**
     * 用于写入某一路径下的文本以输入的内容
     * @param pathname 文件所在的路径
     * @param content 需要被写入的内容
     */
    public static void writeFileInPath(String pathname, String content) {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(pathname));
            bw.write(content);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    /**
     * 用于寻索一个目录下是否存在某个名称的文件
     * @param pathname 被检索的目录路径(相对路径亦可)
     * @param fileName 需要被读取的txt文件名称
     * @return 此文本的内容
     */
    public static String readFile(String pathname, String fileName) {
        String totalPath = "";
        if(pathname.charAt(pathname.length() - 1) != '/') {
            totalPath = pathname + "/" + fileName ;
        } else {
            totalPath = pathname + fileName;
        }
        return readFileInPath(totalPath);
    }


    /**
     * 用于寻索当前目录下是否存在某个名称的文件
     * @param fileName 需要被读取的txt文件名称
     * @return 此文本的内容
     */
    public static String readFile(String fileName) {
        return readFileInPath(fileName);
    }


    /**
     * 读取指定路径下文件中的内容
     * @param pathname 文件的指定路径
     * @return 文件中的内容
     */
    public static String readFileInPath(String pathname) {
        String content = "";
        String firstLine = ""; //第一行的文件内容
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathname));
            firstLine = br.readLine();
            String tempContent = firstLine;  //用于暂时存储每一行的数据
            while (tempContent != null) {
                count++;
                content += tempContent + "\n";
                tempContent = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(count == 1) {
            //如果只有一行数据就返回不带换行符的一行文字
            return firstLine;
        } else {
            return content;
        }
    }


    /**
     * 获取指定路径下的图片
     * @param pathname
     * @return
     */
    public static Image readImage(String pathname) {
        Image image = null;
        try {
            image = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }


    /**
     *
     * @param pathname 图片被写入的路径 最好在路径末尾(即图片对应名称)之后 加上".png" 方便文件系统识别
     * @param image 要写入的图片对象
     */
    public static void writeImageWithPng(String pathname, Image image) {
        try {
            ImageIO.write((RenderedImage) image, "png", new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pathname 图片被写入的路径 最好在路径末尾(即图片对应名称)之后 加上".就jpg" 方便文件系统识别
     * @param image 要写入的图片对象
     */
    public static void writeImageWithJPg(String pathname, Image image) {
        try {
            ImageIO.write((RenderedImage) image, "jpg", new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        Image img1 = readImage("W:\\Up.png");
//        writeImageWithPng("S:\\233", img1);
//    }
}
