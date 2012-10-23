package com.pfalabs.tiny.dao.impl

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pfalabs.tiny.dao.BaseDao;

import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
abstract class AbstractJpaDao[T <: Serializable, V] extends BaseDao[T, V] {

  @PersistenceContext
  protected var manager: EntityManager = null;

  def clazz(): Class[T];

  def clazzSelectName() = clazz().getSimpleName();

  def getEntityManager(): EntityManager = manager;

  def setEntityManager(em: EntityManager) {
    this.manager = em;
  }

  @Transactional(readOnly = true)
  override def find(id: V): Option[T] = {
    if (id == null) {
      return None;
    }
    val o: T = getEntityManager().find(clazz, id);
    if (o == null) {
      return None;
    }
    return Some(o);
  }

  @Transactional(readOnly = true)
  override def getList(): List[T] = List(getEntityManager().createQuery("from " + clazzSelectName)
    .getResultList().toArray: _*).asInstanceOf[List[T]];

  @Transactional(readOnly = true)
  override def getList(page: Int, count: Int): List[T] = List(getEntityManager().createQuery("from " + clazzSelectName)
    .setFirstResult((page - 1) * count).setMaxResults(count)
    .getResultList().toArray: _*).asInstanceOf[List[T]];

  @Transactional(readOnly = true)
  override def getCount(): Long = getEntityManager().createQuery("select count(*) from  " + clazzSelectName).getSingleResult().asInstanceOf[Long];

  protected def flush() = {
    getEntityManager().flush();
    getEntityManager().clear();
  }

  @Transactional
  override def persist(obj: T) = getEntityManager().persist(obj);

  @Transactional
  override def persistAll(l: List[T]) = {
    if (l != null) {
      l.foreach(getEntityManager().persist(_));
    }
  }

  @Transactional
  override def persistAllWithFlush(l: List[T]) = {
    if (l != null) {
      persistAll(l);
      flush();
    }
  }

  @Transactional(readOnly = false)
  override def remove(o: T) = if (getEntityManager.contains(o)) {
    getEntityManager.remove(o);
  } else {
    getEntityManager.remove(getEntityManager.merge(o));
  }

  @Transactional(readOnly = false)
  def removeId(id: V) = find(id) match {
    case Some(obj) ⇒ getEntityManager.remove(obj);
    case None ⇒ {}
  }

  @Transactional
  override def merge(obj: T) = getEntityManager.merge(obj);

  @Transactional
  override def mergeWithFlush(obj: T) = { getEntityManager.merge(obj); flush(); }

}