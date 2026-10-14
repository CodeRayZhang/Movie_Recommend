## 新版推荐系统：CineMatch

> [!IMPORTANT]
> 本项目已在独立仓库中完成现代化重写：**[CineMatch — Movie Recommendation System](https://github.com/CodeRayZhang/cinematch-recommender)**。
> 新版围绕电影发现、个性化推荐和私人片单持续完善。希望体验新版页面、运行系统或继续开发，请前往新版仓库。
>
> **访问说明：新版仓库目前为私有仓库，需要获得访问权限；未获授权时，GitHub 可能显示 404。**

新版主要能力：

- **可训练的混合推荐**：实现 ALS-WR 矩阵分解，结合内容偏好、邻域协同与口碑评分，并进行多样性重排；支持模型训练、启用、版本回退及导出。
- **推荐实验室**：展示观影偏好、模型状态和逐片推荐理由，提供评分引导、精选 / 探索模式、换一组及隐藏反馈。
- **完整观影流程**：电影搜索与组合筛选、详情与相似电影、个人中心、评分短评、想看 / 看过、自定义片单及管理后台。
- **现代技术栈**：Vue 3 + TypeScript + Vite、Spring Boot 4 + Java 21，配套 Spring Security、MyBatis、Flyway；支持 H2 本地运行和 MySQL / Docker Compose 部署。
- **数据与文档**：仓库附带 923 部电影资料和本地海报，提供部署步骤、MovieLens 数据准备与模型训练指南、算法评估及答辩材料。

**新版文档：** [部署与运行](https://github.com/CodeRayZhang/cinematch-recommender/blob/main/docs/deployment.md) · [模型训练](https://github.com/CodeRayZhang/cinematch-recommender/blob/main/docs/recommendation-models.md) · [算法介绍](https://github.com/CodeRayZhang/cinematch-recommender/blob/main/docs/recommendation.md) · [推荐效果评估](https://github.com/CodeRayZhang/cinematch-recommender/blob/main/docs/recommendation-model-evaluation.md) · [答辩 PPT 与讲稿](https://github.com/CodeRayZhang/cinematch-recommender/tree/main/docs/defense)

本仓库保留原毕业设计代码、架构图和部署说明，以下内容仍对应旧版。CineMatch 使用独立代码仓库和数据库，可单独运行，无需启动旧版 Spark / Hadoop 组件；旧账号、历史评分和旧 ALS 模型不会自动迁移，新版模型需按训练指南重新训练并启用。

---

# 基于Spark的电影推荐系统

本次项目是基于大数据过滤引擎的电影推荐系统--“懂你”电影网站，包含了爬虫、电影网站（前端和后端）、后台管理系统以及推荐系统（Spark）。

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/introduce.png)


## 一、爬虫
**开发环境：** pycharm + python3.6

**软件架构：** mysql + scrapy

**运行环境：** 本次爬取的内容在外网，所以需先翻墙后才能成功运行。

**项目架构：**

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/scrapy.png)


## 二、电影网站
**开发环境：** IntelliJ IDEA + maven + git + linux + powerdesigner

**软件架构：** mysql + mybatis + spring + springmvc

**项目描述：** 懂你电影推荐网站是一个基于SSM框架的web项目，类似当前比较流行的豆瓣。用户可以在网站上浏览电影信息和查询电影，并且网站会根据用户的浏览记录给用户进行实时的电影推荐。现已将网站部署在 http://115.159.204.68 网站上，感兴趣的朋友可以自行查看。Git的安装与IDEA和github的集成可以参考[博客](https://blog.csdn.net/u011254180/article/details/79857684)。

**项目架构：**

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/ssm.png)

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/web_1.png)


## 三、后台管理系统

**开发环境：** IntelliJ IDEA + maven + git + linux + powerdesigner

**软件架构：** mysql + mybatis + spring + springmvc + easyui

**项目描述：** 后台管理系统主要对用户信息和电影信息进行管理，如添加删除电影信息和完成用户信息的完善。其中为了更好地保存电影的图片信息，搭建了图片服务器，关于图片服务器FastDFS的搭建可参考[博客](https://blog.csdn.net/u011254180/article/details/79453775)。后台系统也布置在服务器上，感兴趣的朋友可以通过地址 http://115.159.204.68:8080/ 访问，为大家提供的测试账号为 test，密码为88888888。

**项目架构：**

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/admin_1.png)


## 四、推荐系统（Spark）

**开发环境：** IntelliJ IDEA + maven + git + linux

**软件架构：** hadoop + zookeeper + flume + kafka + nginx + spark + hive + mysql

**项目描述：** 通过在电影网站系统埋点，获取到用户的点击事件（如用户喜欢哪部电影或对某部电影的评分）并将信息传至推荐系统，推荐系统根据该信息做出相应的处理，将推荐结果存入到mysql数据库中，web前端通过查询数据库将推荐的电影展示给用户。推荐流程如下：

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/recommend_1.png)

**项目架构：**

