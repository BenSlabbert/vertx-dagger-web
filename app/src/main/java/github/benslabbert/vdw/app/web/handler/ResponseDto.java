/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import io.vertx.core.json.JsonObject;

public record ResponseDto(String data) {

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.put("data", data);
    return json;
  }
}
