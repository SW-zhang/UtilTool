个人工具类
    1, luecne
        入口：com.wang.lucene.LuceneCRUD 类提供索引的增删改查方法
        Item 为索引对象，可以随便定义字段。luceneconf.properties 可以定义检索字段和搜索结果排除字段（对应字段不写入到返回结果对象中）
    2, rabbitmq
        入口：
            生产者：com.wang.rabbitmq.SenderMsgUtil 类提供各种方式发送消息
            消费者：com.wang.rabbitmq.DefaultMessageReceiver
                   com.wang.rabbitmq.DirectMessageReceiver
                   com.wang.rabbitmq.SubscribeMessageReceiver
                   com.wang.rabbitmq.TopicMessageReceiver
            消费者使用方式见MainTest 示例
    3、redis
        工具类：com.wang.utils.redis.RedisUtil