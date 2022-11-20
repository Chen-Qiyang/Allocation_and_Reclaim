package model.Bean;

import java.lang.reflect.Constructor;
public class PCBFactory {
    private static Constructor<PCB> constructor;
    private static PCB PCB;

    static {
        Class<PCB> c = PCB.class;
        try {
            constructor = c.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        constructor.setAccessible(true);
    }

    public static PCB CreateProcess() {
        PCB PCB = null;
        try {
            PCB = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (PCB != null) {
            //初始化进程类的元素
            PCB.setBegin(-1);
            PCB.setPCBstate(null);
            PCB.setPCBstate("未分配");
        }
        return PCB;
    }

    public static PCB CreateProcess(String pid, int runTime, int priority, int size) {
        PCB PCB = CreateProcess();
        PCB.setPid(pid);
        PCB.setRunTime(runTime);
        PCB.setPriority(priority);
        PCB.setSize(size);
        return PCB;
    }
}
