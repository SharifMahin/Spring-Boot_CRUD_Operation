package jp.co.sysystem.springWorkout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.LoginUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.jooqRepository.RegisterjooqRepository;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RegisterService {

  @Autowired
  private RegisterjooqRepository jrep;

  public Object checkDuplicateId(@NonNull String loginId) {
    // TODO:ログイン処理を実装する。引数、戻り値も適宜変更可

    User u = jrep.findById(loginId);
    return u;
  }

  public void createUser(RegistrationForm form) {
    jrep.insert(form);
    
  }

}
