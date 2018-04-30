# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
from common.TransformToList import trans_params

class SProducts():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    def add_product(self, pname, pbrand, pno, psize):
        try:
            new_product = model.Products()
            pid = str(uuid.uuid4())
            new_product.Pid = pid
            new_product.Pname = pname
            new_product.Pbrand = pbrand
            new_product.Pno = pno
            new_product.Pnum = 0
            new_product.Psize = psize
            new_product.Pprice = 0
            new_product.Pstatus = 101
            self.session.add(new_product)
            self.session.commit()
            self.session.close()
            return pid
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
            self.session.close()
            return False

    def update_product_by_pid(self, pid, product):
        try:
            self.session.query(model.Products).filter_by(Pid=pid).update(product)
            self.session.commit()
            self.session.close()
            return True
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
            self.session.close()
            return False

    def get_product_by_pid(self, pid):
        product_abo = None
        try:
            product_abo = self.session.query(model.Products.Pid, model.Products.Pname, model.Products.Pbrand,
                                             model.Products.Pno, model.Products.Psize, model.Products.Pstatus,
                                             model.Products.Pnum, model.Products.Pprice).filter_by(Pid=pid).first()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return product_abo

    def get_all_product(self):
        product_list = None
        try:
            product_list = self.session.query(model.Products.Pid, model.Products.Pname, model.Products.Pbrand,
                                             model.Products.Pno, model.Products.Psize, model.Products.Pstatus,
                                             model.Products.Pnum, model.Products.Pprice).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return product_list

    def get_pid_by_pno(self, pno):
        pid = None
        try:
            pid = self.session.query(model.Products.Pid).filter_by(Pno=pno).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pid

    @trans_params
    def get_pid_by_pbrand(self, pbrand):
        pid = None
        try:
            pid = self.session.query(model.Products.Pid).filter_by(Pbrand=pbrand).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pid

    @trans_params
    def get_pid_by_pno(self, pno):
        pid = None
        try:
            pid = self.session.query(model.Products.Pid).filter_by(Pno=pno).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pid

    def get_pnum_by_pid(self, pid):
        pnum = None
        try:
            pnum = self.session.query(model.Products.Pnum).filter_by(Pid=pid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pnum