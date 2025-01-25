/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import io.vertx.core.json.JsonObject;
import jakarta.validation.constraints.NotBlank;

public record RequestDto(@NotBlank @CheckString(strict = true) String data) {

  public static RequestDto fromJson(JsonObject jsonObject) {
    String data = jsonObject.getString("data");
    return new RequestDto(data);
  }
}
