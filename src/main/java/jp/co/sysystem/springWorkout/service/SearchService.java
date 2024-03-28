package jp.co.sysystem.springWorkout.service;

import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.SearchJooqRepository;
import jp.co.sysystem.springWorkout.domain.repository.SearchRepository;
import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SearchService {

  @Autowired
  protected SearchRepository rep;
  
  @Autowired
  private SearchJooqRepository jrep;
  /**
   * ログイン処理
   * <pre>
   * ログイン画面から受け取った「ログインID」および「パスワード」を使用して、
   * DBで管理されたユーザー情報であるかを検証する
   * </pre>
   * @param loginId
   * <pre>
   * ログインID
   * </pre>
   * @param password
   * <pre>
   * ログインIDと紐づくパスワード
   * </pre>
   * @return ?
   */

  public List<UserInfo> serchByParams(@Null String id, @Null String name, @Null String kana){

    return jrep.findInfo(id, name, kana);

  }

}
