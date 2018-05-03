# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
import datetime
from common.TransformToList import trans_params

class SSaleOrders():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    def new_saleOrder(self, oid, ostatus, oprice):
        try:
            SaleOrder = model.SaleOrders()
            SaleOrder.Oid = oid
            SaleOrder.Ostatus = ostatus
            SaleOrder.Oprice = oprice
            SaleOrder.Otime = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
            self.session.add(SaleOrder)
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

    def new_soitem(self, oid, pid, pnum):
        try:
            SOitem = model.SOitems()
            SOitem.SOIid = str(uuid.uuid4())
            SOitem.Oid = oid
            SOitem.Pid = pid
            SOitem.Pnum = pnum
            self.session.add(SOitem)
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

    def get_order_by_oid(self, oid):
        order = None
        try:
            order = self.session.query(model.SaleOrders.Oid, model.SaleOrders.Otime, model.SaleOrders.Oprice,
                                       model.SaleOrders.Ostatus).filter_by(Oid=oid).first()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return order

    def get_orderitem_by_oid(self, oid):
        orderitem = None
        try:
            orderitem = self.session.query(model.SOitems.SOIid, model.SOitems.Pnum,
                                           model.SOitems.Pid).filter_by(Oid=oid).all()
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
            order_list = self.session.query(model.SaleOrders.Oid, model.SaleOrders.Otime, model.SaleOrders.Oprice,
                                       model.SaleOrders.Ostatus).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return order_list

    @trans_params
    def get_all_by_time_brand(self, oid, time_min, time_max):
        order_list = None
        try:
            order_list = self.session.query(model.SaleOrders.Oprice).filter(model.SaleOrders.Otime>time_min) \
                .filter(model.SaleOrders.Otime<time_max).filter_by(Oid=oid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return order_list

    def get_oid_by_pid(self, pid):
        oid_list = None
        try:
            oid_list = self.session.query(model.SOitems.Oid, model.SOitems.Pnum).filter_by(Pid=pid).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return oid_list

    def get_otime_by_oid(self, oid):
        otime = None
        try:
            otime = self.session.query(model.SaleOrders.Otime).filter_by(Oid=oid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return otime

    def get_ostatus_by_oid(self, oid):
        ostatus = None
        try:
            ostatus = self.session.query(model.SaleOrders.Ostatus).filter_by(Oid=oid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return ostatus

    @trans_params
    def get_oid_by_time_s(self, potime):
        poid = None
        try:
            poid = self.session.query(model.SaleOrders.Oid).filter_by(Ostatus=201).filter(
                model.SaleOrders.Otime.like("{0}%".format(potime))).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return poid

    @trans_params
    def get_oid_by_time_rs(self, potime):
        poid = None
        try:
            poid = self.session.query(model.SaleOrders.Oid).filter_by(Ostatus=202).filter(
                model.SaleOrders.Otime.like("{0}%".format(potime))).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return poid

    @trans_params
    def get_onum_by_poid(self, poid):
        pnum = None
        try:
            pnum = self.session.query(model.SOitems.Pnum).filter_by(Oid=poid).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return pnum