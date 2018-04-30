# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from config.requests import apis_wrong, param_miss

class AUsers(Resource):
    def __init__(self):
        from control.CUsers import CUsers
        self.cusers = CUsers()

    def post(self, users):
        print "==================================================="
        print "api name is {0}".format(users)
        print "==================================================="

        apis = {
            "login": "self.cusers.login()"
        }

        if users not in apis:
            return apis_wrong
        return eval(apis[users])