package pl.pivipi.core.events;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import pl.pivipi.core.Core;

public class Chat implements Listener {
	
	public static int calc(Player p, String pe, Integer r) {
		int l = -1;
		for (int i=r;i>=0;i--) {
			if (p.hasPermission(pe+"."+Integer.toString(i))) {
				l = i;
			}
		}
		return l;
	}
	
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
		int lvl = -1;
		String k = "default";
		for (String key : hm.keySet()) {
			int c = calc(e.getPlayer(), "core.chat." + key, 10);
			if (c > lvl) lvl = c;
		}
		e.setFormat(ChatColor.translateAlternateColorCodes('&', hm.get(k)));
	}
	
}
