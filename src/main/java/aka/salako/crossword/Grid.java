package aka.salako.crossword;

import javafx.scene.layout.GridPane;

public class Grid<T> {
    protected int height;
    protected int width;
    protected T[][] array;

    public Grid(int height, int width) throws RuntimeException {
        if (height <  0 || width <  0) {
            throw new RuntimeException("Erreur de grille!");
        }
        this.height = height;
        this.width = width;
        this.array = (T[][]) new Object[this.height][this.width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean correctCoords(int row, int column) {
        return row >=  1 && column >=  1 && row <= height && column <= width;
    }

    public T getCell(int row, int column) throws RuntimeException {
        if (!correctCoords(row, column)) {
            throw new RuntimeException("Incorrect cords");
        }
        return this.array[row-1][column-1];
    }

    public void setCell(int row, int column, T value) {
        if (!correctCoords(row, column)) {
            throw new RuntimeException("Incorrect cords!");
        }
        this.array[row-1][column-1] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i =  0; i < height; i++) {
            for (int j =  0; j < width; j++) {
                sb.append(array[i][j]).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
