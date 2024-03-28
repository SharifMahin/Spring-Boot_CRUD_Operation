package jp.co.sysystem.springWorkout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.DeleteJooqRepository;
import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.web.form.DeleteForm;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DeleteService {

  @Autowired
  public DeleteJooqRepository jrep;

  public List<UserInfo> deleteById(String id) {
    return jrep.findDeleteInfo(id);
  }

  public void deleteUserInfo(DeleteForm form) {
    jrep.deletById(form);

  }

}
