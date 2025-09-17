package cn.chengzhimeow.ccaction.action.impl.message;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.Map;

public class BroadcastActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"message", "msg"})
    private Component message;

    public BroadcastActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        Bukkit.getServer().broadcast(this.message);
    }
}
