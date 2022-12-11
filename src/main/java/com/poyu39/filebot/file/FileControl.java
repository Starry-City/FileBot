package com.poyu39.filebot.file;

import com.poyu39.filebot.listeners.DiscordCommandListener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import static org.bukkit.Bukkit.getServer;

public class FileControl {

  public static Plugin plugin;
  public File[] listOfFile;
  public FileControl(Plugin plugin) {
    FileControl.plugin = plugin;
    File folder = new File(getServer().getPluginManager().getPlugin(DiscordCommandListener.nowNameOfPlugin).getDataFolder(), DiscordCommandListener.nowNameOfFolder);
    this.listOfFile = folder.listFiles();
  }
  public File getFile(String fileName) {
    for(File searchFile : listOfFile) {
      if(searchFile.getName().equals(fileName)) {
        return searchFile;
      }
    }
    return null;
  }

}
