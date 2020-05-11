package base;


import java.util.List;

public class ResponseResult<T> {
    private String message;
    private Status status;
    private T data;

    public ResponseResult(String message, Status status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}

