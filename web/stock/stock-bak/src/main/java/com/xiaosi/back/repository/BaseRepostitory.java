package com.xiaosi.back.repository;

import com.xiaosi.back.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @description 自己封装的带有逻辑删除的Repostitory基类，entity也必须继承BaseEntry。
 * @ClassName: BaseRepostitory
 * @author: 郭秀志 jbcode@126.com
 * @date: 2020/7/14 17:33
 * @Copyright:
 */
@NoRepositoryBean
public interface BaseRepostitory<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.deleted = 0")
    List<T> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = 0")
    List<T> findAllById(Iterable<ID> iterable);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = 0")
    T getOne(ID id);

    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where e.deleted = 0")
    long count();

    @Override
    @Transactional(readOnly = true)
    default boolean existsById(ID id) {
        return getOne(id) != null;
    }

    @Query("update #{#entityName} e set e.deleted = 1 where e.id = ?1")
    @Transactional
    @Modifying
    void logicDelete(ID id);

    @Transactional
    default void logicDelete(T entity) {
        logicDelete((ID) entity.getId());
    }

    @Transactional
    default void logicDelete(Iterable<? extends T> entities) {
        entities.forEach(entity -> logicDelete((ID) entity.getId()));
    }

    @Query("update #{#entityName} e set e.deleted = 1 ")
    @Transactional
    @Modifying
    void logicDeleteAll();
}