![image](https://github.com/ZzXxL1994/Movie_Recommend/blob/master/zxl_picture/recommend_2.png)

**具体步骤：**

**1.服务器规划（linux镜像为centos6）：**

- spark1（ip 192.168.13.134），分配8G内存，4核
- spark2（ip 192.168.13.135），分配6G内存，4核
- spark3（ip 192.168.13.136），分配6G内存，4核


**2.电影数据集，[地址](https://grouplens.org/datasets/movielens/)：** 本次下载的为1m大小的数据集


**3.环境的搭建：**

1）[hdfs搭建](http://blog.csdn.net/u011254180/article/details/79377808)

- spark1上搭建namenode,secondary namenode,datanode
- spark2上搭建datanode
- spark3上搭建datanode

2）[yarn搭建](http://blog.csdn.net/u011254180/article/details/79377808)

- spark1上搭建resourcemanager,nodemanager
- spark2上搭建nodemanager
- spark3上搭建nodemanager

3）[mysql搭建](http://blog.csdn.net/u011254180/article/details/77893585),在spark2上搭建

4）[hive搭建](http://blog.csdn.net/u011254180/article/details/78068897),在spark1上搭建

5）[spark集群搭建](http://blog.csdn.net/u011254180/article/details/79381172)，搭建standalone模式，spark1为master，其他为worker


**4.数据的清洗：** （上传数据至hdfs中，[hdfs操作](http://blog.csdn.net/u011254180/article/details/79399422)）

1）启动 hdfs：  [root@spark1 ~]# start-dfs.sh 

2）启动 yarn：  [root@spark1 ~]# start-yarn.sh 

3）启动 mysql： [root@spark2 ~]# service mysqld start

4）启动 hive：  [root@spark1 ~]# hive --service metastore

5）启动 spark集群： [root@spark1 spark-1.6.1]# ./sbin/start-all.sh

6）代码(com.zxl.datacleaner.ETL)打包上传（[spark-sql与hive集成](http://blog.csdn.net/u011254180/article/details/79395227)）

- 代码位于 package com.zxl.datacleaner.ETL，打包为 ETL.jar
- 运行代码 spark-submit --class com.zxl.datacleaner.ETL --total-executor-cores 2 --executor-memory 2g lib/ETL.jar
- 成功于hive中建表


**5.数据的加工，** 根据ALS算法对数据建立模型([ALS论文](https://github.com/ZzXxL1994/Machine-Learning-Papers/tree/master/ALS))

1）启动 hdfs：  [root@spark1 ~]# start-dfs.sh

2）启动 yarn：  [root@spark1 ~]# start-yarn.sh

3）启动 mysql： [root@spark2 ~]# service mysqld start

4）启动 hive：  [root@spark1 ~]# hive --service metastore

5）启动 spark集群： [root@spark1 spark-1.6.1]# ./sbin/start-all.sh

6）代码(com.zxl.datacleaner.RatingData)打包上传，测试建立模型


**6.建立模型，** 根据RMSE(均方根误差)选取较好的模型

1）启动上述的服务

2）代码(com.zxl.ml.ModelTraining)打包上传，建立模型

注：com.zxl.ml.ModelTraining2中代码训练单个模型，其中参数 rank=50, iteration = 10, lambda = 0.01

- 代码位于 package com.zxl.ml.ModelTraining，打包为 Spark_Movie.jar
- 运行代码 spark-submit --class com.zxl.ml.ModelTraining lib/Spark_Movie.jar


**7.产生推荐结果**

1）启动上述的服务

2）代码(com.zxl.ml.Recommender)打包上传，产生推荐结果


**8.数据入库，** 存储为所有用户推荐的电影结果，mysql中存入的格式为(userid, movieid,rating)

1）启动上述的服务

2）代码(com.zxl.ml.RecommendForAllUsers)打包上传，数据入库

- 运行代码 spark-submit --class com.zxl.ml.RecommendForAllUsers --jars lib/mysql-connector-java-5.1.35-bin.jar lib/Spark_Movie.jar


**9.实时数据的发送**

1）[安装nginx](https://blog.csdn.net/u011254180/article/details/77897663)，用来接收电影网站上用户的点击信息，写入本地文件

2）[安装flume](https://blog.csdn.net/u011254180/article/details/80000763)，实时监控本地文件，将数据发送至kafka消息队列中


**10.实时数据的接收处理** ，如果打包到服务器运行错误，也可在本地IDEA上运行

1）[安装zookeeper](http://blog.csdn.net/u011254180/article/details/79480234)

2）[安装kafka](http://blog.csdn.net/u011254180/article/details/79481088)，用来接收发送数据

3）启动上述的服务

4）启动zookeeper：  [root@spark1 soft]# zkServer.sh start

4）启动flume：[root@spark1 flume]# bin/flume-ng agent -c ./conf/ -f conf/flume-conf.properties -Dflume.root.logger=DEBUG,console -n a1

5）启动kafka：  [root@spark1 kafka_2.11-0.10.1.0]# bin/kafka-server-start.sh config/server.properties

6）代码(com.zxl.datacleaner.PopularMovies2)运行，用于为没有登录或新用户推荐，默认推荐观看最多的5部电影

7）代码运行(需指定jar包 kafka-clients-0.10.1.0.jar)

- spark-submit --class com.zxl.streaming.SparkDrStreamALS --total-executor-cores 2 --executor-memory 1g --jars lib/kafka-clients-0.10.1.0.jar lib/Spark_Movie.jar
