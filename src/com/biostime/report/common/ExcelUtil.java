/**
 * 
 */
package com.biostime.report.common;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;



/**
 * Export excel util
 * 目前只支持读取的，没更新操作，所有的Excel操作统一入口
 * 
 * @author wpl 
 * @date May 11, 2014 8:17:00 PM
 * @version 1.0
 */
public class ExcelUtil {

	/**
	 * 编辑的workbook
	 */
//	private WritableWorkbook writableWorkbook = null;

	/**
	 * 读取的workbook
	 */
	private Workbook workbook = null;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet = null;
	
	/**
	 * 文件名称
	 */
	private String fileName = null;
	
	/**
	 * 构造方法
	 * @param fileName 文件名称
	 * @param sheetIndex 工作表对象，默认为0（即第一个）
	 * @author wpl 
	 * @date May 11, 2014 8:17:22 PM
	 * @version 1.0
	 */
	public ExcelUtil(String fileName, int sheetIndex) {
		this.fileName = fileName;
		try {
			workbook = Workbook.getWorkbook(new File(this.fileName));
			sheet = workbook.getSheet(0);
		} catch (Exception e) {
			System.out.println("初始化文档出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param cols 列
	 * @param rows 行
	 * @return cell 单元格
	 * @author wpl 
	 * @date May 11, 2014 8:17:22 PM
	 * @version 1.0
	 */
	public Cell getCell(int cols, int rows) {
		Cell cell = sheet.getCell(cols, rows);
		return cell;
	}
	
	
	/**
	 * 
	 * @param cols 列
	 * @param rows 行
	 * @return content 单元格内容
	 * @author wpl 
	 * @date May 11, 2014 8:17:22 PM
	 * @version 1.0
	 */
	public String getContentByCell(int cols, int rows) {
		Cell cell = getCell(cols, rows);
		String content = cell.getContents();
		return content;
	}
	
	/**
	 * @return 有效的行数
	 * @author wpl 
	 * @date ：May 11, 2014 10:30:53 PM
	 * @version 1.0
	 */
	public int getRowNum() {
		int num = sheet.getColumns();
		return num;
	}
	
	
	/**
	 * 关流
	 * 
	 * @author wpl 
	 * @date ：May 11, 2014 8:18:46 PM
	 * @version 1.0
	 */
	public void close() {
		if(workbook != null)workbook.close();
	}

	/**
	 * 返回成员属性 workbook
	 * @return workbook
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * 返回成员属性 sheet
	 * @return sheet
	 */
	public Sheet getSheet() {
		return sheet;
	}
	
	
}
