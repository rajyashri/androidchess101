package com.CS213.controller;

import java.util.LinkedList;

import android.util.Log;

import com.CS213.model.Bishop;
import com.CS213.model.ChessPiece;
import com.CS213.model.King;
import com.CS213.model.Knight;
import com.CS213.model.Move;
import com.CS213.model.Move.MoveType;
import com.CS213.model.Pawn;
import com.CS213.model.Player;
import com.CS213.model.PlayerColor;
import com.CS213.model.Queen;
import com.CS213.model.Rook;
import com.CS213.model.Square;

public class Game {

	private Square[][] board;
	private ChessPiece[] capturedWhite;
	private ChessPiece[] capturedBlack;
	private int capturedWhiteCount;
	private int capturedBlackCount;
	private Player black;
	private Player white;
	private Player turn;
	private boolean blackInCheck;
	private boolean whiteInCheck;
	private boolean blackWins;
	private boolean whiteWins;
	private LinkedList<Move> moves;

	public Game() {

		board = new Square[8][8];
		black = new Player(PlayerColor.BLACK);
		white = new Player(PlayerColor.WHITE);
		black.setOpponent(white);
		white.setOpponent(black);
		blackInCheck = false;
		whiteInCheck = false;
		capturedWhite = new ChessPiece[16];
		capturedBlack = new ChessPiece[16];
		capturedWhiteCount = 0;
		capturedBlackCount = 0;
		turn = white;
		moves = new LinkedList<Move>();

		for (int i = 0; i < 8 ; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square(i, j);
			}
		}

