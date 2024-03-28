package jp.co.sysystem.springWorkout.web.form;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {

  //半角英数チェック用のアノテーション
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validate.hwAlphanumeric}")
  private String id;
  @Pattern(regexp = "^[一-龯ぁ-んァ-ヾー々]*$", message = "{validate.notFwAlphanumeric}")
  private String name;
  @Pattern(regexp = "^[ｧ-ﾝﾞﾟ]*$", message = "{validate.notHwAlphanumeric}")
  private String kana;

}
