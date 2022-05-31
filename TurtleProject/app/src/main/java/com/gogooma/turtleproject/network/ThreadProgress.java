package com.gogooma.turtleproject.network;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

public class ThreadProgress extends Thread{

    private Handler handler; //쓰레드의 값등을 제어하는 클래스...

    private boolean check = true;


    //생성자
    public ThreadProgress(Handler handler){
        this.handler = handler;
    }


    public void setCheck(boolean check){
        this.check = check;
        //run메소드에서 반복할때 Check값이
        //true일때만 반복하도록 하고 false일때는 반복문을
        //탈출하여 Thread를 소멸되도록 하기 위함...
    }

    //스레드가 해야할일들을 정의한 메소드..
    @Override
    public void run() {
        int value = 0;

        while(check){
            try{
                Thread.sleep(100); // 내가 지정한 시간 만큼 멈추어 주는 메소드...
            }catch (InterruptedException e){
                e.printStackTrace();
            }


            Message msg = handler.obtainMessage();
            //Message객체는 정보를 저장하는 객체...
            //Bundle객체로만 저장할수 있다...

            Bundle bundle = new Bundle();

            bundle.putInt("value",value);

            msg.setData(bundle);

            //Handler에게 메세지정보를 전달하도록 한다...
            //핸들러 호출
            handler.sendMessage(msg);
            value++;
        }
        super.run();
    }
}
