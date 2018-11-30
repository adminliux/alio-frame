@file:Suppress("UNCHECKED_CAST")

package ml.alio.frame.common.mybatis

import com.github.pagehelper.PageInfo
import ml.alio.frame.common.Paging
import ml.alio.frame.common.allFieldsSuperclass
import tk.mybatis.mapper.entity.Example
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.persistence.Id
import tk.mybatis.mapper.common.Mapper as TKMapper

interface Mapper<T : BaseEntity> : TKMapper<T>

fun <T : BaseEntity> time(record: T, update: Boolean = false) {
    val date = Date()
    if (!update) record.gmtDatetime = record.gmtDatetime ?: date
    record.uptDatetime = record.uptDatetime ?: date
}

inline fun <reified T> TKMapper<T>.newExample(): Example {
    val clazz = T::class.java
    return Example(clazz)
}

inline fun <reified T> TKMapper<T>.selectByByPrimaryKeys(id: List<*>): MutableList<T> {
    val clazz = T::class.java
    val newExample = newExample()
    newExample.createCriteria().andIn(clazz.primaryKey!!.name, id)
    return selectByExample(newExample)
}

@Suppress("UNCHECKED_CAST")
fun <T> TKMapper<in T>.insertORUpdateByPrimaryKeySelective(entity: T, clazz: Class<T>): Int {
    val primaryKey = clazz.primaryKey ?: throw RuntimeException("not fond primaryKey")
    return if (primaryKey.get(entity) == null) {
        insertSelective(entity)
    } else {
        val newInstance = clazz.newInstance()
        primaryKey.set(newInstance, primaryKey.get(entity))

        val selectCount = selectCount(newInstance)
        if (selectCount > 0) updateByPrimaryKeySelective(entity) else insertSelective(entity)
    }
}

val <T> Class<T>.primaryKey: Field?
    get() {
        for (f in this.allFieldsSuperclass) {
            f.getAnnotation(Id::class.java)?.let {
                f.isAccessible = true
                return f
            }
        }
        return null
    }

fun <T : BaseEntity> BaseService<in T>.insertORUpdateByPrimaryKeySelective(entity: T, clazz: Class<T>): Int {
    val primaryKey = clazz.primaryKey ?: throw RuntimeException("not fond primaryKey")
    return if (primaryKey.get(entity) == null) {
        insertSelective(entity)
    } else {
        val newInstance = clazz.newInstance()
        primaryKey.set(newInstance, primaryKey.get(entity))

        val selectCount = this.baseMapper.selectCount(newInstance)
        if (selectCount > 0) updateByPrimaryKeySelective(entity) else insertSelective(entity)
    }
}

fun <T : BaseEntity> BaseService<T>.selectByByPrimaryKeys(keys: List<Any>): List<T> {
    val example = Example(beanClass)
    example.createCriteria().andIn(BaseEntity::id.name, keys)
    return this.baseMapper.selectByExample(example)
}

val <T : BaseEntity> BaseService<in T>.beanClass: Class<T>
    get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>

fun <T : BaseEntity> BaseService<in T>.updateByByPrimaryKeys(keys: List<Any>, entity: T, example: Example): Int {
    val exampleCriteria = getExampleCriteria(example)
    exampleCriteria.andIn(BaseEntity::id.name, keys)
    return updateByExampleSelective(entity, example)
}

fun <T> TKMapper<T>.selectByExampleAndPage(example: Example, page: Paging) = {
    page.startPage<T>()
    PageInfo(selectByExample(example))
}.invoke()

fun <T : BaseEntity> BaseService<in T>.updateByByPrimaryKeys(keys: List<Any>, entity: T) = updateByByPrimaryKeys(keys, entity, Example(beanClass))
