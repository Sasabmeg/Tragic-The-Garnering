package net.fodev.tragic;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class StartHandSelector {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int depth = 5;
    private List<Card> cards;
    private GuiPicture background;
    private GuiPicture acceptButton;

    StartHandSelector(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        cards = new ArrayList<>();
    }

    void drawBackground(AssetManager assetManager, Node guiNode) {
        if (background == null || guiNode.getChildIndex(background) < 0) {
            background = new GuiPicture("StartHandSelector" + "_background", assetManager, width, height, new ColorRGBA(0, 0, 0, 0.85f));
            background.setBottomLeftPosition(posX, posY, depth - 0.01f);
            guiNode.attachChild(background);
        }
    }

    private void drawAcceptButton(AssetManager assetManager, Node guiNode) {
        if (acceptButton == null || guiNode.getChildIndex(acceptButton) < 0) {
            acceptButton = new GuiPicture("StartHandSelector" + "_acceptButton", assetManager, "interface/accept_button.png", true);
            acceptButton.setCenterPosition(posX + width / 2, posY + acceptButton.getSourceImageHeight(), depth);
            guiNode.attachChild(acceptButton);
        }
    }

    void show(AssetManager assetManager, Node guiNode) {
        drawBackground(assetManager, guiNode);
        showCards(assetManager, guiNode);
        drawAcceptButton(assetManager, guiNode);

    }

    void hide(Node guiNode) {

    }

    void addCard(Card card) {
        card.setWidth(257);
        card.setHeight(366);
        cards.add(card);
        recalcCardPositions();
    }

    private void recalcCardPositions() {
        int spacing = 15;
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            float cardX = posX + spacing;
            float totalWidth = width - spacing * 2;
            float spaceBetweenCards = (totalWidth - (cards.size() * card.getWidth()));
            if (cards.size() > 1) {
                spaceBetweenCards = spaceBetweenCards / (cards.size() - 1);
                cardX += i * (card.getWidth() + spaceBetweenCards);
            } else {
                cardX += totalWidth / 2 - card.getWidth() / 2;
            }
            float cardY =  posY + height - spacing;
            card.setPosX(cardX + card.getWidth() / 2);
            card.setPosY(cardY - card.getHeight() / 2);
            card.setPosZ(depth + 1);
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
        if (acceptButton != null && acceptButton.isMouseOver(mouse)) {
            System.out.println(String.format("Starting Hand Selector: Mouse over Accept Button at (%.0f, %.0f)", mouse.x, mouse.y));
        }

    }
}
