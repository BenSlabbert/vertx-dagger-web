/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import com.google.auto.value.AutoBuilder;
import github.benslabbert.vertxjsonwriter.annotation.JsonWriter;
import io.vertx.core.json.JsonObject;
import jakarta.validation.constraints.NotBlank;

@JsonWriter
public record RequestDto(@NotBlank @CheckString(strict = true) String data) {

  public static RequestDto fromJson(JsonObject jsonObject) {
    return RequestDto_JsonWriter.fromJson(jsonObject);
  }

  public JsonObject toJson() {
    return RequestDto_JsonWriter.toJson(this);
  }

  public static Builder builder() {
    return new AutoBuilder_RequestDto_Builder();
  }

  @AutoBuilder
  public interface Builder {
    Builder data(String data);

    RequestDto build();
  }
}
