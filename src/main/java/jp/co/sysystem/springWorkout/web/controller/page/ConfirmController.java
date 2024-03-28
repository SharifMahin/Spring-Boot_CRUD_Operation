package jp.co.sysystem.springWorkout.web.controller.page;

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

import jp.co.sysystem.springWorkout.service.RegisterService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class ConfirmController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public RegisterService register;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String CONFIRM_REGISTRATION_PROCESS_URL = "/confirm";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String CONFIRM_REGISTRATION_PAGE = "page/confirm";
  public static final String SEARCH_PAGE = "page/search";


  /**
   * 検索画面表示
   * ログイン前でも表示可能
   */
  @RequestMapping(value = CONFIRM_REGISTRATION_PROCESS_URL, method = RequestMethod.GET)
  public String showConfirmRegistrationPage(HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      model.addAttribute("registrationForm", new RegistrationForm());
      return CONFIRM_REGISTRATION_PAGE;
    }
  }

  @RequestMapping(value = CONFIRM_REGISTRATION_PROCESS_URL, method = RequestMethod.POST)
  public String processConfirm(@Validated @ModelAttribute RegistrationForm form,
   Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else {
      if(form == null) {

        String msg = msgutil.getMessage("register.failed");
        model.addAttribute("msg", msg);

        model.addAttribute("registrationForm", form);
        return CONFIRM_REGISTRATION_PAGE;
      }
      if(form != null) {
        register.createUser(form);

        String msgSuccess = msgutil.getMessage("register.success");
         model.addAttribute("msgSuccess", msgSuccess);

         model.addAttribute("searchForm", new SearchForm());
        return SEARCH_PAGE;
     }

     //model.addAttribute("registration", new RegistrationForm());
     //return CONFIRM_REGISTRATION_PAGE;
    }
    return CONFIRM_REGISTRATION_PAGE;
  }
}
