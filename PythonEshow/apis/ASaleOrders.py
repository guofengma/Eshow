# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from control.CSaleOrders import CSaleOrders
from config.requests import apis_wrong

class ASaleOrders(Resource):
    def __init__(self):
        self.control_saleorder = CSaleOrders()

    def post(self, saleorders):
        print "==================================================="
        print "api name is {0}".format(saleorders)
        print "==================================================="

        apis = {
            "new_saleorder": "self.control_saleorder.new_saleorder()"
        }

        if saleorders in apis:
            return eval(apis[saleorders])

        return apis_wrong

    def get(self, saleorders):
        print "==================================================="
        print "api name is {0}".format(saleorders)
        print "==================================================="

        apis = {
            "get_abo": "self.control_saleorder.get_abo()",
            "get_all": "self.control_saleorder.get_all()"
        }
        if saleorders in apis:
            return eval(apis[saleorders])

        return apis_wrong