package net.fodev.tragic;

public class Card {
    String name;
    protected float posX;
    protected float posY;
    protected float width;
    protected float height;
    protected float rotation;
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

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return prototype.getImageFileName();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
