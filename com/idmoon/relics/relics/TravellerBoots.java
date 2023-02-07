package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import org.bukkit.inventory.PlayerInventory;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.entity.Player;

import org.bukkit.Location;
import java.util.ArrayList;

public final class TravellerBoots extends Relic{

  {
    item = Material.LEATHER_BOOTS;
    id = "traveller_boots";

    name = "&7Сапоги&f-&8скороходы";
    description = new String[]{"&aБыстрее&f, &eвыше&f, &cсильнее&f.",
                               "&fУвеличивают &aскорость&f и &eвысоту&f прыжка."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public TravellerBoots(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerToggleSprintEvent event){
    Player player = (Player) event.getPlayer();
    if (plugin.isRelic(player.getInventory().getBoots(), this.id)){
      if (plugin.isRelicEnabled(this.id)){
        if (event.isSprinting()){
          if(player.getLevel() > 0){
            player.setLevel(player.getLevel() - 1);
            ArrayList<PotionEffect> appliedEffects = new ArrayList<PotionEffect>();
            appliedEffects.add(new PotionEffect(PotionEffectType.SPEED, 600, 2));
            appliedEffects.add(new PotionEffect(PotionEffectType.JUMP,  600, 2));
            player.addPotionEffects(appliedEffects);
          } else {
            player.sendMessage("Недостаточно опыта для использования.");
          }
        }
      }
    }
  }
}

