package net.fodev.tragic.core;

import java.util.List;

public class CardFactory {
    private List<CardPrototype> prototypes;

    public CardFactory(List<CardPrototype> prototypes) {
        this.prototypes = prototypes;
    }

    public Card cardByName(String name) {
        for (CardPrototype proto : prototypes) {
            if (proto.getName().equalsIgnoreCase(name)) {
                return new Card(name, proto);
            }
        }
        return null;
    }

    public Card cardByIndex(int index) {
        return new Card(prototypes.get(index).getName(), prototypes.get(index));
    }
}
