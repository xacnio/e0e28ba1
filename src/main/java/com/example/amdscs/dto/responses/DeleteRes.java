package com.example.amdscs.dto.responses;

public class DeleteRes {
    public boolean result;

    public DeleteRes(boolean result) {
        this.result = result;
    }

    public static DeleteRes of(boolean result) {
        return new DeleteRes(result);
    }
}
