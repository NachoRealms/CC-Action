package cn.chengzhimeow.ccaction.action.impl.message;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Map;

public class MessageActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"message", "msg"})
    private Component message;

    public MessageActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        this.player.sendMessage(this.message);
    }
}
