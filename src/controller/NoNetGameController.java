package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 */
public class NoNetGameController implements GameListener {


    public Chessboard model;
    public ChessboardComponent view;
    public ChessGameFrame frame;
    public PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    public ChessboardPoint selectedPoint;
    private ArrayList<Step> steps = new ArrayList<Step>();
    public List<String> history = new ArrayList<String>();
    private int turnCount;
    private List<ChessboardPoint> selectValidMove;
    private AIPlayer aiPlayer;
    private int mode;

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public Chessboard getChessboard() {
        return model;
    }

    public int getTurn() {
        return turnCount;
    }

    public void tranSteps(Step step) {
        String str = step.toString();
        history.add(str);
    }

    public NoNetGameController(ChessboardComponent view, Chessboard model, ChessGameFrame frame, int mode) { // 构造方法
        this.view = view;
        this.model = model;
        this.frame = frame;
        this.currentPlayer = PlayerColor.BLUE;
        this.mode = mode;
        turnCount = 1;
        frame.setStatusLabel(currentPlayer);// set the status label
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        frame.repaint();// repaint the frame
        view.repaint();// repaint the view
        if (mode == 1) {
            aiPlayer = new AIPlayer(this);
//            frame.setUser2(new AIPlayer(this));
        }
    }

    public NoNetGameController getGameController() {
        return this;
    }

    public void setModel(Chessboard model) {
        this.model = model;
    }

