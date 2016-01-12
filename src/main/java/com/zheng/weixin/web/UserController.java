package com.zheng.weixin.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.weixin.domain.User;
import com.zheng.weixin.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPage() {
		List<User> users = userService.findAll();
		putRequestContext("users", users);
		return "/user/userList";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addPage() {
		return "user/userInput";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(User user) {
		userService.save(user);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String updatePage(@PathVariable Integer id) {
		User user = userService.findById(id);
		putRequestContext("user", user);
		return "user/userInput";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String update(@PathVariable Integer id, User user) {
		User dbUser = userService.findById(id);
		dbUser.setNickname(user.getNickname());
		dbUser.setSex(user.getSex());
		userService.update(dbUser);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(User user) {
		User dbUser = userService.loadByUserName(user.getUsername());
		if(dbUser == null) {
			putRequestContext("error", "用户名或密码错误!");
			return "user/login";
		}else {
			if(!user.getPassword().equals(dbUser.getPassword())) {
				putRequestContext("error", "用户名或密码错误!");
				return "user/login";
			}
		}
		
		putSessionContext(LOGIN_USER, dbUser);
		return "redirect:/user/list";
	}
	
	/**
	 * 所有的用户绑定操作都是建立在用户关注后，在关注之后会在session中保存用户信息,绑定的用户与session中的用户是关联的
	 * 所以在绑定之前需要判断session中是否有用户存在，如果不存在就不能允许绑定
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午9:42:17
	 * @return
	 */
	@RequestMapping(value="/bindNewUser", method=RequestMethod.GET)
	public String bindNewUserPage() {
		User user = (User) getSessionInfo(LOGIN_USER);
		if(user == null) {
			return "redirect:/user/login";
		}
		
		if(user.getBind() == 1) { //如果已经进行过绑定，就不能再进行绑定了
			return "redirect:/user/list";
		}
		
		return "user/bindNewUser";
	}
	
	@RequestMapping(value="/bindNewUser", method=RequestMethod.POST)
	public String bindNewUser(User user) {
		User u = (User) getSessionInfo(LOGIN_USER);
		if(user == null) {
			return "redirect:/user/login";
		}
		
		userService.bindNewUser(u, user.getUsername(), user.getPassword());
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/bindExistUser", method=RequestMethod.GET)
	public String bindExistUserPage() {
		User u = (User) getSessionInfo(LOGIN_USER);
		if(u == null) {
			return "redirect:/user/login";
		}
		
		if(u.getBind() == 1) { //如果已经进行过绑定，就不能再进行绑定了
			return "redirect:/user/list";
		}
		
		return "user/bindExistUser";
	}
	
	@RequestMapping(value="/bindExistUser", method=RequestMethod.POST)
	public String bindExistUser(User user) {
		User u = (User) getSessionInfo(LOGIN_USER);
		if(u == null) {
			return "redirect:/user/login";
		}
		
		//绑定已存在的用户需要进行用户身份认证
		User dbUser = userService.loadByUserName(user.getUsername());
		
		if(dbUser == null) {
			throw new RuntimeException("绑定用户不存在!");
		}
		
		if(!dbUser.getPassword().equals(user.getPassword())) {
			throw new RuntimeException("绑定用户身份认证失败,输入凭证错误!");
		}
		
		userService.bindExistUser(dbUser, u);
		//绑定完成后将session中用户更新
		putSessionContext(LOGIN_USER, dbUser);
		
		return "redirect:/user/list";
	}

	@RequestMapping(value="/resetPwd", method=RequestMethod.GET)
	public String resetPwdPage() {
		return "user/resetPwd";
	}
	
	@RequestMapping(value="/resetPwd", method=RequestMethod.POST)
	public String resetPwd(String password, String confirmPwd) {
		if(StringUtils.isBlank(password)
				|| StringUtils.isBlank(confirmPwd)) {
			putRequestContext("error", "重置密码时密码、确认密码不能为空!");
			return "user/resetPwd";
		}
		
		if(!password.equals(confirmPwd)) { //这里的判断应该是在用户完成编辑时就触发检测两次密码是否一致，这里为了简单测试
			putRequestContext("error", "两次输入的密码不匹配!");
			return "user/resetPwd";
		}
		
		User user = (User) getSessionInfo(LOGIN_USER);
		User dbUser = userService.findById(user.getId());
		dbUser.setPassword(password);
		userService.update(dbUser);
		
		return "redirect:/user/login";
	}
	
}
