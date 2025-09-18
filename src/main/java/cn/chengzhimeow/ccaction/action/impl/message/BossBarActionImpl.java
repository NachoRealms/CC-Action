package cn.chengzhimeow.ccaction.action.impl.message;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Map;

public class BossBarActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"title", "t"})
    private Component title;
    @ArgumentKey(keys = {"color", "c"}, required = false)
    private BossBar.Color color = BossBar.Color.YELLOW;
    @ArgumentKey(keys = {"style", "s"}, required = false)
    private BossBar.Overlay style = BossBar.Overlay.PROGRESS;
    @ArgumentKey(keys = {"current"}, required = false)
    private float current = 100;
    @ArgumentKey(keys = {"delay", "d"})
    private int delay;

    public BossBarActionImpl(CCAction ccAction, Map<String, Object> params) {
        super(ccAction, params);
    }

    @Override
    protected void onAction() {
        BossBar bossBar = BossBar.bossBar(this.title, this.current, this.color, this.style);
        this.player.showBossBar(bossBar);
        super.ccAction.getActionThread().execute(() -> this.player.hideBossBar(bossBar), 50L * this.delay);
    }
}
