/**
 * 
 */
package com.biostime.customer.servlet;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.report.constant.SystemConstant;
import com.biostime.report.service.ImportExcelService;

/**
 * 
 *	
 * @author wpl 
 * @date May 17, 2014 11:06:25 PM
 * @version 1.0
 */
public class FileUploadServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType)) {
					
			} else {
				importFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void importFile() {
		String importType = null;
		String realFilePath = null;
		boolean success = false;
		int result = 100;	//success = 100
		int successNum = 0;
		
		//先判断是否是文件上传类型
		boolean isFileUpload = ServletFileUpload.isMultipartContent(request); 
		//如果是文件上传类型
		if(isFileUpload) {
			//得到文件上传工厂
			FileItemFactory factory = new DiskFileItemFactory();
			//处理文件上传核心类
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			//设置文件上传类的编码格式
			fileUpload.setHeaderEncoding("utf-8");
			//每一个表单域，对应一个FileItem对象
			List<FileItem> fileItemList = null;
			try {
				fileItemList = fileUpload.parseRequest(request);
				if(fileItemList != null) {
					for (FileItem fileItem : fileItemList) {
						if(!fileItem.isFormField()) {	//文件类型的表单
							String name = fileItem.getName();
							String fileName = name.substring(name.lastIndexOf("\\")+1);
							String rootPath = request.getRealPath("/");
							String realPath = rootPath + "fileupload";
							File file = new File(realPath);
							if(!file.isDirectory()) {
								file.mkdirs();
							}
							realFilePath =  realPath + File.separator + fileName;
							File newFile = new File(realFilePath);
							fileItem.write(newFile);
							success = true;
						} else {
							//非文件类型的表单
							String filedName = fileItem.getFieldName();
							importType = fileItem.getString();
							System.out.println("type====" + filedName + "===" +importType);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
				result = 101;
			} catch (Exception e) {
				e.printStackTrace();
				result = 101;
			}
		}
		
		if(importType == null || realFilePath == null || !success) {
			result = 101;
			System.out.println("文件导入出错！");
		} else {
			ImportExcelService excelService = new ImportExcelService();
			try {
				Map<String, Object> parseResult = excelService.excute(importType, realFilePath);
				Boolean flag = (Boolean) parseResult.get("result");
				successNum = Integer.parseInt(String.valueOf(parseResult.get("num")));
				if(flag) {
					result = 100;
				} else {
					result = 102;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = 102;
			}
		}
		
		if(result == 101){
			try {
				setAttribute("result", "导入文件出错!");
				requestForward("/reciprocity_admin/index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(result == 102) {
			try {
				setAttribute("result", "文件解析出错!");
				requestForward("/reciprocity_admin/index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			setAttribute("resultNum", successNum);
			if(SystemConstant.CUSTOMERINFO.equals(importType)) {
				requestForward("/customer.action?operType=list");
			} else if(SystemConstant.CONTRACT.equals(importType)) {
				requestForward("/contract.action?operType=list");
			} else if(SystemConstant.ORDER.equals(importType)) {
				requestForward("/workorder.action?operType=list");
			} else if(SystemConstant.PROCESS.equals(importType)) {
				requestForward("/feedback.action?operType=list");
			} else if(SystemConstant.QUESSTION.equals(importType)) {
				requestForward("/question.action?operType=list");
			} else if(SystemConstant.EMPLOYEE.equals(importType)) {
				requestForward("/employee.action?operType=list");
			} 
			
		}
		
	}

}
