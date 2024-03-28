package jp.co.sysystem.springWorkout.web.controller.page;


import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.service.UpdateService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegistrationForm;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class UpdateController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public UpdateService update;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String UPDATE_PROCESS_URL = "/update";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String UPDATE_PAGE = "page/update";
  public static final String CONFIRM_UPDATE_PAGE = "page/confirmUpdate";

  @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
  public String showUpdatePage(@PathVariable(value = "id") String id, HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      List<UserInfo> users = new ArrayList<UserInfo>();
      users = update.serchById(id);
     UpdateForm update = new UpdateForm();
      for(UserInfo i: users) {
        update.setId(i.getId());
        update.setName(i.getName());
        update.setKana(i.getKana());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dateToString = df.format(i.getBirth());
        update.setBirth(dateToString);
        update.setClub(i.getClub());
      }
      model.addAttribute("updateForm",update);
      return UPDATE_PAGE;
    }
  }

  @RequestMapping(value = UPDATE_PROCESS_URL, method = RequestMethod.POST)
  public String processUpdate(
      @Validated @ModelAttribute UpdateForm form, BindingResult bindingResult,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else {
      // BeanValidationの結果確認
      if (bindingResult.hasErrors()) {
        // TODO: 検索フォームからの入力値確認結果に誤りがあった場合の処理
        // エラーメッセージをリソースファイルから取得
        String msg = msgutil.getMessage("update.failure");
        log.debug(msg);
        // エラーメッセージを画面に表示する
        model.addAttribute("msg", msg);

        // 検索に失敗したら、もう一度ログイン画面
        // 検索フォームを格納
        model.addAttribute("updateForm", form);
        return UPDATE_PAGE;
      }
      if(form.getId().length() > 5) {
        String msg = msgutil.getMessage("id.length");
        model.addAttribute("msg", msg);

        model.addAttribute("updateForm", form);
        return UPDATE_PAGE;
      }
      if(form.getBirth()!=null) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        format.setLenient(false);
        try
        {
            format.parse(form.getBirth());

        }catch(Exception e) {

          e.printStackTrace();
          String msg = msgutil.getMessage("update.incorrectDate");
          model.addAttribute("msg", msg);

          model.addAttribute("updateForm", form);
          return UPDATE_PAGE;
        }
      }
      if(form == null) {
        String msg = msgutil.getMessage("update.failed");
        log.debug(msg);

        model.addAttribute("updateForm", form);
        return UPDATE_PAGE;
      }
      if(form != null) {
        model.addAttribute("updateForm", form);
        return CONFIRM_UPDATE_PAGE;
     }
      model.addAttribute("updateForm", new UpdateForm());
      return UPDATE_PAGE;
    }
  }
}
