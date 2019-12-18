package net.fodev.tragic;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import net.fodev.tragic.core.*;
import net.fodev.tragic.ui.GuiPicture;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends SimpleApplication {
    private static int width = 1280;
    private static int height = 800;

    private CardFactory cardFactory;
    private Hand hand;
    private Deck deck;
    private StartHandSelector startHandSelector;
    private String lmbClick = "LMB";

    public static void main(String[] args){
        MainApp app = new MainApp();
        //  disable popup config, it bugs the runtime compile
        app.setShowSettings(false);

        //  this is how we set setting
        AppSettings settings = new AppSettings(true);
        settings.put("Width", width);
        settings.put("Height", height);
        settings.put("Title", "Tragic: the garnering.");
        settings.setFrameRate(60);
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
            initDeck();
            initStartHandSelector(deck);
            initKeys();

            hand.showCards(assetManager, guiNode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initDeck() {
        deck = new Deck(100, 100, 100, 100);
        for (int i = 0; i < 10; i++) {
            deck.addCard(cardFactory.cardByIndex(i));
        }
        System.out.println(deck.listCards());
        //deck.shuffle(10);
        System.out.println(deck.listCards());
    }

    private void initKeys() {
        inputManager.addMapping(lmbClick, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, lmbClick);
        //inputManager.addListener(analogListener, lmbClick);
    }

    private final ActionListener actionListener = (name, keyPressed, tpf) -> {
        if (name.equals(lmbClick) && !keyPressed) {
            Vector2f mouse = inputManager.getCursorPosition();
            System.out.println(String.format("LMB clicked at (%.0f, %.0f)", mouse.x, mouse.y));
            startHandSelector.mouseClick(mouse, assetManager, guiNode, hand, deck);
        }
    };

    /*
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals(lmbClick)) {
                System.out.println("LMB clicked.");
            }
        }
    };
     */

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
        protos.add(new CardPrototype("Enclave Flamer", "cards/card_unit_enclaveFlamer.png"));
        protos.add(new CardPrototype("Fire Gecko", "cards/card_unit_fireGecko.png"));
        protos.add(new CardPrototype("Golden Gecko", "cards/card_unit_goldenGecko.png"));
        protos.add(new CardPrototype("Huge Radscorpion", "cards/card_unit_hugeRadscorpion.png"));
        protos.add(new CardPrototype("klamath Tamer", "cards/card_unit_klamathTamer.png"));
        protos.add(new CardPrototype("Klamath Trapper", "cards/card_unit_klamathTrapper.png"));
        protos.add(new CardPrototype("Little Gecko", "cards/card_unit_littleGecko.png"));
        protos.add(new CardPrototype("Mutated Mantis", "cards/card_unit_mutatedMantis.png"));
        protos.add(new CardPrototype("Mutated Molerat", "cards/card_unit_mutatedMolerat.png"));
        protos.add(new CardPrototype("Mutated Pigrat", "cards/card_unit_mutatedPigRat.png"));
        protos.add(new CardPrototype("Stray Dog", "cards/card_unit_strayDog.png"));
        protos.add(new CardPrototype("Wild Brahmin", "cards/card_unit_wildBrahmin.png"));
        protos.add(new CardPrototype("Vault Officer", "cards/card_unit_vaultOfficer.png"));
        cardFactory = new CardFactory(protos);
    }

    private void initBackground() {
        GuiPicture background = new GuiPicture("Background", assetManager, "dev/base2.png", true);
        background.setCenterPosition(width / 2, height / 2, -100);
        guiNode.attachChild(background);
    }

    private void initStartHandSelector(Deck deck) {
        startHandSelector = new StartHandSelector(190, 150, 900, 500);
        startHandSelector.addCard(deck.popFirst());
        startHandSelector.addCard(deck.popFirst());
        startHandSelector.addCard(deck.popFirst());

//        startHandSelector.addCard(cardFactory.cardByName("Silent Death"));
        startHandSelector.show(assetManager, guiNode);
    }

    private void initHand() {
        hand = new Hand(390, 0, 470, 115);
        //hand = new Hand(90, 150, 970, 415);
        hand.addCard(cardFactory.cardByName("Fan of Knives"));
        hand.addCard(cardFactory.cardByName("Silent Death"));
        hand.addCard(cardFactory.cardByName("Silent Running"));
        hand.addCard(cardFactory.cardByName("Backstab"));
        hand.addCard(cardFactory.cardByName("Betrayal"));
        /*
        hand.addCard(cardFactory.cardByName("Shiv"));
        /*
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
}
