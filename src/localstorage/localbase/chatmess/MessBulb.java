package localbase.chatmess;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 用于保存一段聊天信息
 * 可以按输入顺序保存文字-图片组成的聊天记录
 */
public class MessBulb implements Serializable {
    //用来存储消息气泡内部的图片或文字内容
    private LinkedList<InfoNode> infoList;
    //用来记录是谁发的信息
    public String speaker = null;
    public String time = null;
    /**
     *  用来创建一个普通的信息气泡
     * @param speaker 发言人
     */
    public MessBulb(String speaker, String time) {
        this.time = time;
        this.speaker = speaker;
        infoList = new LinkedList<InfoNode>();
    }

    /**
     * 根据现有的信息列表来创建一个信息气泡
     * @param copiedInfo 需要被复刻的信息列表 可以是java.util包下任何实现了Collection集合类接口的信息形式
     * @param speaker 发言人
     */
    public MessBulb(String speaker, String time, Collection<InfoNode> copiedInfo) {
        this.speaker = speaker;
        this.time = time;
        this.infoList = new LinkedList<InfoNode>();
        infoList.addAll(copiedInfo);
    }

    /**
     * 根据现有的信息列表来创建一个信息气泡
     * @param infoList 需要被复刻的信息列表
     * @param speaker 发言人
     */
    public MessBulb(String speaker, String time, LinkedList<InfoNode> infoList) {
        this.speaker = speaker;
        this.time = time;
        this.infoList = infoList;
    }

    public LinkedList<InfoNode> getInfoList() {
        return infoList;
    }

    public InfoNode getFirst() {
        return infoList.getFirst();
    }

    public InfoNode getLast() {
        return infoList.getLast();
    }

    public InfoNode getNode(int index) {
        return infoList.get(index);
    }

    public InfoNode remove(int index) {
        return infoList.remove(index);
    }

    public void remove(InfoNode info) {
        infoList.remove(info);
    }

    public void addToHead(InfoNode info) {
        infoList.addFirst(info);
    }

    public void addToLast(InfoNode info) {
        infoList.addLast(info);
    }

    public void addToIndex(int index, InfoNode info) {
        infoList.add(index, info);
    }

    public String getSpeaker() {return speaker;}

    public String getTime() {return time;}

    /**
     * 用于返回此聊天气泡中单个文段和图片的总数量
     * @return
     */
    public int getSize() {return infoList.size();}

}


/**
 * 用来保存聊天信息中单个图片或文本的信息
 */
class InfoNode implements Serializable{

    /*
     * 用来表示此节点中存在的是文本还是图片
     */
    public static final int IMG_TYPE = 1;
    public static final int STR_TYPE = 0;
    private int type = 0;

    private Image img = null;
    private String str = null;

    public InfoNode(String str) {
        type = STR_TYPE;
        this.str = str;
    }

    public InfoNode(Image img) {
        type = IMG_TYPE;
        this.img = img;
    }

    public Image getImg() {
        return img;
    }

    public int getType() {
        return type;
    }

    public String getStr() {
        return str;
    }
}
