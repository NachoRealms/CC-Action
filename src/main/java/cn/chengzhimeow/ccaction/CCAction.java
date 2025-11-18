package cn.chengzhimeow.ccaction;

import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ActionBuilder;
import cn.chengzhimeow.ccaction.registry.ActionRegistry;
import cn.chengzhimeow.ccaction.registry.CastRegistry;
import cn.chengzhimeow.ccaction.registry.PreProcessRegistry;
import cn.chengzhimeow.ccaction.thread.ActionThread;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Map;

@Getter
public final class CCAction {
    private final JavaPlugin plugin;
    private final PreProcessRegistry preProcessRegistry;
    private final CastRegistry castRegistry;
    private final ActionRegistry actionRegistry;
    private final ActionThread actionThread;

    public CCAction(JavaPlugin plugin) {
        this.plugin = plugin;
        this.preProcessRegistry = new PreProcessRegistry();
        this.castRegistry = new CastRegistry();
        this.actionRegistry = new ActionRegistry();

        this.actionThread = new ActionThread();
        this.actionThread.start();
    }

    /**
     * 关闭线程
     */
    public void close() {
        this.actionThread.kill();
    }

    /**
     * 获取操作实例
     *
     * @param id  操作ID
     * @param map 操作参数
     * @return 操作实例
     */
    @SneakyThrows
    public ActionBuilder.Builder getAction(String id, Map<String, Object> map) {
        Constructor<? extends AbstractAction> constructor = this.actionRegistry.get(id);
        if (constructor == null) throw new IllegalArgumentException("Cannot find the action with id " + id);
        return ActionBuilder.builder(this)
                .action(constructor)
                .params(map);
    }
}
