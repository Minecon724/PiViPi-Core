package pl.pivipi.core;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import pl.pivipi.core.commands.CmdIp;
import pl.pivipi.core.commands.CmdStat;
import pl.pivipi.core.commands.CmdZakup;
import pl.pivipi.core.events.SpawnProtection;

public class Core extends JavaPlugin {
	
	private File configYml = new File(getDataFolder(), "config.yml");
	public FileConfiguration configCfg = YamlConfiguration.loadConfiguration(configYml);
	
	@Override
	public void onEnable() {
		if (!(configYml.exists())) saveResource("config.yml", false);
		if (configCfg.getBoolean("modules.better_spawn_protection.enable")) {
			getServer().getPluginManager().registerEvents(new SpawnProtection(this), this);
		}
		getCommand("zakup").setExecutor(new CmdZakup());
		getCommand("stat").setExecutor(new CmdStat());
		getCommand("ip").setExecutor(new CmdIp());
	}
	
}
