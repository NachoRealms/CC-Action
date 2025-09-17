package cn.chengzhimeow.ccaction.registry;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.impl.command.ConsoleCommandActionImpl;
import cn.chengzhimeow.ccaction.action.impl.command.PlayerCommandActionImpl;
import cn.chengzhimeow.ccaction.action.impl.message.*;
import cn.chengzhimeow.ccaction.action.impl.player.CloseInventoryActionImpl;
import cn.chengzhimeow.ccaction.action.impl.player.ConnectServerActionImpl;
import cn.chengzhimeow.ccaction.action.impl.player.PlaySoundActionImpl;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.Map;

public final class ActionRegistry extends Registry<String, Constructor<? extends AbstractAction>> {
    @SneakyThrows
    public Constructor<? extends AbstractAction> getConstructor(Class<? extends AbstractAction> clazz) {
        return clazz.getConstructor(CCAction.class, Map.class);
    }

    @Override
    public Map<String, Constructor<? extends AbstractAction>> defaultRegistry() {
        Map<String, Constructor<? extends AbstractAction>> map = super.defaultRegistry();

        map.put("console_command", this.getConstructor(ConsoleCommandActionImpl.class));
        map.put("player_command", this.getConstructor(PlayerCommandActionImpl.class));

        map.put("action_bar", this.getConstructor(ActionBarActionImpl.class));
        map.put("boss_bar", this.getConstructor(BossBarActionImpl.class));
        map.put("broadcast", this.getConstructor(BroadcastActionImpl.class));
        map.put("message", this.getConstructor(MessageActionImpl.class));
        map.put("title", this.getConstructor(TitleActionImpl.class));

        map.put("close_inventory", this.getConstructor(CloseInventoryActionImpl.class));
        map.put("connect_server", this.getConstructor(ConnectServerActionImpl.class));
        map.put("play_sound", this.getConstructor(PlaySoundActionImpl.class));

        return map;
    }

    public void register(String key, Class<? extends AbstractAction> value) {
        super.register(key, this.getConstructor(value));
    }
}
