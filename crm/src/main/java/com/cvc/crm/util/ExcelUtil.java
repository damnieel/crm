package com.cvc.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * Excel 工具类
 * 
 * @author chenfan
 * @version 1.0, 2017/09/06
 *
 */
public class ExcelUtil {

	public static void main(String[] args) throws Exception {

		//String pathname = "D:/Temp/temp.xlsx";

		// 读
		//System.out.println(read(pathname, 0));

		// 写
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(Arrays.asList("ID", "Name"));
		list.add(Arrays.asList("1", "Google"));
		list.add(Arrays.asList("2", "Baidu"));
		list.add(Arrays.asList("3", "Microsoft"));
		list.add(Arrays.asList("4", "Oracle"));
		list.add(Arrays.asList("5", "IBM"));

		write("C:\\Users\\Administrator\\Desktop\\sss.xlsx", list);

		System.out.println("--- End ---");

	}

	// -- ------------------------------------------------
	// -- 写
	// -- ------------------------------------------------

	public static void write(String pathname, List<List<String>> list) {

		// HSSFWorkbook,xxx.xls
		// XSSFWorkbook,xxx.xlsx

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();

		for (int i = 0; i < list.size(); i++) {

			Row row = sheet.createRow(i);

			List<String> items = list.get(i);
			for (int j = 0; j < items.size(); j++) {
				row.createCell(j).setCellValue(items.get(j));
			}

		}

		try {
			wb.write(new FileOutputStream(new File(pathname)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// -- ------------------------------------------------
	// -- 读
	// -- ------------------------------------------------

	public static List<List<String>> read(String pathname) throws Exception {
		return read(pathname, 0);
	}

	public static List<List<String>> read(String pathname, int index) throws Exception {
		return read(new FileInputStream(pathname), index);
	}

	public static List<List<String>> read(InputStream in, int index) throws Exception {

		List<List<String>> list = new ArrayList<List<String>>();

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(in);
		} catch (Exception e) {
			return list;
		}

		if (wb == null || index >= wb.getNumberOfSheets()) {
			return list;
		}

		Sheet sheet = wb.getSheetAt(index);
		if (sheet == null) {
			return list;
		}

		int rows = sheet.getLastRowNum();
		for (int i = 0; i <= rows; i++) {

			List<String> sub = new ArrayList<String>();

			Row row = sheet.getRow(i);
			if (row == null) {
				list.add(sub);
				continue;
			}

			int cells = row.getLastCellNum();
			for (int j = 0; j < cells; j++) {
				sub.add(getValue(row.getCell(j), null));
			}

			list.add(sub);

		}

		return list;

	}
	
	/**
	 * 按照固定列数量读，空单元格用空字符串表示
	 * @param in
	 * @param index
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> read(InputStream in, int index, int columns) throws Exception {
		
		List<List<String>> list = new ArrayList<List<String>>();
		
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(in);
		} catch (Exception e) {
			return list;
		}
		
		if (wb == null || index >= wb.getNumberOfSheets()) {
			return list;
		}
		
		Sheet sheet = wb.getSheetAt(index);
		if (sheet == null) {
			return list;
		}
		
		int rows = sheet.getLastRowNum();
		for (int i = 0; i <= rows; i++) {
			
			List<String> sub = new ArrayList<String>();
			
			Row row = sheet.getRow(i);
			if (row == null) {
				list.add(sub);
				continue;
			}
			
			for (int j = 0; j < columns; j++) {
				sub.add(getValue(row.getCell(j), ""));
			}
			
			list.add(sub);
			
		}
		
		return list;
		
	}

	// -- ------------------------------------------------
	// -- 其它
	// -- ------------------------------------------------

	public static String getValue(Cell cell, String defaultValue) {
		if (cell == null) {
			return defaultValue;
		}
		try {
			cell.setCellType(CellType.STRING);
			return cell.getStringCellValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

}
