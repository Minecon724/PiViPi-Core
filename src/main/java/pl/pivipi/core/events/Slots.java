package pl.pivipi.core.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;

import pl.pivipi.core.Core;

public class Slots implements Listener {
	private Core plugin;
	private ConfigurationSection cfg;
	
	private int add;
	private int limit;
    private String message;
	
	public Slots(Core plugin) {
		this.cfg = plugin.configCfg.getConfigurationSection("modules.slots");
		this.add = cfg.getInt("add");
		this.limit = cfg.getInt("limit");
		this.message = cfg.getString("message");
	}
    
	@EventHandler
	public void ping(ServerListPingEvent e) {
		if (add > 0) {
			e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + add);
		}
	}
	
	@EventHandler
	public void join(PlayerLoginEvent e) {
		if (!(e.getPlayer().hasPermission("core.bypassmax")) && Bukkit.getOnlinePlayers().size() >= limit) {
			e.disallow(Result.KICK_FULL, message);
		}
	}
}
