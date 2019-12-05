package net.fodev.tragic;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

import java.util.ArrayList;
import java.util.List;

public class StartHandSelector {
    public static boolean debugEnabled = false;

    private int posX;
    private int posY;
    private int width;
    private int height;
    private List<Card> cards;

    StartHandSelector(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        cards = new ArrayList<>();
    }

    void drawBackground(AssetManager assetManager, Node guiNode) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(0, 0, 0, 0.66f)); // 0.5f is the alpha value
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Geometry mouseRect = new Geometry("MouseRect", new Quad(width, height));
        mouseRect.setMaterial(mat);
        mouseRect.setLocalTranslation(posX, posY, 5);
        guiNode.attachChild(mouseRect);
    }

    void addCard(Card card) {
        card.setWidth(257);
        card.setHeight(360);
        cards.add(card);
        recalcCardPositions();
    }

    private void recalcCardPositions() {
        int spacing = 15;
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            float cardX = posX + spacing;
            float totalWidth = width - spacing * 2;
            float spaceBetweenCards = (totalWidth - cards.size() * card.getWidth());
            if (cards.size() > 1) {
                spaceBetweenCards = spaceBetweenCards / (cards.size() - 1);
                cardX += i * (card.getWidth() + spaceBetweenCards);
            } else {
                cardX += totalWidth / 2 - card.getWidth() / 2;
            }
            float cardY =  posY + height - spacing;
            card.setPosX(cardX + card.getWidth() / 2);
            card.setPosY(cardY - card.getHeight() / 2);
            card.setPosZ(10);
        }
    }

    void showCards(AssetManager assetManager, Node guiNode) {
        for (Card card : cards) {
            card.show(assetManager, guiNode);
            card.activate(assetManager, guiNode);
        }
    }

    void mouseClick(Vector2f mouse, Node guiNode) {
        for (Card card : cards) {
            if (card.isMouseOver(mouse)) {
                System.out.println(String.format("Starting Hand Selector: Mouse over card %s at (%.0f, %.0f)", card.getName(), mouse.x, mouse.y));
                card.deActivate(guiNode);
            }
        }
    }

    void setCardInactive() {

    }
}
