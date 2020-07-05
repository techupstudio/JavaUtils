package com.techupstudio.utils.general.collections;


public class Stack<T>{

    protected MasterList<T> list = new MasterList<>();

    public void push(T value){ list.append(value); }

    public T pop(){
        T top = list.poplast();
        if (top != null) list.remove(list.size()-1);
        return top;
    }

    public T peekTop(){ return list.poplast(); }

    public int size(){ return list.size(); }

    public boolean isEmpty(){return list.isEmpty();}

    public void clear() { list.clear(); }

    public String toString(){ return list.toString();}

}
