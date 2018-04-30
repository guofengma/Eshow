# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from control.CProducts import CProducts
from config.requests import apis_wrong

class AProducts(Resource):
    def __init__(self):
        self.control_product = CProducts()

    def post(self, products):
        print "==================================================="
        print "api name is {0}".format(products)
        print "==================================================="

        apis = {
            "new_product": "self.control_product.new_product()",
            "delete_product": "self.control_product.delete_product()"
        }

        if products in apis:
            return eval(apis[products])

        return apis_wrong

    def get(self, products):
        print "==================================================="
        print "api name is {0}".format(products)
        print "==================================================="

        apis = {
            "get_abo": "self.control_product.get_abo()",
            "get_all": "self.control_product.get_all()"
        }
        if products in apis:
            return eval(apis[products])

        return apis_wrong