package com.dobe.core.exception;

/**
 * @Description 		: excel导入导出异常类
 * @Project				: dobe-core
 * @Program				: com.dobe.core.exception
 * @Author 				: zc.ding@foxmail.com
 */
public class ExcelException extends BaseException {
	
	    
	private static final long serialVersionUID = 1L;

	public ExcelException() {
		super();
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelException(String message, Throwable cause, String code, Object[] values) {
		super(message, cause, code, values);
	}
	
}
