package it.gabryca.prison_ranks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

class MessagesYML {

    private File file = new File(Main.getInstance().getDataFolder()+"/messages.yml");
    private FileConfiguration conf;

    MessagesYML() {
        if(!file.exists()){
            try {
                file.createNewFile();
                conf = YamlConfiguration.loadConfiguration(file);
                conf.createSection("Messages");
                conf.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        conf = YamlConfiguration.loadConfiguration(file);
    }

    FileConfiguration getFile(){
        return conf;
    }

}
