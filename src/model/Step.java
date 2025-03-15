package model;

import java.io.Serializable;
import java.util.List;

public class Step implements Serializable {
    private ChessboardPoint from;
    private ChessboardPoint to;
    private PlayerColor playerColor;
    private int turnCount;
    private ChessPiece fromChessPiece;
    private ChessPiece toChessPiece;
    private int value;

    public Step(ChessboardPoint from,ChessPiece fromChessPiece ,ChessboardPoint to,ChessPiece toChessPiece, PlayerColor playerColor, int turnCount) {
        this.from = from;
        this.to = to;
        this.playerColor = playerColor;
        this.turnCount = turnCount;
        this.fromChessPiece = fromChessPiece;
        this.toChessPiece = toChessPiece;
    }
    public Step(ChessboardPoint from ,ChessboardPoint to, PlayerColor playerColor, int turnCount) {
        this.from = from;
        this.to = to;
        this.playerColor = playerColor;
        this.turnCount = turnCount;
    }

    public ChessboardPoint getFrom() {
        return from;
    }

    public ChessboardPoint getTo() {
        return to;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public int getTurnCount() {
        return turnCount;
    }
    public ChessPiece getFromChessPiece() {
        return fromChessPiece;
    }
    public ChessPiece getToChessPiece() {
        return toChessPiece;
    }
    public void setFrom(ChessboardPoint from) {
        this.from = from;
    }
    public void setTo(ChessboardPoint to) {
        this.to = to;
    }
    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }
    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }
    public void setFromChessPiece(ChessPiece fromChessPiece) {
        this.fromChessPiece = fromChessPiece;
    }
    public void setToChessPiece(ChessPiece toChessPiece) {
        this.toChessPiece = toChessPiece;
    }
    public String toString(){
        return from+","+to+","+playerColor+","+turnCount;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

}