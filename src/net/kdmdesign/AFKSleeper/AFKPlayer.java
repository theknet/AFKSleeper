package net.kdmdesign.AFKSleeper;

import org.bukkit.entity.Player;

public class AFKPlayer {

	private Player player;
	private AFKStatus status;
	private long lastUpdate;
	
	public AFKPlayer(Player player) {
		
		this.player = player;
		this.lastUpdate = System.currentTimeMillis();
		
		this.setStatus(AFKStatus.ONLINE);
	}
	
	public void setStatus(AFKStatus status) {
		
		this.status = status;
		this.lastUpdate = System.currentTimeMillis();
		
		this.updatePlayerName();
	}
	
	public void updatePlayerName() {
		
		if(this.isAFK()) {
			this.player.setPlayerListName("[AFK] " + this.player.getDisplayName());
		}
		else {
			this.player.setPlayerListName(this.player.getDisplayName());
		}
	}
	
	public boolean isAFK() {
		
		return AFKStatus.AFK == this.status;
	}
	
	public boolean isOnline() {
		
		return AFKStatus.ONLINE == this.status;
	}
	
	public Player getPlayer() {
		
		return this.player;
	}
	
	public long getLastUpdate() {
		
		return this.lastUpdate;
	}
}
