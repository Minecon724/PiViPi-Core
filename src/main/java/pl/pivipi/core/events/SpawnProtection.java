package pl.pivipi.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import pl.pivipi.core.Core;

public class SpawnProtection implements Listener {
	private Core plugin;
	
	public SpawnProtection(Core plugin) {
		this.plugin = plugin;
	}
	
	int radius = plugin.configCfg.getInt("modules.better_spawn_protection.size");
	Location center = Bukkit.getWorld("world").getSpawnLocation();
	
	private boolean isInSpawn(Location lok) {
		if (lok.getWorld() == center.getWorld()) {
			if (lok.getX() >= center.getX() - radius && lok.getX() <= center.getX() + radius) {
				if (lok.getZ() >= center.getZ() - radius && lok.getZ() <= center.getZ() + radius) {
					return true;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void bsExplode(BlockExplodeEvent e) {
		if (isInSpawn(e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsBreak(BlockBreakEvent e) {
		if (!(e.getPlayer().hasPermission("core.bypasssp"))) {
			if (isInSpawn(e.getBlock().getLocation())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void bsPlace(BlockPlaceEvent e) {
		if (!(e.getPlayer().hasPermission("core.bypasssp"))) {
			if (isInSpawn(e.getBlock().getLocation())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void bsFire(BlockIgniteEvent e) {
		if (isInSpawn(e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
		if (e.getIgnitingEntity() != null) {
			if (e.getPlayer().hasPermission("core.bypasssp")) {
				e.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void bsSpawn(EntitySpawnEvent e) {
		if (isInSpawn(e.getLocation())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsDamage(EntityDamageEvent e) {
	    EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e;
		if (e.getEntity() instanceof Player && isInSpawn(e.getEntity().getLocation())) {
			e.setCancelled(true);
		    if (ee.getDamager() instanceof Player) {
		    	if (((Player)ee.getDamager()).hasPermission("core.bypasssp")) {
		    		e.setCancelled(false);
		    	}
		    }
		}
	}
}
