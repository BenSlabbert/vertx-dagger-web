/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import github.benslabbert.vdw.codegen.annotation.builder.GenerateBuilder;
import github.benslabbert.vdw.codegen.annotation.json.JsonWriter;
import jakarta.validation.constraints.NotBlank;

@JsonWriter
@GenerateBuilder
public record RequestDto(@NotBlank @CheckString(strict = true) String data) {}
