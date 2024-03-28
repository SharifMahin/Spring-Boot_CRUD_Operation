package jp.co.sysystem.springWorkout.service;

import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.UpdateJooqRepository;
import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UpdateService {

  @Autowired
  private UpdateJooqRepository jrep;

  public List<UserInfo> serchById(@Null String id){
    return jrep.findInfo(id);
  }

  public void updateUser(UpdateForm form) {
    jrep.updateInfo(form);
  }

}
