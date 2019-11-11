package com.cvc.crm.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.Info;
import com.cvc.crm.model.Product;
import com.cvc.crm.service.InfoService;
import com.cvc.crm.service.ProductService;
import com.cvc.crm.util.Util;
import com.cvc.crm.web.Web;


@WebServlet(name = "InfoServlet", urlPatterns = "/info")
public class InfoServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(InfoServlet.class);

	private InfoService infoService;
	private ProductService productService;
	
	
	@Override
	public void init() throws ServletException {
		infoService = new InfoService();
		productService = new ProductService();
	}

	public void toPage() throws ServletException, IOException {

		final Long id = Util.id();

		String pid = Web.getParameter("pid");
		if (pid == null || "".equals(pid)) {
			return;
		}
		String uid = Web.getParameter("uid");
		if (uid == null || uid == "") {
			uid = "0";
		}

		HttpServletRequest request = Web.getRequest();
		String page = Web.getParameter("page");
		String uri = request.getRequestURI() + "/" + page + "/" + pid + "/" + uid;
		log.debug(uri);
		Web.setAttribute("pid", pid);
		Web.setAttribute("uid", uid);
		Web.setAttribute("id", id);

		Product product = null;

		try {
			product = productService.select(pid);

			log.debug(product);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		if (product == null) {
			return;
		}

		// 查看产品是否启用
		if (Util.equals("0", product.getStatus())) {
			return;
		}

		Map<String, String> headerMap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String element = headerNames.nextElement();
			String header = request.getHeader(element);
			headerMap.put(element, header);
		}

		log.debug("[" + id + "] HeaderMap: " + headerMap);

		final Info info = new Info();
		info.setId(id);
		info.setCreatedDate(new Date());
		info.setRemoteAddr(Web.getRemoteAddr());
		info.setRequestUri(uri);
		info.setUserAgent(headerMap.get("user-agent"));
		info.setProtocol(request.getProtocol());
		info.setMethod(request.getMethod());
		info.setAccept(headerMap.get("accept"));
		info.setAcceptLanguage(headerMap.get("accept-language"));
		info.setAcceptEncoding(headerMap.get("accept-encoding"));
		info.setScanTime(0);
		info.setScanPlace(1);

		log.debug("[" + id + "] " + info);
		
		
		Map<String, String> map = null;
		try {
			map = Util.loadProperties(InfoServlet.class.getResourceAsStream("/forward.properties"));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
			return;
		}

		String addr = map.get(pid);
		if (Util.isNull(addr) || "".equals(addr)) {
			return;
		}
		Web.forward(addr);
		/*Web.forward("views/index.jsp");*/

	}

	public void scanTime() throws ServletException, IOException {
		/* --- 取参 --- */
		String id = Web.getParameter("id");
		String time = Web.getParameter("time");
		String scanPlace = Web.getParameter("place");

		/* --- 校验 --- */
		if (!Util.isWord(id, 1, 32)) {
			id = null;
		}
		if (!Util.isLength(time, 1, 11)) {
			return;
		}
		if (!Util.equals(scanPlace, "1","2","3")) {
			return;
		}

		/* --- 根据id查询当前用户访问信息 和第一个埋点信息 --- */
		Info info = null;
		try {
			info = infoService.select(id);
		} catch (Exception e) {
			log.error(e);
			return;
		}
		info.setScanTime(Integer.parseInt(time));
		info.setScanPlace(Integer.parseInt(scanPlace));
		/* --- 更新 --- */
		try {
			infoService.replace(info);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void list() throws ServletException, IOException {
		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.redirect(null);
			return;
		}

		/* --- 取参 --- */
		String pid = Web.getParameter("pid");
		String uid = Web.getParameter("uid");
		String userAgent = Web.getParameter("userAgent");
		String remoteAddr = Web.getParameter("remoteAddr");
		String scanTime = Web.getParameter("scanTime");
		String scanPlace = Web.getParameter("scanPlace");

		log.debug(pid + ", " + uid + ", " + userAgent + ", " + remoteAddr + ", " + scanTime + ", " + scanPlace);

		/* --- 校验 --- */
		if (!Util.isWord(pid, 1, 32)) {
			pid = null;
		}
		if (!Util.isLength(uid, 1, 32)) {
			uid = null;
		}
		if (!Util.isLength(userAgent, 1, 32)) {
			userAgent = null;
		}
		if (!Util.isLength(remoteAddr, 1, 32)) {
			remoteAddr = null;
		}
		if (!Util.isLength(scanTime, 1, 11)) {
			scanTime = null;
		}
		if (!Util.equals(scanPlace, "1","2","3")) {
			scanPlace = null;
		}

		/* --- 回显 --- */
		Web.setAttribute("pid", pid);
		Web.setAttribute("uid", uid);
		Web.setAttribute("userAgent", userAgent);
		Web.setAttribute("remoteAddr", remoteAddr);
		Web.setAttribute("scanTime", scanTime);
		Web.setAttribute("scanPlace", scanPlace);

		/* --- 封装 --- */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("uid", uid);
		map.put("userAgent", userAgent);
		map.put("remoteAddr", remoteAddr);
		map.put("scanTime", scanTime);
		map.put("scanPlace", scanPlace);

		map.put("pageNumber", Web.getPageNumber());
		map.put("pageSize", Web.getPageSize());

		/* --- 查询 --- */
		try {
			Web.setPager(infoService.query(map));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.setRender("55010", "55010");
		}
		Web.forward("/views/info_list.jsp");
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
			Web.render("25010", String.valueOf(infoService.delete(ids.split(","))));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

}
