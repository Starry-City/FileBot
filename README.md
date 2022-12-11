# FileBot
- 支援版本 1.19 (測試環境)

![image](https://user-images.githubusercontent.com/42506064/206912671-ae78c84d-c4fb-4458-98c7-536aeaa1f590.png)




## ✏簡介
這是一個基於Minecraft paper伺服器端的插件，可從Discord 操作 Minecraft 伺服器中各插件之下的資料夾檔案。
~~（還有許多功能未完善）~~

## 💡功能表
```
- 上傳 !upload
- 檢視資料夾內檔案 /filebot
```

![image](https://user-images.githubusercontent.com/42506064/206912959-16dc0db8-8f35-4086-a4f2-249da8673bb5.png)

![image](https://user-images.githubusercontent.com/42506064/206912706-4f7b77c9-7d1c-491b-9b0e-b8bfbf1ac82c.png)

![image](https://user-images.githubusercontent.com/42506064/206912723-2fc5f325-bfe5-4ffd-9aca-8e2dbe92886a.png)

## 🛠config.yml 
```
# FileBot
# 版本
version: v1.0-alpha
# 填入Discord Bot Token
BotToken: 
# 填入Discord Channel Id (對頻道按右鍵複製ID)
ChannelId: 
# 顯示Discord Bot狀態 (正在玩 )
Activity: 指令 

# !upload 後方如果沒有參數時的預設插件與資料夾
DefaultUpload:
  Plugin: WorldEdit
  Folder: schematics

# 登記需要插件的資料夾
HookPluginList:
  WorldEdit:
  - schematics
```

## 📃License

<img src = https://user-images.githubusercontent.com/42506064/206913125-5738b786-9e4d-47a4-b9cf-ba89614c6c42.png width=200>

- 此插件使用 AGPL-3.0 開源憑證
