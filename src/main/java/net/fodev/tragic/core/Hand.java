package net.fodev.tragic.core;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import net.fodev.tragic.core.Card;

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

    public Hand(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        cards = new ArrayList<>();
        maxCards = 10;
        minRotation = -0.40f;
        maxRotation = 0.40f;
    }

    public void addCard(Card card) {
        //card.setWidth(240);
        //card.setHeight(320);
        card.setWidth(257);
        card.setHeight(366);
        card.setScale(scaleFactor);
        cards.add(card);
        recalcCardPositions();
    }

    private void recalcCardPositions() {
        int spacing = 5;

        System.out.println("HAND - recalcCardPositions()");
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

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public void showCards(AssetManager assetManager, Node guiNode) {
        recalcCardPositions();
        for (Card card : cards) {
            card.setPosZ(1);
            card.setScale(scaleFactor);
            card.show(assetManager, guiNode);
            card.activate(assetManager, guiNode);
            /*
            GuiPicture cardPic = new GuiPicture(card.getName(), assetManager, card.getImageFileName(), true);
            cardPic.setCenterPosition(card.getPosX(), card.getPosY(), 1);
            cardPic.scaleImage(scaleFactor);
            cardPic.rotate(card.getRotation());
            guiNode.attachChild(cardPic);

             */
        }
    }

    String listCards() {
        return listCards(12);
    }

    String listCards(int maxNameLength) {
        String message = "(";
        for (int i = 0; i < cards.size(); i++) {
            if (maxNameLength == 0) {
                message += String.format("[%d] %s", i, cards.get(i).getName());
            } else {
                String format = "[%d] %-" + maxNameLength + "." + maxNameLength + "s";
                message += String.format(format, i, cards.get(i).getName());
            }
            if (i < cards.size() - 1) {
                message += ", ";
            }
        }
        message += ")";
        return message;
    }

}
