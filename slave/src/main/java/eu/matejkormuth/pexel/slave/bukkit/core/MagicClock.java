// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.slave.bukkit.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.actions.CommandAction;
import eu.matejkormuth.pexel.slave.bukkit.actions.JavaArbitraryAction;
import eu.matejkormuth.pexel.slave.bukkit.actions.TeleportAction;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;
import eu.matejkormuth.pexel.slave.bukkit.menu.InventoryMenu;
import eu.matejkormuth.pexel.slave.bukkit.menu.InventoryMenuItem;
import eu.matejkormuth.pexel.slave.bukkit.util.ItemUtils;
import eu.matejkormuth.pexel.slave.bukkit.util.ParametrizedRunnable;

/**
 * Class used for MagicClock.
 * 
 * @auhtor Mato Kormuth
 * 
 */
public class MagicClock implements Listener {
    private InventoryMenu iventoryMenu;
    
    public void buildInventoryMenu() {
        InventoryMenuItem everybodyItem = new InventoryMenuItem(
                ItemUtils.namedItemStack(Material.EYE_OF_ENDER, "Everybody", null),
                new JavaArbitraryAction(new ParametrizedRunnable() {
                    @Override
                    public void run(final Object... args) {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            ((Player) args[0]).showPlayer(onlinePlayer);
                        }
                        ((Player) args[0]).sendMessage(ChatManager.success("Now you can see everybody!"));
                    }
                }), 0, true);
        
        InventoryMenuItem nobodyItem = new InventoryMenuItem(ItemUtils.namedItemStack(
                Material.ENDER_PEARL, "Nobody", null), new JavaArbitraryAction(
                new ParametrizedRunnable() {
                    @Override
                    public void run(final Object... args) {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            //if (StorageEngine.getProfile(
                            //		((Player) args[0]).getUniqueId()).isFriend(
                            //		onlinePlayer.getUniqueId()))
                            //	((Player) args[0]).showPlayer(onlinePlayer);
                            //else
                            ((Player) args[0]).hidePlayer(onlinePlayer);
                        }
                        ((Player) args[0]).sendMessage(ChatManager.success("All players have been vanished!"));
                    }
                }), 1, true);
        
        //InventoryMenuItem kickItem = new InventoryMenuItem(ItemUtils.namedItemStack(
        //        Material.APPLE, "Kick me", null), new KickAction(), 2, true);
        
        InventoryMenuItem teleportItem = new InventoryMenuItem(ItemUtils.namedItemStack(
                Material.BED, "Teleport to 0 255 0", null), new TeleportAction(
                new Location(Bukkit.getWorld("world"), 0, 255, 0)), 3, true);
        
        InventoryMenuItem commandItem = new InventoryMenuItem(ItemUtils.namedItemStack(
                Material.BEACON, "Suprise", null), new CommandAction("me je gay"), 4,
                false);
        
        InventoryMenuItem soundItem = new InventoryMenuItem(ItemUtils.namedItemStack(
                Material.NOTE_BLOCK, "Sound", null), new JavaArbitraryAction(
                new ParametrizedRunnable() {
                    @Override
                    public void run(final Object... args) {
                        ((Player) args[0]).playSound(((Player) args[0]).getLocation(),
                                Sound.ZOMBIE_REMEDY, 1, 1);
                        ((Player) args[0]).playSound(((Player) args[0]).getLocation(),
                                Sound.AMBIENCE_CAVE, 1, 1);
                        ((Player) args[0]).playSound(((Player) args[0]).getLocation(),
                                Sound.ZOMBIE_METAL, 1, 1);
                        ((Player) args[0]).playSound(((Player) args[0]).getLocation(),
                                Sound.BURP, 1, 1);
                    }
                }), 5, false);
        
        this.iventoryMenu = new InventoryMenu(InventoryType.CHEST, "Player visibility",
                Arrays.asList(everybodyItem, nobodyItem, teleportItem, commandItem,
                        soundItem));
    }
    
    public MagicClock() {
        Bukkit.getPluginManager().registerEvents(this, Pexel.getCore());
        this.buildInventoryMenu();
    }
    
    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() != null)
            if (event.getPlayer().getItemInHand().getType() == Material.WATCH)
                this.iventoryMenu.showTo(PexelSlave.getInstance()
                        .getObjectFactory()
                        .getPlayer(event.getPlayer()));
        
        /*
         * if (event.getPlayer().getItemInHand() != null) if (event.getPlayer().getItemInHand().getType() ==
         * Material.WATCH) { ItemMeta meta = event.getPlayer().getItemInHand().getItemMeta(); String rezim =
         * meta.getLore().get(0).substring(7);
         * 
         * if (rezim.equalsIgnoreCase("Vsetci")) { for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         * event.getPlayer().showPlayer(onlinePlayer); }
         * 
         * meta.setLore(Arrays.asList("Rezim: Priatelia", "Pouzi a skry/zobraz skaredych hracov.")); } else if
         * (rezim.equalsIgnoreCase("Priatelia")) { for (Player onlinePlayer : Bukkit.getOnlinePlayers()) { if
         * (StorageEngine.getProfile( event.getPlayer().getUniqueId()).isFriend( onlinePlayer.getUniqueId()))
         * event.getPlayer().showPlayer(onlinePlayer); else event.getPlayer().hidePlayer(onlinePlayer); }
         * 
         * meta.setLore(Arrays.asList("Rezim: Nikto", "Pouzi a skry/zobraz skaredych hracov.")); } else if
         * (rezim.equalsIgnoreCase("Nikto")) { for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         * event.getPlayer().hidePlayer(onlinePlayer); }
         * 
         * meta.setLore(Arrays.asList("Rezim: Vsetci", "Pouzi a skry/zobraz skaredych hracov.")); }
         * 
         * event.getPlayer().getItemInHand().setItemMeta(meta); }
         */
    }
    
    public ItemStack getClock() {
        ItemStack itemstack = new ItemStack(Material.WATCH);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Magic Cock");
        meta.setLore(Arrays.asList("Rezim: Vsetci",
                "Pouzi a skry/zobraz skaredych hracov."));
        itemstack.setItemMeta(meta);
        return itemstack;
    }
}
