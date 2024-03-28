package jp.co.sysystem.springWorkout.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteForm {
  //空白チェック用のアノテーション
  @NotBlank(message = "{validate.notblank}")
  //半角英数チェック用のアノテーション
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validate.hwAlphanumeric}")
  private String id;
  @NotBlank(message = "{validate.notblank}")
  @Pattern(regexp = "^[一-龯ぁ-んァ-ヾー々]*$", message = "{validate.notFwAlphanumeric}")
  private String name;
  @NotBlank(message = "{validate.notblank}")
  @Pattern(regexp = "^[ｧ-ﾝﾞﾟ]*$", message = "{validate.notHwAlphanumeric}")
  private String kana;
  @NotBlank(message = "{validate.notblank}")
  private String birth;
  @Pattern(regexp = "^[一-龯ぁ-んァ-ヾー々]*$", message = "{validate.notFwAlphanumeric}")
  private String club;
}
