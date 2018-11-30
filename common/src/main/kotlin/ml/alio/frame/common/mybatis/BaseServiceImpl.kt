package ml.alio.frame.common.mybatis

import org.springframework.beans.factory.annotation.Autowired

abstract class BaseServiceImpl<T : BaseEntity> : BaseService<T> {
    @Autowired
    override lateinit var baseMapper: Mapper<T>

    override fun insert(record: T): Int {
        time(record)
        return baseMapper.insert(record)
    }

    override fun updateByPrimaryKeySelective(record: T): Int {
        time(record, true)
        return baseMapper.updateByPrimaryKeySelective(record)
    }

    override fun insertSelective(record: T): Int {
        time(record)
        return baseMapper.insertSelective(record)
    }

    override fun updateByPrimaryKey(record: T): Int {
        time(record, true)
        return updateByPrimaryKey(record)
    }

    override fun updateByExampleSelective(record: T, example: Any?): Int {
        time(record, true)
        return baseMapper.updateByExampleSelective(record, example)
    }

    override fun updateByExample(record: T, example: Any?): Int {
        time(record, true)
        return baseMapper.updateByExample(record, example)
    }
}
