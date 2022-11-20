package model.TableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Bean.PCB;
public class RunningList {
    public static int NUM_TRACKS = 8;//限制的道数
    public static int usedTracks = 0;
    private static List<PCB> runningList = new ArrayList<>();

    public static List<PCB> get() {
        return runningList;
    }

    public static List<PCB> getOnlyRunning() {
        return Collections.unmodifiableList(runningList);
    }


    public static boolean set(List<PCB> runningList) {
        if (runningList.size() < NUM_TRACKS) {
            RunningList.runningList = runningList;
            usedTracks = runningList.size();
            return true;
        } else
            return false;
    }

    public static void setNull() {
        usedTracks = 0;
        RunningList.runningList = new ArrayList<>();
    }

    public static boolean addable() {
        return usedTracks < NUM_TRACKS;
    }

    public static boolean addProcess(PCB PCB) {
        for (PCB p : runningList) {
            if (p.equals(PCB)) {
                return false;
            }
        }
        if (addable()) {
            runningList.add(PCB);
            usedTracks++;
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProcess(PCB PCB) {
        for (PCB p : runningList) {
            if (p.equals(PCB)) {
                runningList.remove(p);
                usedTracks--;
                return true;
            }
        }
        return false;
    }

    public static String Sprint() {
        return "Running队列（usedTracks-" + usedTracks + "）：" + runningList;
    }
}
