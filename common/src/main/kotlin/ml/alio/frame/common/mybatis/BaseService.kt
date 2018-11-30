package ml.alio.frame.common.mybatis

import com.github.pagehelper.page.PageMethod.startPage
import ml.alio.frame.common.Paging
import tk.mybatis.mapper.entity.Example


interface BaseService<T : BaseEntity> {

    val baseMapper: Mapper<T>

    fun insert(record: T): Int

    fun updateByPrimaryKeySelective(record: T): Int

    fun insertSelective(record: T): Int

    fun updateByPrimaryKey(record: T): Int

    fun updateByExampleSelective(record: T, example: Any?): Int

    fun updateByExample(record: T, example: Any?): Int


    fun getExampleCriteria(example: Example): Example.Criteria {
        val criteriaList = example.oredCriteria
        return if (criteriaList.isEmpty()) {
            example.createCriteria()
        } else criteriaList[0]
    }

    private fun merge(t: T, example: Example, selective: Boolean = true): Example {
        val exampleCriteria = getExampleCriteria(example)
        val entityAttrMap = EntityUtils.getEntityAttrMap(t, true, true)
        for (attr in entityAttrMap.entries) {
            val value = attr.value
            fun add() {
                exampleCriteria.andCondition(attr.key + "=", attr.value)
            }
            if (value == null) {
                if (!selective) add()
            } else add()
        }
        return example
    }

    fun selectSelective(entity: T, example: Example) = baseMapper.selectByExample(merge(entity, example)) ?: listOf()
}

fun <E> Paging.startPage() = startPage<E>(this.pageNum, this.pageSize)!!
