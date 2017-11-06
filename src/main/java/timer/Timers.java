package timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * java se 定时器
 * Created by duan on 2017/8/20.
 */
public class Timers {

    /**
     * 普通thread
     * 这是最常见的，创建一个thread，然后让它在while循环里一直运行着，
     * 通过sleep(休眠)方法来达到定时任务的效果。
     */
    public void timer1() {
        // 设置任务间隔时间
        final long timeInterval = 1000;
        // 创建任务
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("Hello !!");
                    try {
                        // 使线程停止
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 开启一个线程
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 于第一种方式相比，优势
     * 1>当启动和去取消任务时可以控制
     * 2>第一次执行任务时可以指定你想要的delay时间
     * 在实现时，Timer类可以调度任务，TimerTask则是通过在run()方法里实现具体任务。
     * Timer实例可以调度多任务，它是线程安全的。
     * 当Timer的构造器被调用时，它创建了一个线程，这个线程可以用来调度任务。
     */
    public void timer2() {
        // 创建任务类
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Hello !!!");
            }
        };
        // 创建定时器
        Timer timer = new Timer();
        // 延迟时间
        long delay = 0;
        // 间隔时间
        long intevalPeriod = 1 * 1000;
        // 使用定时器执行任务
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    /**
     * ScheduledExecutorService是
     * 从Java SE5的java.util.concurrent里，做为并发工具类被引进的，
     * 这是最理想的定时任务实现方式。
     * 相比于上两个方法，它有以下好处：
     * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的
     * 2>可以很灵活的去设定第一次执行任务delay时间
     * 3>提供了良好的约定，以便设定执行的时间间隔
     */
    public void timer3(){
        // 创建任务
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("Hello !!");
            }
        };
        // 创建定时执行的线程池
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 执行任务（runnable要执行的任务,延迟时间，间隔时间，单位）
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);
    }

}