		placePieces(black);
		placePieces(white);


	}

	private void placePieces(Player player) {

		int row = player.getColor() == PlayerColor.WHITE ? 7: 0;

		ChessPiece[] pieces = new ChessPiece[]
				{ new Rook(), new Knight(), new Bishop(), new Queen(), new King(), new Bishop(), new Knight(), new Rook()};


		player.setKing((King)pieces[4]);

		for (int i = 0; i < 8; i++) {
			pieces[i].setLocation(board[row][i]);
			pieces[i].setBoard(board);
			board[row][i].setPiece(pieces[i]);
			board[row][i].getPiece().setPlayer(player);
			player.addPiece(pieces[i]);
			player.addUncapturedPiece(pieces[i]);

		}

		row = player.getColor() == PlayerColor.WHITE ? 6 : 1;

		for (int i = 0; i < 8; i++) {
			ChessPiece pawn = new Pawn();
			pawn.setLocation(board[row][i]);
			pawn.setBoard(board);
			board[row][i].setPiece(pawn);
			board[row][i].getPiece().setPlayer(player);
			player.addPiece(pawn);
			player.addUncapturedPiece(pawn);
		}

	}

	public Square[][] getBoard() { return board; }

	public ChessPiece[] getCapturedWhite() { return capturedWhite; }

	public ChessPiece[] getCapturedBlack() { return capturedBlack; }

	public Player getCurrentPlayer() { return turn; } 

	public boolean move(int s, int d) {

		blackInCheck = false;
		whiteInCheck = false;
		int c = s%8;
		int r = s/8;


		Square source = board[r][c];

		c = d%8;
		r = d/8;


		Square dest = board[r][c];

		ChessPiece sourcePiece = source.getPiece();

		ChessPiece destPiece = dest.getPiece();

		ChessPiece capture = null;

		MoveType moveType = MoveType.NORMAL;

		if (sourcePiece == null) return false;

		//attempting to move opponent's piece
		if (sourcePiece.getPlayer().getColor() != getCurrentPlayer().getColor()) return false;


		if (!sourcePiece.canMoveTo(dest)) return false;

		//capture
		if (destPiece != null) {

			destPiece.setLocation(null);
			if (turn == white) {

				capturedBlack[capturedBlackCount++] = destPiece;
			} 
			else {

				capturedWhite[capturedWhiteCount++] = destPiece;
			}

			destPiece.getPlayer().removePiece(destPiece);
			capture = destPiece;

		}

		Move move = new Move(sourcePiece, capture, source, dest);

		if (enPassant(sourcePiece, dest)) moveType = MoveType.ENPASSANT;

		if (castle(sourcePiece, dest)) {

			moveType = MoveType.CASTLE;
			if (sourcePiece.getPlayer().getColor() == PlayerColor.WHITE) {

				if (dest.getX() == 6) {


					ChessPiece rook = board[7][7].getPiece();
					rook.setLocation(board[7][5]);
					rook.incrementMoves();
					board[7][5].setPiece(rook);
					board[7][7].setPiece(null);
				}
				else if (dest.getX() == 2) {

					ChessPiece rook = getBoard()[7][0].getPiece();
					rook.setLocation(getBoard()[7][3]);
					rook.incrementMoves();
					board[7][3].setPiece(rook);
					board[7][0].setPiece(null);

				}
			}
			else {

				if (dest.getX() == 6) {

					ChessPiece rook = board[0][7].getPiece();
					rook.setLocation(board[0][5]);
					rook.incrementMoves();
					board[0][5].setPiece(rook);
					board[0][7].setPiece(null);

				}
				else if (dest.getX() == 2) {

					ChessPiece rook = board[0][0].getPiece();
					rook.setLocation(board[0][3]);
					rook.incrementMoves();
					board[0][3].setPiece(rook);
					board[0][0].setPiece(null);
				}
			}
		}
		
		source.setPiece(null);
		sourcePiece.setLocation(dest);
		dest.setPiece(sourcePiece);
		sourcePiece.incrementMoves();


		//promotion
		if (((turn == white && dest.getY() == 0) || (turn == black && dest.getY() == 7)) && sourcePiece instanceof Pawn) {

			ChessPiece promotedPiece = promotion("Q");
			promotedPiece.setLocation(dest);
			promotedPiece.setNumberOfMoves(sourcePiece.numberOfMoves());
			promotedPiece.setPlayer(turn);
			promotedPiece.setBoard(board);
			sourcePiece = promotedPiece;
			dest.setPiece(sourcePiece);
		}

		Square oppKing = turn.getOpponent().getKing().getLocation();

		if (turn.getOpponent().getKing().inCheck(oppKing)) {

			if (turn.getColor() == PlayerColor.WHITE) {
				blackInCheck = true;
				if (black.getKing().checkmate(black.getKing().getLocation())) {
					whiteWins = true;
				}
			} else {
				whiteInCheck = true;
				if (white.getKing().checkmate(white.getKing().getLocation())) {
					blackWins = true;
				}
			}
		}

		move.setType(moveType);
		moves.add(move);
		turn = (turn == white) ? black : white;

		return true;
	}

	private boolean enPassant(ChessPiece sourcePiece, Square dest) {

		if (sourcePiece instanceof Pawn && dest.getPiece() == null) {

			int yPos = sourcePiece.getPlayer().getColor() == PlayerColor.WHITE ? 1 : -1;


			if (Math.abs(sourcePiece.getLocation().getX() - dest.getX()) == 1 && sourcePiece.getLocation().getY() == dest.getY() + yPos) {

				ChessPiece capturedPawn = board[sourcePiece.getLocation().getY()][dest.getX()].getPiece();

				if (turn == white) {
					capturedBlack[capturedBlackCount++] = capturedPawn;
					capturedPawn.getPlayer().removePiece(capturedPawn);
				}
				else {
					capturedWhite[capturedWhiteCount++] = capturedPawn;
					capturedPawn.getPlayer().removePiece(capturedPawn);
				}

				board[sourcePiece.getLocation().getY()][dest.getX()].setPiece(null);

				return true;
			}
		}

		return false;
	}


	private boolean castle(ChessPiece sourcePiece, Square dest) {

		int xPos = Math.abs( dest.getX() - sourcePiece.getLocation().getX());

		if (sourcePiece instanceof King && xPos != -9) return true;

		return false;
	}

	public int fileToIndex(char file) {

		switch (file) {

		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		default:
			return -1;
		}

	}

	public ChessPiece promotion(String p) {

		if (p == null) p = "Q";

		switch(p.charAt(0)) {

		case 'Q':
			return new Queen();
		case 'B':
			return new Bishop();
		case 'N':
			return new Knight();
		case 'R':
			return new Rook();
		default:
			return new Queen();
		}
	}

	public int blackCaptureCount() { return capturedBlackCount; }

	public int whiteCaptureCount() { return capturedWhiteCount; }

	public boolean blackInCheck() { return blackInCheck; }

	public boolean whiteInCheck() { return whiteInCheck; }

	public void nextTurn() {
		turn = turn == white ? black : white;
	}

	public boolean whiteWin() { return whiteWins; }

	public boolean blackWin() { return blackWins; }


}
