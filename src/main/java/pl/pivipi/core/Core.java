package pl.pivipi.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.sun.net.httpserver.HttpServer;

import dev.simplix.core.common.CommonSimplixModule;
import dev.simplix.core.common.aop.SimplixApplication;
import dev.simplix.core.common.aop.ScanComponents;
import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.spigot.quickstart.SimplixQuickStart;

import pl.pivipi.core.commands.CmdCore;
import pl.pivipi.core.commands.CmdIp;
import pl.pivipi.core.commands.CmdStat;
import pl.pivipi.core.commands.CmdZakup;
import pl.pivipi.core.events.Slots;
import pl.pivipi.core.events.SpawnProtection;
import pl.pivipi.core.utils.NMS;
import pl.pivipi.core.webapi.WebHandler;

@SimplixApplication(
    name = "PiViPi-Core",
    version = "1.0.0",
    authors = "Minecon724",
    dependencies = "SimplixCore")
@ScanComponents("dev.simplix.core")
public class Core extends JavaPlugin {
	private File configYml = new File(getDataFolder(), "config.yml");
	public FileConfiguration configCfg = YamlConfiguration.loadConfiguration(configYml);
	
	@Override
	public void onEnable() {
                if (!SimplixQuickStart.ensureSimplixCore(this)) {
                       return;
                }
		String consolePrefix = "[" + getDescription().getName() + "]";
		try {
			NMS.serverInstance = NMS.getClass("MinecraftServer").getMethod("getServer").invoke(null);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (!(configYml.exists())) saveResource("config.yml", false);
		getCommand("zakup").setExecutor(new CmdZakup());
		getCommand("stat").setExecutor(new CmdStat());
		getCommand("ip").setExecutor(new CmdIp(this));
		getCommand("core").setExecutor(new CmdCore(this));
		if (configCfg.getBoolean("modules.better_spawn_protection.enable")) {
			Bukkit.getLogger().log(Level.INFO, consolePrefix + "Enabling better spawn protection module...");
			getServer().getPluginManager().registerEvents(new SpawnProtection(this), this);
		}
		/*
		if (configCfg.getBoolean("modules.chat.enable")) {
			Bukkit.getLogger().log(Level.INFO, consolePrefix + "Enabling chat module...");
			getServer().getPluginManager().registerEvents(new Chat(this), this);
		}
		*/
		if (configCfg.getBoolean("modules.slots.enable")) {
			Bukkit.getLogger().log(Level.INFO, consolePrefix + "Enabling slots module...");
			getServer().getPluginManager().registerEvents(new Slots(this), this);
		}
		if (configCfg.getBoolean("modules.webapi.enable")) {
			Bukkit.getLogger().log(Level.INFO, consolePrefix + "Starting WebApi...");
			HttpServer server;
			try {
				server = HttpServer.create(new InetSocketAddress(configCfg.getInt("modules.webapi.port")), 0);
				server.createContext("/", new WebHandler());
			    server.setExecutor(null);
			    server.start();
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.getLogger().log(Level.SEVERE, consolePrefix + "Failed to start WebApi");
			}
		}
		Bukkit.getLogger().log(Level.INFO, consolePrefix + " v" + getDescription().getVersion() + " loaded!");
	}
	
}
