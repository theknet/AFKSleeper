package net.kdmdesign.AFKSleeper;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerCheckingTask implements Runnable {

	private PlayerStatusManager statusManager;
	
	public PlayerCheckingTask(PlayerStatusManager statusManager) {
		
		this.statusManager = statusManager;
	}
	
	@Override
	public void run() {
		
		HashMap<Player, AFKPlayer> players = this.statusManager.getPlayers();
		long currentTime = System.currentTimeMillis();
		for(Player player : players.keySet()) {
			
			AFKPlayer p = players.get(player);
			
			if(p.isOnline() && (currentTime - p.getLastUpdate() > 1000)) {

				p.setStatus(AFKStatus.AFK);
			}
		}
	}

}
