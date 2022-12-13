package com.starrycity.poyu39.filebot.commands;

import com.google.common.collect.Lists;
import com.starrycity.poyu39.filebot.file.FileControl;
import com.starrycity.poyu39.filebot.listeners.DiscordCommandListener;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BotCommand {
  private static Plugin plugin;

  public BotCommand(Plugin plugin) { BotCommand.plugin = plugin; }

  public static int page;

  public static void onOpenFolderCommand(SlashCommandInteractionEvent event) {
    FileControl files = new FileControl(plugin);
    List<Button> buttonsRows = new ArrayList<>();
    List<List<Button>> buttonsRowLists;
    List<Button> controlButtons = new ArrayList<>();
    for(File file : files.listOfFile) {
      buttonsRows.add(Button.secondary(file.getName(), Emoji.fromUnicode("U+1F4C4")).withLabel(file.getName()));
    }
    int maxPage = (buttonsRows.size() + 5) / 5 / 4;
    int buttonRowCount;
    if (buttonsRows.size() % 5 != 0) {
      buttonRowCount = buttonsRows.size() / 5 + 1;
    } else {
      buttonRowCount = buttonsRows.size() / 5;
    }
    buttonsRowLists = Lists.partition(buttonsRows, 5);

    controlButtons.add(Button.danger("close", Emoji.fromFormatted("U+26D4")).withLabel("關閉"));
    controlButtons.add(Button.primary("last_page", Emoji.fromFormatted("U+21A9")).withLabel("上一頁").asDisabled());
    controlButtons.add(Button.primary("refresh_page", Emoji.fromFormatted("U+1F504")).withLabel("重新整理"));
    if(buttonRowCount > 4) {
      controlButtons.add(Button.primary("next_page", Emoji.fromFormatted("U+21AA")).withLabel("下一頁"));
    } else {
      controlButtons.add(Button.primary("next_page", Emoji.fromFormatted("U+21AA")).withLabel("下一頁").asDisabled());
    }
    page = 0;
    if(buttonRowCount - page * 4 >= 4) {
      event.reply("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .addActionRow(controlButtons)
          .addActionRow(buttonsRowLists.get(0 + page * 4))
          .addActionRow(buttonsRowLists.get(1 + page * 4))
          .addActionRow(buttonsRowLists.get(2 + page * 4))
          .addActionRow(buttonsRowLists.get(3 + page * 4))
          .queue();
    }else if(buttonRowCount - page * 4 == 3) {
      event.reply("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .addActionRow(controlButtons)
          .addActionRow(buttonsRowLists.get(0 + page * 4))
          .addActionRow(buttonsRowLists.get(1 + page * 4))
          .addActionRow(buttonsRowLists.get(2 + page * 4))
          .queue();
    }else if(buttonRowCount - page * 4 == 2) {
      event.reply("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .addActionRow(controlButtons)
          .addActionRow(buttonsRowLists.get(0 + page * 4))
          .addActionRow(buttonsRowLists.get(1 + page * 4))
          .queue();
    }else if(buttonRowCount - page * 4 == 1) {
      event.reply("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .addActionRow(controlButtons)
          .addActionRow(buttonsRowLists.get(0 + page * 4))
          .queue();
    }
  }
}
