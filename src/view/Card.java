/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Pardo
 */
public class Card extends BorderPane {

    private static final String HEART = "HEART";
    private static final String DOLLAR = "DOLLAR";
    private static final String SMILE = "SMILE";
    private static final String EMPTY = "EMPTY";

    private boolean showing;
    private String symbol = EMPTY;
    private boolean winned = false;
    
    
    @FXML
    private ImageView imageView = new ImageView(new Image("/view/images/empty.png"));

    Card() {
    }

    void init() {
        imageView.setVisible(false);
        this.setCenter(imageView);
        this.getStyleClass().add("card");
        this.setCursor(Cursor.HAND);
    }

    void show() {
        imageView.setVisible(true);
        this.setCursor(Cursor.DEFAULT);
        showing = true;
        this.getStyleClass().clear();
    }

    void hide() {
        imageView.setVisible(false);
        this.setCursor(Cursor.HAND);
        showing = false;
    }

    void win() {
        imageView.setVisible(true);
        this.setCursor(Cursor.DEFAULT);
        showing = true;
        this.getStyleClass().clear();
    }

    public void paintHeart() {
        imageView.setImage(new Image("/view/images/heart.png"));
        this.setCenter(imageView);
        symbol = HEART;
    }

    public void paintDollar() {
        imageView.setImage(new Image("/view/images/dollar.png"));
        this.setCenter(imageView);
        symbol = DOLLAR;
    }

    public void paintSmile() {
        imageView.setImage(new Image("/view/images/smile.png"));
        this.setCenter(imageView);
        symbol = SMILE;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isEmpty() {
        return symbol.equals(EMPTY);
    }

    public boolean isWinned() {
        return winned;
    }

    public void setWinned(boolean winned) {
        this.winned = winned;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.symbol);
        return hash;
    }



  
        
}
