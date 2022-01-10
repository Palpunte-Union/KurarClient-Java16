package si.f5.pa_union;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Options {
    Minecraft minecraft;
    File optionsFile;
    boolean isUpdated = false;

    public Options(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.optionsFile = new File(minecraft.gameDirectory, "kurar_options.json");
        createDefaultConfig(optionsFile);

        Gson gson = new Gson();

        try {
            JsonObject object = gson.fromJson(new FileReader(optionsFile), JsonObject.class);
            if (object.get("version").getAsInt() != KurarClient.getInstance().currentVersion) {
                isUpdated = true;
                gson.toJson(new KurarOption(KurarClient.getInstance().currentVersion), new FileWriter(optionsFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultConfig(File file, String... v) {
        if (!file.exists()) {
            try {
                Gson gson = new Gson();
                gson.toJson(new KurarOption(0), new FileWriter(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}
