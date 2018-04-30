# *- coding:utf8 *-
from flask import Flask
import flask_restful
from apis.AUsers import AUsers
from apis.AProducts import AProducts
from apis.ASaleOrders import ASaleOrders
from apis.APurchaseOrders import APurchaseOrders
from apis.ACount import ACount

app = Flask(__name__)
api = flask_restful.Api(app)

api.add_resource(AUsers, "/wxf/users/<string:users>")
api.add_resource(AProducts, "/wxf/products/<string:products>")
api.add_resource(ASaleOrders, "/wxf/saleorders/<string:saleorders>")
api.add_resource(APurchaseOrders, "/wxf/purchaseorders/<string:purchaseorders>")
api.add_resource(ACount, "/wxf/counts/<string:counts>")

if __name__ == '__main__':
    app.run('0.0.0.0', 8113, debug=True)