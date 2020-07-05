package com.techupstudio.GeneralUtils.collections;

import com.techupstudio.GeneralUtils.collections.exceptions.EmptyObjectException;

import java.util.Iterator;
import java.util.function.Consumer;

public class Deque<T> implements Iterable<T>{

    private LinkedList<T> DATA = new LinkedList<>();

    Deque(){}

    public void enqueueEnd(T object){ DATA.append(object); }

    public void enqueueStart(T object){ DATA.insert(0,object); }

    public T dequeueEnd(){ T temp = peekBack(); DATA.remove(size()-1); return temp; }

    public T dequeueStart(){ T temp = peekFront(); DATA.remove(0); return temp; }

    public T peekFront(){
        if (isEmpty()){
            try {
                throw new EmptyObjectException("Deque");
            } catch (EmptyObjectException e) {
                e.printStackTrace();
            }
        }
        return DATA.get(0);
    }

    public T peekBack(){
        if (isEmpty()){
            try {
                throw new EmptyObjectException("Deque");
            } catch (EmptyObjectException e) {
                e.printStackTrace();
            }
        }
        return DATA.get(size()-1);
    }

    public boolean isEmpty(){ return DATA.isEmpty();}

    public int size(){ return DATA.size();}

    public String toString(){ return DATA.toString().replace("LinkedList", "Deque");}

    @Override
    public Iterator<T> iterator() { return DATA.iterator(); }

    @Override
    public void forEach(Consumer<? super T> action) { DATA.forEach(action); }
}
