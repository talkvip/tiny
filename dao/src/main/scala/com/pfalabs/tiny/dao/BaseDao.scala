package com.pfalabs.tiny.dao

trait BaseDao[T <: Serializable, V] {

  def find(id: V): Option[T];

  def persist(t: T);

  def persistAll(list: List[T]);

  def persistAllWithFlush(list: List[T]);

  def merge(obj: T): T;

  def mergeWithFlush(obj: T);

  def remove(obj: T);

  def removeId(id: V);

  def getList(): List[T];

  def getList(page: Int, count: Int): List[T];

  def getCount(): Long;

}