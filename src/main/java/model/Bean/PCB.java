package model.Bean;

import java.util.Objects;
public class PCB {
    private String pid;
    private int runTime;
    private int priority;
    private int size;
    private int begin;
    private String PCBstate;

    private PCB() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public String getPCBstate() {
        return PCBstate;
    }

    public void setPCBstate(String PCBstate) {
        this.PCBstate = PCBstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PCB))
            return false;
        PCB PCB = (PCB) o;
        return pid.equals(PCB.pid);
    }

    @Override
    public String toString() {
        return  pid  +
                "：剩余时间 =" + runTime +
                ", priority=" + priority +
                ", size=" + size +
                ", begin=" + begin +
                ", PCBstate='" + PCBstate +
                '\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, runTime, priority,  size, begin, PCBstate);
    }
}
