package com.pfalabs.tiny.dao

import scala.reflect.BeanProperty

import javax.persistence.{ Column, Entity, Id, Table }

@Entity
@Table(name = "tokens")
class Token extends Serializable {

  @Id
  @Column
  @BeanProperty
  var id: Int = _

  @BeanProperty
  @Column(length = 150, nullable = false)
  var token: String = null.asInstanceOf[String];

}