package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import org.bukkit.event.block.Action;

import org.bukkit.inventory.ItemStack;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;

import org.bukkit.metadata.Metadatable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class RiftBlade extends Relic{

  {
    item = Material.GOLD_SWORD;
    id = "rift_blade";

    name = "&6Клинок &5разлома";
    description = new String[]{"&fОдна нога &6здесь&f, другая - &5там&f.",
                               "&fИспользуйте, чтобы запустить &dжемчуг Энда&f.",
                               "&fВозможно &3головокружение&f.",
                               "&fНе работает, когда &bмокро."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public RiftBlade(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerInteractEvent event){
    Player player = event.getPlayer();
    if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
      if (event.hasItem()){
        if (plugin.isRelic(event.getItem(), id)){
          if (plugin.isRelicEnabled(this.id)){
            World world = player.getWorld();
            Location location = player.getLocation();
            if (world.hasStorm() && (world.getHighestBlockYAt(location) <= location.getBlockY())
            || location.getBlock().isLiquid()){
              player.sendMessage("Слишком мокро.");
            } else {
              if(player.getLevel() > 0){
                player.setLevel(player.getLevel() - 1);
                player.launchProjectile(EnderPearl.class).setMetadata(this.id, new FixedMetadataValue(plugin, true));
              } else {
                player.sendMessage("Недостаточно опыта для использования");
              }
            }
          } else {
            player.sendMessage("Эта реликвия не разрешена на сервере.");
          }
        }
      }
    }
  }

  @EventHandler
  public void act(ProjectileHitEvent event){
    if (event.getEntity().hasMetadata(this.id) && event.getEntity().getShooter() instanceof Player){
      Player player = (Player) event.getEntity().getShooter();
      if ((player.getWorld().getTime()%3) == 1){
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
      }    
    }
  }

  @EventHandler
  public void act(PlayerTeleportEvent event){
    if (event.getCause() == TeleportCause.ENDER_PEARL){
      Player player = event.getPlayer();
      if (plugin.isRelic(player.getInventory().getItemInMainHand(), this.id)
      ||  plugin.isRelic(player.getInventory().getItemInOffHand(), this.id)){
        if (plugin.isRelicEnabled(this.id)){
          event.setCancelled(true);
          player.teleport(event.getTo());
        }
      }
    }
  }
}

