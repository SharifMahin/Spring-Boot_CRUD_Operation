package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Null;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;

@Component
public class UpdateJooqRepository {

  @Autowired
  private DSLContext dsl;

  public List<UserInfo> findInfo(@Null String id) {
    List<UserInfo> users = dsl.select()
        .from(USER)
        .join(USERDETAIL)
        .on(USERDETAIL.ID.eq(USER.ID))
        .where(
            USER.ID.eq(id))
        .fetchInto(UserInfo.class);
    return users;
  }

  public void updateInfo(UpdateForm form) {
    dsl.update(USER)
    .set(USER.NAME, form.getName())
    .set(USER.KANA, form.getKana())
    .where(
        USER.ID.eq(form.getId()))
    .execute();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse(form.getBirth());
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Timestamp formateDate = new java.sql.Timestamp( parsedDate.getTime() );

    dsl.update(USERDETAIL)
    .set(USERDETAIL.BIRTH, formateDate)
    .set(USERDETAIL.CLUB, form.getClub())
    .where(
        USERDETAIL.ID.eq(form.getId()))
    .execute();
  }
}
