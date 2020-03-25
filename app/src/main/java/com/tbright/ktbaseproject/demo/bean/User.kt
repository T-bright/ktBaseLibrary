package com.tbright.ktbaseproject.demo.bean


class User {
    var userType: Int = 0  //1：老师；2：学生；4：家长
    var userId: String? = null //用户id
    var userName: String? = null //用户姓名
    var loginName : String? = null//登录名
    var mobile: String? = null //用户手机
    var avatar: String? = null //用户头像url
    var description: String? = null //用户简介
    var schools: List<School>? = null //用户学校
    var familyRelation: String? = null  //与家长关系 关系Id, 在数据字典以此为key查询"
    var children: List<User>? = null //孩子信息
    var parents: List<User>? = null //用户与孩子关系 没有则为""
    var institutions: List<Institution>? = null //用户机构
    var wechatUnionId: String? = null //用户微信绑定id
    var qqUnionId: String? = null //用户qq绑定id

    val isTeacher get() = userType == TEACHER
    val isStudent get() = userType == STUDENT
    val isParent get() = userType == PARENT

    var userTypeString: String? = null
        get() {
            return when (userType) {
                TEACHER -> "老师"
                STUDENT -> "学生"
                PARENT -> "家长"
                else -> "无"
            }
        }

    companion object {
        const val TEACHER = 1
        const val STUDENT = 2
        const val PARENT = 4

        const val WECHAT = 1
        const val QQ = 2
    }


}