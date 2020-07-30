package localbase.localmap;

import localbase.FileOprUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocalMap {
    //当前操作的文件夹的路径
    private static String currentPath = "";
    //当前操作的LocalMap对象的名称
    private static String currentMapName = "";


    /**
     * 用于创建一个本地映射集
     * @param name 需要创建的映射集的名称
     */
    public static void createLocalMap(String name) {
        //需要添加Map的命名规范
        if(fileNameIsValid(name)) {
            if (!FileOprUtil.existDir(name)) {
                FileOprUtil.createDir(name);
                //在指定文件下创建一个map文件作为映射集的标识
                FileOprUtil.createFile(name, "map");
            } else {
                System.out.println("已经存在名为" + name + "的本地映射集");
            }
        } else {
            System.out.println("你输入的映射集名称不能包含" +
                    "? \\ * | \" < > : / 等字符");
        }
    }


    /**
     * 通过已存在的HashMap拷贝形成一个新的本地映射集
     * @param pathname 需要创建的本地映射集的路径
     * @param copiedMap 需要被进行拷贝的HashMap
     */
    public static void createLocalMap(String pathname, HashMap<String, String> copiedMap) {
        //先对当前所在路径做一份拷贝
        String tempPath = new String(currentPath);
        createMapByPath(pathname);
        LocalMap.switchMapByPath(pathname);
        LocalMap.updateLocalMap(copiedMap);
    }


    /**
     * 通过程序内的HashMap实现对当前本地映射集的更新 使本地映射集与程序内的HashMap一致
     * @param hashMap 被用于作为更新依据的数据
     */

    public static void updateLocalMap(HashMap<String, String> hashMap) {

        if(currentMapExist()) {
            if (currentPath.length() != 0) {
                System.out.println("开始根据传入的HashMap更新本地映射集.....:");
                updateAddData(hashMap);
                updateDeleData(hashMap);
                System.out.println("----------------更新完成了---------------");
            } else {
                System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
            }
        }  else {
            loseCurrentMapOpr();
        }
    }


    /**
     * 作为支持方法 以传入的HashMap对象为依据增添更新本地映射集的数据
     * @param hashMap 作为更新依据的HashMap对象
     */
    private static void updateAddData(HashMap hashMap) {
        //首先获得此HashMap的迭代器并对其进行迭代
        Iterator it = hashMap.entrySet().iterator();
        //增添程序HashMap中新增的数据
        while (it.hasNext()) {
            Map.Entry<String, String> data = (Map.Entry<String, String>) it.next();
            //若当前映射集内不存在此条新数据则予以更新
            if(!FileOprUtil.existFile(data.getKey() + ".txt")) {
                LocalMap.createReflect(data.getKey(), data.getValue());
            }
        }
    }


    /**
     * 作为支持方法 以传入的HashMap对象为依据更新删除本地映射集的数据
     * @param hashMap 作为更新依据的HashMap对象
     */
    private static void updateDeleData(HashMap hashMap) {
        File localMap = new File(currentPath);
        //本地映射集文件夹目录下的所有文件列表
        String[] list = localMap.list();
        //用于保存每个关键字的名称
        String key = "";
        for(int i=0; i<list.length; i++) {
            //先检测它是否是保存映射数据的文本文件
            if(list[i].length() - 4 > 0 &&list[i].substring((list[i].length()-3)).equals("txt")) {
                //再检测作为更新依据的HashMap中现在是否存有此条数据
                key = list[i].substring(0, list[i].length() - 4);
                if(!hashMap.containsKey(key)) {
                    LocalMap.dropReflect(key);
                }
            }
        }
    }

    /**
     * 用于将操作目标切换到某个本地映射集上
     * @param pathname 你选择的本地映射集所在路径
     */
    public static void switchToMap(String pathname) {
        if(FileOprUtil.existDir(pathname) && FileOprUtil.existFile(pathname, "map")) {
            currentPath = "./" + pathname;
            currentMapName= pathname;
        }
        else {
            System.out.println("你输入的Map不存在 请先使用LocalMap.createLocalMap(String name)进行创建");
        }
    }


    /**
     * 用于在当前操作的本地映射集上创建一个新的映射关系
     * @param key 创建的映射关系的关键字
     * @param initContent 创建的映射关系中映射值的初始值
     */
    public static void createReflect(String key, String initContent) {
        //查看映射关键字是否符合规范
        if(fileNameIsValid(key)) {
            //查看当前指定映射集是否在程序以外被误删
            if (currentMapExist()) {
                //利用当前目录的长度来确定是否已指定被操作的LocalMap
                if (currentPath.length() != 0) {
                    if (!FileOprUtil.existFile(currentPath, key + ".txt")) {
                        FileOprUtil.createTxt(currentPath, key);
                        FileOprUtil.writeTxt(currentPath, key, initContent);
                    } else {
                        System.out.println("当前已经存在" + key + "对应的映射 你可以使用LocalMap.setReflectData(String key, String data);" +
                                "来修改此映射的内容");
                    }
                } else {
                    System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
                }
            } else {
                loseCurrentMapOpr();
            }
        } else {
            System.out.println("你输入的映射关键字不能包含" +
                    "? \\ * | \" < > : / 等字符");
        }
    }



    /**
     * 用于在当前操作的本地映射集上读取一个映射关系中的映射值
     * @param key 需要获取的映射关系的关键字
     */
    public static String getReflectData(String key) {
        String content = "";
        //利用当前目录的长度来确定是否已指定被操作的LocalMap
        if(currentMapExist()) {
            if (currentPath.length() != 0) {
                if (FileOprUtil.existFile(currentPath, key + ".txt")) {
                    content = FileOprUtil.readTxt(currentPath, key);
                } else {
                    System.out.println("不存在" + key + "对应的映射 你可以使用LocalMap.createReflect(String key, String initContent);" +
                            "来创建新的映射关系");
                }
            } else {
                System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
            }
        }  else {
            loseCurrentMapOpr();
        }
        return content;
    }


    /**
     * 用于在当前操作的本地映射集上删除一个映射关系
     * @param key 需要删除的映射关系的关键字
     */
    public static void dropReflect(String key) {
        //利用当前目录的长度来确定是否已指定被操作的LocalMap
        if(currentMapExist()) {
            if (currentPath.length() != 0) {
                if (FileOprUtil.existFile(currentPath, key + ".txt")) {
                    FileOprUtil.dropFileOrDir(currentPath, key + ".txt");
                } else {
                    System.out.println("未找到" + key + "对应的映射");
                }
            } else {
                System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
            }
        }  else {
            loseCurrentMapOpr();
        }
    }


    /**
     * 用于删除当前操作的映射集
     */
    public static void dropCurrentMap() {
        //利用当前目录的长度来确定是否已指定被操作的LocalMap
        if(currentPath.length() != 0 && FileOprUtil.existDir(currentMapName)) {
            FileOprUtil.dropFileOrDir(currentMapName);
            currentPath = "";
            currentMapName = "";
        }
        else if(currentPath.length() == 0) {
            System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
        }
    }


    /**
     * 用于设置当前操作的本地映射集中一个映射的数据
     * @param key
     * @param data
     */
    public static void setReflectData(String key, String data) {

        if(currentMapExist()) {
            //利用当前目录的长度来确定是否已指定被操作的LocalMap
            if (currentPath.length() != 0) {
                if (FileOprUtil.existFile(currentPath, key + ".txt")) {
                    FileOprUtil.writeTxt(currentPath, key, data);
                } else {
                    System.out.println("不存在" + key + "对应的映射 你可以使用LocalMap.createReflect(String key, String initContent);" +
                            "来创建新的映射关系");
                }
            } else {
                System.out.println("你当前未指定被操作Map 请先使用LocalMap.switchToMap(String name);");
            }
        }  else {
            loseCurrentMapOpr();
        }
    }

    /**
     * 用于创建一个本地映射集
     * @param pathname 需要创建的映射集所在的目录
     */
    public static void createMapByPath(String pathname) {
        //需要添加Map的命名规范
        String[] pathCell = pathname.split("/");
        String name = pathCell[pathCell.length-1];
        if(fileNameIsValid(name)) {
            int pathLength = pathname.length() - name.length(); //求出目录部分的长度
            if (!FileOprUtil.existDir(pathname.substring(0, pathLength), name) ||
                !FileOprUtil.existFile(pathname, "map")) {
                FileOprUtil.createDir(pathname.substring(0, pathLength), name);
                //在指定文件下创建一个map文件作为映射集的标识
                FileOprUtil.createFile(pathname, "map");
            } else {
                System.out.println("已经存在对应" + pathname + "的本地映射集");
            }
        } else {
            System.out.println("你输入的映射集名称不能包含" +
                    "? \\ * | \" < > : / 等字符");
        }
    }


    /**
     * 用于把某个本地映射集转化为程序中的HashMap
     * @param pathname
     * @return
     */
    public static HashMap<String, String> getMap(String pathname) {
        //首先先查找是否存在指定位置下的映射集
        if(FileOprUtil.existDirInPath(pathname) && FileOprUtil.existFile(pathname, "map")) {
            HashMap<String, String> result = new HashMap<String, String>();
            File localMap = new File(pathname);

            //用于保存进行操作前的映射集位置 以便完成操作后切回去
            String tempPath = new String(currentPath);
            //目录下所有的文件列表
            String[] list = localMap.list();
            //切换至此映射集以便操作
            LocalMap.switchMapByPath(pathname);
            //用于保存迭代中映射关键字的名称
            String key = "";
            for(int i=0; i<list.length; i++) {
                if(list[i].length() - 4 > 0 &&list[i].substring((list[i].length()-3)).equals("txt")) {
                    key = list[i].substring(0, list[i].length() - 4);
                    result.put(key, LocalMap.getReflectData(key));
                }
            }
            return result;
        } else {
            System.out.println("不存在指定路径下的映射集");
            return null;
        }
    }


    /**
     * 用于将操作目标切换到某个本地映射集上
     * @param pathname 你选择的本地映射集所在的目录
     */
    public static void switchMapByPath(String pathname) {
        if(FileOprUtil.existDir(pathname)) {
            currentPath = pathname;
            String[] pathCell = pathname.split("/");
            String name = pathCell[pathCell.length-1];
            currentMapName= name;
        }
        else {
            System.out.println("你输入的Map不存在 请先使用LocalMap.createLocalMap(String name)进行创建");
        }
    }


    public static boolean fileNameIsValid(String name) {
        boolean valid = true;
        if(name.contains("?")
                || name.contains("\\")
                || name.contains("*")
                || name.contains("|")
                || name.contains("\"")
                || name.contains("<")
                || name.contains(">")
                || name.contains(":")
                || name.contains("/")
                || name.contains("-")
                || name.length() > 250
        ) {
            valid = false;
        }
        return valid;
    }

    //判断在本地程序之外是否有别的进程把当前操作的映射集删除了
    private static boolean currentMapExist() {
        return currentMapName.length() == 0 || FileOprUtil.existDirInPath(currentPath);
    }

    //当丢失进程时所做的操作
    private static void loseCurrentMapOpr() {
        System.out.println("当前映射集所在的文件夹被其它进程误删" +
                " 已丢失作为操作对象的映射集");
        currentPath = "";
        currentMapName = "";
    }

    public static void main(String[] args) {
        LocalMap.switchMapByPath("./233");
        LocalMap.dropCurrentMap();
        LocalMap.createMapByPath("./233");
        LocalMap.switchMapByPath("./233");
        LocalMap.createReflect("PI", "3.1415926");
        LocalMap.createReflect("e", "2.753123");
        LocalMap.createReflect("bi", "2312313333");
        LocalMap.dropReflect("bi");
        System.out.println(LocalMap.getReflectData("e"));
        HashMap<String, String> myMap = LocalMap.getMap("./233");
        System.out.println(myMap.get("e"));
        myMap.put("qwq", "2020");
        myMap.remove("e");
        LocalMap.updateLocalMap(myMap);
        System.out.println(LocalMap.getReflectData("qwq"));
        System.out.println(LocalMap.getReflectData("e"));
    }
}
