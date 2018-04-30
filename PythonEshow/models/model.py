# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, create_engine, Integer, String, Text, Float
from config import dbconfig as cfg
import pymysql

DB_PARAMS = "{0}://{1}:{2}@{3}/{4}?charset={5}".format(
    cfg.sqlenginename, cfg.username, cfg.password, cfg.host, cfg.database, cfg.charset)
mysql_engine = create_engine(DB_PARAMS, echo=True)
Base = declarative_base()

class Users(Base):
    __tablename__ = "Users"
    Uid = Column(String(64), primary_key=True)
    Uname = Column(String(64), nullable=False)
    Upwd = Column(String(64), nullable=False)

class Products(Base):
    __tablename__ = "Products"
    Pid = Column(String(64), primary_key=True)
    Pname = Column(String(128), nullable=False)
    Pbrand = Column(String(64), nullable=False)
    Pno = Column(String(64), nullable=False)
    Psize = Column(Integer, nullable=False)
    Pnum = Column(Integer, nullable=False)
    Pstatus = Column(Integer, nullable=False) # 101在售，102下架
    Pprice = Column(Float, nullable=False)

class SaleOrders(Base):
    __tablename__ = "SaleOrders"
    Oid = Column(String(64), primary_key=True)
    Otime = Column(String(14), nullable=False)
    Ostatus = Column(Integer, nullable=False) # 201销售单，202退货单
    Oprice = Column(Float, nullable=False)

class SOitems(Base):
    __tablename__ = "SOitems"
    SOIid = Column(String(64), primary_key=True)
    Oid = Column(String(64), nullable=False)
    Pid = Column(String(64), nullable=False)
    Pnum = Column(Integer, nullable=False)

class PurchaseOrders(Base):
    __tablename__ = "PurchaseOrders"
    POid = Column(String(64), primary_key=True)
    POtime = Column(String(14), nullable=False)
    POstatus = Column(Integer, nullable=False) # 301全部入库，302部分入库，303未收货
    POprice = Column(Float, nullable=False)

class POitems(Base):
    __tablename__ = "POitems"
    POIid = Column(String(64), primary_key=True)
    POid = Column(String(64), nullable=False)
    Pid = Column(String(64), nullable=False)
    Pnum = Column(Integer, nullable=False)

class databse_deal():
    def __init__(self):
        self.conn = pymysql.connect(host=cfg.host, user=cfg.username, passwd=cfg.password, charset=cfg.charset)
        self.cursor = self.conn.cursor()

    def create_database(self):
        sql = "create database if not exists {0} DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;".format(
            cfg.database)
        print sql
        try:
            self.cursor.execute(sql)
        except Exception, e:
            print(e)
        finally:
            self.conn_close()

    def drop_database(self):
        sql = "drop database if exists {0} ;".format(
            cfg.database)
        print sql
        try:
            self.cursor.execute(sql)
        except Exception, e:
            print(e)

        finally:
            self.conn_close()

    def conn_close(self):
        self.conn.close()


def create():
    databse_deal().create_database()
    Base.metadata.create_all(mysql_engine)


def drop():
    databse_deal().drop_database()


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    如果需要清除数据库，输入drop
    如果需要创建数据库 输入任意不包含drop的字符
    '''
    action = raw_input("create database?")
    if "drop" in action:
        drop()
    else:
        create()
