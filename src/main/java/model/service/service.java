package model.service;

import model.Bean.Memory;
import model.Bean.NoAllocateItem;
import model.Bean.MemoryFactory;
import model.Bean.PCB;
import model.TableList.NewList;
import model.TableList.ReadyList;
import model.TableList.RunningList;
import model.TableList.TerminatedList;

import java.util.ArrayList;
import java.util.List;
public class service {
    public static void cleanAll() {
        NewList.setNull();
        ReadyList.setNull();
        RunningList.setNull();
        TerminatedList.setNull();
        MemoryFactory.getMemory().getNoAllocateTable().clear();
        MemoryFactory.getMemory().getNoAllocateTable().add(new NoAllocateItem(0,Memory.TOTAL_SIZE));
    }

    public static void printAll() {
        System.out.println(NewList.Sprint());
        System.out.println(ReadyList.Sprint());
        System.out.println(RunningList.Sprint());
        System.out.println(TerminatedList.Sprint());
    }

    public static List<PCB> getNewList() {
        return NewList.getOnlyNew();
    }

    public static List<PCB> getReadyList() {
        return ReadyList.getOnlyReady();
    }

    public static List<PCB> getRunningList() {
        return RunningList.getOnlyRunning();
    }

    public static List<PCB> getTerminatedList() {
        return TerminatedList.getOnlyTerminatedList();
    }

    public static void addNew(PCB PCB) {
        NewList.addProcess(PCB);
    }

    public static void addRunningFromReady(PCB PCB) {
        boolean b = RunningList.addProcess(PCB);
        boolean b1 = ReadyList.deleteProcess(PCB);
        if (!b && b1) {
            throw new RuntimeException("cpu调度失败");
        }
    }

    public static void addTerminatedFromRunning(PCB PCB) throws Exception {
        boolean b = TerminatedList.addProcess(PCB);
        boolean b1 = RunningList.deleteProcess(PCB);
        if (!(b && b1)) {
            String wrongInfo = "";
            if (!b){
                wrongInfo+="添加到Terminated错误，";
            }
            if(!b1){
                wrongInfo+="从Running删除错误，";
            }
            throw new Exception(wrongInfo+"进程未能终止"+ PCB);
        }
    }

    private static void recycleMemory(Memory memory, PCB PCB) {
        memory.recycleMemory(PCB.getBegin(), PCB.getSize());
        PCB.setBegin(-1);
    }

    public static void OrderReadyListByPriority() {
        ReadyList.order();
    }

    public static void UpdateRunningList(Memory memory) {
        List<PCB> PCBS = RunningList.getOnlyRunning();
        List<PCB> changed = new ArrayList<>();
        for (int i = 0; i < PCBS.size(); i++) {
            PCB PCB = PCBS.get(i);
            int runTime = PCB.getRunTime();
            runTime--;
            PCB.setRunTime(runTime);
            if (runTime == 0) {
                PCB.setPCBstate("已回收");
                changed.add(PCB);
                recycleMemory(memory, PCB);
            }
        }

        for (PCB PCB : changed) {
            try {
                addTerminatedFromRunning(PCB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean addableToRun() {
        return RunningList.addable();
    }

    public static void solveHungerByPromoteReadyPriority() {
        ReadyList.getOnlyReady().forEach(process -> {
            if (process.getPriority() != 0)
                process.setPriority(process.getPriority() - 1);
        });
    }

    //将进程加入主存：更改主存内部未分分区表、更改进程的基址属性
    public static void allocateMemory(Memory memory, PCB PCB) throws Exception {
        if (!memory.addable(PCB.getSize())) {
            throw new RuntimeException("内存不够");
        }
        memory.order();
        List<NoAllocateItem> noAllocateTable = memory.getNoAllocateTable();
        int index = -1;
        if (memory.addable(PCB.getSize())) {
            index = memory.getFirstPutableIndex(PCB.getSize());
        }
        if (index == -1) {
            throw new Exception("内存不够");
        }
        NoAllocateItem item = noAllocateTable.get(index);
        PCB.setBegin(item.getBegin());
        item.setBegin(PCB.getBegin() + PCB.getSize());
        item.setSize(item.getSize() - PCB.getSize());
        if (item.getSize() == 0) {
            noAllocateTable.remove(index);
        }
    }

    public static void OrderNewListByPriority() {
        NewList.order();
    }

    public static boolean addableToMemory(Memory memory, PCB PCB) {
        return memory.addable(PCB.getSize());
    }

    public static void addListToReadyFromNew(List<PCB> addedList) {
        for (PCB PCB : addedList) {
            ReadyList.addProcess(PCB);
            NewList.deleteProcess(PCB);
        }
    }
}
