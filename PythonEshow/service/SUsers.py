# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
from common.TransformToList import trans_params

class SUsers():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    @trans_params
    def get_all_user_name(self):
        all_name = None
        try:
            all_name = self.session.query(model.Users.Uname).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
        finally:
            self.session.close()

        return all_name

    def get_upwd_by_uname(self, uname):
        upwd = None
        try:
            upwd = self.session.query(model.Users.Upwd).filter_by(Uname=uname).scalar()
        except Exception as e:
            print(e.message)
        finally:
            self.session.close()
        return upwd

    def get_uid_by_uname(self, uname):
        uid = None
        try:
            uid = self.session.query(model.Users.Uid).filter_by(Uname=uname).scalar()
        except Exception as e:
            print(e.message)
        finally:
            self.session.close()
        return uid