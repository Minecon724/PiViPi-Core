package pl.pivipi.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import pl.pivipi.core.Core;

public class SpawnProtection implements Listener {
	private Core plugin;
	private ConfigurationSection cfg;
	private ConfigurationSection offsets;
	private int radius;
        private HashMap<Integer, String> warnings;
	
	public SpawnProtection(Core plugin) {
		this.plugin = plugin;
		this.cfg = plugin.configCfg.getConfigurationSection("modules.better_spawn_protection");
		this.offsets = cfg.getConfigurationSection("extend");
		this.radius = cfg.getInt("size");
                for (int Integer.valueOf(key) : cfg.getConfigurationSection("warnings").getKeys(false)) {
                    this.warnings.put(key, cfg.getConfigurationSection("warnings").getString(Integer.toString(key)));
                }
	}
	
	Location center = Bukkit.getWorld("world").getSpawnLocation();
	
	private boolean isInSpawn(Location lok, Integer offset) {
		if (lok.getWorld() == center.getWorld()) {
			if (lok.getX() >= center.getX() - radius - offset && lok.getX() <= center.getX() + radius + offset) {
				if (lok.getZ() >= center.getZ() - radius - offset && lok.getZ() <= center.getZ() + radius + offset) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isInSpawn(Location lok) {
		return isInSpawn(lok, 0);
	}
	
	@EventHandler
	public void bsBExplode(BlockExplodeEvent e) {
		if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("explode"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsBreak(BlockBreakEvent e) {
		if (!(e.getPlayer().hasPermission("core.bypasssp"))) {
			if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("break"))) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void bsPlace(BlockPlaceEvent e) {
		if (!(e.getPlayer().hasPermission("core.bypasssp"))) {
			if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("place"))) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void bsFire(BlockIgniteEvent e) {
		if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("fire"))) {
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
		if (isInSpawn(e.getLocation(), offsets.getInt("entity_spawn"))) {
			if (e.getEntityType() != EntityType.ARROW && e.getEntityType() != EntityType.DROPPED_ITEM && e.getEntityType() != EntityType.EGG && e.getEntityType() != EntityType.SNOWBALL && e.getEntityType() != EntityType.SPECTRAL_ARROW && e.getEntityType() != EntityType.SPLASH_POTION && e.getEntityType() != EntityType.EXPERIENCE_ORB && e.getEntityType() != EntityType.THROWN_EXP_BOTTLE)
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && isInSpawn(e.getEntity().getLocation(), offsets.getInt("damage"))) {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void bsExplode(EntityExplodeEvent e) {
		if (isInSpawn(e.getLocation(), offsets.getInt("explode"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsELiquid(PlayerBucketEmptyEvent e) {
		if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("liquid")) && !(e.getPlayer().hasPermission("core.bypasssp"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsFLiquid(PlayerBucketFillEvent e) {
		if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("liquid")) && !(e.getPlayer().hasPermission("core.bypasssp"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bsRedstone(BlockRedstoneEvent e) {
		if (isInSpawn(e.getBlock().getLocation(), offsets.getInt("redstone"))) {
			e.setNewCurrent(0);
		}
	}
}
