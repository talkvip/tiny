package com.pfalabs.tiny.dao

import org.junit.{ AfterClass, Test, Before }
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import junit.framework.Assert._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("/applicationContext.xml"))
class TokenDaoTest {

  @Autowired
  var dao: TokenDao = null;

  @Before
  def before() {
    dao.getList().foreach(dao.remove(_));
    assertEquals(0, dao.getList().size);
  }

  @Test
  def testCreate() {

    val tokenIn = new Token();
    tokenIn.token = "test";
    val token = dao.persist(tokenIn);

    val f = dao.find(tokenIn.id).get;
    assertEquals(tokenIn.token, f.token);

    f.token = "t2";
    dao.merge(f);

    val f2 = dao.find(f.id).get;
    assertEquals(f.token, f2.token);

    val l = dao.getList();
    assertFalse("getList should work ;)", l.isEmpty);
    assertEquals(f.id, l.head.id);
    assertEquals(f.token, l.head.token);

  }
}