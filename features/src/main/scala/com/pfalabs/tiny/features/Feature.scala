package com.pfalabs.tiny.features

trait Feature {

  def getName(): String;

  def getFilter(): String;

  // write

  def +(id: Int): Feature;

  def +(ids: List[Int]): Feature;

  def -(id: Int): Feature;

  def --(): Feature;

  def ++(): Feature;

}