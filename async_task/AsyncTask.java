package com.techupstudio.otc_chingy.mychurch.utils.async_task;

public abstract class AsyncTask<Param, Result> {


    protected AsyncTask() {
    }

    public abstract void doBeforeTask();

    public abstract Result doInBackGround(Param[] args);

    public abstract void doAfterTask(Result result);

    public void execute(final Param[] args) {

        doBeforeTask();

        Thread thread = new Thread(() -> doAfterTask(doInBackGround(args)));

        thread.start();

    }


}
