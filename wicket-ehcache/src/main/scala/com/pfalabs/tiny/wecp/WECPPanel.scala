package com.pfalabs.tiny.wecp

import scala.collection.JavaConversions._
import org.apache.wicket.markup.html.panel.Panel
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Cache
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.markup.repeater.data.DataView
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.markup.repeater.data.ListDataProvider
import org.apache.wicket.markup.html.basic.Label
import net.sf.ehcache.Ehcache
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.apache.wicket.model.PropertyModel
import scala.math.BigDecimal
import org.apache.wicket.model.Model

class WECPPanel(id: String) extends Panel(id) {

  val cm = new LoadableDetachableModel[CacheManager]() {
    def load = CacheManager.ALL_CACHE_MANAGERS.head;
  }
  //TODO detatch model manually?

  add(new DataView[String]("clist", new ListDataProvider(cm.getObject().getCacheNames().toSeq.sorted)) {
    override def populateItem(item: Item[String]) =
      {
        val cmO = cm.getObject();
        val c: Ehcache = cmO.getEhcache(item.getModelObject());
        item.add(new Label("name", c.getName()));
        item.add(new Label("size", c.getKeysNoDuplicateCheck().size().toString));
        item.add(new Label("memsize", sizeInKb(c.calculateInMemorySize())));
        item.add(newRemoveLink(new LoadableDetachableModel[Ehcache]() {
          def load = cm.getObject().getEhcache(item.getModelObject());
        }));
      }
  });

  def newRemoveLink(c: IModel[Ehcache]): Link[Ehcache] = new Link[Ehcache]("remove", c) {
    override def onClick() = getModelObject().removeAll();
  }

  def sizeInKb(k: Long): String = BigDecimal(k)./(1024).setScale(1, BigDecimal.RoundingMode.HALF_UP).toString;

}