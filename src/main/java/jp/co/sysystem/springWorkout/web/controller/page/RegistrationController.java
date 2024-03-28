package jp.co.sysystem.springWorkout.web.controller.page;


import java.sql.Date;
import java.text.SimpleDateFormat;
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

import jp.co.sysystem.springWorkout.domain.jooqObject.tables.User;
import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.service.RegisterService;
import jp.co.sysystem.springWorkout.service.SearchService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class RegistrationController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public RegisterService register;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String REGISTRATION_PROCESS_URL = "/registration";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String REGISTRATION_PAGE = "page/registration";
  public static final String CONFIRM_REGISTRATION_PAGE = "page/confirm";


  /**
   * 検索画面表示
   * ログイン前でも表示可能
   */
  @RequestMapping(value = REGISTRATION_PROCESS_URL, method = RequestMethod.GET)
  public String showRegistrationPage(HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      model.addAttribute("registrationForm", new RegistrationForm());
      return REGISTRATION_PAGE;
    }
  }


  @RequestMapping(value = REGISTRATION_PROCESS_URL, method = RequestMethod.POST, params = "checkId")
  public String checkId(@ModelAttribute RegistrationForm form, BindingResult bindingResult,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else{
    if(form.getId() != null) {

      if ( register.checkDuplicateId(form.getId())==null) {
        String msg = msgutil.getMessage("register.notExistId");
        log.debug(msg);
        // エラーメッセージを画面に表示する
        model.addAttribute("msg", msg);

        // 検索に失敗したら、もう一度ログイン画面
        // 検索フォームを格納
        model.addAttribute("registrationForm", form);
        return REGISTRATION_PAGE;

      }else {
      String msg = msgutil.getMessage("register.existId");
      log.debug(msg);
      // エラーメッセージを画面に表示する
      model.addAttribute("msg", msg);

      // 検索に失敗したら、もう一度ログイン画面
      // 検索フォームを格納
      model.addAttribute("registrationForm", form);
      return REGISTRATION_PAGE;

      }
    }
    model.addAttribute("registration", new RegistrationForm());
    return REGISTRATION_PAGE;
    }
  }

  @RequestMapping(value = REGISTRATION_PROCESS_URL, method = RequestMethod.POST)
  public String processRegistartion(
      @Validated @ModelAttribute RegistrationForm form, BindingResult bindingResult,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else {
      // BeanValidationの結果確認
      if (bindingResult.hasErrors()) {
        // TODO: 検索フォームからの入力値確認結果に誤りがあった場合の処理
        // エラーメッセージをリソースファイルから取得
        String msg = msgutil.getMessage("registration.failure");
        log.debug(msg);
        // エラーメッセージを画面に表示する
        model.addAttribute("msg", msg);

        // 検索に失敗したら、もう一度ログイン画面
        // 検索フォームを格納
        model.addAttribute("registrationForm", form);
        return REGISTRATION_PAGE;
      }
      if(register.checkDuplicateId(form.getId())!=null) {
       String msg = msgutil.getMessage("register.idExist");
       model.addAttribute("msg", msg);

       model.addAttribute("registrationForm", form);
       return REGISTRATION_PAGE;
      }
      if(!form.getPassword().equals(form.getRePassword())) {
       String msg = msgutil.getMessage("register.passwordNotMatch");
       model.addAttribute("msg", msg);

       model.addAttribute("registrationForm", form);
       return REGISTRATION_PAGE;
      }
      if(form.getBirth()!=null) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        format.setLenient(false);
        try
        {
            format.parse(form.getBirth());

        }catch(Exception e) {

          e.printStackTrace();
          String msg = msgutil.getMessage("register.incorrectDate");
          model.addAttribute("msg", msg);

          model.addAttribute("registrationForm", form);
          return REGISTRATION_PAGE;
        }
      }
      if(form.getId().length() > 5) {
        String msg = msgutil.getMessage("id.length");
        model.addAttribute("msg", msg);

        model.addAttribute("registrationForm", form);
        return REGISTRATION_PAGE;
      }
      if(form == null) {

        String msg = msgutil.getMessage("register.failed");
        model.addAttribute("msg", msg);

        model.addAttribute("registrationForm", form);
        return REGISTRATION_PAGE;
     }
      if(form != null ) {
         model.addAttribute("registrationForm", form);
         return CONFIRM_REGISTRATION_PAGE;
      }

      model.addAttribute("registrationForm", new RegistrationForm());
      return REGISTRATION_PAGE;
     }
   }
}