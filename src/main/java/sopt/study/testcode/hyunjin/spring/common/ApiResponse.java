package sopt.study.testcode.hyunjin.spring.common;

public record ApiResponse<T>(boolean status, int code, String message, T data) {
    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess, T data) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(ResponseError responseError) {
        return new ApiResponse<>(false, responseError.getCode(), responseError.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ResponseError responseError, String message) {
        return new ApiResponse<>(false, responseError.getCode(), message, null);
    }

}
