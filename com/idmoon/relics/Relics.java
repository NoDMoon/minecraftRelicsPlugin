package com.idmoon.relics;

import com.idmoon.relics.relics.*;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;

import org.bukkit.ChatColor;

public final class Relics extends JavaPlugin {

  HashMap<String, Relic> relicList = new HashMap<String, Relic>();

  public HashMap<String, Relic> getRelicList(){return relicList;}

  public boolean onCommand(CommandSender sender,
                           Command command,
                           String label,
                           String[] args){

    if (command.getName().equals("relic")) {		
      if (sender instanceof Player){

        Player player = (Player) sender;

        if ((args.length == 2) && args[0].equals("create")){
          if(relicList.keySet().contains(args[1])){
            if (isRelicEnabled(args[1])){
              player.getInventory().setItemInMainHand(createRelic(args[1]));
            } else {
              player.sendMessage("Эта реликвия не разрешена на сервере.");
            }
          } else {
            player.sendMessage("Неверный ID реликвии");
          }
        }else {
          player.sendMessage("Использование: /relic create [id]");
        }
      }
    }
    return false;
  }

  public ItemStack createRelic(String id){
    //проверка есть ли айди

    ItemStack relic = new ItemStack(relicList.get(id).getItem());

    net.minecraft.server.v1_12_R1.ItemStack nmsRelic = CraftItemStack.asNMSCopy(relic);
    NBTTagCompound relicCompound = new NBTTagCompound();
    relicCompound.set("relicID", new NBTTagString(id));
    nmsRelic.setTag(relicCompound);

    relic = CraftItemStack.asBukkitCopy(nmsRelic);

    ItemMeta relicMeta = relic.getItemMeta();

    String colorName = ChatColor.translateAlternateColorCodes​('&', relicList.get(id).getName());
    List<String> colorLore = new ArrayList<String>();

    //setting the colorful lore
    for(String loreLine: Arrays.asList(relicList.get(id).getDescription()))
      colorLore.add(ChatColor.translateAlternateColorCodes​('&', loreLine));

    relicMeta.setDisplayName(colorName);
    relicMeta.setLore(colorLore);

    relic.setItemMeta(relicMeta);

    return relic;
  }

  public boolean isRelic(ItemStack item, String id){
    net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
    NBTTagCompound itemCompound = nmsItem.getTag();
    return (itemCompound != null)
        && itemCompound.hasKey("relicID")
        && itemCompound.getString("relicID").equals(id)
        && !(itemCompound.hasKey("gm"));
  }
  
  public boolean isRelicEnabled(String id){
 
    return ((relicList.get(id) != null)
        && relicList.get(id).isEnabled());
  }

  public void onEnable() {

    this.saveDefaultConfig();

    new IfritFlame(this);
    new Hook(this);
    new Splinter(this);
    new TravellerBoots(this);
    new ForceWave(this);
    new StarDust(this);
    new Mjolnir(this);
    new RiftBlade(this);

    getLogger().info("Relics has been planted");
   
  }

  public void onDisable() {
      getLogger().info("Relics has been defused");
  }
} 
