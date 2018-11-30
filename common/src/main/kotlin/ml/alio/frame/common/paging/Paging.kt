package ml.alio.frame.common.paging

/**
 * @author Lix@jchvip.com
 * @date 2018/6/6 14:53
 */
class Page(var totalNum: Int = 0,//总页数
           var totalPage: Int = 0,//总页数
           var pageSize: Int = 0,//每页条数
           var pageIndex: Int = 0,//当前页码
           var queryIndex: Int = 0)//当前页从第几条开始查

/**
 * 计算页玛
 * @param totalNum 总条数字
 * @param pageSize 一页大小
 * @param pageIndex 页玛
 */
fun pagination(totalNum: Int, pageSize: Int, pageIndex: Int): Page {
    val page = Page()
    page.totalNum = totalNum
    val totalPage = if (totalNum % pageSize == 0) totalNum / pageSize else totalNum / pageSize + 1
    page.totalPage = totalPage
    page.pageIndex = pageIndex + 1
    page.pageSize = pageSize
    page.queryIndex = pageSize * pageIndex
    return page
}

/**
 * 集合分页
 * @param pageSize 一页大小
 * @param pageIndex 页玛
 */
inline fun <T> List<T>.page(pageSize: Int, pageIndex: Int, body: (page: Page, sub: List<T>) -> Unit) {
    val size = this.size
    val pagination = pagination(size, pageSize, pageIndex)

    val fromIndex = pagination.queryIndex
    val toIndex: Int
    toIndex = if (fromIndex + pagination.pageSize >= size) {
        size
    } else {
        fromIndex + pagination.pageSize
    }
    body(pagination, if (fromIndex > toIndex) {
        listOf()
    } else this.subList(fromIndex, toIndex))
}
