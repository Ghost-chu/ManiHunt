package io.ib67.manhunt.listener;

import io.ib67.manhunt.ManHunt;
import io.ib67.manhunt.game.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Respawn implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Game game = ManHunt.getInstance().getGame();

        if (!game.isStarted() || !game.isCompassEnabled())
            return;

       event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
       event.getPlayer().setCompassTarget(game.getRunner().getLocation());
    }
}
