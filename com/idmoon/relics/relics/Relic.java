package com.idmoon.relics.relics;

import com.idmoon.relics.*;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import org.bukkit.Material;

public abstract class Relic implements Listener{

  final Relics plugin;

  Material item;
  String id ;

  String name ;
  String[] description;

  boolean enabled;

  public Material getItem()        {return item;}

  public String   getID()          {return id;}
  public String   getName()        {return name;}
  public String[] getDescription() {return description;}

  public boolean  isEnabled()      {return enabled;}

  public Relic(Relics plugin){
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void act(PlayerInteractEvent event){};

  @EventHandler
  public void act(PlayerFishEvent event){};

  @EventHandler
  public void act(EntityDamageByEntityEvent event){};

  @EventHandler
  public void act(PlayerToggleSprintEvent event){};

  @EventHandler
  public void act(PlayerToggleSneakEvent event){};

  @EventHandler
  public void act(ProjectileHitEvent event){};

  @EventHandler
  public void act(PlayerTeleportEvent event){};
 
}

