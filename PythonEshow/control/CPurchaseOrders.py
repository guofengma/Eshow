# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import json
import uuid
from config.requests import param_miss, system_error, response_ok
class CPurchaseOrders():
    def __init__(self):
        from service.SPurchaseOrders import SPurchaseOrders
        self.spurchaseorder = SPurchaseOrders()
        from service.SProducts import SProducts
        self.sproducts = SProducts()

    def new_purchaseorder(self):
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

        if "POstatus" not in data or "POprice" not in data or "POrder_items" not in data or "POrder_items" == []:
            return param_miss

        POid = str(uuid.uuid4())

        add_purchaseOrder = self.spurchaseorder.new_purchaseOrder(POid, data["POstatus"], data["POprice"])

        if not add_purchaseOrder:
            return system_error

        for row in data["POrder_items"]:
            if "Pno" not in row or "Pnum" not in row or "Pbrand" not in row or "Psize" not in row:
                return param_miss

            Pid = self.sproducts.get_pid_by_pno(row["Pno"], row["Pbrand"], row["Psize"])
            if not Pid:
                return system_error
            add_poitem = self.spurchaseorder.new_poitem(POid, Pid, row["Pnum"])
            if not add_poitem:
                return system_error
            pnum = self.sproducts.get_pnum_by_pid(Pid)
            pnum = pnum + row["Pnum"]
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
        if "POid" not in args:
            return param_miss

        POid = args["POid"]
        order_abo = self.spurchaseorder.get_purchase_by_poid(POid)
        if not order_abo:
            return system_error
        data = {}
        data["POid"] = order_abo.POid
        data["POtime"] = order_abo.POtime
        data["POstatus"] = order_abo.POstatus
        data["POprice"] = order_abo.POprice
        data["POrder_items"] = []
        order_items = self.spurchaseorder.get_porderitem_by_poid(POid)
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
            order_item["Pno"] = product.Pno
            data["POrder_items"].append(order_item)

        response_ok["data"] = data
        return response_ok

    def get_all(self):
        order_list = self.spurchaseorder.get_all()
        if not order_list:
            return system_error
        data = []
        for row in order_list:
            print "======================row==========================="
            print row
            print "======================row==========================="
            data_item = {}
            data_item["POid"] = row.POid
            data_item["POtime"] = row.POtime
            data_item["POstatus"] = row.POstatus
            data_item["POprice"] = row.POprice
            data_item["POrder_items"] = []
            order_items = self.spurchaseorder.get_porderitem_by_poid(row.POid)
            if not order_items:
                return system_error
            for raw in order_items:
                print "======================raw==========================="
                print raw
                print "======================raw==========================="
                order_item = {}
                product = self.sproducts.get_product_by_pid(raw.Pid)
                if not product:
                    return system_error
                order_item["Pnum"] = raw.Pnum
                order_item["Pname"] = product.Pname
                order_item["Pbrand"] = product.Pbrand
                order_item["Psize"] = product.Psize
                data_item["POrder_items"].append(order_item)
            data.append(data_item)

        response_ok["data"] = data
        return response_ok