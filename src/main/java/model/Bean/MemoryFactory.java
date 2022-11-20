package model.Bean;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class MemoryFactory {
    private static Constructor<Memory> constructor;
    private static Memory memory;

    static {
        Class<Memory> c = Memory.class;
        try {
            constructor = c.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        constructor.setAccessible(true);
        try {
            memory = constructor.newInstance();
            memory.setNoAllocateTable(new ArrayList<>());
            memory.getNoAllocateTable().add(new NoAllocateItem(0,Memory.TOTAL_SIZE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Memory getMemory() {
        return memory;
    }
}
