package model.TableList;

import java.util.List;
import model.Bean.PCB;
public class TerminatedList {
    private static BaseList list = new BaseList();

    public static List<PCB> getOnlyTerminatedList() {
        return list.getList();
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
        return "Terminated队列：" + list.getList();
    }
}
