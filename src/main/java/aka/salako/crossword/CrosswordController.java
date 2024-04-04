package aka.salako.crossword;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static aka.salako.crossword.MenuController.choix;

public class CrosswordController  implements Initializable{


    private final List<CrosswordSquare> allSquares = new ArrayList<>(); // List to store all CrosswordSquare objects

    @FXML
    private HBox board_container;

    @FXML
    private ListView<String> list_HorInd;

    @FXML
    private ListView<String> list_VertInd;

    static GridPane boardGrid;

    Crossword  crossword = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        Database database = new Database();
        try {
            crossword = Crossword.createPuzzle(database, choix + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boardGrid = new GridPane();
        boardGrid.setPrefHeight(34* crossword.height);


        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                boardGrid.add(new CrosswordSquare(crossword.getCell(i+1, j+1).getSolution(),crossword.getCell(i+1, j+1).getProposition(),"","",crossword.getCell(i+1, j+1).isBlackSquare()), j, i);
            }
        }

        // Récupérer les indices horizontaux depuis la base de données et les ajouter à la liste view
        try {
            List<Clue> horizontalIndices = database.chargerIndicesHorizontaux(choix);
            for (Clue indice : horizontalIndices) {
                list_HorInd.getItems().add(indice.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Récupérer les indices verticaux depuis la base de données et les ajouter à la liste view
        try {
            List<Clue> verticalIndices = database.chargerIndicesVerticaux(choix);
            for (Clue indice : verticalIndices) {
                list_VertInd.getItems().add(indice.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boardGrid.setPadding(new Insets(10,10,10,10));
        boardGrid.setGridLinesVisible(true);
        board_container.setAlignment(Pos.CENTER);
        board_container.getChildren().add(boardGrid);
    }

}
