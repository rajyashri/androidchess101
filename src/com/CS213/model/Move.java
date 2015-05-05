package com.CS213.model;

public class Move {

	public static enum MoveType {
		NORMAL, CASTLE, ENPASSANT
	}

	public ChessPiece chessPiece;
	public ChessPiece capture;
	public Square source;
	public Square destination;
	public MoveType type;

	public Move(ChessPiece chessPiece, ChessPiece capture, Square source, Square destination) {
		this.chessPiece = chessPiece;
		this.capture = capture;
		this.source = source;
		this.destination = destination;
	}

	public void setType(MoveType type) { this.type = type; }
	
}

