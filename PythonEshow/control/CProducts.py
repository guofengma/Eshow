# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import json
from config.requests import param_miss, system_error, response_ok

class CProducts():
    def __init__(self):
        from service.SProducts import SProducts
        self.sproducts = SProducts()

    def new_product(self):
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)

        if "Pname" not in data or "Pbrand" not in data or "Psize" not in data or "Pno" not in data:
            return param_miss

        new_product = self.sproducts.add_product(data["Pname"], data["Pbrand"], data["Pno"], data["Psize"])

        if not new_product:
            return system_error

        response_ok["data"] = {}
        response_ok["data"]["Pid"] = new_product

        return response_ok

    def delete_product(self):
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)

        if "Pid" not in data:
            return param_miss

        Pid = data["Pid"]
        product = {}
        product["Pid"] = data["Pid"]
        product["Pstatus"] = 102

        update_status = self.sproducts.update_product_by_pid(Pid, product)

        if not update_status:
            return system_error

        response_ok["data"] = {}

        return response_ok

    def get_abo(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="

        if "Pid" not in args:
            return param_miss
        Pid = args["Pid"]

        product_abo = self.sproducts.get_product_by_pid(Pid)
        data = {}
        data["Pid"] = product_abo.Pid
        data["Pname"] = product_abo.Pname
        data["Pbrand"] = product_abo.Pbrand
        data["Pno"] = product_abo.Pno
        data["Psize"] = product_abo.Psize
        data["Pnum"] = product_abo.Pnum
        data["Pprice"] = product_abo.Pprice
        data["Pstatus"] = product_abo.Pstatus

        response_ok["data"] = data

        return response_ok

    def get_all(self):
        product_list = self.sproducts.get_all_product()
        data = []
        for row in product_list:
            data_item = {}
            data_item["Pid"] = row.Pid
            data_item["Pname"] = row.Pname
            data_item["Pbrand"] = row.Pbrand
            data_item["Pno"] = row.Pno
            data_item["Psize"] = row.Psize
            data_item["Pnum"] = row.Pnum
            data_item["Pprice"] = row.Pprice
            data_item["Pstatus"] = row.Pstatus
            data.append(data_item)

        response_ok["data"] = data

        return response_ok