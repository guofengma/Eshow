# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import datetime
from config.requests import param_miss, system_error, response_ok
class CCount():
    def __init__(self):
        from service.SProducts import SProducts
        self.sproducts = SProducts()
        from service.SSaleOrders import SSaleOrders
        self.saleorders = SSaleOrders()
        from service.SPurchaseOrders import SPurchaseOrders
        self.spurchaseorders = SPurchaseOrders()

    def get_all_by_time_brand(self):
        list_brand = []
        product_list = self.sproducts.get_all_product()
        for row in product_list:
            if row.Pbrand not in list_brand:
                list_brand.append(row.Pbrand)

        data = []
        for ruw in list_brand:
            data_item = {}
            pid_list = self.sproducts.get_pid_by_pbrand(ruw)

            if not pid_list:
                return system_error
            month_sale = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            month_resale = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            month_purchase = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            for row in pid_list:
                oid_otime_list = self.saleorders.get_oid_by_pid(row)
                poid_potime_list = self.spurchaseorders.get_poid_by_pid(row)
                if oid_otime_list == None or poid_potime_list == None:
                    return system_error
                for raw in oid_otime_list:
                    otime = self.saleorders.get_otime_by_oid(raw.Oid)
                    print "======================otime==========================="
                    print otime
                    print "======================otime==========================="
                    month = datetime.datetime.strptime(otime, "%Y%m%d%H%M%S").month
                    if self.saleorders.get_ostatus_by_oid(raw.Oid) == 201:
                        month_sale[month] = raw.Pnum + month_sale[month]
                    elif self.saleorders.get_ostatus_by_oid(raw.Oid) == 202:
                        month_resale[month] = raw.Pnum + month_resale[month]
                for raw in poid_potime_list:
                    potime = self.spurchaseorders.get_potime_by_poid(raw.POid)
                    print "======================otime==========================="
                    print potime
                    print "======================otime==========================="
                    month = datetime.datetime.strptime(potime, "%Y%m%d%H%M%S").month
                    month_purchase[month] = raw.Pnum + month_purchase[month]
            # 一遍循环处理完某个品牌的销售数和退货数
            data_item["Pbrand"] = ruw
            data_item["Psalenum"] = []
            data_item["Presalenum"] = []
            data_item["Purchasenum"] = []
            i = 1
            while i < 13:
                sale_item = {}
                resale_item = {}
                purchase_item = {}
                sale_item["month"] = i
                resale_item["month"] = i
                purchase_item["month"] = i
                sale_item["num"] = month_sale[i]
                resale_item["num"] = month_resale[i]
                purchase_item["num"] = month_purchase[i]
                data_item["Psalenum"].append(sale_item)
                data_item["Presalenum"].append(resale_item)
                data_item["Purchasenum"].append(purchase_item)
                i = i + 1
            # 封装完毕一个品牌的body
            data.append(data_item)

        response_ok["data"] = data

        return response_ok

    def get_all_by_time_no(self):
        list_no = []
        product_list = self.sproducts.get_all_product()
        for row in product_list:
            if row.Pno not in list_no:
                list_no.append(row.Pno)

        data = []
        for ruw in list_no:
            data_item = {}
            pid_list = self.sproducts.get_pid_by_pno(ruw)

            if not pid_list:
                return system_error
            month_sale = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            month_resale = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            month_purchase = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            for row in pid_list:
                oid_otime_list = self.saleorders.get_oid_by_pid(row)
                print "======================oid_otime_list==========================="
                print oid_otime_list
                print "======================oid_otime_list==========================="
                poid_potime_list = self.spurchaseorders.get_poid_by_pid(row)
                if oid_otime_list == None or poid_potime_list == None:
                    return system_error
                for raw in oid_otime_list:
                    otime = self.saleorders.get_otime_by_oid(raw.Oid)
                    print "======================otime==========================="
                    print otime
                    print "======================otime==========================="
                    month = datetime.datetime.strptime(otime, "%Y%m%d%H%M%S").month
                    if self.saleorders.get_ostatus_by_oid(raw.Oid) == 201:
                        month_sale[month] = raw.Pnum + month_sale[month]
                    elif self.saleorders.get_ostatus_by_oid(raw.Oid) == 202:
                        month_resale[month] = raw.Pnum + month_resale[month]
                for raw in poid_potime_list:
                    potime = self.spurchaseorders.get_potime_by_poid(raw.POid)
                    print "======================otime==========================="
                    print potime
                    print "======================otime==========================="
                    month = datetime.datetime.strptime(potime, "%Y%m%d%H%M%S").month
                    month_purchase[month] = raw.Pnum + month_purchase[month]
            # 一遍循环处理完某个品牌的销售数和退货数
            data_item["Pno"] = ruw
            data_item["Psalenum"] = []
            data_item["Presalenum"] = []
            data_item["Purchasenum"] = []
            i = 1
            while i < 13:
                sale_item = {}
                resale_item = {}
                purchase_item = {}
                sale_item["month"] = i
                resale_item["month"] = i
                purchase_item["month"] = i
                sale_item["num"] = month_sale[i]
                resale_item["num"] = month_resale[i]
                purchase_item["num"] = month_purchase[i]
                data_item["Psalenum"].append(sale_item)
                data_item["Presalenum"].append(resale_item)
                data_item["Purchasenum"].append(purchase_item)
                i = i + 1
            # 封装完毕一个品牌的body
            data.append(data_item)

        response_ok["data"] = data

        return response_ok