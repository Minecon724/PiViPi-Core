package pl.pivipi.core.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import pl.pivipi.core.Core;

public class Chat implements Listener {
	
	private Core plugin;
	private HashMap<String, String> hm = new HashMap<String, String>();
	
	public Chat(Core plugin) {
		this.plugin = plugin;
	    for (String key : plugin.configCfg.getConfigurationSection("modules.chat.format").getKeys(false)) {
	        hm.put(key, plugin.configCfg.getString("modules.chat.format."+key));
	    }
	}

	@EventHandler
	public void ChatEvent(AsyncPlayerChatEvent e) {
		if (e.getPlayer().hasPermission("core.chatcolor")) {
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
		}
		boolean set = false;
		for (String key : hm.keySet()) {
			if (e.getPlayer().hasPermission("core.chat." + key)) {
				Bukkit.broadcastMessage(key);
				e.setFormat(hm.get(key));
				set = true;
				break;
			}
		}
		if (!(set)) {
			Bukkit.broadcastMessage("default");
			e.setFormat(hm.get("default"));
		}
	}
	
}
