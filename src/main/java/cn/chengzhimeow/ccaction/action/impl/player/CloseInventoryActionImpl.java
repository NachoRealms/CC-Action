package cn.chengzhimeow.ccaction.action.impl.player;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import org.bukkit.entity.Player;

import java.util.Map;

public class CloseInventoryActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;

    public CloseInventoryActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        this.player.closeInventory();
    }
}
