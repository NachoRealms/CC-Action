package cn.chengzhimeow.ccaction.action.impl.player;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class ConnectServerActionImpl extends AbstractAction {
    @ArgumentKey(keys = {"player", "p"}, disabledCheck = true)
    private Player player;
    @ArgumentKey(keys = {"server", "s"})
    private String server;

    public ConnectServerActionImpl(CCAction ccAction, Map<String, Object> prams) {
        super(ccAction, prams);
    }

    @Override
    public boolean require() {
        return Bukkit.getMessenger().isOutgoingChannelRegistered(super.ccAction.getPlugin(), "BungeeCord");
    }

    @Override
    protected void onAction() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(this.server);

        this.player.sendPluginMessage(super.ccAction.getPlugin(), "BungeeCord", out.toByteArray());
    }
}
