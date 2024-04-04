module aka.salako.crossword {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens aka.salako.crossword to javafx.fxml;
    exports aka.salako.crossword;
}