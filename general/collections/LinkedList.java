package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;

import android.os.Build;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Action;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.IndexedAction;

import java.util.Iterator;
import java.util.function.Consumer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.format;
import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.range;

public class LinkedList<T> implements Iterable<T> {

    private int SIZE = 0;
    private Node head = null;

    LinkedList() {
    }

    public void append(T value) {
        if (isEmpty()) {
            head = new Node(value);
        } else {
            getTempAt(size() - 1).next = new Node(value);
        }
        SIZE++;
    }

    public void insert(int index, T value) {
        if (validateIndex(index)) {

            if (index == 0) {
                Node temp = new Node(value);
                temp.next = head;
                head = temp;
                SIZE++;
                return;
            }

            Node new_node = new Node(value);
            new_node.next = getTempAt(index - 1).next;
            getTempAt(index - 1).next = new_node;

            SIZE++;
        }
    }

    public void replace(int index, T value) {
        if (validateIndex(index)) {
            getTempAt(index).value = value;
        }
    }

    public void replaceFirst(T object, T value) {
        int found = findFirstIndexOf(object);
        if (found != -1) {
            replace(found, value);
        }
    }

    public void replaceLast(T object, T value) {
        int found = findLastIndexOf(object);
        if (found != -1) {
            replace(found, value);
        }
    }

    public void replaceAll(T object, T value) {

        if (findLastIndexOf(object) != -1 && get(findFirstIndexOf(object)) == value) {
            return;
        }

        while (findFirstIndexOf(object) != -1) {
            replace(findFirstIndexOf(object), value);
        }
    }

    public void remove(int index) {
        if (validateIndex(index)) {
            if (index == 0) {
                head = head.next;
                SIZE--;
                return;
            }
            getTempAt(index - 1).next = getTempAt(index - 1).next.next;
            SIZE--;
        }
    }

    public void removeObject(T object) {
        int found = findFirstIndexOf(object);
        if (found != -1) {
            remove(found);
        }
    }

    public void removeAllOf(T object) {
        while (findFirstIndexOf(object) != -1) {
            removeObject(object);
        }
    }

    public T get(int index) {
        if (validateIndex(index)) {
            return getTempAt(index).value;
        }
        return null;
    }

    public int findFirstIndexOf(T object) {
        if (!isEmpty()) {
            int i = -1;
            for (T o : this) {
                i++;
                if (o == object) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int findLastIndexOf(T object) {
        int lastIndex = -1;
        if (!isEmpty()) {
            int i = -1;
            for (T o : this) {
                i++;
                if (o == object) {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }

    public int count(T object) {
        int counter = 0;
        for (T o : this) {
            if (o == object) {
                counter++;
            }
        }
        return counter;
    }

    public int size() {
        return SIZE;
    }

    public void clear() {
        head = null;
        SIZE = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String toString() {
        StringBuilder list = new StringBuilder("LinkedList[");
        forEach((i, o) -> list.append(o).append((i == size() - 1) ? "" : ", "));
        return list + "]";
    }

    private boolean validateIndex(int index) {
        if (index < 0 || index >= SIZE) {
            throw new IndexOutOfBoundsException();
        } else {
            return true;
        }
    }

    private Node getTempAt(int index) {
        Node temp = head;

        for (int i : range(index)) {
            temp = temp.next;
        }

        return temp;
    }

    public void forEach(Action<T> action) {
        for (T t : this) {
            action.run(t);
        }
    }

    public void forEach(IndexedAction<T> action) {
        int i = -1;
        for (T t : this) {
            i++;
            action.run(i, t);
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node temp = head;

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                T value = temp.value;
                temp = temp.next;
                return value;
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(@NonNull Consumer<? super T> action) {
        for (T t : this) {
            action.accept(t);
        }
    }

    private class Node {

        public T value;
        Node previous;
        Node next;

        Node(T value) {
            this.value = value;
        }

        public String toString() {
            return format("Node(value:<>)", value);
        }

        public Node clone() {
            Node n = new Node(value);
            n.previous = previous;
            n.next = next;
            return n;
        }

    }

}