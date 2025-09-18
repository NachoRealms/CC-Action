package cn.chengzhimeow.ccaction.action.impl.message;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Map;

public class TitleActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"title"}, required = false)
    private Component title = Component.empty();
    @ArgumentKey(keys = {"subtitle"}, required = false)
    private Component subtitle = Component.empty();
    @ArgumentKey(keys = {"in"}, required = false)
    private int in = 20;
    @ArgumentKey(keys = {"step"}, required = false)
    private int step = 70;
    @ArgumentKey(keys = {"out"}, required = false)
    private int out = 10;

    public TitleActionImpl(CCAction ccAction, Map<String, Object> params) {
        super(ccAction, params);
    }

    @Override
    protected void onAction() {
        this.player.sendTitlePart(TitlePart.TIMES, Title.Times.times(
                Duration.ofMillis(50L * this.in),
                Duration.ofMillis(50L * this.step),
                Duration.ofMillis(50L * this.out)
        ));
        this.player.sendTitlePart(TitlePart.SUBTITLE, this.subtitle);
        this.player.sendTitlePart(TitlePart.TITLE, this.title);
        this.player.resetTitle();
    }
}
