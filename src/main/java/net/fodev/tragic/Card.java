package net.fodev.tragic;

import com.jme3.asset.TextureKey;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture2D;

public class Card {
    String name;
    private float posX;
    private float posY;
    private float posZ;
    private float width;
    private float height;
    private float rotation;
    CardPrototype prototype;

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

    public boolean isMouseOver(Vector2f mouse) {
        if (posX - width / 2 <= mouse.x && mouse.x <= posX + width / 2
            && posY - height / 2 <= mouse.y && mouse.y <= posY + height / 2) {
            return true;
        } else {
            return false;
        }
    }
}
