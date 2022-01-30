package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Enumerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Enumeration<T> extends Enumerator<T> {

    private final int type;

    private List<T> tList;
    private LinkedList<T> tLinkedList;

    public Enumeration(T[] list) {
        tList = Arrays.asList(list);
        type = 1;
    }

    Enumeration(Collection<T> list) {
        tList = new ArrayList<>(list);
        type = 1;
    }

    Enumeration(LinkedList<T> list) {
        tLinkedList = list;
        type = 0;
    }

    @Override
    public T get(int index) {
        if (type == 0) {
            return tLinkedList.get(index);
        }
        return tList.get(index);
    }

    public int size() {
        if (type == 0) {
            return tLinkedList.size();
        }
        return tList.size();
    }


}