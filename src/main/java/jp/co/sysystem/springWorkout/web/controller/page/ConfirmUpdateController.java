package jp.co.sysystem.springWorkout.web.controller.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.service.UpdateService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class ConfirmUpdateController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public UpdateService updateInfo;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String CONFIRM_UPDATE_PROCESS_URL = "/confirmUpdate";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String SEARCH_PAGE = "page/search";
  public static final String CONFIRM_UPDATE_PAGE = "page/confirmUpdate";

  @RequestMapping(value = CONFIRM_UPDATE_PROCESS_URL, method = RequestMethod.GET)
  public String showConfirmRegistrationPage(HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      model.addAttribute("updateForm", new UpdateForm());
      return CONFIRM_UPDATE_PAGE;
    }
  }
  @RequestMapping(value = CONFIRM_UPDATE_PROCESS_URL, method = RequestMethod.POST)
  public String processConfirmUpdate(@Validated @ModelAttribute UpdateForm form,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else {
     if(form == null) {
        String msg = msgutil.getMessage("update.failed");
        model.addAttribute("msg", msg);

        model.addAttribute("updateForm", form);
        return CONFIRM_UPDATE_PAGE;
      }
      if(form != null) {
        updateInfo.updateUser(form);

        String msgSuccess = msgutil.getMessage("update.success");
         model.addAttribute("msgSuccess", msgSuccess);

         model.addAttribute("searchForm", new SearchForm());
        return SEARCH_PAGE;
     }

    }
    return CONFIRM_UPDATE_PAGE;
  }
}
