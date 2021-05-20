package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;

import androidx.annotation.NonNull;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Action;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Enumerable;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Enumerator;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.IndexedAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.range;

public class Items<T> extends ArrayList<T> implements Enumerable<T> {

    public Items() {
        super();
    }

    public Items(T[] collection) {
        super(Arrays.asList(collection));
    }

    public Items(Collection<T> collection) {
        super(collection);
    }

    public void appendBack(Collection<T> collection) {
        addAll(collection);
    }

    public void appendFront(Collection<T> collection) {
        addAll(0, collection);
    }

    public void appendBack(T item) {
        add(item);
    }

    public void appendFront(T item) {
        add(0, item);
    }

    public Items<T> joinBack(Collection<T> obj) {
        Items<T> result = clone();
        result.addAll(obj);
        return result;
    }

    public Items<T> joinFront(Collection<T> collection) {
        Items<T> result = clone();
        result.addAll(0, collection);
        return result;
    }

    public T pop(int index) {
        if (isValidIndex(index)) return remove(index);
        return null;
    }

    public T popFront() {
        return pop(0);
    }

    public T popBack() {
        return pop(size() - 1);
    }

    public T peek(int index) {
        if (isValidIndex(index)) return get(index);
        return null;
    }

    public T peekFront() {
        return peek(0);
    }

    public T peekBack() {
        return peek(size() - 1);
    }

    public void assign(Collection<T> collection) {
        clear();
        addAll(collection);
    }

    public void assign(T[] collection) {
        clear();
        addAll(Arrays.asList(collection));
    }

    public void insert(int index, T item) {
        add(index, item);
    }

    public void update(int index, T item) {
        set(index, item);
    }

    public int updateAll(T oldItem, T newItem) {
        Variable<Integer> count = new Variable<>(0);
        clone().forEach((i, o) -> {
            if (o == oldItem) {
                update(i, newItem);
                count.setValue(count.getValue() + 1);
            }
        });
        return count.getValue();
    }

    public void removeAll(T item) {
        while (contains(item)) remove(item);
    }

    public int firstIndexOf(T obj) {
        return indexOf(obj);
    }

    public boolean equal(Items<T> items) {
        return Arrays.equals(toArray(), items.toArray());
    }

    public boolean contains(T[] collection) {
        return contains(Arrays.asList(collection));
    }

    public boolean isSuperSetOf(Collection<T> collection) {
        return containsAll(collection);
    }

    public boolean isSubSetOf(Collection<T> collection) {
        return collection.containsAll(this);
    }

    public int count(T item) {
        List<T> dummy = asList();
        int count = 0;
        while (dummy.contains(item)) {
            count++;
            dummy.remove(dummy.lastIndexOf(item));
        }
        return count;
    }

    public boolean isValidIndex(int index) {
        return index > -1 && index < size();
    }

    public boolean hasItemAt(int index) {
        return isValidIndex(index) && get(index) != null;
    }

    public Set<T> asSet() {
        return new HashSet<T>(this);
    }

    public List<T> asList() {
        return new ArrayList<>(this);
    }

    public Items<T> toSet() {
        return new Items<T>(asSet());
    }

    public Items<T> reverse() {
        Items<T> n = new Items<>();
        for (int i : range(size())) {
            n.add(peek(size() - (i + 1)));
        }
        return n;
    }

    public Items<T> sample(int size) {
        return sample(0, size);
    }

    public Items<T> sample(int beginIndex, int endIndex) {
        Items<T> n = new Items<>();
        Items<T> clone = clone();
        for (int i : range(beginIndex, endIndex)) {
            n.add(clone.peek(i));
        }
        return n;
    }

    public Items<T> randSample(int size) {
        List<Integer> indexes = Arrays.asList(Funcs.range(size()));
        Items<T> result = new Items<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(indexes.size());
            result.add(peek(indexes.remove(index)));
        }
        return result;
    }

    public List<Items<T>> randSample(int size, int groups) {
        List<Items<T>> result = new ArrayList<>();
        for (int i = 0; i < groups; i++) {
            result.add(randSample(size));
        }
        return result;
    }

    public void forEach(Action<T> action) {
        Funcs.forEach(this, action);
    }

    public void forEach(IndexedAction<T> action) {
        Funcs.forEach(this, action);
    }

    @Override
    public Enumerator<T> enumerator() {
        return new Enumeration<T>(this);
    }

    @NonNull
    public Items<T> clone() {
        return new Items<T>(new ArrayList<>(this));
    }

    @NonNull
    @Override
    public String toString() {
        return "Items<" + super.toString() + ">";
    }
}
