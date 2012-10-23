package com.pfalabs.tiny.features

class FeatureVerifier(provider: FeatureProvider) {

  def verify(f: Feature, s: FeatureSubject): Boolean = {
    if (s.isAdmin) {
      return true;
    }
    return false;
  }

}