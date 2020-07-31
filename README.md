# OA系统后端项目
所有请求全部使用post
## 4.8日更新了需求 前面写的废弃
## 4.10日
- [x] 登录整合shiro
- [x] Post	/permissions  			获取所有权限
- [x] Post	/role  					创建角色（角色和角色对应的权限）
- [x] Post	/role/addPermission		角色-权限新增
- [x] Post	/role/delPermission		角色-权限删除
- [x] Post	/role/getRoles			获取所有角色
- [x] Post	/role/get				获取某个角色以及对应的权限
- [x] Post	/user/addRole			用户-角色配置/新增
- [x] Post	/user/delRole			用户-角色删除
- [x] Post	/user/getRoles			获取某个用户的所有角色
## 4.11日
- [x] Post	/user/addUsers		传用户对象数组新增用户
- [x] Post	/user/getUsers		获取用户列表 （多条件模糊查询）
- [x] Post	/user/login		学号密码登录
- [x] Post	/user/logout		退出
## 4.14日
- [x] Post	/team/add				创建小组（小组基本信息，父级和对应的用户）
- [x] Post	/team/addMember		添加成员
- [x] Post	/team/delMember		删除成员
- [x] Post	/team/update			修改小组基本信息
- [x] Post	/team/del				删除小组（小组和小组对应的成员逻辑上级联删除）
- [x] Post	/team/			获取某一个小组所有信息（基本信息，成员）
- [x] Post	/team/createdList		根据当前用户查找所有该用户创建的小组列表
- [x] Post	/team/joinedList  		根据当前用户查找所有该用户参加的小组列表
- [x] Post	/user/getUsers		获取用户列表 （多条件模糊查询）
## 4.18日
- [x] Post	/tag				创建标签（基本信息，父标签）
- [x] Post	/tag/update		修改标签
- [x] Post	/tag/del			删除标签, 一般不建议（实现优先级10）
- [x] Post	/tag/tags			根据用户查询标签列表（个人的私有和全部共有，和对应的父级，这是一个树形对象）
- [x] Post	/tag/createdTags	某个用户创建的标签（是一个列表，因为可能是树上的某几片叶子无法构成树）
# 4.21-4.24
- [x] Post	/thing				创建事务，追加事务（基本信息，接受人，投票，文件，标签）
- [x] Post	/thing/createdList		创建的事务结果列表查询，每个事务阅读人数，回执人数等（查询条件传给后端，按条件查询后分页）
- [x] Post	/thing/created		（事务发送者）获取某个事务结果（阅读人数，回执人数，投票的统计，总人数），以及对应的receiver用户列表。
- [x] Post /thing/finish			事务接受者对一个进行事务回执。
- [x] Post	/thing/finish/get		（事务发送者/接受者）获取某个用户对事务的回执结果（包括回执的文件，投票详细信息）
- [x] Post	/thing/joinedList   	需要解决的事务列表（包含事务和事务完成情况）查询，
- [x] Post	/thing/get				事务接受者获取一个事务信息
- [x] Post	/thing/read			用户对一个事务已读
- [x] Post /thing/ifFInished		是否已经回复