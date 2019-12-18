package net.fodev.tragic.core;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import net.fodev.tragic.ui.GuiPicture;

public class Card {
    String name;
    private float posX;
    private float posY;
    private float posZ;
    private float width;
    private float height;
    private float rotation;
    private boolean active;
    private boolean shown;
    private float scale = 1;
    private CardPrototype prototype;
    private GuiPicture cardImage;
    private GuiPicture activeImage;
    private GuiPicture apImage;
    private GuiPicture hpImage;
    private GuiPicture damageImage;
    private GuiPicture armorImage;

    public Card(String name, CardPrototype prototype) {
        this.name = name;
        this.prototype = prototype;
        posX = 0;
        posY = 0;
        width = 0;
        height = 0;
        rotation = 0;
    }

    String getName() {
        return name;
    }

    String getImageFileName() {
        return prototype.getImageFileName();
    }

    float getWidth() {
        return width;
    }
    void setWidth(float width) {
        this.width = width;
    }

    float getHeight() {
        return height;
    }
    void setHeight(float height) {
        this.height = height;
    }

    float getScale() {
        return scale;
    }
    void setScale(float scale) {
        this.scale = scale;
    }

    float getPosX() {
        return posX;
    }
    void setPosX(float posX) {
        this.posX = posX;
    }

    float getPosY() {
        return posY;
    }
    void setPosY(float posY) {
        this.posY = posY;
    }

    float getPosZ() {
        return posZ;
    }
    void setPosZ(float posZ) {
        this.posZ = posZ;
    }

    float getRotation() {
        return rotation;
    }
    void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isShown() {
        return shown;
    }
    public void setShown(boolean shown) {
        this.shown = shown;
    }

    boolean isMouseOver(Vector2f mouse) {
        if (posX - width / 2 <= mouse.x && mouse.x <= posX + width / 2
            && posY - height / 2 <= mouse.y && mouse.y <= posY + height / 2) {
            return true;
        } else {
            return false;
        }
    }

    void show(AssetManager assetManager, Node guiNode) {
        //  add card to GUI, unless already exists there
        setShown(true);
        if (cardImage == null || guiNode.getChildIndex(cardImage) < 0) {
            cardImage = new GuiPicture(name, assetManager, getImageFileName(), true);
            cardImage.scale(scale);
            guiNode.attachChild(cardImage);
        }
        if (cardImage != null) {
            cardImage.setCenterPosition(posX, posY, posZ);
            cardImage.rotate(rotation);
        }
        System.out.println(String.format("Card.show() - %-20s Size: (%4d, %4d); Center: (%4d, %4d); Rotation: %f; Scale: %f",
                name, cardImage.getSourceImageWidth(), cardImage.getSourceImageHeight(), (int)posX, (int)posY, rotation, scale));
        //  if it's active, add card glow to GUI, unless already exists there
        if (isActive()) {
            activate(assetManager, guiNode);
        }
    }

    void hide(Node guiNode) {
        setShown(false);
        if (cardImage != null && guiNode.getChildIndex(cardImage) >= 0) {
            guiNode.detachChild(cardImage);
        }
        if (isActive()) {
            deActivate(guiNode);
        }
    }

    void activate(AssetManager assetManager, Node guiNode) {
        setActive(true);
        if (activeImage == null || guiNode.getChildIndex(activeImage) < 0) {
            activeImage = new GuiPicture(name + "_activeGlow", assetManager, width, height, new ColorRGBA(0, 1, 0, 0.5f));
            activeImage.setCenterPosition(posX, posY, posZ - 0.01f);
            activeImage.scale(scale);
            guiNode.attachChild(activeImage);
        }
    }

    void deActivate(Node guiNode) {
        setActive(false);
        if (activeImage != null && guiNode.getChildIndex(activeImage) >= 0) {
            System.out.println("Deactivating image.");
            guiNode.detachChild(activeImage);
        }
    }
}
