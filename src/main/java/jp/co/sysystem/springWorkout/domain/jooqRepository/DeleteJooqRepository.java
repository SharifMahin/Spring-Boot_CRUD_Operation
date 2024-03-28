package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.web.form.DeleteForm;

@Component
public class DeleteJooqRepository {

  @Autowired
  private DSLContext dsl;

  public List<UserInfo> findDeleteInfo(String id) {

    List<UserInfo> deleteUsers = dsl.select()
        .from(USER)
        .join(USERDETAIL)
        .on(USERDETAIL.ID.eq(USER.ID))
        .where(
            USER.ID.eq(id))
        .fetchInto(UserInfo.class);
    return deleteUsers;
  }

  public void deletById(DeleteForm form) {
    dsl.delete(USER)
    .where(USER.ID.eq(form.getId()))
    .execute();

    dsl.delete(USERDETAIL)
    .where(USERDETAIL.ID.eq(form.getId()))
    .execute();
  }
}
