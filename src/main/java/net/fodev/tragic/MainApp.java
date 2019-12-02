package net.fodev.tragic;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends SimpleApplication {
    public static int width = 1280;
    public static int height = 800;

    CardFactory cardFactory;
    Hand hand;

    public static void main(String[] args){
        MainApp app = new MainApp();
        //  disable popup config, it bugs the runtime compile
        app.setShowSettings(false);

        //  this is how we set setting
        AppSettings settings = new AppSettings(true);
        settings.put("Width", width);
        settings.put("Height", height);
        settings.put("Title", "Tragic: the garnering.");
        app.setSettings(settings);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        try {
            init();
            initCardPrototypes();
            initBackground();
            initHand();

            Vector2f mouse = inputManager.getCursorPosition();

            /*
            GuiPicture fanOfKnives = new GuiPicture("Fan of knives", assetManager, "cards/card_act_fanOfKnives.png", true);
            fanOfKnives.setPosition(settings.getWidth() / 2, settings.getHeight() / 2, 2);
            guiNode.attachChild(fanOfKnives);
            */
            //showCard(hand.getTopCard());
            hand.showCards(assetManager, guiNode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void simpleUpdate(float tpf) {
        try {
            //GuiPicture fanOfKnives = (GuiPicture)guiNode.getChild("Fan of knives");
            //fanOfKnives.rotate(0, 0, 0.01f);
            //fanOfKnives.move(0.1f, 0.1f, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        //  enable mouse pointer
        flyCam.setEnabled(false);
        setDisplayStatView(false);
        //setDisplayFps(false);
    }

    private void initCardPrototypes() {
        List<CardPrototype> protos = new ArrayList<>();
        protos.add(new CardPrototype("Fan of Knives", "cards/card_act_fanOfKnives.png"));
        protos.add(new CardPrototype("Silent Death", "cards/card_act_silentDeath.png"));
        protos.add(new CardPrototype("Silent Running", "cards/card_act_silentRunning.png"));
        protos.add(new CardPrototype("Backstab", "cards/card_act_backstab.png"));
        protos.add(new CardPrototype("Betrayal", "cards/card_act_betrayal.png"));
        protos.add(new CardPrototype("Shiv", "cards/card_act_shiv.png"));
        protos.add(new CardPrototype("Stealthboy", "cards/card_act_stealthboy.png"));
        protos.add(new CardPrototype("Deadly Poison", "cards/card_act_deadlyPoison.png"));
        protos.add(new CardPrototype("Wakizashi Blade", "cards/card_weap_wakizashiBlade.png"));

        cardFactory = new CardFactory(protos);
    }

    private void initBackground() {
        GuiPicture background = new GuiPicture("Background", assetManager, "dev/base2.png", true);
        background.setPosition(width / 2, height / 2, -100);
        guiNode.attachChild(background);
    }

    private void initHand() {
        hand = new Hand(390, 0, 470, 115);
        //hand = new Hand(90, 150, 970, 415);
        hand.addCard(cardFactory.cardByName("Fan of Knives"));
        hand.addCard(cardFactory.cardByName("Silent Death"));
        hand.addCard(cardFactory.cardByName("Silent Running"));
        hand.addCard(cardFactory.cardByName("Backstab"));
        /*hand.addCard(cardFactory.cardByName("Betrayal"));
        /*
        hand.addCard(cardFactory.cardByName("Shiv"));
        hand.addCard(cardFactory.cardByName("Stealthboy"));
        hand.addCard(cardFactory.cardByName("Deadly Poison"));
        hand.addCard(cardFactory.cardByName("Wakizashi Blade"));
        hand.addCard(cardFactory.cardByName("Wakizashi Blade"));

         */
        drawBackgroundRectangle(hand.getPosX(), hand.getPosY(), 1, hand.getWidth(), hand.getHeight());
    }

    private void drawBackgroundRectangle(int posX, int posY, int posZ, int width, int height) {
        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(0, 0, 0, 0.5f)); // 0.5f is the alpha value
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Geometry mouseRect = new Geometry("MouseRect", new Quad(width, height));
        mouseRect.setMaterial(mat);
        mouseRect.setLocalTranslation(posX, posY, posZ);
        guiNode.attachChild(mouseRect);
    }

    private void showCard(Card card) {
        if (card != null) {
            GuiPicture cardPic = new GuiPicture(card.getName(), assetManager, card.getImageFileName(), true);
            if (cardPic != null) {
                cardPic.setPosition(card.getPosX(), card.getPosY(), 1);
                guiNode.attachChild(cardPic);
            } else {
                System.out.println("Warning: showCard() - cardPic is null.");
            }
        } else {
            System.out.println("Warning: showCard() - card is null.");
        }
    }
}