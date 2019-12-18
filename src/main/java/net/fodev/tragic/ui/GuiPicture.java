package net.fodev.tragic.ui;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

public class GuiPicture extends Node {
    Picture picture;
    private int sourceImageWidth;
    private int sourceImageHeight;

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

    public GuiPicture(String name, AssetManager assetManager, float width, float height, ColorRGBA color) {
        super(name);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color); // 0.5f is the alpha value
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Geometry rect = new Geometry("MouseRect", new Quad(width, height));
        rect.setMaterial(mat);
        sourceImageWidth = (int)width;
        sourceImageHeight = (int)height;
        rect.setLocalTranslation(this.getLocalTranslation().getX() - sourceImageWidth / 2, this.getLocalTranslation().getY() - sourceImageHeight / 2, 0);
        attachChild(rect);
    }

    public Spatial rotate(float xAngle, float yAngle, float zAngle) {
        return super.rotate(xAngle, yAngle, zAngle);
    }

    public void rotate(float zAngle) {
        super.rotate(0, 0, -zAngle);
    }

    public void setCenterPosition(float x, float y, float z) {
        setLocalTranslation(x, y, z);
    }

    public void setBottomLeftPosition(float x, float y, float z) {
        setLocalTranslation(x + sourceImageWidth * getLocalScale().x / 2, y + sourceImageHeight * getLocalScale().y / 2, z);
    }

    public int getSourceImageWidth() {
        return sourceImageWidth;
    }

    public int getSourceImageHeight() {
        return sourceImageHeight;
    }

    void scaleImage(float factor) {
        if (picture != null) {
            picture.setWidth(sourceImageWidth * factor);
            picture.setHeight(sourceImageHeight * factor);
            picture.setPosition(- sourceImageWidth / 2 * factor, - sourceImageHeight / 2 * factor);
        }
    }

    public boolean isMouseOver(Vector2f mouse) {
        float posX = this.getLocalTranslation().x;
        float posY = this.getLocalTranslation().y;
        float width = picture.getLocalScale().x;
        float height = picture.getLocalScale().y;
        if (posX - width / 2 <= mouse.x && mouse.x <= posX + width / 2
                && posY - height / 2 <= mouse.y && mouse.y <= posY + height / 2) {
            return true;
        } else {
            return false;
        }
    }

    public void hide(Node guiNode) {
        if (guiNode.getChildIndex(this) >= 0) {
            guiNode.detachChild(this);
        }
    }
}
