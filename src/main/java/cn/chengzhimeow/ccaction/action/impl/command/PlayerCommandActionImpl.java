package cn.chengzhimeow.ccaction.action.impl.command;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerCommandActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"op", "o"}, required = false)
    private boolean op = false;
    @ArgumentKey(keys = {"chat", "c"}, required = false)
    private boolean chat = false;
    @ArgumentKey(keys = {"command", "cmd"})
    private String command;

    public PlayerCommandActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        boolean changeOp = this.op && !this.player.isOp();
        if (changeOp) this.player.setOp(true);

        if (this.chat) this.player.chat("/" + this.command);
        else Bukkit.dispatchCommand(this.player, this.command);

        if (changeOp) this.player.setOp(false);
    }
}
