package aka.salako.crossword;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class CrosswordSquare extends Label {
    private char solution;
    private char proposition;
    private String horizontalDefinition;
    private String verticalDefinition;
    private boolean isBlackSquare;

    public CrosswordSquare(char solution, char proposition, String horizontalDefinition, String verticalDefinition, boolean isBlackSquare) {
        this.solution = solution;
        this.proposition = proposition;
        this.horizontalDefinition = horizontalDefinition;
        this.verticalDefinition = verticalDefinition;
        this.isBlackSquare = isBlackSquare;

        initializeSquare();
    }

    private void initializeSquare() {
        // Chargement de ma feuille de Style CSS externe
        String cssFile = Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm();
        this.getStylesheets().add(cssFile);

        this.getStyleClass().add("crossword-square");
        if (isBlackSquare) {
            this.getStyleClass().add("black");
        }

        this.setCursor(Cursor.HAND);
        this.setPrefWidth(99.9);
        this.setPrefHeight(99.9);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("System", FontWeight.BOLD, 30));
        this.setPadding(new Insets(0, 0, 0, 0));

        // Ajouter un écouteur d'événements pour obtenir le focus lorsque la case est cliquée
        setOnMouseClicked(event -> {
            if (!this.isBlackSquare){
                requestFocus();
            }
        });

        // Ajouter un écouteur d'événements pour les touches du clavier
        setOnKeyPressed(this::handleKeyPressed);
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case BACK_SPACE:
                setText("");
                break;
            default:
                if (event.getCode().isLetterKey()) {
                    char typedLetter = event.getText().charAt(0); // Obtenir le caractère tapé
                    if (!event.getText().isEmpty() && event.getText().length() == 1) {
                        setText(String.valueOf(typedLetter).toUpperCase());
                        this.setProposition(typedLetter);
                    }
                }
                break;
        }
    }

    public char getSolution() {
        return solution;
    }

    public void setSolution(char solution) {
        this.solution = solution;
    }

    public char getProposition() {
        return proposition;
    }

    public void setProposition(char proposition) {
        this.proposition = proposition;
    }

    public String getHorizontalDefinition() {
        return horizontalDefinition;
    }

    public void setHorizontalDefinition(String horizontalDefinition) {
        this.horizontalDefinition = horizontalDefinition;
    }

    public String getVerticalDefinition() {
        return verticalDefinition;
    }

    public void setVerticalDefinition(String verticalDefinition) {
        this.verticalDefinition = verticalDefinition;
    }

    public boolean isBlackSquare() {
        return this.getSolution()==' ';
    }
}
