package com.jw.admin.modular.system.controller;

import com.google.code.kaptcha.Constants;
import com.jw.admin.core.controller.BaseController;
import com.jw.sys.api.IMenuService;
import com.jw.admin.core.config.web.RedisSessionDao;
import com.jw.admin.core.redis.IRedisService;
import com.jw.admin.core.shiro.ShiroKit;
import com.jw.admin.core.shiro.ShiroUser;
import com.jw.admin.core.shiro.UsernamePasswordTypeToken;
import com.jw.admin.core.util.ApiMenuFilter;
import com.jw.admin.core.util.KaptchaUtil;
import com.jw.admin.core.util.ToolUtil;
import com.jw.common.constant.state.ManagerStatus;
import com.jw.sys.bean.vo.MenuNode;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    IMenuService menuService;
    @Autowired
    private IUserService userService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private RedisSessionDao redisSessionDao;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
      /*  List<MenuNode> menus = menuService.getMenus();
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        model.addAttribute("titles", titles);
        return "/index.html";*/
        //获取角色列表
        //List<Integer> roleList = ShiroKit.getUser().getRoleList();
        try {
            UserVo userVo = userService.selectById(ShiroKit.getUser().getId());
            if(userVo.getStatus()== ManagerStatus.INIT.getCode()){
                return "/user_chpwd.html";
            }
            List<Integer> roleIdList = userService.selectRoleIdsByUserId(ShiroKit.getUser().getId());
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleIdList,ShiroKit.getUser().getType());
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            titles = ApiMenuFilter.build(titles);
            model.addAttribute("titles", titles);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/index.html";

    }



    /**
     * 跳转到登录页面
     */
    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }*/

    /**
     * 跳转到登录页面
     */
   /* @RequestMapping(value = "/alogin", method = RequestMethod.GET)
    public String alogin() {
        return "/alogin.html";
    }*/
    /**
     * 跳转到登录页面
     */
   /* @RequestMapping(value = "/zjlogin", method = RequestMethod.GET)
    public String talogin() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/zjlogin.html";
        }
    }*/
    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/newlogin", method = RequestMethod.GET)
    public String newlogin() {
       /* if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/newlogin.html";
        }*/
        return "/newlogin.html";
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali(Model model, HttpServletRequest httpServletRequest) {
        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String type = super.getPara("type").trim();
        String remember = super.getPara("remember");
        String url="/newlogin.html";
        String text = "账号密码错误";
        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                model.addAttribute("tips", "验证码错误");
                model.addAttribute("username", username);
                model.addAttribute("password", password);
                return url;
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordTypeToken token = new UsernamePasswordTypeToken(username, password.toCharArray(), type);
        //UsernamePasswordToken token = new UsernamePasswordToken(userVo.getAccount(), password.toCharArray());
        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }
        try {
            currentUser.login(token);
        } catch (Exception e) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                UserVo userVo = userService.getByAccountAndType(username,Integer.parseInt(type));
                UserVo userVoNew = new UserVo();
                int i = 1;
                if (userVo != null) {
                    userVoNew.setId(userVo.getId());
                    if (userVo.getErrorLogin() != null && !"".equals(userVo.getErrorLogin())) {
                        String[] dateStr = userVo.getErrorLogin().split(",");
                        i = dateStr.length;
                        if (i == 1) {
                            if (simpleDateFormat.format(simpleDateFormat1.parse(dateStr[0])).equals(simpleDateFormat.format(new Date()))) {
                                userVoNew.setErrorLogin(userVo.getErrorLogin() + simpleDateFormat1.format(new Date()) + ",");
                                text = "账号密码错误,今日剩余登录错误次数1";
                            } else {
                                if (userVo.getStatus() == ManagerStatus.ERRORLOGIN.getCode()) {
                                    userVoNew.setStatus(1);
                                }
                                userVoNew.setErrorLogin(simpleDateFormat1.format(new Date()) + ",");
                                text = "账号密码错误,今日剩余登录错误次数2";
                            }

                        } else if (i == 2) {
                            if (simpleDateFormat.format(simpleDateFormat1.parse(dateStr[0])).equals(simpleDateFormat.format(new Date()))) {
                                if (userVo.getStatus() == ManagerStatus.OK.getCode()) {
                                    userVoNew.setStatus(5);
                                }
                                text = "账号密码错误,今日剩余登录错误次数0";
                                userVoNew.setErrorLogin(userVo.getErrorLogin() + simpleDateFormat1.format(new Date()) + ",");
                            } else {
                                if (userVo.getStatus() == ManagerStatus.ERRORLOGIN.getCode()) {
                                    userVoNew.setStatus(1);
                                }
                                userVoNew.setErrorLogin(simpleDateFormat1.format(new Date()) + ",");
                                text = "账号密码错误,今日剩余登录错误次数2";
                            }

                        } else {
                            if (!simpleDateFormat.format(simpleDateFormat1.parse(dateStr[0])).equals(simpleDateFormat.format(new Date()))) {
                                if (userVo.getStatus() == ManagerStatus.ERRORLOGIN.getCode()) {
                                    userVoNew.setStatus(1);
                                }
                                userVoNew.setErrorLogin(simpleDateFormat1.format(new Date()) + ",");
                                text = "账号密码错误,今日剩余登录错误次数2";
                            }else {
                                text = "账号密码错误,今日剩余登录错误次数已用完,该账号已被冻结,请联系管理员或明日重试";
                            }

                        }
                    } else {
                        userVoNew.setErrorLogin(simpleDateFormat1.format(new Date()) + ",");
                        text = "账号密码错误,今日剩余登录错误次数2";
                    }
                    userService.update(userVoNew);
                }else {
                    text = "账号密码错误";
                }
                model.addAttribute("tips", text);
            }catch (Exception e1){
                e1.printStackTrace();
            }
            return url;
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        UserVo userVo = userService.selectById(shiroUser.getId());
        UserVo userVoNew = new UserVo();
        userVoNew.setId(userVo.getId());
        System.out.println(type+"   "+shiroUser);

        if(userVo.getStatus()==ManagerStatus.FREEZED.getCode()){
            model.addAttribute("tips", "该用户已被冻结，无法登陆");
            ShiroKit.getSubject().logout();
            return url;
        }
        if(userVo.getStatus()==ManagerStatus.ERRORLOGIN.getCode()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String[] dateStr = userVo.getErrorLogin().split(",");
            try {
                if(simpleDateFormat.format(simpleDateFormat1.parse(dateStr[0])).equals(simpleDateFormat.format(new Date()))) {
                    model.addAttribute("tips", "该用户今日登录次数超过限制，无法登陆,请联系管理员或明日登录");
                    ShiroKit.getSubject().logout();
                    return url;
                }else {
                    userVoNew.setErrorLogin("");
                    userVoNew.setStatus(1);
                    userService.update(userVoNew);
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        UserVo userVoNew1 = new UserVo();
        userVoNew1.setId(userVo.getId());
        userVoNew1.setErrorLogin("");
        userService.update(userVoNew1);

        List<Integer> roleIdList = userService.selectRoleIdsByUserId(userVo.getId());
        if (roleIdList == null || roleIdList.size() == 0) {
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            ShiroKit.getSubject().logout();
            System.out.println("url"+url);
            return url;
        }

        System.out.println("获取shiroUser = "+shiroUser);
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());
        //LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
//        ShiroKit.getSession().setAttribute("sessionFlag", true);
//        //
//        String redisKey = type + shiroUser.getAccount() + "login";
//        // 如果该账号已登录 则主动注销改账户的session
//        if (redisService.exists(redisKey)) {
//            String preSessionId = redisService.get(redisKey);
//            redisSessionDao.delete(preSessionId);
//        }
//        // 存入当前的sessionid
//        redisService.set(redisKey, super.getSession().getId());

        String basePath = (String) httpServletRequest.getAttribute("basePath");
        log.info("login_basePath:" + basePath);
        return REDIRECT + basePath + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest httpServletRequest) {
        System.out.println("退出登录l ");
        //LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        /*int type = ShiroKit.getUser().getType();
        String url = "/login";
        if(type==1){
            url="/zjlogin";
        }*/
        ShiroKit.getSubject().logout();
        String basePath = (String) httpServletRequest.getAttribute("basePath");
        return REDIRECT + basePath + "/newlogin";
    }
}
