package aka.salako.crossword;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Crossword extends Grid<CrosswordSquare> {

    private Crossword(int height, int width) {
        super(height, width);
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 1; row <= getHeight(); row++) {
            for (int col = 1; col <= getWidth(); col++) {
                CrosswordSquare square = new CrosswordSquare(' ', ' ', " ", " ", false);
                setCell(row, col, square);
                setBlackSquare(row, col, true);
            }
        }
    }




    public static Crossword createPuzzle(Database database, int puzzleNumber) throws SQLException {
        Crossword crossword = null;
        String query = "SELECT * FROM GRID WHERE numero_grille = " + puzzleNumber;
        Statement stmt = database.getConnexion().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            int hauteur = rs.getInt("hauteur");
            int largeur = rs.getInt("largeur");
            crossword = new Crossword(hauteur, largeur);

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM CROSSWORD WHERE numero_grille = " + puzzleNumber);
            while (rs2.next()){
                String definition = rs2.getString("definition");
                boolean horizontal = rs2.getBoolean("horizontal");
                int row = rs2.getInt("ligne");
                int col = rs2.getInt("colonne");
                String solution = rs2.getString("solution");
                crossword.setDefinition(row, col, horizontal, definition);

                for(int i = 0; i<solution.length(); i++) {
                    if(horizontal) {
                        crossword.setSolution(row, col+i, solution.toUpperCase().charAt(i));
                        System.out.println(solution.charAt(i) + "horizontal"+col);
                    } else {
                        crossword.setSolution(row+i, col, solution.toUpperCase().charAt(i));
                        System.out.println(solution.charAt(i) + "vertical");
                    }
                }
            }
        }
        return crossword;
    }

    public boolean isWithinGrid(int row, int column) {
        return correctCoords(row, column);
    }

    public boolean isBlackSquare(int row, int column) {
        if (isWithinGrid(row, column)) {
            return (Character) getCell(row, column).getSolution() == null;
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public void setBlackSquare(int row, int column, boolean black) {
        if (isWithinGrid(row, column)) {
            if(!black){
                getCell(row, column).setSolution(' ');
            }
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public char getSolution(int row, int column) {
        if (!isBlackSquare(row, column)) {
            return getCell(row, column).getSolution();
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public void setSolution(int row, int column, char solution) {
        if (!isBlackSquare(row, column)) {
            getCell(row, column).setSolution(solution);
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public char getProposition(int row, int column) {
        if (!isBlackSquare(row, column)) {
            Character cellValue = getCell(row, column).getProposition();
            if (cellValue != null) {
                return cellValue;
            } else {
                throw new IllegalArgumentException("Cell content is not a character");
            }
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public void setProposition(int row, int column, char prop) {
        if (!isBlackSquare(row, column)) {
            getCell(row, column).setProposition(prop);
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public String getDefinition(int row, int column, boolean horizontal) {
        if (!isBlackSquare(row, column)) {
            return horizontal ? getCell(row, column).getHorizontalDefinition() : getCell(row, column).getVerticalDefinition();
        } else {
            throw new IllegalArgumentException("Row and column must be within the grid dimensions");
        }
    }

    public void setDefinition(int row, int column, boolean horizontal, String definition) {
        if (!isBlackSquare(row, column)) {
            if (horizontal) {
                getCell(row, column).setHorizontalDefinition(definition);
            } else {
                getCell(row, column).setVerticalDefinition(definition);
            }
        } else {
            throw new IllegalArgumentException("Can't set black square definition");
        }
    }
}