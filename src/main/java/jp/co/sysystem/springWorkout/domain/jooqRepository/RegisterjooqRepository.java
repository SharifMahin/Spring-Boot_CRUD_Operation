package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.domain.table.UserDetail;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import lombok.NonNull;
@Component
public class RegisterjooqRepository {

  @Autowired
  private DSLContext dsl;

  public User findById(@NonNull String loginId) {
       User result = dsl.select()
        .from(USER)
        .where(
            USER.ID.eq(loginId))
        .fetchOneInto(User.class);
       return result;
  }

  public void insert(RegistrationForm form) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse(form.getBirth());
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Timestamp formateDate = new java.sql.Timestamp( parsedDate.getTime() );


    dsl.insertInto(USER, USER.ID, USER.PASS, USER.NAME, USER.KANA)
    .values(form.getId(),form.getPassword(), form.getName(), form.getKana())
    .execute();

    dsl.insertInto(USERDETAIL, USERDETAIL.ID, USERDETAIL.BIRTH, USERDETAIL.CLUB)
        .values(form.getId(), formateDate, form.getClub())
        .execute();

  }
}
