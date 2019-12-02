package net.fodev.tragic;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

public class GuiPicture extends Node {
    Picture picture;
    private int sourceImageWidth = 0;
    private int sourceImageHeight = 0;

    public GuiPicture(String name, AssetManager assetManager, String imgName, boolean useAlpha) {
        super(name);
        picture = new Picture(name);
        TextureKey key = new TextureKey(imgName, true);
        Texture2D tex = (Texture2D) assetManager.loadTexture(key);
        sourceImageWidth = tex.getImage().getWidth();
        sourceImageHeight = tex.getImage().getHeight();
        picture.setTexture(assetManager, tex, useAlpha);
        picture.setWidth(sourceImageWidth);
        picture.setHeight(sourceImageHeight);
        picture.setPosition(this.getLocalTranslation().getX() - sourceImageWidth / 2, this.getLocalTranslation().getY() - sourceImageHeight / 2);
        attachChild(picture);
    }

    public void setImage(AssetManager assetManager, String imgName, boolean useAlpha) {
        picture = new Picture(name);
        TextureKey key = new TextureKey(imgName, true);
        Texture2D tex = (Texture2D) assetManager.loadTexture(key);
        sourceImageWidth = tex.getImage().getWidth();
        sourceImageHeight = tex.getImage().getHeight();
        picture.setTexture(assetManager, tex, useAlpha);
        picture.setWidth(sourceImageWidth);
        picture.setHeight(sourceImageHeight);
        picture.setPosition(- sourceImageWidth / 2, - sourceImageHeight / 2);
        attachChild(picture);
    }

    public Spatial rotate(float xAngle, float yAngle, float zAngle) {
        return super.rotate(xAngle, yAngle, zAngle);
    }

    public void rotate(float zAngle) {
        super.rotate(0, 0, -zAngle);
    }

    public void setPosition(float x, float y, float z) {
        setLocalTranslation(x, y, z);
    }

    public int getSourceImageWidth() {
        return sourceImageWidth;
    }

    public int getSourceImageHeight() {
        return sourceImageHeight;
    }

    public void scaleImage(float factor) {
        picture.setWidth(sourceImageWidth * factor);
        picture.setHeight(sourceImageHeight * factor);
        picture.setPosition(- sourceImageWidth / 2 * factor, - sourceImageHeight / 2 * factor);
    }
}
