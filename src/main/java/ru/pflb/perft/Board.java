package ru.pflb.perft;

import java.util.ArrayList;
import java.util.List;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;
import static ru.pflb.perft.Piece.*;
import static ru.pflb.perft.Square.*;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
class Board {

    private static final int TOTAL_PIECE_TYPES = 12;
    private static final int MAX_PER_TYPE = 10;
    private final Piece[] mailbox120 = {
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 0-9
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 10-19
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 20-29
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 30-39
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 40-49
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 50-59
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 60-69
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 70-79
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 80-89
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 90-99
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 100-109
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT  // 110-119
    };

    /* Очередь хода */
    private Color sideToMove = WHITE;

    private final byte[][] piecePos = new byte[TOTAL_PIECE_TYPES][MAX_PER_TYPE];

    Board(String fen) {
        for (byte[] squares : piecePos) {
            for (int i = 0; i < squares.length; i++) {
                squares[i] = 0;
            }
        }

        String[] fenParts = fen.split("\\s");
        sideToMove = fenParts[1].startsWith("w") ? WHITE : BLACK;

        for (byte square = 98, fenIndex = 0; fenIndex < fenParts[0].length(); fenIndex++, square--) {
            char c = fenParts[0].charAt(fenIndex);
            switch (c) {
                case 'K':
                    mailbox120[square] = W_KING;
                    piecePos[W_KING.code][0] = (square);
                    break;
                case 'R':
                    mailbox120[square] = W_ROOK;
                    for (int i = 0; i < piecePos[W_ROOK.code].length; i++) {
                        if (!(piecePos[W_ROOK.code][i] >= H1.value && piecePos[W_ROOK.code][i] <= A8.value)) {
                            piecePos[W_ROOK.code][i] = (square);
                            break;
                        }
                    }
                    break;
                case 'B':
                    mailbox120[square] = W_BISHOP;
                    for (int i = 0; i < piecePos[W_BISHOP.code].length; i++) {
                        if (!(piecePos[W_BISHOP.code][i]>= H1.value && piecePos[W_BISHOP.code][i] <= A8.value)) {
                            piecePos[W_BISHOP.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'Q':
                    mailbox120[square] = W_QUEEN;
                    for (int i = 0; i < piecePos[W_QUEEN.code].length; i++) {
                        if (!(piecePos[W_QUEEN.code][i]>= H1.value && piecePos[W_QUEEN.code][i] <= A8.value)) {
                            piecePos[W_QUEEN.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'N':
                    mailbox120[square] = W_KNIGHT;
                    for (int i = 0; i < piecePos[W_KNIGHT.code].length; i++) {
                        if (!(piecePos[W_KNIGHT.code][i]>= H1.value && piecePos[W_KNIGHT.code][i] <= A8.value)) {
                            piecePos[W_KNIGHT.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'k':
                    mailbox120[square] = B_KING;
                    piecePos[B_KING.code][0] =  (square);
                    break;
                case 'r':
                    mailbox120[square] = B_ROOK;
                    for (int i = 0; i < piecePos[B_ROOK.code].length; i++) {
                        if (!(piecePos[B_ROOK.code][i]>= H1.value && piecePos[B_ROOK.code][i] <= A8.value)) {
                            piecePos[B_ROOK.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'b':
                    mailbox120[square] = B_BISHOP;
                    for (int i = 0; i < piecePos[B_BISHOP.code].length; i++) {
                        if (!(piecePos[B_BISHOP.code][i]>= H1.value && piecePos[B_BISHOP.code][i] <= A8.value)) {
                            piecePos[B_BISHOP.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'q':
                    mailbox120[square] = B_QUEEN;
                    for (int i = 0; i < piecePos[B_QUEEN.code].length; i++) {
                        if (!(piecePos[B_QUEEN.code][i]>= H1.value && piecePos[B_QUEEN.code][i] <= A8.value)) {
                            piecePos[B_QUEEN.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case 'n':
                    mailbox120[square] = B_KNIGHT;
                    for (int i = 0; i < piecePos[B_KNIGHT.code].length; i++) {
                        if (!(piecePos[B_KNIGHT.code][i]>= H1.value && piecePos[B_KNIGHT.code][i] <= A8.value)) {
                            piecePos[B_KNIGHT.code][i] =  (square);
                            break;
                        }
                    }
                    break;
                case '/':
                    square -= 1;
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                    square -= c - '1';
                    break;
                default:
                    throw new IllegalStateException("Недопустимый символ - " + c);
            }
        }
    }

    private static final byte PIECE_OFFSETS[][] = {
            // EMP
            {},
            // W KING
            {-11, -10, -9, -1, +11, +10, +9, +1},
            // W BISHOP
            {-11, -9, 9, 11},
            // W ROOK
            {-10, -1, 1, 10},
            // W QUEEN
            {-10, -1, 1, 10, -11, -9, 9, 11},
            // W KNIGHT
            {-8, -12, -19, -21, +8, +12, +19, +21},
            // W PAWN
            {},
            // B KING
            {-11, -10, -9, -1, +11, +10, +9, +1},
            // B BISHOP
            {-11, -9, 9, 11},
            // B ROOK
            {-10, -1, 1, 10},
            // B QUEEN
            {-10, -1, 1, 10, -11, -9, 9, 11},
            // B KNIGHT
            {-8, -12, -19, -21, +8, +12, +19, +21},
            // B PAWN
            {},
    };

    List<Move> genAllMoves() {
        List<Move> moves = new ArrayList<>();

        Piece[] whiteSliders = {W_ROOK, W_BISHOP, W_QUEEN},
                blackSliders = {B_ROOK, B_BISHOP, B_QUEEN};

        // итерация по всем дальнобойным фигурам
        for (Piece piece : sideToMove == WHITE ? whiteSliders : blackSliders) {

            byte[] squares = piecePos[piece.code];
            for (byte from : squares) {
                if (!(from>= H1.value && from <= A8.value)) {
                    break;
                }
                for (byte offset : PIECE_OFFSETS[piece.code]) {
                    byte to;
                    for (to = (byte) (from + offset); mailbox120[to] == EMP; to += offset) {
                        moves.add(new Move(new Square(from), new Square(to), piece));
                    }
                    // для вражеских фигур генерируем взятия
                    if (mailbox120[to] != OUT && mailbox120[to].getColor() != sideToMove) {
                        moves.add(new Move(new Square(from), new Square(to), piece, mailbox120[to]));
                    }
                }
            }
        }

        // отдельно - генерация ходов короля и коня
        Piece[] whiteKN = {W_KING, W_KNIGHT},
                blackKN = {B_KING, B_KNIGHT};
        for (Piece piece : sideToMove == WHITE ? whiteKN : blackKN) {

            byte[] squares = piecePos[piece.code];
            for (byte from : squares) {
                if (!(from >= H1.value && from <= A8.value)) {
                    break;
                }
                for (byte offset : PIECE_OFFSETS[piece.code]) {
                    byte to = (byte) (from + offset);
                    if (mailbox120[to] == EMP) {
                        moves.add(new Move(new Square(from), new Square(to), piece));
                    } else if (mailbox120[to] != OUT && mailbox120[to].getColor() != sideToMove) {
                        // для вражеских фигур генерируем взятия
                        moves.add(new Move(new Square(from), new Square(to), piece, mailbox120[to]));
                    }
                }
            }
        }

        return moves;
    }

    void makeMove(Move move) {

        // перемещаем фигуру на новое поле
        mailbox120[move.to.value] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (int i = 0; i < piecePos[move.piece.code].length; i++) {
            if (piecePos[move.piece.code][i] == move.from.value) {
                piecePos[move.piece.code][i] = move.to.value;
            }
        }

        // удаляем ходящую фигуру с предыдущего поля
        mailbox120[move.from.value] = EMP;

        if (move.isCapture()) {

            // обновляем массив быстрого доступа для взятой фигуры
            byte check = move.to.value;
            for (int i = 0; i < piecePos[move.capture.code].length; i++) {
                if (piecePos[move.capture.code][i] == check) {
                    piecePos[move.capture.code][i] = piecePos[move.capture.code][i+1];
                    if (check==0 && piecePos[move.capture.code][i+1]==0)
                        break;
                    if (i == piecePos[move.capture.code].length - 1) {
                        check = 0;
                    } else {
                        check = piecePos[move.capture.code][i + 1];
                    }
                }
            }
        }

            // передача очереди хода
        sideToMove = sideToMove == WHITE ? BLACK : WHITE;

    }

    void takeBack(Move move) {

        // возвращаем фигуру на старое поле
        mailbox120[move.from.value] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (int i = 0; i < piecePos[move.piece.code].length; i++) {
            if (piecePos[move.piece.code][i] == move.to.value) {
                piecePos[move.piece.code][i] = move.from.value;
            }
        }

        if (move.isCapture()) {

            // возвращение взятой фигуры
            mailbox120[move.to.value] = move.capture;

            // обновляем массив быстрого доступа для взятой фигуры
            for (int i = 0; i < piecePos[move.capture.code].length; i++) {
                if (!(piecePos[move.capture.code][i]>= H1.value && piecePos[move.capture.code][i] <= A8.value)) {
                    piecePos[move.capture.code][i] = move.to.value;
                    break;
                }
            }
        } else {
            // пустое поле на месте стоянки фигуры
            mailbox120[move.to.value] = EMP;
        }

        // возврат очереди хода
        sideToMove = sideToMove == WHITE ? BLACK : WHITE;
    }

    /**
     * Проверка того, что король стороны, только что сделавшей ход, не находится под шахом.
     */


    boolean isLegal() {

        if (piecePos[W_KING.code][0] != 0 && piecePos[B_KING.code][0] != 0) {
            //проверка на стоящих рядом королей
            int kingCheck = Math.abs(piecePos[W_KING.code][0] - piecePos[B_KING.code][0]);
            if (kingCheck == 11 || kingCheck == 10 || kingCheck == 9 || kingCheck == 1)
                return false;

        /*
        * перемещаем короля до тех пор пока не встретится конец доски или другая фигура.
        * если фигура наша или чужой король , прерываем движение
        * если фигура чужая и не король ход плохой
        * */
            Piece piece = sideToMove == WHITE ? B_KING : W_KING;

            for (byte offset : PIECE_OFFSETS[piece.code]) {
                byte from = piecePos[piece.code][0];
                byte to;
                do {
                    to = (byte) (from + offset);
                    if (mailbox120[to] == EMP)
                        from = to;
                    else if (mailbox120[to] == OUT)
                        break;
                        //наши фигуры
                    else if (mailbox120[to].getColor() != sideToMove)
                        break;

                        //не наши фигуры
                    else {
                        if ((9 == Math.abs(offset) || 11 == Math.abs(offset)) &&
                                (mailbox120[to].name == 'B' || mailbox120[to].name == 'b')) {
                            return false;
                        }

                        if ((1 == Math.abs(offset) || 10 == Math.abs(offset)) && (mailbox120[to].name == 'R' || mailbox120[to].name == 'r' ))
                            return false;

                        if (mailbox120[to].name == 'Q' || mailbox120[to].name == 'q')
                            return false;

                        break;

                    }
                } while (mailbox120[to] != OUT);
            }
        }
        else
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int s = A8.value; s >= H8.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A7.value; s >= H7.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A6.value; s >= H6.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A5.value; s >= H5.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A4.value; s >= H4.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A3.value; s >= H3.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A2.value; s >= H2.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A1.value; s >= H1.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }

        return sb.toString();
    }
}
