package model.TableList;

import java.util.List;
import model.Bean.PCB;
public class NewList {
    private static BaseList list = new BaseList();

    public static List<PCB> getOnlyNew() {
        return list.getList();
    }

    public static void set(List<PCB> newList) {
        list.set(newList);
    }

    public static void setNull() {
        list.setNull();
    }

    public static boolean addProcess(PCB PCB) {
        return list.addProcess(PCB);
    }

    public static boolean deleteProcess(PCB PCB) {
        return list.deleteProcess(PCB);
    }

    public static String Sprint() {
        return "New队列：" + list.getList();
    }

    public static void order(){
        list.order();
    }
}
