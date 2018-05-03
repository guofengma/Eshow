# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import json
import uuid
from config.requests import param_miss, system_error, response_ok
class CSaleOrders():
    def __init__(self):
        from service.SSaleOrders import SSaleOrders
        self.ssaleorders = SSaleOrders()
        from service.SProducts import SProducts
        self.sproducts = SProducts()

    def new_saleorder(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="
        if "Uid" not in args:
            return param_miss

        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)

        if "Ostatus" not in data or "Oprice" not in data or "Order_items" not in data or "Order_items" == []:
            return param_miss

        Oid = str(uuid.uuid4())

        add_saleOrder = self.ssaleorders.new_saleOrder(Oid, data["Ostatus"], data["Oprice"])

        if not add_saleOrder:
            return system_error

        for row in data["Order_items"]:
            if "Pno" not in row or "Pnum" not in row or "Pbrand" not in row or "Psize" not in row:
                return param_miss

            Pid = self.sproducts.get_pid_by_pno(row["Pno"], row["Pbrand"], row["Psize"])
            print "=============================Pid====================="
            print Pid
            print "=============================Pid====================="
            if not Pid:
                return system_error
            add_soitem = self.ssaleorders.new_soitem(Oid, Pid, row["Pnum"])
            if not add_soitem:
                return system_error
            pnum = self.sproducts.get_pnum_by_pid(Pid)
            pnum = pnum - row["Pnum"]
            update_product = self.sproducts.update_product_by_pid(Pid, {"Pnum": pnum})
            if not update_product:
                return system_error

        response_ok["data"] = {}
        return response_ok

    def get_abo(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="
        if "Oid" not in args:
            return param_miss

        Oid = args["Oid"]
        order_abo = self.ssaleorders.get_order_by_oid(Oid)
        if not order_abo:
            return system_error
        data = {}
        data["Oid"] = order_abo.Oid
        data["Otime"] = order_abo.Otime
        data["Ostatus"] = order_abo.Ostatus
        data["Oprice"] = order_abo.Oprice
        data["Order_items"] = []
        order_items = self.ssaleorders.get_orderitem_by_oid(Oid)
        if not order_items:
            return system_error
        for row in order_items:
            order_item = {}
            product = self.sproducts.get_product_by_pid(row.Pid)
            if not product:
                return system_error
            order_item["Pnum"] = row.Pnum
            order_item["Pname"] = product.Pname
            order_item["Pbrand"] = product.Pbrand
            order_item["Psize"] = product.Psize
            data["Order_items"].append(order_item)

        response_ok["data"] = data
        return response_ok

    def get_all(self):
        order_list = self.ssaleorders.get_all()
        if not order_list:
            return system_error
        data = []
        for row in order_list:
            data_item = {}
            data_item["Oid"] = row.Oid
            data_item["Otime"] = row.Otime
            data_item["Ostatus"] = row.Ostatus
            data_item["Oprice"] = row.Oprice
            data_item["Order_items"] = []
            order_items = self.ssaleorders.get_orderitem_by_oid(row.Oid)
            if not order_items:
                return system_error
            for raw in order_items:
                order_item = {}
                product = self.sproducts.get_product_by_pid(raw.Pid)
                if not product:
                    return system_error
                order_item["Pnum"] = raw.Pnum
                order_item["Pname"] = product.Pname
                order_item["Pbrand"] = product.Pbrand
                order_item["Pno"] = product.Pno
                order_item["Psize"] = product.Psize
                data_item["Order_items"].append(order_item)
            data.append(data_item)

        response_ok["data"] = data
        return response_ok