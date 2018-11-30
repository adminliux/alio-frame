package ml.alio.frame.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端分页
 *
 * @author 刘兴
 */
public class Paging {
    /**
     * 默认分页
     */
    public static int defaultPageSize = 20;

    /**
     * 页数
     */
    private Integer pageNum = 1;
    /**
     * 一页大小
     */
    private Integer pageSize = defaultPageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 自定义分页(不使用分页插件分页)
     */
    public void customInit() {
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = defaultPageSize;

        pageNum = (pageNum - 1) * pageSize;

        //针对第一个
        if (pageNum < 0) {
            pageNum = 0;
        }
    }

    /**
     * 获取分页
     *
     * @param request   请求对象
     * @param isDefault 参数错误是否取默认值
     * @return 分页对象
     */
    private static Paging getPage(HttpServletRequest request, boolean isDefault) {
        String pageSizeParam = request.getParameter("pageSize");
        String pageNumParam = request.getParameter("pageNum");
        Paging paging = new Paging();

        try {
            paging.setPageNum(Integer.parseInt(pageNumParam));
        } catch (Exception e) {
            if (!isDefault) {
                return null;
            }
        }
        try {
            paging.setPageSize(Integer.parseInt(pageSizeParam));
        } catch (Exception e) {
            if (!isDefault) {
                return null;
            }
        }
        return init(paging, isDefault);
    }

    public Paging(Integer pageNum, Integer pageSize) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 获取分页参数错误取默认值
     *
     * @param request 请求对象
     * @return 分页对象
     */
    public static Paging getPage(HttpServletRequest request) {
        return getPage(request, true);
    }


    /**
     * 分页初始化
     *
     * @param paging    分页
     * @param isDefault 参数错误是否取默认值
     * @return paging
     */
    public static Paging init(Paging paging, boolean isDefault) {
        if (paging == null) {
            return new Paging();
        }
        Integer pageSizeParam = paging.getPageSize(), pageNumParam = paging.getPageNum();

        if (pageSizeParam == null || pageNumParam == null) {
            if (!isDefault) {
                return null;
            } else {
                paging = new Paging();
                if (pageSizeParam != null) {
                    paging.setPageSize(pageSizeParam);
                }
                return paging;
            }
        }
        return paging;
    }

    /**
     * 分页初始化
     *
     * @param paging 分页
     * @return paging
     */
    public static Paging init(Paging paging) {
        return init(paging, true);
    }

    public Paging() {
    }
}
