package sopt.study.testcode.seongjae.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;

@Getter
public class ApiResponse<T> {

  private int code;
  private HttpStatus status;
  private String message;
  private T data;

  public ApiResponse(final HttpStatus status, final String message, final T data) {
    this.code = status.value();
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> of(HttpStatus status, String message, final T data) {
    return new ApiResponse<>(status, message, data);
  }

  public static <T> ApiResponse<T> of(HttpStatus status, final T data) {
    return ApiResponse.of(status, status.name(), data);
  }

  public static <T> ApiResponse<T> ok(final T data) {
    return ApiResponse.of(HttpStatus.OK, data);
  }
}
