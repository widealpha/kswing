package localbase.chatmess;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 用于保存一组成段的聊天信息(比如和xxx之间的聊天信息可以作为一个集合保存在其中)
 */
public class ChatMessSet implements Serializable {
    private static final long serialVersionNumber = 1L;
    //用来存储消息气泡内部的图片或文字内容
    private LinkedList<MessBulb> record;
    //用来说明这个记录的名字(比如和某某人的聊天记录)
    public String recordName = null;
    /**
     *  用来创建一个聊天记录集
     * @param recordName 这个聊天记录集的名字
     */
    public ChatMessSet(String recordName) {
        this.recordName = recordName;
        record = new LinkedList<MessBulb>();
    }

    /**
     * 根据现有的信息列表来创建一个聊天记录集
     * @param copiedReco 需要被复刻的聊天记录集 可以是java.util包下任何实现了Collection集合类接口的信息形式
     * @param recordName 这个聊天记录集的名字
     */
    public ChatMessSet(String recordName, Collection<MessBulb> copiedReco) {
        this.recordName = recordName;
        this.record = new LinkedList<MessBulb>();
        record.addAll(copiedReco);
    }

    /**
     * 根据现有的信息列表来创建一个聊天记录集
     * @param recoList 需要被复刻的聊天记录
     * @param recordName 此聊天记录集的名字
     */
    public ChatMessSet(String recordName, LinkedList<MessBulb> recoList) {
        this.recordName = recordName;
        this.record = recoList;
    }

    public LinkedList<MessBulb> getInfoList() {
        return record;
    }

    public MessBulb getFirst() {
        return record.getFirst();
    }

    public MessBulb getLast() {
        return record.getLast();
    }

    public MessBulb getBulb(int index) {
        return record.get(index);
    }

    public MessBulb remove(int index) {
        return record.remove(index);
    }

    public void remove(MessBulb info) { record.remove(info); }

    public void addToHead(MessBulb info) {record.addFirst(info); }

    public void addToLast(MessBulb info) { record.addLast(info); }

    public void addToIndex(int index, MessBulb info) { record.add(index, info); }

    public String getRecordName() {return recordName;}

    /**
     * 用于获取当前聊天记录中的聊天气泡个数
     * @return 聊天气泡的数量
     */
    public int getSize() {return record.size();}

    /**
     * 把当前记录信息按给予的路径写入本地
     * @param pathName 所指定的路径(也可以直接输入指定的名字 记录会被保存在当前源文件所在目录)
     */
    public void writeToLocal(String pathName) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(pathName)));
            objectOutputStream.writeObject(this);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据输入的路径读取指定路径下的记录信息并返回
     * @param pathName 需要被读取的记录所在路径
     * @return 读取的结果 如果读取失败则返回null
     */
    public static ChatMessSet readLocalSet(String pathName) {
        ObjectInputStream objectInputStream = null;
        ChatMessSet result = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(pathName)));
            result =  (ChatMessSet) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                return result;
            }
        }
    }

//    public static void main(String[] args) {
//        MessBulb mess1 = new MessBulb("Lisa", "2019-11-2");
//        MessBulb mess2 = new MessBulb("WanE", "2019-11-2");
//        mess1.addToHead(new InfoNode("你好"));
//        mess2.addToHead(new InfoNode("你好!"));
//        mess2.addToHead(new InfoNode("我喜欢椒盐蛋炒饭!"));
//        ChatMessSet record = new ChatMessSet("聊天记录");
//        record.addToHead(mess1);
//        record.addToHead(mess2);
//        record.writeToLocal("第一次聊天");
//        ChatMessSet record1 = ChatMessSet.readLocalSet("第一次聊天");
//        System.out.println(record1.getFirst().getFirst().getStr() + record1.getBulb(1).getFirst().getStr());
//
//        //从对应路径为"./1145"的文件下提取聊天记录
//        ChatMessSet record1145 = readLocalSet("./1145");
//        //用于暂存单个聊天气泡
//        MessBulb bulb = null;
//        //用于暂存单个文段或图片节点
//        InfoNode node = null;
//        for(int i=0; i<record1145.getSize(); i++) {
//            bulb = record1145.getBulb(i);
//            for(int j=0; j<bulb.getSize(); j++) {
//                //表示如果此节点存储的是字符串信息
//                if(node.getType() == InfoNode.STR_TYPE) {
//                    //表示对提取出来的文段作对应操作
//                    strOpr(node.getStr());
//                }
//
//                if(node.getType() == InfoNode.IMG_TYPE) {
//                    //表示对提取出来的单个图片作对应操作
//                    imgOpr(node.getImg());
//                }
//            }
//        }
//    }
}
