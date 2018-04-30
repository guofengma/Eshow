# *- coding:utf8 *-

apis_wrong = {
    "status": 405,
    "status_code": 405000,
    "messages": "找不到对应的api"
}

system_error = {
    "status": 404,
    "messages": "系统异常"
}

no_tel = {
    "status": 405,
    "status_code": 405100,
    "messages": "用户名错误"
}

wrong_pwd = {
    "status": 405,
    "status_code": 405101,
    "messages": "密码错误"
}

login_ok = {
    "status": 200,
    "messages": "登录成功"
}

response_ok = {
    "status": 200,
    "messages": "获取数据成功"
}

param_miss = {
    "status": 405,
    "status_code": 405001,
    "messages": "参数缺失"
}

no_permission = {
    "status": 405,
    "status_code": 405201,
    "messages": "没有权限"
}