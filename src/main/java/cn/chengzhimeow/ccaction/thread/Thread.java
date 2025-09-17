package cn.chengzhimeow.ccaction.thread;

import lombok.Getter;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public abstract class Thread implements Runnable {
    private final String id;
    private final java.lang.Thread thread;
    private final DelayQueue<Task> tasks = new DelayQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(true);

    public Thread(String id) {
        this.id = id;
        this.thread = new java.lang.Thread(this, id);
    }

    /**
     * 运行线程
     */
    public void start() {
        if (!this.running.compareAndSet(false, true)) return;
        this.thread.start();
    }

    /**
     * 关闭线程
     */
    public void kill() {
        if (!this.running.compareAndSet(true, false)) return;
        this.thread.interrupt();
        this.tasks.clear();
    }

    /**
     * 执行任务
     *
     * @param task  任务实例
     * @param delay 延迟时间
     */
    public void execute(Runnable task, long delay) {
        if (this.running.get()) throw new IllegalStateException("线程没有运行!");
        this.tasks.offer(new Task(task, delay));
    }

    /**
     * 执行任务
     *
     * @param task 任务实例
     */
    public void execute(Runnable task) {
        this.execute(task, 0);
    }

    @Override
    public void run() {
        while (this.running.get()) {
            try {
                this.tasks.take().run();
            } catch (InterruptedException e) {
                if (!this.running.get()) break;
                java.lang.Thread.currentThread().interrupt();
            }
        }
    }
}
