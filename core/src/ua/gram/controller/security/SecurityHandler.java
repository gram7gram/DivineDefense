package ua.gram.controller.security;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import ua.gram.DDGame;
import ua.gram.controller.factory.PlayerFactory;
import ua.gram.model.Player;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class SecurityHandler {

    private final String prefPath;
    private final String sumPath;
    private final HashMap<String, String> device;
    private final Json json;
    private String checksum;
    private FileReader input;
    private FileWriter output;
    private PlayerFactory container;

    public SecurityHandler(HashMap<String, String> device) {
        this.device = device;
        json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        prefPath = device.get("user.prefs") + "divine.json";
        sumPath = device.get("user.prefs") + "divine.sum";
        System.out.println("INFO: Security initialized");
    }

    public void checkSum() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(sumPath));
            checksum = br.readLine();
            br.close();
            if (!checksum.equals(computeChecksum())) {
                Gdx.app.error("ERROR", "Checksum file is corrupted");
            } else {
                Gdx.app.log("INFO", "Checksum is OK");
            }
        } catch (FileNotFoundException e) {
            Gdx.app.log("WARN", "Missing checksum file");
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not read checksum file: I/O error");
        }
    }

    public void save() {
        try {
            encrypt(json.toJson(container));
            output = new FileWriter(new File(sumPath));
            output.write(computeChecksum());
            output.close();
            Gdx.app.log("INFO", "Player saved successfully");
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not save player: I/O error");
        }
    }

    public Player load(DDGame game) {
        container = game.deserialize("data/player.json", PlayerFactory.class, true);
        if (checksum != null && checksum.equals(computeChecksum()) && decrypt()) {
            try {
                input = new FileReader(new File(prefPath));
                container = json.fromJson(PlayerFactory.class, input);
                if (container.id.equals(device.get("device.id"))) {
                    Gdx.app.log("INFO", "Preferences loaded successfully");
                    return container.create(game);
                } else {
                    Gdx.app.log("WARN", "Preferences are corrupted");
                }
            } catch (FileNotFoundException e) {
                Gdx.app.error("ERROR", "Missing preferences file");
            }
        } else {
            Gdx.app.log("WARN", "Missing preferences file");
        }
        Player player = container.create(game);
        player.setDefault(true);
        Gdx.app.log("WARN", "Player defaults are used");
        return player;
    }

    private String computeChecksum() {
        String algorithm = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(device.get("device.id").getBytes());//salt
            input = new FileReader(new File(prefPath));
            BufferedReader reader = new BufferedReader(input);
            String s;
            while ((s = reader.readLine()) != null) {
                md.update(s.getBytes());
            }
            input.close();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : md.digest()) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Gdx.app.error("ERROR", "Could not compute checksum: " + algorithm + " is missing");
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not compute checksum: I/O error");
        }
        return "undefined";
    }

    private boolean encrypt(String data) {
        try {
            data = Base64Coder.encodeString(data);
            output = new FileWriter(new File(prefPath));
            output.write(data);
            output.flush();
            output.close();
            Gdx.app.log("INFO", "Preferences encrypted successfully");
            return true;
        } catch (FileNotFoundException e) {
            Gdx.app.error("ERROR", "Could not encrypt preferences: missing file " + prefPath);
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not encrypt preferences: I/O error");
        }
        return false;
    }

    private boolean decrypt() {
        try {
            String data = "";
            input = new FileReader(new File(prefPath));
            BufferedReader reader = new BufferedReader(input);
            String s;
            while ((s = reader.readLine()) != null) {
                data += s;
            }
            input.close();
            data = Base64Coder.decodeString(data);
            output = new FileWriter(new File(prefPath));
            output.write(data);
            output.flush();
            output.close();
            Gdx.app.log("INFO", "Preferences decrypted successfully");
            return true;
        } catch (FileNotFoundException e) {
            Gdx.app.error("ERROR", "Could not decrypt preferences: missing file");
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not decrypt preferences: I/O error");
        }
        return false;
    }

    public boolean sendBugReport(String error) {
        return new BugReport(device).sendReport(error);
    }
}
