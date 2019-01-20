package xin.marcher.blog.utils;

/**
 * 分页工具
 *
 * @author marcher
 */
public class PageUtil {

    /**
     * 总数据数
     */
    private int totalRow;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 每页数据数
     */
    private int pageSize;

    /**
     * 当前页数
     */
    private int curPage;

    /**
     * 是否首页
     */
    private boolean firstPage;

    /**
     * 是否末页
     */
    private boolean lastPage;

    /**
     * 分页
     *
     * @param totalRow    总记录数
     * @param pageSize    每页记录数
     * @param currentPage 当前页数
     */
    public PageUtil(int totalRow, int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.curPage = currentPage;
        this.totalRow = totalRow;
        this.totalPage = (int) Math.ceil((double) totalRow / pageSize);
        this.firstPage = this.curPage == 1;
        this.lastPage = this.curPage >= this.totalPage;
    }

}
