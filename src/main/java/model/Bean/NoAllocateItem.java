package model.Bean;

public class NoAllocateItem implements Comparable<NoAllocateItem>{
    private int begin;
    private int size;

    public NoAllocateItem() {
        ;
    }

    public NoAllocateItem(int begin, int size) {
        this.begin = begin;
        this.size = size;
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

    @Override
    public int compareTo(NoAllocateItem o) {
        return size - o.size;
    }

    @Override
    public String toString() {
        return "[" + begin +
                "-" + (size+begin) +
                ']';
    }
}
