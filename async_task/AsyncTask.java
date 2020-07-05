package com.techupstudio.utils.async_task;

public abstract class AsyncTask<Param, Result> {

    private Thread thread;

    protected AsyncTask(){  }

    public abstract void doBeforeTask();

    public abstract Result doInBackGround(Param[] args);

    public abstract void doAfterTask(Result result);

    public void execute(Param[] args){

        doBeforeTask();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doAfterTask(doInBackGround(args));
            }
        });

        thread.run();

    }


}
