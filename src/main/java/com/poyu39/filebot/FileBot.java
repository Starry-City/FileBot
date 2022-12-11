package com.poyu39.filebot;

import com.poyu39.filebot.commands.BotCommand;
import com.poyu39.filebot.listeners.DiscordButtonListener;
import com.poyu39.filebot.listeners.DiscordCommandListener;
import com.poyu39.filebot.listeners.DiscordMessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class FileBot extends JavaPlugin {

  public static String BOTTOKEN;

  public static String CHANNELID;

  public static String VERSION;

  public static String ActivityText;

  public static String getChannelId() {
    return CHANNELID;
  }

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    BOTTOKEN = getConfig().getString("BotToken");
    CHANNELID = getConfig().getString("ChannelId");
    VERSION = getConfig().getString("version");
    ActivityText = getConfig().getString("Activity");
    JDA jda = JDABuilder.createDefault(BOTTOKEN)
        .setStatus(OnlineStatus.ONLINE)
        .setActivity(Activity.playing(ActivityText))
        .addEventListeners(new DiscordCommandListener(this))
        .addEventListeners(new DiscordMessageListener(this))
        .addEventListeners(new DiscordButtonListener(this))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build();
    new BotCommand(this);

    OptionData fileOptionData = new OptionData(OptionType.STRING, "插件", "選擇要開啟的插件", true);
    OptionData folderOptionData = new OptionData(OptionType.STRING, "資料夾", "選擇要開啟的資料夾", true);
    Map<String, Object> test = this.getConfig().getConfigurationSection("HookPluginList").getValues(true);
    for (Map.Entry<String, Object> entry : test.entrySet()) {
      String nameOfPlugin = entry.getKey();
      String path = entry.getValue().toString();
      path = path.substring(1, path.length() - 1);
      fileOptionData.addChoice(nameOfPlugin, nameOfPlugin);
      folderOptionData.addChoice(path, path);
    }
    jda.updateCommands().addCommands(
        Commands.slash("filebot", "選擇伺服器").addOptions(fileOptionData, folderOptionData)
    ).queue();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(command.getName().equals("FileBot") && args[0].equals("reload")) {
      super.reloadConfig();
      saveDefaultConfig();
      getConfig().options().copyDefaults(true);
      saveConfig();
      sender.sendMessage("已重新載入");
      return true;
    }
    return false;
  }
  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
