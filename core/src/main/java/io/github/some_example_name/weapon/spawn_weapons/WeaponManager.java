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
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;
import java.util.List;

public class WeaponManager {
    private List<Weapon> weapons;
    private float unitScale;
    private Player player;
    private BitmapFont font;
    private Weapon weaponToBuy;
    private boolean isBuying;
    private String message; // Message to display
    private GlyphLayout layout;
    private Pap pap;

    public WeaponManager(TiledMap tiledMap, Player player, float unitScale) {
        this.weapons = new ArrayList<>();
        this.unitScale = unitScale;
        this.player = player;
        this.font = new BitmapFont();
        this.weaponToBuy = null;
        this.isBuying = false;
        this.message = ""; // Initialize message
        this.layout = new GlyphLayout();

        // Initialize weapon spawns
        initializeWeaponSpawns(tiledMap);
        // Initialize pap spawns
        initializePapSpawns(tiledMap);
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
        for (MapObject papSpawn : tiledMap.getLayers().get("pap_interactions").getObjects()) {
            if (papSpawn instanceof RectangleMapObject) {
                String name = papSpawn.getName();
                if ("pap".equalsIgnoreCase(name)) {
                    Rectangle rect = ((RectangleMapObject) papSpawn).getRectangle();
                    float x = rect.x * unitScale;
                    float y = rect.y * unitScale;
                    pap = new Pap(x, y);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }

        if (pap != null) {
            pap.render(batch);
        } else {
            System.out.println("Pap is null");
        }

        // Check for player proximity to weapon spawns and display message
        for (Weapon weapon : weapons) {
            if (isPlayerNearWeapon(weapon)) {
                if (isBuying && weaponToBuy == weapon) {
                    displayMessage(batch, "Appuyez sur E pour confirmer l'achat de " + weapon.getName(), weapon.getX(), weapon.getY());
                } else {
                    displayMessage(batch, "Acheter", weapon.getX() + 250, weapon.getY() + 250);
                }
            }
        }

        // Check for player proximity to pap and display message
        if (pap != null && isPlayerNearPap()) {
            displayMessage(batch, "Voulez-vous améliorer votre " + player.getCurrentWeapon().getName() + " ?", pap.getBounds().x, pap.getBounds().y + pap.getBounds().height);
        }

        // Display purchase message if any
        if (!message.isEmpty()) {
            displayMessage(batch, message, player.getX(), player.getY() + 50);
        }
    }

    public void update(float delta, OrthographicCamera camera) {
        for (Weapon weapon : weapons) {
            weapon.update(delta, camera, null); // Pass the camera and null for enemies for now
        }

        // Handle weapon purchase
        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            handleWeaponPurchase();
            handlePapInteraction();
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

    private void displayMessage(SpriteBatch batch, String message, float x, float y) {
        layout.setText(font, message);
        font.getData().setScale(2); // Increase font size
        font.draw(batch, message, x, y); // Center the message
        font.getData().setScale(1); // Reset font size
    }

    private void handleWeaponPurchase() {
        for (Weapon weapon : weapons) {
            if (isPlayerNearWeapon(weapon)) {
                if (isBuying && weaponToBuy == weapon) {
                    int weaponCost = 100; // Example cost
                    if (ScoreManager.getInstance().getScore() >= weaponCost) {
                        ScoreManager.getInstance().addPoints(-weaponCost);
                        Weapon newWeapon = createWeaponInstance(weapon); // Create a new instance of the weapon
                        player.addWeapon(newWeapon); // Add the new weapon to the player
                        isBuying = false;
                        weaponToBuy = null;
                    } else {
                        message = "Points insuffisants pour " + weapon.getName();
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
            if (ScoreManager.getInstance().getScore() >= upgradeCost) {
                ScoreManager.getInstance().addPoints(-upgradeCost);
                upgradeWeapon(currentWeapon);
                message = "";
            } else {
                message = "";
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
        weapon.setBallSpeed((float) Settings.WEAPON_STATS.get(papTextureName).get("ballSpeed"));
    }
}