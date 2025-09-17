package cn.chengzhimeow.ccaction.action.impl.command;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import org.bukkit.Bukkit;

import java.util.Map;

public class ConsoleCommandActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"command", "cmd"})
    private String command;

    public ConsoleCommandActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command);
    }
}
