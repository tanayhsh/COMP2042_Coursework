package com.comp2042;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.EnumMap;
import java.util.Map;

public class EntityManager {

    private final ImageView head = new ImageView(new Image("resources/views/res/snakehead.png"));
    private final ImageView body = new ImageView(new Image("resources/views/res/snakebody.png"));
    private final ImageView turn = new ImageView(new Image("resources/views/res/snaketurnleft.png"));
    private final ImageView tail = new ImageView(new Image("resources/views/res/snaketail.png"));

    // its like a dictionary where the keys are the enum values
    private final Map<CoinType, Image> coinImages = new EnumMap<>(CoinType.class);

    public EntityManager(){
        coinImages.put(CoinType.RED, new Image("resources/views/res/redcoin.png"));
        coinImages.put(CoinType.GOLD, new Image("resources/views/res/goldcoin.png"));
        coinImages.put(CoinType.BLUE, new Image("resources/views/res/bluecoin.png"));
        coinImages.put(CoinType.RAINBOW, new Image("resources/views/res/rainbowcoin.png"));
        coinImages.put(CoinType.SILVER, new Image("resources/views/res/silvercoin.png"));
    }

    public ImageView getHead() {
        return head;
    }

    public ImageView getBody() {
        return body;
    }

    public ImageView getTurn() {
        return turn;
    }

    public ImageView getTail() {
        return tail;
    }

    public Image getCoinImages(CoinType type) {
        return coinImages.get(type);
    }
}
