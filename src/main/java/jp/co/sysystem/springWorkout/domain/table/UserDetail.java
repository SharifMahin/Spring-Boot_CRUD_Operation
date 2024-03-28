package jp.co.sysystem.springWorkout.domain.table;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String no;
    private String id;
    private Date birth;
    private String club;
  }
