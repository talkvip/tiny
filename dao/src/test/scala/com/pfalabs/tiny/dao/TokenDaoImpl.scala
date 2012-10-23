package com.pfalabs.tiny.dao

import com.pfalabs.tiny.dao.impl.AbstractJpaDao

class TokenDaoImpl extends AbstractJpaDao[Token, Int] with TokenDao {

  override def clazz(): Class[Token] = classOf[Token];

}