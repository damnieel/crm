package com.cvc.crm.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.User;
import com.cvc.crm.service.UserService;
import com.cvc.crm.util.Constants;
import com.cvc.crm.util.HttpRequest;
import com.cvc.crm.util.JSONRender;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Secret;
import com.cvc.crm.util.Util;
import com.cvc.crm.web.Web;




/**
 * 
 * 用户 Servlet
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/17
 *
 */
@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(UserServlet.class);

	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}

	public void list() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.redirect(null);
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id");
		String username = Web.getParameter("username");
		String type = Web.getParameter("type");
		String status = Web.getParameter("status");

		log.debug(id + ", " + username + ", " + type + ", " + status);

		/* --- 校验 --- */
		if (!Util.isWord(id, 1, 32)) {
			id = null;
		}
		if (!Util.isWord(username, 1, 32)) {
			username = null;
		}
		if (!Util.isNumber(type, 1, 1)) {
			type = null;
		}
		if (!Util.isNumber(status, 1, 1)) {
			status = null;
		}

		/* --- 回显 --- */
		Web.setAttribute("id", id);
		Web.setAttribute("username", username);
		Web.setAttribute("type", type);
		Web.setAttribute("status", status);

		/* --- 封装 --- */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("username", username);
		map.put("type", type);
		map.put("status", status);
		map.put("pageNumber", Web.getPageNumber());
		map.put("pageSize", Web.getPageSize());

		/* --- 查询 --- */
		try {
			Web.setPager(userService.query(map));
		} catch (Exception e) {
			Web.setRender("55010", "55010");
		}

		Web.forward("/views/user_list.jsp");

	}

	public void allList() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.redirect(null);
			return;
		}
		/* --- 查询 --- */
		try {
			Pager<User> query = userService.query(null);
			List<User> content = query.getContent();
			Web.render((new JSONRender<List<User>>("25200", content)));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.setRender("55010", "55010");
		}

	}

	public void save() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id");
		String username = Web.getParameter("username");
		String password = Web.getParameter("password");
		String type = Web.getParameter("type");
		String status = Web.getParameter("status");
		String desc = Web.getParameter("desc");
		String created = Web.getParameter("created");

		log.debug(id + ", " + username + ", " + password + ", " + type + ", " + status + ", " + desc + ", " + created);

		/* --- 校验 --- */
		if (!Util.isWord(id, 1, 32)) {
			Web.render("45010", "45010");
			return;
		}
		if (!Util.isWord(username, 1, 32)) {
			Web.render("45020", "45020");
			return;
		}
		if (!Web.isAction("edit") && !Util.isLength(password, 1, 32)) {
			Web.render("45030", "45030");
			return;
		}

		if (!Util.isNumber(type, 1, 1)) {
			Web.render("45040", "45040");
			return;
		}
		if (!Util.isNumber(status, 1, 1)) {
			Web.render("45050", "45050");
			return;
		}
		if (!Util.isLength(desc, 0, 96)) {
			Web.render("45060", "45060");
			return;
		}
		if (!Util.isNumber(created, 1, 24)) {
			created = Util.datetime();
		}
		if (!Web.isAction("edit")) {
			password = Secret.encrypt(password);
		}

		if (Web.isAction("edit") && Util.isLength(password, 1, 32)) {
			password = Secret.encrypt(password);
		}
		/* --- 封装 --- */
		User o = new User();
		o.setUsername(username);
		o.setPassword(password);
		o.setDesc(desc);

		/* --- 存储 --- */
		try {
			if (Web.isAction("edit")) {
				if ("".equals(password)) {
					User user = userService.select(id);
					o.setPassword(user.getPassword());
				}
				userService.delete(id);
			}
			userService.save(o);
			Web.render("25010", "25010");
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	public void load() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id");

		log.debug(id);

		/* --- 校验 --- */
		if (!Util.isWord(id, 1, 32)) {
			Web.render("45010", "45010");
			return;
		}

		/* --- 查询 --- */
		try {
			Web.render(new JSONRender<User>("25010", userService.select(id)));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	public void delete() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String ids = Web.getParameter("ids");
		log.debug(ids);

		/* --- 校验 --- */
		if (ids == null) {
			Web.render("45010", "45010");
			return;
		}

		/* --- 删除 --- */
		try {
			Web.render("25010", String.valueOf(userService.delete(ids.split(","))));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	/**
	 * 
	 * 登录
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void signin() throws ServletException, IOException {
		/* --- 权限 --- */
		if (Web.getUser() != null) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String username = Web.getParameter("username");
		String password = Web.getParameter("password");

		log.debug(username + ", " + password);

		/* --- 校验 --- */
		if (!Util.isWord(username, 1, 32)) {
			Web.render("45010", "45010");
			return;
		}
		if (!Util.isWord(password, 1, 32)) {
			Web.render("45020", "45020");
			return;
		}
		password = Secret.encrypt(password);
		User user = null;

		/* --- 查询 --- */
		try {
			user = userService.select(username, password);
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55020", "55020");
			return;
		}

		/* --- 校验 --- */
		if (user == null) {
			Web.render("45030", "45030");
			return;
		}
		if (!"1".equals(String.valueOf(user.getStatus()))) {
			Web.render("45040", "45040");
			return;
		}
		
		// 返回
		Web.setUser(user);
		Web.render("25010", "25010");

	}

	/**
	 * 
	 * 登出
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void signout() throws ServletException, IOException {
		Web.invalidate();
		Web.redirect("/signin");
	}

	/**
	 * 修改密码
	 */
	public void updatePassword() throws ServletException, IOException {

		/* --- 取参 --- */
		String oldpassword = Web.getParameter("oldpassword");
		String newpassword = Web.getParameter("newpassword");

		/* --- 获取当前用户 --- */
		User user = Web.getUser();
		if (user == null) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 校验 --- */
		if (!Util.isWord(oldpassword, 3, 32)) {
			Web.render("45010", "45010");
			return;
		} else {
			oldpassword = Secret.encrypt(oldpassword);
		}
		if (!Util.isWord(newpassword, 3, 32)) {
			Web.render("45020", "45020");
			return;
		} else {
			newpassword = Secret.encrypt(newpassword);
		}
		if (!Util.equals(oldpassword, user.getPassword())) {
			Web.render("45030", "45030");
			return;
		}

		/* --- 封装 --- */
		user.setPassword(newpassword);

		User o = new User();
		o.setId(user.getId());
		o.setUsername(user.getUsername());
		o.setPassword(user.getPassword());
		o.setStatus(user.getStatus());
		o.setDesc(user.getDesc());

		try {
			Web.render("25200", userService.replace(o));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}
	/**
	 * 发送短信验证码
	 * 
	 * @throws Exception
	 */
	public void send() throws Exception {
		
		System.out.println("====进入发送方法====");
		/* --- 取参 --- */
		String username = Web.getParameter("name");
		String mobile = Web.getParameter("mobile");
		String captcha = Web.getParameter("captcha");
		String sign = Util.md5(Constants.XXK_DEV_ID + Constants.XXK_KEY + mobile);
		String fourRandom = Util.random(6);
		String smsParam = "{\"name\":\"" + username + "\",\"code\":\"" + fourRandom + "\"}";
		String smsTemplateCode = Constants.XXK_SMS_TEMPLATE_CODE;

		/* --- 校验 --- */
		if (!Util.isLength(username, 1, 64)) {
			Web.render("45020");
			return;
		}
		if (!Util.isNumber(mobile, 11, 11) || !Util.matches("[0-9,]+", mobile)) {
			Web.render("49030", "接收号码不合法！");
			return;
		}
		if (!Util.isWord(captcha, 4, 4) || !Util.eq(true, captcha, String.valueOf(Web.getAttribute("session", "SESSION_CAPTCHA")))) {
			Web.render("45030");
			return;
		}
		
		/*// 一天一个用户只能发送五次，一个ip只能发十次
		String remoteAddr = Web.getRemoteAddr();
		long countIp = RDSPlus.count("limit_signup_" + remoteAddr);
		long countPhone = RDSPlus.count("limit_signup_" + username);

		if (countPhone > 5 || countIp > 10) {
			Web.render("45050");
			return;
		}
		
		// 将验证码存进redis里面
		String fourRandom = Util.random(6);
		RDSUtils.set("phone_" + username, fourRandom);
		RDSUtils.expire("phone_" + username, 300);*/
		// 将验证码存进session里面
		Web.setAttribute("session", "phone_"+username, fourRandom);
		
		//发送
		Map<String, String> data = new HashMap<String, String>();
		data.put("dev_id", Constants.XXK_DEV_ID);
		data.put("sign",sign);
		data.put("rec_num", mobile);
		data.put("sms_template_code", smsTemplateCode);
		data.put("sms_param", smsParam);
		System.out.println(data);
		
		String result = HttpRequest.post("http://www.xinxinke.com/api/send", data);
		System.out.println(result);
		
		Web.render("25010","短信已经成功发送...");
	}
}
