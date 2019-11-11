package com.cvc.crm.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.web.Web;



@WebServlet(name = "CaptchaServlet", urlPatterns = "/captcha")
public class CaptchaServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(CaptchaServlet.class);

	private int width; // 宽度
	private int height; // 高度
	private Color bgColor; // 背景色
	private Color fontColor; // 字体色
	private Font font; // 字体
	private String str; // 字符
	private int x; // 坐标X
	private int y; // 坐标Y

	// 设置样式
	private void setStyle(String style) {
		if ("style1".equalsIgnoreCase(style)) {
			this.width = 100;
			this.height = 35;
			this.bgColor = c(0);
			this.fontColor = new Color(255, 255, 255);
			this.font = new Font("Verdana", Font.PLAIN, 18);
			this.str = s(4);
			this.x = 25;
			this.y = 25;
		}
	}

	// 获取验证码值
	public void value() throws ServletException, IOException {
		Web.write(String.valueOf(Web.getAttribute("session", "SESSION_CAPTCHA")));
	}

	// 获取验证码图片
	public void image() throws ServletException, IOException {

		setStyle("style1");
		log.debug(str);
  
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		g.setColor(fontColor);
		g.setFont(font);
		g.drawString(str, x, y);
		g.dispose();

		Web.setAttribute("session", "SESSION_CAPTCHA", str);
		ImageIO.write(bi, "JPEG", Web.getResponse().getOutputStream());

	}

	// 获取随机数
	private int r(int n) {
		return (new Random()).nextInt(n);
	}

	// 获取随机字符
	private String s(int n) {
		String[] s = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String str = "";
		for (int i = 1; i <= n; i++) {
			str += s[r(s.length)];
		}
		return "null".equalsIgnoreCase(str) ? s(n) : str;
	}

	// 获取随机颜色
	private Color c(int n) {
		n = n == 0 ? r(20) : n;
		switch (n) {
		case 1:
			return new Color(0, 0, 0); // 黑色
		case 2:
			// return new Color(255, 255, 255); // 白色
		case 3:
			return new Color(127, 127, 127); // 深灰色
		case 4:
			return new Color(195, 195, 195); // 淡灰色
		case 5:
			return new Color(136, 0, 21); // 深红色
		case 6:
			return new Color(185, 122, 87); // 褐色
		case 7:
			return new Color(237, 28, 36); // 红色
		case 8:
			return new Color(255, 174, 201); // 玫瑰色
		case 9:
			return new Color(255, 127, 39); // 橙色
		case 10:
			return new Color(255, 201, 14); // 金色
		case 11:
			// return new Color(255, 242, 0); // 黄色
		case 12:
			// return new Color(239, 228, 176); // 淡黄色
		case 13:
			return new Color(34, 177, 76); // 绿色
		case 14:
			return new Color(181, 230, 29); // 酸橙色
		case 15:
			return new Color(0, 162, 232); // 青色
		case 16:
			return new Color(153, 217, 234); // 淡青绿色
		case 17:
			return new Color(63, 72, 204); // 靛色
		case 18:
			return new Color(112, 146, 190); // 蓝灰色
		case 19:
			return new Color(163, 73, 164); // 紫色
		default:
			return new Color(200, 191, 231); // 淡紫色
		}
	}

}
