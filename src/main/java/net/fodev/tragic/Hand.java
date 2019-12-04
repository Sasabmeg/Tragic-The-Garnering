package net.fodev.tragic;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private int posX;
    private int posY;
    private int width;
    private int height;

    private float scaleFactor = 0.38f;
    private float minRotation;
    private float maxRotation;
    private int maxCards;

    private List<Card> cards;

    Hand(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        cards = new ArrayList<>();
        maxCards = 10;
        minRotation = -0.40f;
        maxRotation = 0.40f;
    }

    void addCard(Card card) {
        card.setWidth(240);
        card.setHeight(320);
        cards.add(card);
        recalcCardPositions();
    }

    private void recalcCardPositions() {
        int spacing = 5;

        //System.out.println("RECALCPOSITIONS");
        float rotationScale = (float)cards.size() / maxCards;
        float minRot = minRotation * rotationScale;
        float maxRot = maxRotation * rotationScale;
        //System.out.println(String.format("%f - (%f, %f)", rotationScale, minRot, maxRot));
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            float cardWidth = card.getWidth() * scaleFactor;
            float sink = 0;
            if (width > cards.size() * (cardWidth + spacing)) {
                card.setPosX(posX + (width - cards.size() * (cardWidth + spacing)) / 2 + cardWidth / 2 + i * (cardWidth + spacing));
            } else {
                card.setPosX(posX + 10 + (width - 20) * (i + 1) / (cards.size() + 1));
                float rotation = getBetweenValue(minRot, maxRot, 0, cards.size() - 1, i );
                //System.out.println(String.format("rotation = %f ((%f, %f), (%d, %d), %d)", rotation, minRotation, maxRotation, 0, cards.size() - 1, i));
                int max = Math.abs(cards.size() / 2);
                if (i <= cards.size() / 2) {
                    for (int j = i; j <= max; j++) {
                        float rot = getBetweenValue(minRot, maxRot, 0, cards.size() - 1, j);
                        sink += (float)Math.abs(Math.sin(rot)) * cardWidth / 2;
                        //System.out.println(String.format("j = %d, i = %d, rot = %f, sink = %f, max = %d, size = %d", j, i, rot, sink, max, cards.size()));
                    }
                } else {
                    for (int j = max + 1; j <= i; j++) {
                        float rot = getBetweenValue(minRot, maxRot, 0, cards.size() - 1, j);
                        sink += (float)Math.abs(Math.sin(rot)) * cardWidth / 2;
                    }
                }
                //sink = (float)Math.abs(Math.sin(rotation)) * cardWidth;
                card.setRotation(rotation);
            }
            card.setPosY(posY + (height - 10 - card.getHeight() * scaleFactor / 2) - sink);
        }
    }

    private float getBetweenValue(float minRotation, float maxRotation, float minValue, float maxValue, float value) {
        if (minValue >= maxValue) {
            return 0;
        }
        if (minRotation <= maxRotation) {
            float dif = maxRotation - minRotation;
            return minRotation + dif * (value - minValue) / (maxValue - minValue);
        } else {
            return 0;
        }
    }

    public void removeTopCard() {
        if (!cards.isEmpty()) {
            cards.remove(cards.size() - 1);
        }
    }
    public Card getTopCard() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        } else {
            return null;
        }
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    void showCards(AssetManager assetManager, Node guiNode) {
        for (Card card : cards) {
            GuiPicture cardPic = new GuiPicture(card.getName(), assetManager, card.getImageFileName(), true);
            cardPic.setPosition(card.getPosX(), card.getPosY(), 1);
            cardPic.scaleImage(scaleFactor);
            cardPic.rotate(card.getRotation());
            guiNode.attachChild(cardPic);
        }
    }
}
