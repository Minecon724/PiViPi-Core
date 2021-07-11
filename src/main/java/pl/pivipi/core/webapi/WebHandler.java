package pl.pivipi.core.webapi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		String uri = t.getRequestURI().toString().substring(1);
		String[] parts = uri.split("/");
		//Player player = Bukkit.getPlayer(parts[0]);
		OfflinePlayer player = Bukkit.getOfflinePlayer(parts[0]);
		int rCode = 200;
		String response = "OK";
		if (player == null) {
			rCode = 404;
			response = "Player " + parts[0] + " not found";
		} else {
			try {
				if (EnumUtils.isValidEnum(Statistic.class, parts[1].toUpperCase())) {
					int statistic;
					if (EnumUtils.isValidEnum(EntityType.class, parts[2].toUpperCase())) {
						statistic = player.getStatistic(Statistic.valueOf(parts[1].toUpperCase()), EntityType.valueOf(parts[2].toUpperCase()));
					} else if (EnumUtils.isValidEnum(Material.class, parts[2].toUpperCase())) {
						statistic = player.getStatistic(Statistic.valueOf(parts[1].toUpperCase()), Material.valueOf(parts[2].toUpperCase()));
					} else {
						statistic = player.getStatistic(Statistic.valueOf(parts[1].toUpperCase()));
					}
					rCode = 200;
					response = Integer.toString(statistic);
				} else {
					rCode = 404;
					response = "Statistic " + parts[1].toUpperCase() + " not found";
				}
			} catch (IllegalArgumentException e) {
				rCode = 406;
				response = e.getMessage();
			}
		}
		t.sendResponseHeaders(rCode, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
