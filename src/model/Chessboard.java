package model;


import java.util.*;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private final Set<ChessboardPoint> river = new HashSet<>();
    private final Set<ChessboardPoint> trap = new HashSet<>();
    private final Set<ChessboardPoint> dens = new HashSet<>();

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//9*7
        initSets();
        initGrid();
        initPieces();
    }

    private void initSets() {
        river.add(new ChessboardPoint(3, 1));
        river.add(new ChessboardPoint(3, 2));
        river.add(new ChessboardPoint(4, 1));
        river.add(new ChessboardPoint(4, 2));
        river.add(new ChessboardPoint(5, 1));
        river.add(new ChessboardPoint(5, 2));

        river.add(new ChessboardPoint(3, 4));
        river.add(new ChessboardPoint(3, 5));
        river.add(new ChessboardPoint(4, 4));
        river.add(new ChessboardPoint(4, 5));
        river.add(new ChessboardPoint(5, 4));
        river.add(new ChessboardPoint(5, 5));

        trap.add(new ChessboardPoint(0, 2));
        trap.add(new ChessboardPoint(0, 4));
        trap.add(new ChessboardPoint(1, 3));
        trap.add(new ChessboardPoint(7, 3));
        trap.add(new ChessboardPoint(8, 2));
        trap.add(new ChessboardPoint(8, 4));

        dens.add(new ChessboardPoint(0, 3));
        dens.add(new ChessboardPoint(8, 3));
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (river.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell();
                } else if (trap.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell();
                    if (i < 2) {
                        grid[i][j].setOwner(PlayerColor.RED);
                    } else if (i > 6) {
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                } else if (dens.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell();
                    if (i == 0) {
                        grid[i][j].setOwner(PlayerColor.RED);
                    }
                    if (i == 8) {
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                } else {
                    grid[i][j] = new Cell();
                }
            }
        }
    }

    public void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));

        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
    }


    private boolean isRiver(ChessboardPoint point) {
        return river.contains(point);
    }

    private boolean isTrap(ChessboardPoint point) {
        return trap.contains(point);
    }

    private boolean isDen(ChessboardPoint point) {
        return dens.contains(point);
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }// 计算两点之间的距离

    private ChessPiece removeChessPiece(ChessboardPoint point) { // 撤掉某格棋子
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }// 在某格放入某棋子

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) { //将某格(src)棋子放入dest格
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) { // 将dest格清空，并将src格棋子放入dest格
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        } else {
            removeChessPiece(dest);
            moveChessPiece(src, dest);
        }
        // TODO: Finish the method.(finish)
    }


    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        } else if (calculateDistance(src, dest) == 1) {
            if (isRiver(dest) && getChessPieceAt(src).getName().equals("Rat")) {
                return true;//鼠可以过河
            } else if (!isRiver(dest) && !isDen(dest)) {
                return true;//非河非陷阱非兽穴可以走
            }
//            else if (isTrap(dest)) {
//                getChessPieceAt(src).setRank(0);
//                return true;//陷阱可以走，但是排位变为0
//            }
            else if (isDen(dest) && getChessPieceAt(src).getOwner() != getGridAt(dest).getOwner()) {
                return true;//对方兽穴可以走
            } else {
                return false;
            }
            //狮子老虎跳河
        } else if (calculateDistance(src, dest) > 1
                && (getChessPieceAt(src).getName().equals("Lion")
                || getChessPieceAt(src).getName().equals("Tiger"))) {
            if (src.getRow() != dest.getRow() && src.getCol() != dest.getCol()) {
                return false;
            } // 检查横纵坐标是否都不等
            // 检查两个格子是否在同一行或者同一列
            else if (src.getRow() == dest.getRow()) {
                int min = Math.min(src.getCol(), dest.getCol());
                int max = Math.max(src.getCol(), dest.getCol());
                for (int i = min + 1; i < max; i++) {
                    if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                        return false;
                    }// 检查两个格子之间是否有棋子
                    if (!isRiver(new ChessboardPoint(src.getRow(), i))) {
                        return false;
                    }// 检查两个格子之间是否有河
                }
                if (isRiver(dest)) {
                    return false;
                }// 检查目标格是否有河
                return true;
            } else if (src.getCol() == dest.getCol()) {
                int min = Math.min(src.getRow(), dest.getRow());
                int max = Math.max(src.getRow(), dest.getRow());
                for (int i = min + 1; i < max; i++) {
                    if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                        return false;
                    }
                    if (!isRiver(new ChessboardPoint(i, src.getCol()))) {
                        return false;
                    }
                }
                if (isRiver(dest)) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }//目标格为空，且符合规则，返回true

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {

        if (getChessPieceAt(src) == null)
            return false;//如果源格为空，返回false
        //如果目标格为空，且返回isValidMove为true，则返回true
        if (getChessPieceAt(src) != null && getChessPieceAt(dest) == null)
            return isValidMove(src, dest);
        else if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
            return false;
        }//如果源格和目标格棋子颜色相同，返回false
        else if (calculateDistance(src, dest) == 1) {
            //河中老鼠不能被陆地动物捕获
            if (!isRiver(src) && isRiver(dest)) {
                return false;
            }
            //但是河中老鼠可以捕获河中老鼠
            else if (isRiver(dest) && isRiver(src)) {
                return true;
            }
            //河中鼠不能捕获陆地动物
            else if (isRiver(src) && !isRiver(dest)) {
                return false;
            }
            //如果目标格不在己方的trap中，则返回true
            else if (isTrap(dest) && getGridAt(dest).getOwner() != getChessPieceAt(dest).getOwner()) {
                return true;
            }
            //如果目标格是在对方的的trap中，则返回cancapture
            else if (isTrap(dest) && getGridAt(dest).getOwner() == getChessPieceAt(dest).getOwner()) {
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            }
            //如果目标格不是在己方的trap中，则返回cancapture
            else if (!isRiver(dest) && !isRiver(src) && !isTrap(dest) && !isTrap(src)) {
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            } else {
                return false;
            }
        }

        //狮子和老虎可以跳过河捕获
        else if (calculateDistance(src, dest) > 1
                && (getChessPieceAt(src).getName().equals("Lion")
                || getChessPieceAt(src).getName().equals("Tiger"))) {
            if (src.getRow() != dest.getRow() && src.getCol() != dest.getCol()) {
                return false;
            } // 检查横纵坐标是否都不等
            // 检查两个格子是否在同一行或者同一列
            else if (src.getRow() == dest.getRow()) {
                int min = Math.min(src.getCol(), dest.getCol());
                int max = Math.max(src.getCol(), dest.getCol());
                for (int i = min + 1; i < max; i++) {
                    if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                        return false;
                    }// 检查两个格子之间是否有棋子
                    if (!isRiver(new ChessboardPoint(src.getRow(), i))) {
                        return false;
                    }// 检查两个格子之间是否有河
                }
                if (isRiver(dest)) {
                    return false;
                }// 检查目标格是否有河
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            } else if (src.getCol() == dest.getCol()) {
                int min = Math.min(src.getRow(), dest.getRow());
                int max = Math.max(src.getRow(), dest.getRow());
                for (int i = min + 1; i < max; i++) {
                    if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                        return false;
                    }
                    if (!isRiver(new ChessboardPoint(i, src.getCol()))) {
                        return false;
                    }
                }
                if (isRiver(dest)) {
                    return false;
                }// 检查目标格是否有河
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkRedWin() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (getGridAt(new ChessboardPoint(i, j)).getPiece() == null) {
                    continue;
                } else if (getGridAt(new ChessboardPoint(i, j)).getPiece().getOwner() == PlayerColor.BLUE) {
                    count++;
                }//如果有一只蓝方棋子没有被吃掉，返回false
            }
        }
        if (count == 0) {
            return true;
        }
        if (getGridAt(new ChessboardPoint(8, 3)).getPiece() == null) {
            return false;
        }//
        else if (getGridAt(new ChessboardPoint(8, 3)).getPiece().getOwner() == PlayerColor.RED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBlueWin() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (getGridAt(new ChessboardPoint(i, j)).getPiece() == null) {
                    continue;
                } else if (getGridAt(new ChessboardPoint(i, j)).getPiece().getOwner() == PlayerColor.RED) {
                    count++;
                }//如果有一只红方棋子没有被吃掉，返回false
            }
        }
        if (count == 0) {
            return true;
        }
        if (getGridAt(new ChessboardPoint(0, 3)).getPiece() == null) {
            return false;
        }//
        else if (getGridAt(new ChessboardPoint(0, 3)).getPiece().getOwner() == PlayerColor.BLUE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWin() {
        if (checkRedWin() || checkBlueWin()) {
            return true;
        } else {
            return false;
        }
    }

    public Step recordStep(ChessboardPoint src, ChessboardPoint dest, PlayerColor playerColor, int turn) {
        ChessPiece topiece = getChessPieceAt(dest);
        ChessPiece frompiece = getChessPieceAt(src);
        Step step = new Step(src, topiece, dest, frompiece, playerColor, turn);
        return step;
    }//记录每一步的信息

    public void undoStep(Step step) {
        ChessboardPoint src = step.getFrom();
        ChessboardPoint dest = step.getTo();
        ChessPiece ftompiece = step.getFromChessPiece();
        ChessPiece topiece = step.getToChessPiece();
        if (topiece == null) {
            removeChessPiece(dest);
            setChessPiece(src, ftompiece);
        } else if (topiece != null) {
            removeChessPiece(dest);
            setChessPiece(src, ftompiece);
            setChessPiece(dest, topiece);
        }
        if (step.getPlayerColor() == PlayerColor.RED) {
            step.setTurnCount(step.getTurnCount());
        } else if (step.getPlayerColor() == PlayerColor.BLUE) {
            step.setTurnCount(step.getTurnCount() - 1);
        }
    }//撤销一步的信息

    public void runStep(Step step) {
        if (step.getFromChessPiece() != null) {
            removeChessPiece(step.getTo());
            if (step.getToChessPiece() == null) {
                setChessPiece(step.getTo(), step.getFromChessPiece());
            } else if (step.getToChessPiece() != null) {
                removeChessPiece(step.getTo());
                setChessPiece(step.getTo(), step.getFromChessPiece());
            }
        }
        if (step.getPlayerColor() == PlayerColor.RED) {
            step.setTurnCount(step.getTurnCount() + 1);
        } else if (step.getPlayerColor() == PlayerColor.BLUE) {
            step.setTurnCount(step.getTurnCount());
        }
    }//执行一步的信息

    public List<ChessboardPoint> selectValidMove(ChessboardPoint selectedPoint) {
        List<ChessboardPoint> valid = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (isValidMove(selectedPoint, point) || isValidCapture(selectedPoint, point)) {
                    valid.add(point);
                }
            }
        }
        return valid;
    }

    public List<Step> validStep(PlayerColor playerColor, int turn) {
        List<Step> validStep = new ArrayList<>();
        List<ChessboardPoint> validselect = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (getChessPieceAt(point) == null) {
                    continue;
                }
                if (getChessPieceAt(point).getOwner() == playerColor) {
                    validselect.add(point);
                }
            }
        }
        for (ChessboardPoint point : validselect) {
            List<ChessboardPoint> validmove = selectValidMove(point);
            for (ChessboardPoint point1 : validmove) {
                Step step = recordStep(point, point1, playerColor, turn);
                validStep.add(step);
            }
        }
        return validStep;
    }

    public Step getMostValueStep(PlayerColor playerColor, int turn) {
        List<Step> validStep = validStep(playerColor, turn);
        int max = 0;
        Step maxStep = null;
        //能吃掉对方棋子的步数价值高
        for (Step step : validStep) {
            if (getChessPieceAt(step.getTo()) != null) {
                if (getChessPieceAt(step.getTo()).getOwner() != playerColor && isValidCapture(step.getFrom(), step.getTo())) {
                    if (getChessPieceAt(step.getFrom()).getRank() > getChessPieceAt(step.getTo()).getRank() ) {
                        step.setValue(step.getValue()
                                + 200 / (getChessPieceAt(step.getFrom()).getRank() - getChessPieceAt(step.getTo()).getRank())
                                + 100 / calculateDistance(step.getTo(), new ChessboardPoint(0, 3)));
                    }//如果能吃掉对方棋子，且自己的棋子等级大于等于对方的棋子，价值加100/(自己的棋子等级-对方的棋子等级),范围在（100/6，100/1）
                    else if (getChessPieceAt(step.getFrom()).getRank() <= getChessPieceAt(step.getTo()).getRank()) {
                        step.setValue(step.getValue() + 200 + 100 / calculateDistance(step.getTo(), new ChessboardPoint(0, 3)));
                    }//如果能吃掉对方棋子，且自己的棋子等级小于对方的棋子，价值加100
                }
            }
            //能走到对方阵营的棋子价值高
            if (getChessPieceAt(step.getTo()) == null && isValidMove(step.getFrom(), step.getTo())) {
                //如果是红方，价值加20*（目标行数-起始行数+起始列数-3+目标列数-3）,范围在（0，20*4）
                if (playerColor == PlayerColor.RED) {
                    step.setValue(step.getValue() + 20 * (step.getTo().getRow() - step.getFrom().getRow() + Math.abs(3 - step.getFrom().getCol()) - Math.abs(3 - step.getTo().getCol())));
                    //如果是兽穴，价值加1000
                    if (step.getTo().getRow() == 7 && step.getTo().getCol() == 3) {
                        step.setValue(step.getValue() + 1000);
                    }
                }//如果是蓝方，价值加20*（起始行数-目标行数+起始列数-3+目标列数-3）,范围在（0，20*4）
                else if (playerColor == PlayerColor.BLUE) {
                    step.setValue(step.getValue() + 20 * (step.getFrom().getRow() - step.getTo().getRow() + Math.abs(3 - step.getFrom().getCol()) - Math.abs(3 - step.getTo().getCol())));
                    //如果是兽穴，价值加1000
                    if (step.getTo().getRow() == 0 && step.getTo().getCol() == 3) {
                        step.setValue(step.getValue() + 1000);
                    }
                }
                //检验下下步是否会被吃掉，如果会被吃掉，价值减100
                if (getChessPieceAt(step.getTo()) == null) {
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.UP).getRow() >= 0
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)).getOwner() != playerColor
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)).canCapture(getChessPieceAt(step.getFrom()))) {
                        step.setValue(step.getValue() - 200);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.UP).getRow() >= 0
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)).getOwner() != playerColor
                            && getChessPieceAt(step.getFrom()).canCapture(getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.UP)))) {
                        step.setValue(step.getValue() + 100);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN).getRow() <= 8
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)).getOwner() != playerColor
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)).canCapture(getChessPieceAt(step.getFrom()))) {
                        step.setValue(step.getValue() - 200);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN).getRow() <= 8
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)).getOwner() != playerColor
                            && getChessPieceAt(step.getFrom()).canCapture(getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.DOWN)))) {
                        step.setValue(step.getValue() + 100);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT).getCol() >= 0
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)).getOwner() != playerColor
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)).canCapture(getChessPieceAt(step.getFrom()))) {
                        step.setValue(step.getValue() - 200);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT).getCol() >= 0
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)).getOwner() != playerColor
                            && getChessPieceAt(step.getFrom()).canCapture(getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.LEFT)))) {
                        step.setValue(step.getValue() + 100);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT).getCol() <= 6
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)).getOwner() != playerColor
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)).canCapture(getChessPieceAt(step.getFrom()))) {
                        step.setValue(step.getValue() - 200);
                    }
                    if (step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT).getCol() <= 6
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)) != null
                            && getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)).getOwner() != playerColor
                            && getChessPieceAt(step.getFrom()).canCapture(getChessPieceAt(step.getTo().getNeighbor(ChessboardPoint.Direction.RIGHT)))) {
                        step.setValue(step.getValue() + 100);
                    }
                }
            }
            //能跳河击杀对方棋子价值高
            if (calculateDistance(step.getFrom(), step.getTo()) > 1 && isValidCapture(step.getFrom(), step.getTo())) {
                step.setValue(step.getValue() + 40);
            }
            //能进河的价值高
            if (isRiver(step.getTo()) && isValidMove(step.getFrom(), step.getTo())) {
                step.setValue(step.getValue() + 20);
            }
            //鼠防象价值高
            if (getChessPieceAt(step.getFrom()).getRank() == 1&&!isRiver(step.getTo())) {
                ChessboardPoint Xiang = null;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        ChessPiece Weizhi = getChessPieceAt(new ChessboardPoint(i, j));
                        if (Weizhi == null) {
                            continue;
                        }
                        if (Weizhi.getRank() == 8 && Weizhi.getOwner() != playerColor) {
                            Xiang = new ChessboardPoint(i, j);
                        }
                    }
                }
                if (Xiang != null && calculateDistance(step.getTo(), Xiang) < calculateDistance(step.getFrom(), Xiang)) {
                    if (calculateDistance(step.getTo(), Xiang) != 0) {
                        step.setValue(step.getValue() + 190 / calculateDistance(step.getTo(), Xiang));
                    }
                    if (calculateDistance(step.getTo(), Xiang) == 0) {
                        step.setValue(step.getValue() + 210);
                    }
                }

            }
            //象离鼠越远价值高
            if (getChessPieceAt(step.getFrom()).getRank() == 8){
                ChessboardPoint Shu = null;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        ChessPiece Weizhi = getChessPieceAt(new ChessboardPoint(i, j));
                        if (Weizhi == null) {
                            continue;
                        }
                        if (Weizhi.getRank() == 1 && Weizhi.getOwner() != playerColor) {
                            Shu = new ChessboardPoint(i, j);
                        }
                    }
                }
                if (Shu != null && calculateDistance(step.getTo(), Shu) > calculateDistance(step.getFrom(), Shu)) {
                    step.setValue(step.getValue() + 12*calculateDistance(step.getTo(), Shu)-5*calculateDistance(step.getTo(),new ChessboardPoint(0,3)));
                }

            }

        }
        //价值最高的步数，对Value进行排序，用sort方法，返回最后一个
        Collections.sort(validStep, new Comparator<Step>() {
            @Override
            public int compare(Step o1, Step o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        return validStep.get(validStep.size() - 1);
    }
}