    public Chessboard getModel() {
        return model;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public ChessGameFrame getFrame() {
        return frame;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setSelectedPoint(ChessboardPoint selectedPoint) {
        this.selectedPoint = selectedPoint;
    }


    public void restart() {
        model.initPieces();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        setCurrentPlayer(PlayerColor.BLUE);
        setSelectedPoint(null);
        frame.setTurn(1);
        frame.getTurnLabel().setText("Turn 1:");
        frame.setStatusLabel(currentPlayer);
        setTurnCount(1);
        history.clear();
        steps.clear();
        steps.trimToSize();
        writeFileByLib("steps.txt");
        frame.repaint();
        view.revalidate();
        view.repaint();
    }

    public void ini() {
        model.initPieces();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        setCurrentPlayer(PlayerColor.BLUE);
        frame.setStatusLabel(currentPlayer);
        setSelectedPoint(null);
        frame.setTurn(1);
        setTurnCount(1);
        frame.getTurnLabel().setText("Turn 1:");
        frame.setStatusLabel(currentPlayer);
        frame.repaint();
        view.revalidate();
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

//    private boolean win() {

    // TODO: Check the board if there is a winner
//    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            ChessPiece FromPiece = model.getGridAt(selectedPoint).getPiece();
            ChessPiece ToPiece = model.getGridAt(point).getPiece();
            Step step = new Step(new ChessboardPoint(selectedPoint.getRow(), selectedPoint.getCol()), FromPiece, new ChessboardPoint(point.getRow(), point.getCol()), ToPiece, currentPlayer, turnCount);
            steps.add(step);
            tranSteps(step);
            writeFileByLib("steps.txt");
            if (currentPlayer == PlayerColor.RED) {
                turnCount++;
            }
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));// set the chess component at the grid
            selectedPoint = null;// set the selected point to null
            hideValidMoves();
            swapColor();// swap the color
            frame.setStatusLabel(currentPlayer);
            frame.setTurnLabel(currentPlayer);
            view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
            if (getModel().checkWin()) {
                if (getModel().checkBlueWin()) {
                    JOptionPane.showMessageDialog(null, "Blue Win!");
                    frame.getUser1().setScore(frame.getUser1().getScore() + 1);////////////
                } else if (getModel().checkRedWin()) {
                    JOptionPane.showMessageDialog(null, "Red Win!");
                    frame.getUser2().setScore(frame.getUser2().getScore() + 1);////////////
                }
            } else {
                AIMove();
                if (getModel().checkWin()) {
                    if (getModel().checkBlueWin()) {
                        JOptionPane.showMessageDialog(null, "Blue Win!");
                        frame.getUser1().setScore(frame.getUser1().getScore() + 1);////////////
                    } else if (getModel().checkRedWin()) {
                        JOptionPane.showMessageDialog(null, "Red Win!");
                        frame.getUser2().setScore(frame.getUser2().getScore() + 1);////////////
                    }
                }
            }
        }

    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                showValidMoves();
                component.setSelected(true);
                component.repaint();
            }// if the selected point is null,且棋子的拥有者是当前玩家，那么就把选中的棋子设置为true，然后重绘。
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            hideValidMoves();
            component.setSelected(false);
            component.repaint();
        } // if the selected point is not null,且选中的棋子和当前棋子相同，那么就把选中的棋子设置为null，然后重绘。
        else if (model.isValidCapture(selectedPoint, point)) {
            ChessPiece FromPiece = model.getGridAt(selectedPoint).getPiece();
            ChessPiece ToPiece = model.getGridAt(point).getPiece();
            Step step = new Step(new ChessboardPoint(selectedPoint.getRow(), selectedPoint.getCol()), FromPiece, new ChessboardPoint(point.getRow(), point.getCol()), ToPiece, currentPlayer, turnCount);
            steps.add(step);
            tranSteps(step);
            writeFileByLib("steps.txt");
            if (currentPlayer == PlayerColor.RED) {
                turnCount++;
            }
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            component.setSelected(false);
            selectedPoint = null;
            hideValidMoves();
            swapColor();
            frame.setStatusLabel(currentPlayer);
            frame.setTurnLabel(currentPlayer);
            view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
            if (getModel().checkWin()) {
                if (getModel().checkBlueWin()) {
                    JOptionPane.showMessageDialog(null, "Blue Win!");

                } else if (getModel().checkRedWin()) {
                    JOptionPane.showMessageDialog(null, "Red Win!");
                }
            } else {
                AIMove();
                if (getModel().checkWin()) {
                    if (getModel().checkBlueWin()) {
                        JOptionPane.showMessageDialog(null, "Blue Win!");

                    } else if (getModel().checkRedWin()) {
                        JOptionPane.showMessageDialog(null, "Red Win!");
                    }
                }
            }
        }
    }

    public void writeFileByLib(String path) {
        try {
            Files.write(Path.of(path), this.history, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readFileByLib(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Step> tranhistory(List<String> history) { //把history转换成step
        ArrayList<Step> steps = new ArrayList<Step>();
        for (String str : history) {
            String[] strs = str.split(",");
            ChessboardPoint fromPoint = new ChessboardPoint(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
            ChessboardPoint toPoint = new ChessboardPoint(Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
            PlayerColor playerColor = strs[4].equals("BLUE") ? PlayerColor.BLUE : PlayerColor.RED;
            int turnCount = Integer.parseInt(strs[5]);
            Step step = new Step(fromPoint, toPoint, playerColor, turnCount);
            steps.add(step);
        }
        return steps;
    }

    public void redo() {
        ini();
        List<String> lines = readFileByLib("steps.txt");
        ArrayList<Step> steps = tranhistory(lines);
        for (Step step : steps) {
            if (step.getTurnCount() == turnCount) {
                ChessboardPoint fromPoint = step.getFrom();
                ChessboardPoint toPoint = step.getTo();
                ChessPiece fromPiece = model.getGridAt(fromPoint).getPiece();
                ChessPiece toPiece = model.getGridAt(toPoint).getPiece();
                currentPlayer = step.getPlayerColor();
                if (fromPiece != null && toPiece == null) {
                    selectedPoint = fromPoint;
                    showValidMoves();
                    paintByTime(500);
                    hideValidMoves();
                    model.moveChessPiece(fromPoint, toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    paintByTime(500);
                } else if (fromPiece != null && toPiece != null) {
                    selectedPoint = fromPoint;
                    showValidMoves();
                    paintByTime(500);
                    hideValidMoves();
                    model.captureChessPiece(fromPoint, toPoint);
                    view.removeChessComponentAtGrid(toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    paintByTime(500);
                }
            }
            selectedPoint = null;
        }
        swapColor();
        frame.setStatusLabel(currentPlayer);
        frame.repaint();
    }

    public void paintByTime(int k) {//not finished
        try {
            Thread.sleep(k);
            view.validate();
            view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void undo() {
        ini();
        if (!isAI()) {
            for (int i = 0; i < steps.size() - 1; i++) {
                Step step = steps.get(i);
                ChessboardPoint fromPoint = step.getFrom();
                ChessboardPoint toPoint = step.getTo();
                ChessPiece fromPiece = model.getGridAt(fromPoint).getPiece();
                ChessPiece toPiece = model.getGridAt(toPoint).getPiece();
                currentPlayer = step.getPlayerColor();
                if (fromPiece != null && toPiece == null) {
                    model.moveChessPiece(fromPoint, toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    view.repaint();
                } else if (fromPiece != null && toPiece != null) {
                    model.captureChessPiece(fromPoint, toPoint);
                    view.removeChessComponentAtGrid(toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    view.repaint();
                }
            }
            if (steps.size() > 0) {
                steps.remove(steps.size() - 1);
                if (steps.size() > 0) {
                    swapColor();
                }

                frame.setStatusLabel(currentPlayer);
                frame.repaint();
            }
        }
        if (isAI()) {
            for (int i = 0; steps.size() > 1 && i < steps.size() - 2; i++) {
                Step step = steps.get(i);
                ChessboardPoint fromPoint = step.getFrom();
                ChessboardPoint toPoint = step.getTo();
                ChessPiece fromPiece = model.getGridAt(fromPoint).getPiece();
                ChessPiece toPiece = model.getGridAt(toPoint).getPiece();
                currentPlayer = step.getPlayerColor();
                if (fromPiece != null && toPiece == null) {
                    model.moveChessPiece(fromPoint, toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    view.repaint();
                } else if (fromPiece != null && toPiece != null) {
                    model.captureChessPiece(fromPoint, toPoint);
                    view.removeChessComponentAtGrid(toPoint);
                    view.setChessComponentAtGrid(toPoint, view.removeChessComponentAtGrid(fromPoint));
                    if (currentPlayer == PlayerColor.RED) {
                        turnCount++;
                    }
                    frame.setStatusLabel(currentPlayer);
                    frame.setTurnLabel(currentPlayer);
                    frame.repaint();
                    view.repaint();
                }
            }
            if (steps.size() > 1) {
                steps.remove(steps.size() - 2);
                steps.remove(steps.size() - 1);
                if (steps.size() > 1) {
                    swapColor();
                }
                frame.setStatusLabel(currentPlayer);
                frame.repaint();
                view.repaint();
            }
        }
    }

    public void save() {//这里我改过了，你看看对不对，能不能改
        JFileChooser fileChooser = new JFileChooser();//文件选择器
        int result = fileChooser.showSaveDialog(view.getChessGameFrame());//显示保存对话框
        if (result == JFileChooser.APPROVE_OPTION) {//如果用户选择了保存
            File file = fileChooser.getSelectedFile();//获取选择的文件
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {//创建对象输出流
                outputStream.writeObject(history);//写入对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {//这里我改过了，你看看对不对，能不能改
        JFileChooser fileChooser = new JFileChooser();//文件选择器
        int result = fileChooser.showOpenDialog(view.getChessGameFrame());//显示打开对话框
        if (result == JFileChooser.APPROVE_OPTION) {//如果用户选择了打开
            File file = fileChooser.getSelectedFile();//获取选择的文件
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {//创建对象输入流
                history = (ArrayList<String>) inputStream.readObject();//读取对象
                writeFileByLib("steps.txt");
                steps = tranhistory(history);
                redo();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void showValidMoves() {
        selectValidMove = model.selectValidMove(selectedPoint);
        selectValidMove.add(selectedPoint);
        view.showValidMoves(selectValidMove);
        view.repaint();
    }

    public void hideValidMoves() {
        view.hideValidMoves(selectValidMove);
        view.repaint();
        selectValidMove.clear();
    }

    private boolean test = false;

    public boolean isAI() {
        if (mode == 1) {
            test = true;
        } else {
            test = false;
        }
        return test;
    }


    public void AIMove() {
        if (isAI() && currentPlayer == PlayerColor.RED) {
            Step aiStep = aiPlayer.generateMove();
            aiStep.setTurnCount(turnCount);
            if (aiStep != null) {
                view.setAIPlaying(true);
                selectedPoint = aiStep.getFrom();
                showValidMoves();
                paintByTime(400);
                hideValidMoves();
                selectedPoint = null;
                steps.add(aiStep);
                tranSteps(aiStep);
                writeFileByLib("steps.txt");
                if (currentPlayer == PlayerColor.RED) {
                    turnCount++;
                }
                if (model.isValidMove(aiStep.getFrom(), aiStep.getTo())) {
                    model.moveChessPiece(aiStep.getFrom(), aiStep.getTo());
                    view.setChessComponentAtGrid(aiStep.getTo(), view.removeChessComponentAtGrid(aiStep.getFrom()));
                } else if (model.isValidCapture(aiStep.getFrom(), aiStep.getTo())) {
                    model.captureChessPiece(aiStep.getFrom(), aiStep.getTo());
                    view.removeChessComponentAtGrid(aiStep.getTo());
                    view.setChessComponentAtGrid(aiStep.getTo(), view.removeChessComponentAtGrid(aiStep.getFrom()));
                }
                swapColor();
                frame.setStatusLabel(currentPlayer);
                frame.setTurnLabel(currentPlayer);
                view.setAIPlaying(false);
                paintByTime(400);
            }
        }
    }

}