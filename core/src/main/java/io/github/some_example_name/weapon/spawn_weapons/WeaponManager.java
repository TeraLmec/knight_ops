package io.github.some_example_name.weapon.spawn_weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.weapon.bullets.PapAmmo;
import io.github.some_example_name.weapon.range.Ar;
import io.github.some_example_name.weapon.range.Bmg;
import io.github.some_example_name.weapon.range.Mauser;
import io.github.some_example_name.weapon.range.Vector;
import io.github.some_example_name.weapon.range.Winchester;
import io.github.some_example_name.ScoreManager;
import io.github.some_example_name.Settings;
import io.github.some_example_name.Spawn;
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.github.some_example_name.MessageManager;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeaponManager {
    private List<Weapon> weapons;
    private float unitScale;
    private Player player;
    private BitmapFont font;
    private Weapon weaponToBuy;
    private boolean isBuying;
    private GlyphLayout layout;
    private Pap pap;
    private Spawn spawnManager;
    private MessageManager messageManager;

    public WeaponManager(TiledMap tiledMap, Player player, float unitScale, Spawn spawnManager) {
        this.weapons = new ArrayList<>();
        this.unitScale = unitScale;
        this.player = player;
        this.font = new BitmapFont();
        this.weaponToBuy = null;
        this.isBuying = false;
        this.layout = new GlyphLayout();
        this.spawnManager = spawnManager;
        this.messageManager = new MessageManager(player);

        // Initialize weapon spawns
        initializeWeaponSpawns(tiledMap);

    }

    private void initializeWeaponSpawns(TiledMap tiledMap) {
        for (MapObject weaponSpawn : tiledMap.getLayers().get("interactions").getObjects()) {
            if (weaponSpawn instanceof RectangleMapObject) {
                String name = weaponSpawn.getName();
                Rectangle rect = ((RectangleMapObject) weaponSpawn).getRectangle();
                float x = rect.x * unitScale - 250;
                float y = rect.y * unitScale - 250;

                Weapon weapon = null;
                switch (name.toLowerCase()) {
                    case "ar":
                        weapon = new Ar(player, null);
                        break;
                    case "bmg":
                        weapon = new Bmg(player, null);
                        break;
                    case "mauser":
                        weapon = new Mauser(player, null);
                        break;
                    case "vector":
                        weapon = new Vector(player, null);
                        break;
                    case "winchester":
                        weapon = new Winchester(player, null);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown weapon: " + name);
                }

                if (weapon != null) {
                    weapon.setPosition(x, y); // Center the weapon image
                    weapons.add(weapon);
                }
            }
        }
    }

    private void initializePapSpawns(TiledMap tiledMap) {
        if (spawnManager.getCurrentRound() == 2) {
            for (MapObject papSpawn : tiledMap.getLayers().get("pap_interactions").getObjects()) {
                if (papSpawn instanceof RectangleMapObject) {
                    String name = papSpawn.getName();
                    if ("pap".equalsIgnoreCase(name)) {
                        Rectangle rect = ((RectangleMapObject) papSpawn).getRectangle();
                        float x = rect.x * unitScale;
                        float y = rect.y * unitScale;
                        pap = new Pap(x, y);
                        Sound papJingle = AssetLoader.getSound("pap_jingle");
                        messageManager.setMessage("Pack-a-punch spawned", "yes");
                        papJingle.play(Settings.PAP_JINGLE_VOLUME);
                    }
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }

        if (pap != null) pap.render(batch);

        // Check for player proximity to weapon spawns and display message
        for (Weapon weapon : weapons) {
            if (isPlayerNearWeapon(weapon)) {
                if (isBuying && weaponToBuy == weapon) {
                    messageManager.displayMessage(batch, "Press E to buy " + weapon.getName(), weapon.getX() + 250, weapon.getY() + 250);
                } else {
                    messageManager.displayMessage(batch, "Buy " + weapon.getName() + " | " + weapon.getWeaponCost(), weapon.getX() + 250, weapon.getY() + 250);
                }
            }
        }

        // Check for player proximity to pap and display message
        if (pap != null && isPlayerNearPap()) {
            messageManager.displayMessage(batch, "Do you want to upgrade " + player.getCurrentWeapon().getName() + " | " + player.getCurrentWeapon().getWeaponCost() +  " ?", pap.getBounds().x, pap.getBounds().y + pap.getBounds().height);
        }

        messageManager.render(batch);
    }

    public void update(float delta, OrthographicCamera camera) {
        for (Weapon weapon : weapons) {
            weapon.update(delta, camera, null);
        }

        // Handle weapon purchase
        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            handleWeaponPurchase();
            handlePapInteraction();
        }

        // Initialize pap spawns if the current round is 2
        if (spawnManager.getCurrentRound() == 2 && pap == null) {
            initializePapSpawns(spawnManager.getTiledMap());
        }
    }

    private boolean isPlayerNearWeapon(Weapon weapon) {
        float weaponCenterX = weapon.getX() + 250;
        float weaponCenterY = weapon.getY() + 250;
        float distance = (float) Math.sqrt(Math.pow(player.getX() - weaponCenterX, 2) + Math.pow(player.getY() - weaponCenterY, 2));
        return distance < 250;
    }

    private boolean isPlayerNearPap() {
        return pap != null && pap.getBounds().contains(player.getX(), player.getY());
    }

    private void handleWeaponPurchase() {
        for (Weapon weapon : weapons) {
            if (isPlayerNearWeapon(weapon)) {
                if (isBuying && weaponToBuy == weapon) {
                    int weaponCost = weapon.getWeaponCost();
                    if (ScoreManager.getInstance().getScore() >= weaponCost) {
                        ScoreManager.getInstance().addPoints(-weaponCost);
                        Weapon newWeapon = createWeaponInstance(weapon);
                        player.addWeapon(newWeapon);
                        isBuying = false;
                        weaponToBuy = null;
                        messageManager.setMessage(weapon.getName() + " acquired!", "yes");
                    } else {
                        messageManager.setMessage("Not enough points to buy " + weapon.getName(), "no");
                        isBuying = false;
                        weaponToBuy = null;
                    }
                } else {
                    weaponToBuy = weapon;
                    isBuying = true;
                }
                break;
            }
        }
    }

    private Weapon createWeaponInstance(Weapon weapon) {
        if (weapon instanceof Ar) {
            return new Ar(player, null);
        } else if (weapon instanceof Bmg) {
            return new Bmg(player, null);
        } else if (weapon instanceof Mauser) {
            return new Mauser(player, null);
        } else if (weapon instanceof Vector) {
            return new Vector(player, null);
        } else if (weapon instanceof Winchester) {
            return new Winchester(player, null);
        } else {
            throw new IllegalArgumentException("Unknown weapon type: " + weapon.getClass().getSimpleName());
        }
    }

    private void handlePapInteraction() {
        if (isPlayerNearPap()) {
            Weapon currentWeapon = player.getCurrentWeapon();
            int upgradeCost = 100;
            Sound papDoneSound = AssetLoader.getSound("pap_done");
            Sound papDenySound = AssetLoader.getSound("pap_deny");
            float pitch = Settings.MIN_PITCH + new Random().nextFloat() * (Settings.MAX_PITCH - Settings.MIN_PITCH);

            if (ScoreManager.getInstance().getScore() >= upgradeCost) {
                ScoreManager.getInstance().addPoints(-upgradeCost);
                upgradeWeapon(currentWeapon);
                papDoneSound.play(Settings.PAP_DONE_VOLUME, pitch, 0);
                messageManager.setMessage(player.getCurrentWeapon().getName() + " upgraded!", "yes");
            } else {
                papDenySound.play(Settings.PAP_DENY_VOLUME, pitch, 0);
                messageManager.setMessage("Not enough points to upgrade" + player.getCurrentWeapon().getName(), "no");
            }
        }
    }

    private void upgradeWeapon(Weapon weapon) {
        String weaponName = weapon.getName().toLowerCase();
        String papTextureName = weaponName + "_pap";
        Texture papTexture = AssetLoader.getTexture(papTextureName);
        weapon.setPap(true);
        
        if (papTexture == null) {
            System.out.println("Pap texture not found for weapon: " + weaponName);
        } else {
            weapon.setTexture(papTexture);
        }

        weapon.setDamage((int) Settings.WEAPON_STATS.get(papTextureName).get("damage"));
        weapon.setFireRate((float) Settings.WEAPON_STATS.get(papTextureName).get("fireRate"));
        if (!(weapon instanceof Bmg)) {
            weapon.setBallSpeed((float) Settings.WEAPON_STATS.get(papTextureName).get("ballSpeed"));
        }
    }
}