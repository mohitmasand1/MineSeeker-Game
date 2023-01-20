/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * BoardElement consists of a single element of the many on a board.
 * Either it is a mine or not. Status of the Element determines whether it is
 * searched and/or a mine or any combination of the two.
 */

package com.example.assignment3.Game;

public class BoardElement {
    private boolean isMine;
    private int value;
    private int status;

    BoardElement(int status, boolean isMine) {
        this.isMine = isMine;
        this.status = status;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
