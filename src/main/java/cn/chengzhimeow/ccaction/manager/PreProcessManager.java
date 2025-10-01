package cn.chengzhimeow.ccaction.manager;

import cn.chengzhimeow.ccaction.action.AbstractAction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

public interface PreProcessManager {
    Object handle(AbstractAction action, Object value);

    class PlaceholderAPIPreProcessManager implements PreProcessManager {
        @Override
        public Object handle(AbstractAction action, Object value) {
            return PlaceholderAPI.setPlaceholders(
                    action.getParam("placeholder_owner", OfflinePlayer.class),
                    (String) value
            );
        }
    }
}
