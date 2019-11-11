package com.cvc.crm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

@SuppressWarnings("restriction")
public class QRCodeGeneration {
	
	public static void main(String[] args) throws Exception {

		System.out.println("--- Start ---");

		//read();
		System.out.println(url2Image("http://46q.cn/wechat/scan/qrcode"));
		//System.out.println(url2BASE64Code("www.baidu.com"));

		System.out.println("--- End ---");

	}

	public static void read() throws Exception {

		String pathname = "D:/Temp/1513686215457.png";
		BinaryBitmap image = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new File(pathname)))));

		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

		Result result = (new MultiFormatReader()).decode(image, hints);

		System.out.println(result.getText());

	}
	/**
	 * url转成二维码返回 二维码生成地址
	 * @param url
	 * @return 
	 * @throws Exception
	 */
	public static String url2Image(String url) throws Exception {

		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, "2");
		hints.put(EncodeHintType.QR_VERSION, "3");

		BitMatrix matrix = (new MultiFormatWriter()).encode(url, BarcodeFormat.QR_CODE, 500, 500, hints);

		String pathname = "D:/Temp/" + System.currentTimeMillis() + ".png";
		MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get(pathname));

		return pathname;

	}
	
	/**
	 * url转成BASE64字符串   可以直接使用 <img alt="QRCode" src="data:image/png;base64," + code + ""> 识别显示
	 * @param url
	 * @return 
	 * @throws Exception
	 */
	public static String url2BASE64Code(String url) throws Exception {

		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, "0");
		hints.put(EncodeHintType.QR_VERSION, "3");

		BitMatrix matrix = (new MultiFormatWriter()).encode(url, BarcodeFormat.QR_CODE, 230, 230, hints);

		// 二维码图片 BASE64 转码
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
		String qrcode = (new BASE64Encoder()).encode(baos.toByteArray());
		
		return qrcode;

	}
}
