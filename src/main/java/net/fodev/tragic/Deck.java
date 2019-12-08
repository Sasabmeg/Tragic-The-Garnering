package net.fodev.tragic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private int posX;
    private int posY;
    private int width;
    private int height;
    private int maxCards;

    private List<Card> cards;

    Deck(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        cards = new ArrayList<>();
        maxCards = 100;
    }

    void addCard(Card card) {
        card.setWidth(257);
        card.setHeight(366);
        cards.add(card);
    }

    void addCardAtRandomIndex(Card card) {
        card.setWidth(257);
        card.setHeight(366);
        Random random = new Random();
        int index = random.nextInt(cards.size());
        cards.add(index, card);
    }

    void shuffle(int times) {
        Random random = new Random();
        for (int j = 0; j < times; j++) {
            for (int i = 0; i < cards.size(); i++) {
                int newPos = random.nextInt(cards.size());
                Collections.swap(cards, i, newPos);
            }
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

    Card popLast() {
        int lastIndex = cards.size()-1;
        return cards.remove(lastIndex);
        /*
        Card ret = cards.get(lastIndex);
        cards.remove(lastIndex);
        return ret;

         */
    }

    Card popFirst() {
        return cards.remove(0);
    }
}
