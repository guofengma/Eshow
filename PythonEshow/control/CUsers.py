# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import json
from config.requests import param_miss, system_error
class CUsers():
    def __init__(self):
        from service.SUsers import SUsers
        self.susers = SUsers()

    def login(self):
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)
        if "Uname" not in data or "Upwd" not in data:
            return param_miss

        Uname = data["Uname"]
        list_uname = self.susers.get_all_user_name()

        if list_uname == False:
            return system_error

        if Uname not in list_uname:
            from config.requests import no_tel
            return no_tel

        upwd = self.susers.get_upwd_by_uname(Uname)
        if upwd != data["Upwd"]:
            from config.requests import wrong_pwd
            return wrong_pwd

        Uid = self.susers.get_uid_by_uname(Uname)

        from config.requests import login_ok
        login_ok["data"] = {}
        login_ok["data"]["Uid"] = Uid

        return login_ok