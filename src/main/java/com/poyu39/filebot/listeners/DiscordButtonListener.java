package com.poyu39.filebot.listeners;

import com.google.common.collect.Lists;
import com.poyu39.filebot.file.FileControl;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.poyu39.filebot.commands.BotCommand.*;

public class DiscordButtonListener extends ListenerAdapter {
  private static Plugin plugin;

  public DiscordButtonListener(Plugin plugin) { DiscordButtonListener.plugin = plugin; }

  @Override
  public void onButtonInteraction(ButtonInteractionEvent event) {
    switch (Objects.requireNonNull(event.getButton().getId())) {
      case "close":
        event.editMessage("已關閉資料夾")
            .setComponents()
            .queue();
        break;
      case "last_page":
        page = page - 1;
        refreshButton(event);
        break;
      case "refresh_page":
        refreshButton(event);
        break;
      case "next_page":
        page += 1;
        refreshButton(event);
        break;
    }
  }

  public static void refreshButton(ButtonInteractionEvent event) {
    FileControl files = new FileControl(plugin);
    List<Button> buttonsRows = new ArrayList<>();
    List<List<Button>> buttonsRowLists;
    List<Button> controlButtons = new ArrayList<>();
    for (File file : files.listOfFile) {
      buttonsRows.add(Button.secondary(file.getName(), Emoji.fromUnicode("U+1F4C4")).withLabel(file.getName()));
    }
    int length = buttonsRows.size();
    int buttonRowCount = (length + 5) / 5;
    int maxPage = (length + 5) / 5 / 4;
    buttonsRowLists = Lists.partition(buttonsRows, 5);

    controlButtons.add(Button.danger("close", Emoji.fromFormatted("U+26D4")).withLabel("關閉"));

    if (page >= 1 && page < maxPage) {
      controlButtons.add(Button.primary("last_page", Emoji.fromFormatted("U+21A9")).withLabel("上一頁"));
      controlButtons.add(Button.primary("refresh_page", Emoji.fromFormatted("U+1F504")).withLabel("重新整理"));
      controlButtons.add(Button.primary("next_page", Emoji.fromFormatted("U+21AA")).withLabel("下一頁"));
    } else if (page < 1 && page < maxPage) {
      controlButtons.add(Button.primary("last_page", Emoji.fromFormatted("U+21A9")).withLabel("上一頁").asDisabled());
      controlButtons.add(Button.primary("refresh_page", Emoji.fromFormatted("U+1F504")).withLabel("重新整理"));
      controlButtons.add(Button.primary("next_page", Emoji.fromFormatted("U+21AA")).withLabel("下一頁"));
    } else if (page == maxPage) {
      controlButtons.add(Button.primary("last_page", Emoji.fromFormatted("U+21A9")).withLabel("上一頁"));
      controlButtons.add(Button.primary("refresh_page", Emoji.fromFormatted("U+1F504")).withLabel("重新整理"));
      controlButtons.add(Button.primary("next_page", Emoji.fromFormatted("U+21AA")).withLabel("下一頁").asDisabled());
    }
    if (buttonRowCount - page * 4 >= 4) {
      event.editMessage("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .setComponents(
              ActionRow.of(controlButtons),
              ActionRow.of(buttonsRowLists.get(0 + page * 4)),
              ActionRow.of(buttonsRowLists.get(1 + page * 4)),
              ActionRow.of(buttonsRowLists.get(2 + page * 4)),
              ActionRow.of(buttonsRowLists.get(3 + page * 4))
          ).queue();
    } else if (buttonRowCount - page * 4 == 3) {
      event.editMessage("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .setComponents(
              ActionRow.of(controlButtons),
              ActionRow.of(buttonsRowLists.get(0 + page * 4)),
              ActionRow.of(buttonsRowLists.get(1 + page * 4)),
              ActionRow.of(buttonsRowLists.get(2 + page * 4))
          ).queue();
    } else if (buttonRowCount - page * 4 == 2) {
      event.editMessage("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .setComponents(
              ActionRow.of(controlButtons),
              ActionRow.of(buttonsRowLists.get(0 + page * 4)),
              ActionRow.of(buttonsRowLists.get(1 + page * 4))
          ).queue();
    } else if (buttonRowCount - page * 4 == 1) {
      event.editMessage("已讀取" + DiscordCommandListener.nowNameOfPlugin + "/" + DiscordCommandListener.nowNameOfFolder + "資料夾 (頁數: " + page + "/" + maxPage + ")")
          .setComponents(
              ActionRow.of(controlButtons),
              ActionRow.of(buttonsRowLists.get(0 + page * 4))
          ).queue();
    }
  }
}
