package io.ib67.manhunt.game.stat;

import io.ib67.manhunt.ManHunt;
import io.ib67.manhunt.game.GamePlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GameStat {
    private final Map<UUID, PlayerStat> playerStats = new LinkedHashMap<>();
    @Getter
    private Phase gamePhase = Phase.GETTING_STARTED;
    @Getter
    @Setter
    private long totalTime;

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;

        switch (gamePhase){
            case GETTING_STARTED:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.BREAD,5)));
            case IRON_ARMOR:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT,8)));
            case IN_NETHER:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.OBSIDIAN,2)));
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.FLINT,1)));
            case BLAZE_ROD_GOT:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.BLAZE_POWDER, 2)));
            case FIND_STRONGHOLD:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_EYE, 6)));
            case IN_END:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 32)));
            case KILLED_THE_DRAGON:
                ManHunt.getInstance().getGame().getInGamePlayers().forEach(p->p.getPlayer().getInventory().addItem(new ItemStack(Material.CAKE, 1)));
        }
    }

    /**
     * @param player
     * @param advancement
     * @return -1 when player not found.
     */
    public int addAdvancement(Player player, Advancement advancement) {
        if (playerStats.containsKey(player.getUniqueId())) {
            int score = playerStats.get(player.getUniqueId()).achieve(advancement);
            if (score > 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);
            }
            return score;
        } else {
            return -1; //Not Found
        }
    }

    public void addPlayer(GamePlayer player) {
        playerStats.put(player.getPlayer().getUniqueId(), new PlayerStat(player));
    }

    public void addAdvancement(Player player, String advancement, int score) {
        if (playerStats.containsKey(player.getUniqueId())) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);
            playerStats.get(player.getUniqueId()).achieve(advancement, score);
        }
    }

    public void readySerialization() {
        playerStats.values().forEach(PlayerStat::calculate);
    }

    public enum Phase {
        GETTING_STARTED, IRON_ARMOR, IN_NETHER, BLAZE_ROD_GOT, FIND_STRONGHOLD, IN_END, KILLED_THE_DRAGON
    }
}
