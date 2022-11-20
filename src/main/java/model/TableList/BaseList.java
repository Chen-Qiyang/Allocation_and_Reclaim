package model.TableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Bean.PCB;
public class BaseList {
    private List<PCB> List = new ArrayList<>();

    public BaseList() {
    }

    public void order(){
        List.sort((Object o1, Object o2) -> {
            PCB P1 = (PCB) o1;
            PCB P2 = (PCB) o2;
            return P1.getPriority() - P2.getPriority();
        });
    }

    public boolean addProcess(PCB PCB) {
        for (PCB p : List) {
            if (p.equals(PCB)) {
                return false;
            }
        }
        List.add(PCB);
        return true;
    }

    public boolean deleteProcess(PCB PCB) {
        for (PCB p : List) {
            if (p.equals(PCB)) {
                List.remove(p);
                return true;
            }
        }
        return false;
    }

    public String Sprint() {
        return "基本队列：" + List;
    }

    public void setNull() {
        List = new ArrayList<>();
    }

    public List<PCB> getList() {
        return Collections.unmodifiableList(List);
    }

    public void set(List<PCB> newList) {
        List = newList;
    }
}
