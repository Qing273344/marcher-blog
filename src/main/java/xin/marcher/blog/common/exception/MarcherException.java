package xin.marcher.blog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 * 
 * @author marcher
 */
@Getter
@Setter
public class MarcherException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public MarcherException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public MarcherException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public MarcherException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public MarcherException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

}