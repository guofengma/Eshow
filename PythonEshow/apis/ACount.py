# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from control.CCount import CCount
from config.requests import apis_wrong

class ACount(Resource):
    def __init__(self):
        self.control_count = CCount()

    def get(self, counts):
        print "==================================================="
        print "api name is {0}".format(counts)
        print "==================================================="

        apis = {
            "get_all_by_time_brand": "self.control_count.get_all_by_time_brand()",
            "get_all_by_time_no": "self.control_count.get_all_by_time_no()",
            "get_num_by_time":"self.control_count.get_num_by_time()",
            "get_rnum_by_time":"self.control_count.get_rnum_by_time()"
        }
        if counts in apis:
            return eval(apis[counts])

        return apis_wrong