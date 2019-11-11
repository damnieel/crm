package com.cvc.crm.exception;

/**
 * 业务异常类 BusinessException.java <BR>
 * 创建人:Kyle<BR>
 * 时间：2015年7月22日 下午2:52:08 <BR>
 * 
 * @version 1.0.0
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -3982645755980473694L;

	public int uri;

	public BusinessCode code;

	/**
	 * 默认构造方法
	 */
	public BusinessException() {
	}

	public BusinessException(int uri, BusinessCode code) {
		super("uri=" + uri + ",code=" + code.getCode());
		this.uri = uri;
		this.code = code;
	}

	public BusinessException(BusinessCode code) {
		super("code=" + code.getCode());
		this.code = code;
	}

	public BusinessException(BusinessCode code, Throwable cause) {
		super("code=" + code.getCode(), cause);
		this.code = code;
	}

	public BusinessException(int uri, BusinessCode code, Throwable cause) {
		super("uri=" + uri + ",code=" + code.getCode(), cause);
		this.uri = uri;
		this.code = code;
	}

	public BusinessException(BusinessCode code, String msg) {
		super("code=" + code.getCode() + ",msg=" + msg);
		this.code = code;
	}

	/**
	 * 带有异常消息简单描述和详细发生异常过程的构造方法（异常消息参照已定义常量）
	 * 
	 * @param message
	 * @param cause
	 */
	public BusinessException(BusinessCode code, String msg, Throwable cause) {
		super("code=" + code.getCode() + ",msg=" + msg, cause);
		this.code = code;
	}
}
