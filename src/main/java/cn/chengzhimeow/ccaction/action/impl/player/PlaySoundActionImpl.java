package cn.chengzhimeow.ccaction.action.impl.player;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlaySoundActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"sound", "s"})
    private String sound;
    @ArgumentKey(keys = {"volume", "v"}, required = false)
    private float volume = 1f;
    @ArgumentKey(keys = {"pitch", "p"}, required = false)
    private float pitch = 1f;

    public PlaySoundActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    protected void onAction() {
        this.player.playSound(this.player.getLocation(), this.sound, this.volume, this.pitch);
    }
}
