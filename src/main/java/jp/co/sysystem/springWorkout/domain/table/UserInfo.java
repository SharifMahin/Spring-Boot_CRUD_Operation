package jp.co.sysystem.springWorkout.domain.table;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class UserInfo implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private String pass;
  private String name;
  private String kana;
  private String no;
  private Date birth;
  private String club;
}
