package xin.marcher.blog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常(提示)
 * 
 * @author marcher
 */
@Getter
@Setter
public class MarcherHintException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 400;

    public MarcherHintException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public MarcherHintException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public MarcherHintException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public MarcherHintException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

}