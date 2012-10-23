package com.pfalabs.tiny.features

trait FeatureProvider {

  def getFeature(name: String): Feature;

}