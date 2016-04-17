package net.kdmdesign.AFKSleeper;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerStatusManager implements Listener {

	private AFKSleeper instance;
	private HashMap<Player, AFKPlayer> players = new HashMap<Player, AFKPlayer>();
	
	public PlayerStatusManager(AFKSleeper instance) {
		
		this.instance = instance;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if(!this.players.containsKey(player)) {
			
			AFKPlayer p = new AFKPlayer(player);
			
			this.players.put(player, p);
		}
		else {
			
			this.players.get(player).setStatus(AFKStatus.ONLINE);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		this.players.get(player).setStatus(AFKStatus.AFK);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		Location from = event.getFrom();
		Location to   = event.getTo();
		
		double fromX = from.getX();
		double fromY = from.getY();
		double fromZ = from.getZ();
		double toX   = to.getX();
		double toY   = to.getY();
		double toZ   = to.getZ();
		AFKPlayer afkPlayer = this.players.get(player);
		
		if(fromX != toX && fromY != toY && fromZ != toZ && afkPlayer.isAFK()) {
			
			afkPlayer.setStatus(AFKStatus.ONLINE);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Server server = this.instance.getServer();
		World world = server.getWorld("world");
		
		if(world.getTime() > 13000) {
			
			boolean canGoToDay = true;
			
			for(Player p : server.getOnlinePlayers()) {
				
				if(!this.players.get(p).isAFK() && !p.isSleeping()) {
					
					canGoToDay = false;
				}
			}
			
			if(canGoToDay) {
				
				world.setTime(0);
			}
		}
	}
	
	public void createInitialStatus() {
		for(Player player : this.instance.getServer().getOnlinePlayers()) {
			
			AFKPlayer p = new AFKPlayer(player);
			
			this.players.put(player, p);
		}
	}
	
	public HashMap<Player, AFKPlayer> getPlayers() {
		
		return this.players;
	}
}
