/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import com.google.auto.value.AutoBuilder;
import github.benslabbert.vertxjsonwriter.annotation.JsonWriter;
import io.vertx.core.json.JsonObject;

@JsonWriter
public record ResponseDto(String data) {

  public static ResponseDto fromJson(JsonObject jsonObject) {
    return ResponseDto_JsonWriter.fromJson(jsonObject);
  }

  public JsonObject toJson() {
    return ResponseDto_JsonWriter.toJson(this);
  }

  public static ResponseDto.Builder builder() {
    return new AutoBuilder_ResponseDto_Builder();
  }

  @AutoBuilder
  public interface Builder {
    Builder data(String data);

    ResponseDto build();
  }
}
