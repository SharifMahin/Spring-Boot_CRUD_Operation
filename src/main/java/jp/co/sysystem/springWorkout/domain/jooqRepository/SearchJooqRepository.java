package jp.co.sysystem.springWorkout.domain.jooqRepository;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;
import java.util.List;

import javax.validation.constraints.Null;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;

      @Component
      public class SearchJooqRepository {
      @Autowired
      private DSLContext dsl;
      public List <UserInfo> findInfo(@Null String id, @Null String name, @Null String kana){
             List<UserInfo> users = dsl.select()
             .from(USER)
             .join(USERDETAIL)
             .on(USERDETAIL.ID.eq(USER.ID))
             .where(USER.ID.like("%" + id + "%")
                 .and(USER.NAME.like("%" + name + "%"))
                 .and(USER.KANA.like("%" + kana + "%")))
             .fetchInto(UserInfo.class);
           return users;
        }
      }
