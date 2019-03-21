package xin.marcher.blog.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页工具
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class PageUtil {

    /**
     * 总数据数
     */
    private Integer totalRow;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 每页数据数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer curPage;

    /**
     * 是否首页
     */
    private Boolean firstPage;

    /**
     * 是否末页
     */
    private Boolean lastPage;

    public PageUtil() {
    }

    /**
     * 分页
     *
     * @param totalRow  总记录数
     * @param queryPage 分页参数
     */
    public PageUtil(Integer totalRow, QueryPage queryPage) {
        this.pageSize = queryPage.getLimit();
        this.curPage = queryPage.getCurPage();
        this.totalRow = totalRow;
        this.totalPage = (int) Math.ceil((double) this.totalRow / this.pageSize);
        this.firstPage = this.curPage == 1;
        this.lastPage = this.curPage >= this.totalPage;
    }

}
