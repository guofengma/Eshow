# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
import datetime
from common.TransformToList import trans_params

class SPurchaseOrders():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    def new_purchaseOrder(self, poid, postatus, poprice):
        try:
            PurchaseOrder = model.PurchaseOrders()
            PurchaseOrder.POid = poid
            PurchaseOrder.POstatus = postatus
            PurchaseOrder.POprice = poprice
            PurchaseOrder.POtime = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
            self.session.add(PurchaseOrder)
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

    def new_poitem(self, poid, pid, pnum):
        try:
            POitem = model.POitems()
            POitem.POIid = str(uuid.uuid4())
            POitem.POid = poid
            POitem.Pid = pid
            POitem.Pnum = pnum
            self.session.add(POitem)
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

    def get_purchase_by_poid(self, poid):
        order = None
        try:
            order = self.session.query(model.PurchaseOrders.POid, model.PurchaseOrders.POtime, model.PurchaseOrders.POprice,
                                       model.PurchaseOrders.POstatus).filter_by(POid=poid).first()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return order

    def get_porderitem_by_poid(self, poid):
        orderitem = None
        try:
            orderitem = self.session.query(model.POitems.POIid, model.POitems.Pnum,
                                           model.POitems.Pid).filter_by(POid=poid).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return orderitem

    def get_all(self):
        order_list = None
        try:
            order_list = self.session.query(model.PurchaseOrders.POid, model.PurchaseOrders.POtime, model.PurchaseOrders.POprice,
                                       model.PurchaseOrders.POstatus).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return order_list

    def get_poid_by_pid(self, pid):
        oid_list = None
        try:
            oid_list = self.session.query(model.POitems.POid, model.POitems.Pnum).filter_by(Pid=pid).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return oid_list

    def get_potime_by_poid(self, oid):
        otime = None
        try:
            otime = self.session.query(model.PurchaseOrders.POtime).filter_by(POid=oid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return otime

    @trans_params
    def get_poid_by_time(self, potime):
        poid = None
        try:
            poid = self.session.query(model.PurchaseOrders.POid).filter(model.PurchaseOrders.POtime.like("{0}%".format(potime))).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return poid

    @trans_params
    def get_pnum_by_poid(self, poid):
        pnum = None
        try:
            pnum = self.session.query(model.POitems.Pnum).filter_by(POid=poid).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pnum

if __name__ == '__main__':
    a = SPurchaseOrders()
    print a.get_poid_by_time('20180427')