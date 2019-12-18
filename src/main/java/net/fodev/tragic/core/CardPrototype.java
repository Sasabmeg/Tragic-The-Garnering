package net.fodev.tragic.core;

public class CardPrototype {
    String name;
    String imageFileName;

    public CardPrototype(String name, String imageName) {
        this.name = name;
        this.imageFileName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }
}
