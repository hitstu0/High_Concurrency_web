package com.hitsz.high_concurrency.Ping;


import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.hitsz.high_concurrency.Ping.Interfaces.Observer;
import com.hitsz.high_concurrency.Ping.Interfaces.Subject;

public class heartBeatPing implements Subject,Runnable{

    private List<Observer> list = new LinkedList<>();
    private String host;
    private String port;
    private int delay;
    private int timeout;
    private int retryTime; //最大重试次数
    private String failInfo = "Was unable to connect";
    private Runtime r = Runtime.getRuntime();
    private int currentTime = 0; // 当前重试次数
    private boolean success = true; //判断当前 ping 是否失败
    public heartBeatPing(String host,String port,int delay,int timeout,int retryTime) {  
        this.host = host;
        this.port = port;
        this.delay = delay;
        this.timeout = timeout;
        this.retryTime = retryTime;
    }
    
    public heartBeatPing(String host,String port) {
        this(host,port,500,500,1);
    }

    @Override
    public void run() {    
        while(true) {      
            String pingCommond = "tcping " + host + " "+ port;
            try {
                //进行 Ping 并等待执行完毕
                success = true;
                Process p = r.exec(pingCommond);
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String nextLing = null;
                //从输出结果判断是否成功
                while(reader != null && (nextLing = reader.readLine()) != null) {
                  if(nextLing.contains(failInfo)) {
                      success = false;
                      break;
                  }
                }     
                //通过当前 Ping 是否成功执行不同方法
                if(success) {
                    System.out.println("Ping 成功");
                    currentTime = 0;
                    success();
                } else {                
                    ++ currentTime;
                    System.out.println("Ping 失败 " + currentTime);
                    //超过最大重试次数，执行不同的策略
                   if(currentTime >= retryTime) {
                       shutDown();
                   } else {
                       failOnce();
                   }
                }         

                Thread.sleep(delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }      
    }

    private void success() {
        for(Observer o : list) {
            o.doWhenSuccess();
        }
    }

    private void failOnce() {
        for(Observer o : list) {
            o.doWhenFailOnce();
        }
    }
    
    private void shutDown() {
        for(Observer o : list) {
            o.doWhenShutDown();
        }
    }

    @Override
    public void addObserver(Observer o) {
        list.add(o);        
    }

    @Override
    public void deleteObserver(Observer o) {
       list.remove(o);          
    }
}
