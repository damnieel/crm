package com.cvc.crm.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.Product;
import com.cvc.crm.model.User;
import com.cvc.crm.service.ProductService;
import com.cvc.crm.util.JSONRender;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Util;
import com.cvc.crm.web.Web;


@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ProductServlet.class);

	private ProductService productService;

	@Override
	public void init() throws ServletException {
		productService = new ProductService();
	}

	public void list() throws ServletException, IOException {

		/* --- 权限 --- */
		User user = Web.getUser();
		if (user == null) {
			Web.redirect("/signin");
			return;
		}
		/* --- 鉴权 --- */
		/*Auth auth = Web.getAuth();
		if (!auth.isAdmin()) {
			//鉴权
			Web.redirect(null);
			return;
		}*/

		/* --- 取参 --- */
		String pid = Web.getParameter("id");
		String prefixLink = Web.getParameter("prefixLink");
		String name = Web.getParameter("name");
		String status = Web.getParameter("status");

		log.debug(prefixLink + ", " + name + ", " + status + ", " + pid);

		/* --- 校验 --- */
		if (!Util.isWord(prefixLink, 1, 64)) {
			prefixLink = null;
		}
		if (!Util.isWord(pid, 1, 64)) {
			pid = null;
		}
		if (!Util.isLength(name, 1, 32)) {
			name = null;
		}

		if (!Util.isNumber(status, 1, 1)) {
			status = null;
		}

		/* --- 回显 --- */
		Web.setAttribute("prefixLink", prefixLink);
		Web.setAttribute("id", pid);
		Web.setAttribute("name", name);
		Web.setAttribute("status", status);

		/* --- 封装 --- */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prefixLink", prefixLink);
		map.put("id", pid);
		map.put("productName", name);
		map.put("status", status);
		map.put("pageNumber", Web.getPageNumber());
		map.put("pageSize", Web.getPageSize());

		/* --- 查询 --- */
		try {
			Web.setPager(productService.query(map));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.setRender("55010", "55010");
		}
		Web.forward("/views/console/product_list.jsp");
	}

	public void search() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String pid = Web.getParameter("id");
		String prefixLink = Web.getParameter("prefixLink");
		String productName = Web.getParameter("productName");
		String status = Web.getParameter("status");

		log.debug(prefixLink + ", " + productName + ", " + status + ", " + pid);

		/* --- 校验 --- */
		if (!Util.isWord(prefixLink, 1, 64)) {
			prefixLink = null;
		}
		if (!Util.isWord(pid, 1, 64)) {
			pid = null;
		}
		if (!Util.isLength(productName, 1, 32)) {
			productName = null;
		}

		if (!Util.isNumber(status, 1, 1)) {
			status = null;
		}

		/* --- 封装 --- */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prefixLink", prefixLink);
		map.put("id", pid);
		map.put("productName", productName);
		map.put("status", status);
		map.put("pageNumber", Web.getPageNumber());
		map.put("pageSize", Web.getPageSize());

		/* --- 查询 --- */
		try {
			Web.render(new JSONRender<Pager<Product>>("25200", productService.query(map)));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	public void save() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String pid = Web.getParameter("id");
		String name = Web.getParameter("name");
		String price = Web.getParameter("price");
		String status = Web.getParameter("status");
		String desc = Web.getParameter("desc");
		String created = Web.getParameter("created");

		log.debug(pid + ", " + name + ", " + desc + ", " + created);

		/* --- 校验 --- */
		if (Web.isAction("new")) {
			// pid = Util.random();
			created = Util.datetime();
		}
		if (!Util.isWord(pid, 1, 64)) {
			Web.render("45010", "45010");
			return;
		}
		if (!Util.isWord(pid, 1, 64)) {
			Web.render("45010", "45010");
			return;
		}
		
		if (!Util.isLength(name, 1, 64)) {
			Web.render("45030", "45030");
			return;
		}
		
		if (!Util.isLength(price, 1, 32)) {
			Web.render("45040", "45040");
			return;
		}

		if (!Util.isNumber(status, 1, 1)) {
			Web.render("45070", "45070");
			return;
		}
		if (!Util.isNull(desc) && !Util.isLength(desc, 0, 96)) {
			Web.render("45090", "45090");
			return;
		}
		if (!Util.isNumber(created, 1, 24)) {
			created = Util.datetime();
		}

		/* --- 封装 --- */
		Product o = new Product();
		o.setId(pid);
		o.setPrice(Double.valueOf(price));
		o.setStatus(status);
		o.setDesc(desc);
		o.setCreated(created);
		o.setUpdated(Util.datetime());

		/* --- 存储 --- */
		try {
			if (Web.isAction("edit")) {
				productService.delete(o.getId());
			}
			productService.save(o);
			Web.render("25010", "25010");
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	public void load() throws ServletException, IOException {

		/* --- 权限 --- */
/*		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}
*/
		/* --- 取参 --- */
		String pid = Web.getParameter("id");

		log.debug(pid);

		/* --- 校验 --- */
		if (!Util.isWord(pid, 1, 64)) {
			Web.render("45010", "45010");
			return;
		}

		/* --- 查询 --- */
		try {
			Web.render(new JSONRender<Product>("25010", productService.select(pid)));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

	public void update() throws ServletException, IOException {

		/* --- 权限 --- */
		if (!Web.implies("1")) {
			Web.render("45403", "45403");
			return;
		}

		/* --- 取参 --- */
		String ids = Web.getParameter("ids");
		String status = Web.getParameter("status");

		log.debug(ids + ", " + status);

		/* --- 校验 --- */
		if (ids == null) {
			Web.render("45010", "45010");
			return;
		}

		if (!Util.isNumber(status, 1, 1)) {
			Web.render("45020", "45020");
			return;
		}

		/* --- 删除 --- */
		try {
			Web.render("25010", String.valueOf(productService.update("status", status, ids.split(","))));
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
			Web.render("25010", String.valueOf(productService.delete(ids.split(","))));
		} catch (Exception e) {
			log.error(e.getMessage());
			Web.render("55010", "55010");
		}

	}

}
