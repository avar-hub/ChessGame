import java.util.Objects;

class ChessGame {
    Cell[][] board = new Cell[8][8];
}

class Cell {
    private Position position;
    private Piece piece;

    public Cell(Position position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
class Position {
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
enum Color {
    WHITE,
    BLACK
}
abstract class Piece {
    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean isValidMove(Position source, Position destination, Cell[][] board);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

class Pyada extends Piece {

    public Pyada(Color color) {
        super(color);
    }
    @Override
    public boolean isValidMove(Position source, Position destination, Cell[][] board) {
        int sourceCol = source.getCol();
        int sourceRow = source.getRow();
        int destCol = destination.getCol();
        int destRow = destination.getRow();
        Color color = getColor();

        int direction = (color == Color.WHITE ? 1 : -1);

        if(destCol == sourceCol && destRow == sourceRow + direction) {
            return Objects.isNull(board[destRow][destCol].getPiece());
        }

        // 2 moves at start
        if(destCol == sourceCol && destRow == sourceRow + 2 * direction && (color == Color.WHITE ? sourceRow == 1 : sourceRow == 6)) {
            return Objects.isNull(board[destRow][destCol].getPiece());
        }
        // Capture digonal
        if(Math.abs(sourceCol - destCol) == 1 && destRow == sourceRow + direction) {
            Piece destPiece = board[destRow][destCol].getPiece();
            if(Objects.nonNull(destPiece) && destPiece.getColor() != color)
                return true;
        }
        return false;
    }
}

class Hathi extends Piece {

    public Hathi(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Position source, Position destination, Cell[][] board) {
        int sourceRow = source.getRow();
        int sourceCol = source.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();
        Color color = getColor();

        if (sourceRow == destRow || sourceCol == destCol) {
            if (sourceRow == destRow) {
                int startCol = Math.min(sourceCol, destCol);
                int endCol = Math.max(sourceCol, destCol);
                for (int col = startCol + 1; col < endCol; col++) {
                    if (Objects.nonNull(board[sourceRow][col].getPiece())) {
                        return false;
                    }
                }
            } else {
                int startRow = Math.min(sourceRow, destRow);
                int endRow = Math.max(sourceRow, destRow);
                for (int row = startRow + 1; row < endRow; row++) {
                    if (Objects.nonNull(board[row][sourceCol].getPiece())) {
                        return false;
                    }
                }
            }

            Piece destinationPiece = board[destRow][destCol].getPiece();
            if (destinationPiece != null && destinationPiece.getColor() == color) {
                return false;
            }

            return true;
        }
        return false;
    }
}

class Ghoda extends Piece {

    public Ghoda(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Position source, Position destination, Cell[][] board) {
        int sourceRow = source.getRow();
        int sourceCol = source.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();
        Color color = getColor();

        int rowDiff = Math.abs(sourceRow - destRow);
        int colDiff = Math.abs(sourceCol - destCol);

        if ((rowDiff == 2 && colDiff == 1 ) || (rowDiff == 1 && colDiff == 2)) {
            Piece destPiece = board[destRow][destCol].getPiece();
            if(Objects.isNull(destPiece) || destPiece.getColor() != color)
                return true;
        }
            return false;
    }
}

class Raja extends Piece {

    public Raja(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Position source, Position destination, Cell[][] board) {
        int sourceRow = source.getRow();
        int sourceCol = source.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();
        Color color = getColor();

        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);
        Piece destPiece = board[destRow][destCol].getPiece();

        if (rowDiff <= 1 && colDiff <= 1) {
            if (rowDiff == 0 || colDiff == 0 || rowDiff == colDiff) {
                return destPiece == null || destPiece.getColor() != color;
            }
        }

        return  false;
    }
}
