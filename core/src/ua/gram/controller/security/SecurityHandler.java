package ua.gram.controller.security;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import ua.gram.DDGame;
import ua.gram.model.Player;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.model.prototype.PlayerPrototype;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class SecurityHandler {

    private final String prefPath;
    private final String sumPath;
    private final GamePrototype prototype;
    private final Json json;
    private FileReader input;
    private FileWriter output;

    public SecurityHandler(GamePrototype prototype) {
        this.prototype = prototype;
        json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        prefPath = prototype.configPath + "divine.json";
        sumPath = prototype.configPath + "divine.sum";
        System.out.println("INFO: Security initialized");
    }

    /**
     * @deprecated
     */
    public void checkSum() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(sumPath));
            String checksum = br.readLine();
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

    /**
     * @deprecated
     */
    public void save() {
        try {
            encrypt(json.toJson(prototype));
            output = new FileWriter(new File(sumPath));
            output.write(computeChecksum());
            output.close();
            Gdx.app.log("INFO", "Player saved successfully");
        } catch (IOException e) {
            Gdx.app.error("ERROR", "Could not save player: I/O error");
        }
    }


    public Player load(DDGame game) {
        PlayerPrototype prototype = game.deserialize("data/player.json", PlayerPrototype.class, true);
        Player player = new Player(prototype);
        player.setDefault(true);
        Gdx.app.log("WARN", "Player defaults are used");
        return player;
    }

    /**
     * @deprecated
     */
    private String computeChecksum() {
        String algorithm = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(prototype.id.getBytes());//salt
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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
        return new BugReport(prototype).sendReport(error);
    }
}
