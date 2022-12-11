package com.poyu39.filebot.listeners;

import com.poyu39.filebot.FileBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class DiscordMessageListener extends ListenerAdapter {

  private final Plugin plugin;

  public DiscordMessageListener(Plugin plugin) { this.plugin = plugin; }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    MessageChannel channel = event.getChannel();
    if(event.getMessage().getContentRaw().startsWith("!") && channel.getId().equals(FileBot.getChannelId())){
      switch (event.getMessage().getContentRaw()) {
        case "!upload":
          uploadFile(event, channel);
          break;
        default:
          channel.sendMessage("[ERROR] 無此指令").queue();
          break;
      }
    }
  }

  public void uploadFile(MessageReceivedEvent event, MessageChannel channel) {
    List<Message.Attachment> attachments = event.getMessage().getAttachments();
    if (attachments.isEmpty()) {
      channel.sendMessage("[ERORR] 沒有附件").queue();
    } else {
      String[] uploadPath = event.getMessage().toString().split(" ");
      String uploadPluginName;
      String uploadFolderName;
      if(uploadPath.length == 3) {
        uploadPluginName = uploadPath[1];
        uploadFolderName = uploadPath[2];
      } else {
        uploadPluginName = plugin.getConfig().getString("DefaultUpload.Plugin");
        uploadFolderName = plugin.getConfig().getString("DefaultUpload.Folder");
      }
      for (Message.Attachment attachment : attachments) {
        attachment.downloadToFile(getServer().getPluginManager().getPlugin(uploadPluginName).getDataFolder() + "/" + uploadFolderName + "/" + attachment.getFileName())
            .thenAccept(file -> {
              channel.sendMessage("[Done] 已經同步" + file.getName() + "到伺服器").queue();
            });
      }

    }
  }
}
