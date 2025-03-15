package view;

import controller.NoNetGameController;
import model.Cell;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;
import view.chessComponent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>(); // 河流
    private final Set<ChessboardPoint> densCell = new HashSet<>(); // 兽穴
    private final Set<ChessboardPoint> trapsCell = new HashSet<>(); // 陷阱
    private boolean isAIPlaying = false;
    private NoNetGameController gameController;
    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }
    public ChessboardComponent getChessboardComponent() {
        return this;
    }

    public Set<ChessboardPoint> getTrapsCell() {
        return trapsCell;
    }
    private ChessGameFrame chessGameFrame;
    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }

    public ChessboardComponent(int chessSize,ChessGameFrame chessGameFrame) {//这里我改过了，加了一个参数，是为了能够在ChessGameFrame中调用ChessboardComponent的方法
        this.chessGameFrame = chessGameFrame;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * 根据棋盘的状态，初始化棋盘上的棋子
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    gridComponents[i][j].add(checkType(chessPiece));
                }
            }
        }
    }

    private ChessComponent checkType(ChessPiece chessPiece) {
        return switch (chessPiece.getName()) {
            case "Elephant" -> new ElephantChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Lion" -> new LionChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Tiger" -> new TigerChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Leopard" -> new LeopardChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Wolf" -> new WolfChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Dog" -> new DogChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Cat" -> new CatChess(chessPiece.getOwner(), CHESS_SIZE);
            case "Rat" -> new RatChess(chessPiece.getOwner(), CHESS_SIZE);
            default -> null;
        };
    }

    /**
     * 根据棋盘的状态，更新棋盘上的特殊棋格
     */
    public void initiateGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }

        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        densCell.add(new ChessboardPoint(0, 3));
        densCell.add(new ChessboardPoint(8, 3));

        trapsCell.add(new ChessboardPoint(0, 2));
        trapsCell.add(new ChessboardPoint(0, 4));
        trapsCell.add(new ChessboardPoint(1, 3));

        trapsCell.add(new ChessboardPoint(8, 2));
        trapsCell.add(new ChessboardPoint(8, 4));
        trapsCell.add(new ChessboardPoint(7, 3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(new Color(114, 217, 255), calculatePoint(i, j), CHESS_SIZE);
                } else if (trapsCell.contains(temp)) {
                    cell = new CellComponent(new Color(204, 125, 5), calculatePoint(i, j), CHESS_SIZE);
                } else if (densCell.contains(temp)) {
                    cell = new CellComponent(new Color(255, 196, 0), calculatePoint(i, j), CHESS_SIZE);
                } else {
                    cell = new CellComponent(new Color(115, 255, 24, 255), calculatePoint(i, j), CHESS_SIZE);
                }
                this.add(cell);
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(NoNetGameController gameController) {
        this.gameController = gameController;
    }

    public NoNetGameController getGameController() {
        return gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) { //选定某格并将其中的棋子移除，返回该棋子
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED && !isAIPlaying) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    public void showValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint point : validMoves) {
            getGridComponentAt(point).setBackground(new Color(247, 255, 0, 255));
        }
    }

    public void hideValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint point : validMoves) {
            if (riverCell.contains(point)) {
                getGridComponentAt(point).setBackground(new Color(114, 217, 255));
            } else if (trapsCell.contains(point)) {
                getGridComponentAt(point).setBackground(new Color(204, 125, 5));
            } else if (densCell.contains(point)) {
                getGridComponentAt(point).setBackground(new Color(255, 196, 0));
            } else {
                getGridComponentAt(point).setBackground(new Color(115, 255, 24, 255));
            }
        }
    }

    public boolean setAIPlaying(boolean AIPlaying) {
        return this.isAIPlaying = AIPlaying;
    }
}
