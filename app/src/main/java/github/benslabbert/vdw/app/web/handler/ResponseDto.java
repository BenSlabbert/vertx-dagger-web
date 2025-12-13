/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import github.benslabbert.vdw.codegen.annotation.builder.GenerateBuilder;
import github.benslabbert.vdw.codegen.annotation.json.JsonWriter;

@JsonWriter
@GenerateBuilder
public record ResponseDto(String data) {}
