package pl.pivipi.core;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import pl.pivipi.core.commands.CmdCore;
import pl.pivipi.core.commands.CmdIp;
import pl.pivipi.core.commands.CmdStat;
import pl.pivipi.core.commands.CmdZakup;
import pl.pivipi.core.events.Chat;
import pl.pivipi.core.events.SpawnProtection;

public class Core extends JavaPlugin {
	
	private File configYml = new File(getDataFolder(), "config.yml");
	public FileConfiguration configCfg = YamlConfiguration.loadConfiguration(configYml);
	
	@Override
	public void onEnable() {
		if (!(configYml.exists())) saveResource("config.yml", false);
		if (configCfg.getBoolean("modules.better_spawn_protection.enable")) {
			Bukkit.getLogger().log(Level.INFO, "Enabling better spawn protection module...");
			getServer().getPluginManager().registerEvents(new SpawnProtection(this), this);
		}
		if (configCfg.getBoolean("modules.better_spawn_protection.enable")) {
			Bukkit.getLogger().log(Level.INFO, "Enabling chat module...");
			getServer().getPluginManager().registerEvents(new Chat(this), this);
		}
		Bukkit.getLogger().log(Level.INFO, "Enabling other modules...");
		getCommand("zakup").setExecutor(new CmdZakup());
		getCommand("stat").setExecutor(new CmdStat());
		getCommand("ip").setExecutor(new CmdIp());
		getCommand("core").setExecutor(new CmdCore(this));
		Bukkit.getLogger().log(Level.INFO, getDescription().getName() + " v" + getDescription().getVersion() + " loaded!");
	}
	
}
