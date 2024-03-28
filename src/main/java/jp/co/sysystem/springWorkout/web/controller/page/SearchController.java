package jp.co.sysystem.springWorkout.web.controller.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.service.SearchService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class SearchController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public SearchService search;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String SEARCH_PROCESS_URL = "/search";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String SEARCH_PAGE = "page/search";


  /**
   * 検索画面表示
   * ログイン前でも表示可能
   */
  @RequestMapping(value = SEARCH_PROCESS_URL, method = RequestMethod.GET)
  public String showSearchPage(HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      model.addAttribute("searchForm", new SearchForm());
      return SEARCH_PAGE;
    }
  }

  /**
   * ログイン処理<br>
   * ログイン成功である場合、メニュー画面へリダイレクトする。
   * @param form
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = SEARCH_PROCESS_URL, method = RequestMethod.POST)
  public String processSearch(
      @Validated @ModelAttribute SearchForm form, BindingResult bindingResult,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      // BeanValidationの結果確認
      if (bindingResult.hasErrors()) {
        // TODO: 検索フォームからの入力値確認結果に誤りがあった場合の処理

        // エラーメッセージをリソースファイルから取得
        String msg = msgutil.getMessage("search.failure");
        log.debug(msg);
        // エラーメッセージを画面に表示する
        model.addAttribute("msg", msg);

        // 検索に失敗したら、もう一度ログイン画面
        // 検索フォームを格納
        model.addAttribute("searchForm", form);
        return SEARCH_PAGE;
      }
      List<UserInfo> users = new ArrayList<UserInfo>();
      // ログインユーザー情報の正当性判定
      if (form.getId() != null || form.getName() != null ||form.getKana() !=null) {
        users = search.serchByParams(form.getId(), form.getName(), form.getKana());
         if(users.isEmpty()) {
           String msg = msgutil.getMessage("input.wrong");
           log.debug(msg);
           // エラーメッセージをリソースファイルから取得
           model.addAttribute("msg", msg);
         }
      }
      model.addAttribute("users", users);
      model.addAttribute("searchForm", new SearchForm());
      return SEARCH_PAGE;
     }
   }
}