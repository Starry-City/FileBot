package com.poyu39.filebot.listeners;

import com.poyu39.filebot.commands.BotCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.plugin.Plugin;

public class DiscordCommandListener extends ListenerAdapter {

  private final Plugin plugin;

  public DiscordCommandListener(Plugin plugin) { this.plugin = plugin; }

  public static String nowNameOfPlugin;

  public static String nowNameOfFolder;

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    nowNameOfPlugin = event.getOption("插件").getAsString();
    nowNameOfFolder = event.getOption("資料夾").getAsString();
    BotCommand.onOpenFolderCommand(event);
  }
}
