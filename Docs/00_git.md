
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

## 3. 配置SSH秘钥（如果尚未配置）

为了无密码同时向两个平台推送代码，你需要分别生成并添加SSH公钥到Gitee和GitHub账户中。
具体步骤如下：
+ 在本地生成SSH密钥对
```shell
ssh-keygen -t rsa -C "ijunfu@163.com" # 生成SSH密钥对, 修改成自己的邮箱
```
+ 将生成的~/.ssh/id_rsa.pub内容分别添加到Gitee和GitHub的SSH密钥设置中。

## 4. 推送代码至远程仓库
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

此后，每次提交只需要重复最后一步的推送操作即可。

## 5. 一次推送到多个平台
如果你希望每次推送时自动推送至两个平台，。但这不是必须得，因为通常情况下我们会明确指定推送的目标。
例如：
```shell
# 新增一个名为origin的远程仓库（这里选择gitee仓库）
git remote add origin git@gitee.com:weijunfu/springboot3.git

# 为origin添加Github的推送地址
git remote set-url --add origin git@github.com:weijunfu/springboot3.git

# 查看配置的所有远程仓库地址
git remote -v

# 推送代码至两个远程仓库
git push --set-upstream origin main
```
这样，在执行git push时不加任何参数时，会自动将main分支推送到两个远程仓库。

## 6. 小结
通过以上步骤，我们成功实现了在一个项目中同时管理Gitee和GitHub的远程仓库，并能够在一次提交后，方便地将代码推送到两个平台。这对于跨平台分享项目、参与开源社区活动以及满足团队协作需求非常有帮助。