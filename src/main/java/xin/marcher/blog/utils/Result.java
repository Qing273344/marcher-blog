package xin.marcher.blog.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 *
 * @author marcher
 */
public class Result extends HashMap<String, Object> implements IResult {

    private static final long serialVersionUID = 1L;

    public static Result error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result().put("status", statusData(code, msg));
    }

    public static Result success() {
        Result result = new Result();
        result.putAll(dataResult());
        result.putAll(statusResult());
        return result;
    }

    public static Result success(Object obj) {
        Result result = new Result();
        result.putAll(dataResult(obj));
        result.putAll(statusResult());
        return result;
    }

    public static Result success(Map<String, Object> map) {
        Result result = new Result();
        result.putAll(statusResult());
        result.putAll(dataResult(map));
        return result;
    }

    public static Result successPage(Object obj, PageUtil page) {
        Result result = new Result();
        result.putAll(dataResult(obj));
        result.putAll(putPage(page));
        result.putAll(statusResult());
        return result;
    }

    protected static Result dataResult() {
        return new Result().put("data", putData("result", true));
    }

    protected static Result dataResult(Object obj) {
        return new Result().put("data", obj);
    }

    protected static Result dataResult(List<?> list) {
        return new Result().put("data", putData("list", list));
    }

    protected static Result dataResult(Map<String, Object> map) {
        return new Result().put("data", map);
    }

    private static Result statusResult(Map<String, Object> map) {
        return new Result().put("status", map);
    }

    protected static Result putData(String key, Object value) {
        return new Result().put(key, value);
    }

    private static Result putPage(PageUtil page) {
        return new Result().put("page", page);
    }

    private static Result putStatus(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    protected static Result statusResult() {
        return new Result().put("status", statusData(0, "success"));
    }

    protected static Result statusData(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}