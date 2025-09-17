package cn.chengzhimeow.ccaction.thread;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
public final class Task implements Delayed, Runnable {
    private final Runnable runnable;
    private final long executeTime;
    private final long delay;

    public Task(Runnable runnable, long delay) {
        this.runnable = runnable;
        this.executeTime = System.currentTimeMillis() + delay;
        this.delay = delay;
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        if (o instanceof Task task) {
            return Long.compare(this.executeTime, task.executeTime);
        }
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return this.delay;
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
