package com.pfalabs.tiny.features

trait FeatureSubject {

  def isAdmin(): Boolean = false;

  def subjectId: Option[Int];

}