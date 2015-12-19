/**
 * 
 */
package com.biostime.magazine.servlet;

import com.baiytfp.commonlib.upload.UploadFileProcess;

/**
 *	
 * @author cjl 新增日期：2014-2-13下午04:16:26
 * @version 1.0
 */
public class UploadFileServlet extends UploadFileProcess {

	@Override
	public void doHandle() {
		try {
			String fileProcess = super.fileProcess(true);
			renderHTML(fileProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
