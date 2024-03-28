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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.domain.table.UserInfo;
import jp.co.sysystem.springWorkout.service.DeleteService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.DeleteForm;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import jp.co.sysystem.springWorkout.web.form.UpdateForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
public class DeleteController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public DeleteService delete;


  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String DELETE_PROCESS_URL = "/delete";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String DELETE_PAGE = "page/delete";
      public static final String SEARCH_PAGE = "page/search";


  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public String showDeletePage(@PathVariable(value = "id") String id, HttpServletRequest request, Model model) {
    // 検索フォームを格納
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    } else {
      List<UserInfo> users = new ArrayList<UserInfo>();
           users = delete.deleteById(id);
           DeleteForm delete = new DeleteForm();
            for(UserInfo i: users) {
            delete.setId(i.getId());
            delete.setName(i.getName());
            delete.setKana(i.getKana());
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String dateToString = df.format(i.getBirth());
            delete.setBirth(dateToString);
            delete.setClub(i.getClub());
          }
      model.addAttribute("deleteForm",delete);
      return DELETE_PAGE;
    }
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String processConfirmDelete(@Validated @ModelAttribute DeleteForm form,
      Model model) {
    if (null == session.getAttribute("user")) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_FORM_URL;
    }else {
      if(form != null) {
        delete.deleteUserInfo(form);

        String msgSuccess = msgutil.getMessage("delete.success");
         model.addAttribute("msgSuccess", msgSuccess);

         model.addAttribute("searchForm", new SearchForm());
        return SEARCH_PAGE;
     }
    }
    return DELETE_PAGE;
  }


}
