redis的
	缓存穿透
		当Redis和mysql中都没有数据，别人一直使用两者都没的数据继续请求数据造成数据库压力巨大，数据库奔溃
	缓存击穿
		击穿，是在Redis中有当前key，当某一个时间当前热点key消失了，再次查询时找不到当前key，就会吧当前请求转到mysql上。导致mysql挂掉。
	缓存雪崩
		缓存雪崩是指缓存中数据大批量到过期时间，而查询数据量巨大，引起数据库压力过大甚至down机

redis 
    9大数据类型
        1.String
        2.Hash
        3.List
        4.Set
        5.SortedSet
        6.Bitmap
        7.HyperLogLog
        8.GEO
        9.Stream

redis 查看版本2种方式
    1.redis--server -v
    2.redis-cli 命令下的 info
redis里放一个序列化对象时有汉字，查看有乱码，怎么解决，首先出现问题的原因，是在客户端查看问题，可以使用redis-cli --raw 命令下查看