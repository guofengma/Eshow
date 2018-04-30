# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from control.CPurchaseOrders import CPurchaseOrders
from config.requests import apis_wrong

class APurchaseOrders(Resource):
    def __init__(self):
        self.control_purchaseorder = CPurchaseOrders()

    def post(self, purchaseorders):
        print "==================================================="
        print "api name is {0}".format(purchaseorders)
        print "==================================================="

        apis = {
            "new_purchaseorder": "self.control_purchaseorder.new_purchaseorder()"
        }

        if purchaseorders in apis:
            return eval(apis[purchaseorders])

        return apis_wrong

    def get(self, purchaseorders):
        print "==================================================="
        print "api name is {0}".format(purchaseorders)
        print "==================================================="

        apis = {
            "get_abo": "self.control_purchaseorder.get_abo()",
            "get_all": "self.control_purchaseorder.get_all()"
        }
        if purchaseorders in apis:
            return eval(apis[purchaseorders])

        return apis_wrong