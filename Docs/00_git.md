
# 如何将项目同时推送至[Gitee](https://www.gitee.com)与[Github](https://www.github.com)：多平台同步代码仓库的实践教程

## 引言
在开源社区中，开发者经常需要将自己的项目托管在多个代码托管平台，以便于不同用户群体访问和贡献。国内常用的Gitee和国际流行的GitHub就是这样的两个平台。本文将详细介绍如何配置本地Git环境，实现在一次提交后，代码能自动推送到Gitee和GitHub两个代码托管平台。

## 1. 初始化本地Git仓库
首先，确保你的项目已经在本地创建了Git仓库：
```shell
cd my_project
git init
```

## 2. 添加远程仓库

### 2.1 添加Gitee远程仓库
登录Gitee网站，创建一个新的仓库（或者使用已有的），获取其SSH地址。然后，在本地项目根目录下执行以下命令：
```shell
git remote add gitee git@gitee.com:weijunfu/springboot3.git
```

### 2.2 添加Github远程仓库
同样地，在Github上创建或选择一个仓库，并获取其SSH地址。然后执行以下命令：
```shell
git remote add github git@github.com:weijunfu/springboot3.git
```

至此，你已经分别设置了名为gitee和github的两个远程仓库。

## 3.配置SSH秘钥（如果尚未配置）

为了无密码同时向两个平台推送代码，你需要分别生成并添加SSH公钥到Gitee和GitHub账户中。
具体步骤如下：
+ 在本地生成SSH密钥对
```shell
ssh-keygen -t rsa -C "ijunfu@163.com" # 生成SSH密钥对, 修改成自己的邮箱
```
+ 将生成的~/.ssh/id_rsa.pub内容分别添加到Gitee和GitHub的SSH密钥设置中。

## 4.推送代码至远程仓库
完成上述步骤后，你就可以在本地项目根目录下执行以下命令，将代码推送至Gitee和GitHub两个远程仓库：
```shell
# 添加所有文件到暂存区
git add .

# 提交所有修改到本地仓库, 附带提交信息
git commit -m "first commit"

# 移动/重命名当前分支，保持分支名称统一
git branch -M main

# 分别向Gitee和Github推送主分支
git push -u gitee main
git push -u github main
```
