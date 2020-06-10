/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Pardo
 */
public class PlayController implements EventHandler {

    @FXML
    private GridPane container;

    @FXML
    private Label moves;

    @FXML
    private final Card[][] cards = new Card[4][5];

    int moves_left = 12;

    /**
     * Initialize method: fill the grid with empty cards & add random symbols to
     * random cards.
     */
    @FXML
    private void initialize() {
        moves.setText(moves_left + "");
        //Fill with empty cards
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Card card = new Card();
                card.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
                card.init();
                cards[i][j] = card;
                container.add(card, i, j);
            }
        }

        //Put symbols on some cards
        for (int i = 1; i <= 3; i++) {
            getValidLocation().paintHeart();
        }

        for (int i = 1; i <= 3; i++) {
            getValidLocation().paintDollar();
        }

        for (int i = 1; i <= 3; i++) {
            getValidLocation().paintSmile();
        }

        //Take a peak to all the cards for 1 second.
        Runnable ShowCards = () -> {
            showAllCardsNSeconds(1);
        };
        Thread thread = new Thread(ShowCards);
        thread.start();
    }

    /**
     * Return a valid empty random location for a symbol.
     *
     * @return
     */
    private Card getValidLocation() {
        while (true) {
            int column = getRandomNumberInRange(cards.length - 1);
            int row = getRandomNumberInRange(cards[0].length - 1);
            if (cards[column][row].isEmpty()) {
                return cards[column][row];
            }
        }
    }

    /**
     * Gets a random number between 0 and max
     *
     * @param max
     * @return
     */
    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt((max) + 1);
    }

    /**
     * Event for when a card is clicked.
     *
     * @param event
     */
    @Override
    public void handle(Event event) {
        Card card = (Card) event.getSource();
        hideEmptyCards();
        ArrayList<Card> activeCards = getActiveCards();
        if (activeCards.isEmpty()) {
            card.show();
        } else if (activeCards.get(0).getSymbol().equals(card.getSymbol())) {
            if (activeCards.size() == 2) {
                card.show();
                card.setWinned(true);
                for (Card active : activeCards) {
                    active.setWinned(true);
                }
            } else {
                card.show();
            }
        } else {
            showCardnSeconds(card);
            for (Card active : activeCards) {
                active.hide();
            }
        }
        updateMoves();
        if (moves_left == 0) {
            showError("LOSER", "No moves left you lose!");
            returnToPrincipal(card);
        } else if (gameWinned()) {
            showInformation("WINNER", "All hidden cards are showing you win!");
            returnToPrincipal(card);
        }
    }

    /**
     * Subtract 1 to the moves left.
     */
    private void updateMoves() {
        moves_left--;
        moves.setText(moves_left + "");
    }

    /**
     * Change scene to the principal.
     * @param card 
     */
    private void returnToPrincipal(Card card) {
        try {
            Node node = ((Node) card);
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/view/Principal.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return true/false if all the cards are uncovered.
     * @return 
     */
    private boolean gameWinned() {
        for (Card[] row : cards) {
            for (Card card : row) {
                if (!card.isEmpty()) {
                    if (!card.isShowing()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Show a error message (used for when you lose the game)
     * @param title
     * @param message 
     */
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Show an information message (used for when you win the game).
     * @param title
     * @param message 
     */
    public void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Hide all the empty cards that are showing (Used for hiding empty cards that are temporary showing).
     */
    private void hideEmptyCards() {
        for (Card[] row : cards) {
            for (Card card : row) {
                if (card.isEmpty() && card.isShowing()) {
                    card.hide();
                }
            }
        }
    }

    /**
     * Get all active cards from the game (Showing not empty and not winned).
     *
     * @return
     */
    private ArrayList<Card> getActiveCards() {
        ArrayList<Card> activeCards = new ArrayList<>();
        for (Card[] row : cards) {
            for (Card card : row) {
                if (!card.isEmpty() && !card.isWinned() && card.isShowing()) {
                    activeCards.add(card);
                }
            }
        }
        return activeCards;
    }

    /**
     * Show a card for 1 second.
     *
     * @param card
     */
    void showCardnSeconds(Card card) {
        Runnable ShowCards = () -> {
            card.show();
            long duration = 1 * 3600;
            long start = System.currentTimeMillis();
            long end = start + duration;
            while (System.currentTimeMillis() < end) {
            }
            card.hide();
        };
        Thread thread = new Thread(ShowCards);
        thread.start();
    }

    /**
     * Show all cards for n seconds.
     *
     * @param n
     */
    private void showAllCardsNSeconds(int n) {
        showAllCards();
        long duration = n * 3600;
        long start = System.currentTimeMillis();
        long end = start + duration;
        while (System.currentTimeMillis() < end) {
        }
        hideAllCards();
    }

    /**
     * Show the content of all cards.
     */
    public void showAllCards() {
        for (Card[] row : cards) {
            for (Card card : row) {
                card.show();
            }
        }
    }

    /**
     * Hide the content of all cards.
     */
    public void hideAllCards() {
        for (Card[] row : cards) {
            for (Card card : row) {
                card.hide();
            }
        }
    }
}
