package com.CS213.model;

import java.io.Serializable;

public class Move implements Serializable{

	private static final long serialVersionUID = 1L;

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

	public Move(ChessPiece chessPiece, ChessPiece capture, Square source, Square destination, MoveType type) {
		this.chessPiece = chessPiece;
		this.capture = capture;
		this.source = source;
		this.destination = destination;
		this.type = type;
	}


}

