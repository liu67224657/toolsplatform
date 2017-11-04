2015-08-31
1、适配cmsimage的页面 【完成】
2、适配UGC的wiki      【完成】
4、调整reids的缓存（现在是一个json的zset，无法修改，应该改成zset保存id，object保存对象，这样可以进行修改）【完成】
5、修改Htmlprase的结构。适应不同的程序。（通过urltype得到不同的工厂实现类）  【完成 50%后边随着业务在补充】
6、调整获取wikikey的代码（可能 wiki article 等获取方式不一样）   【完成 wiki和cms的】


2015-09-01
1、UrlFilter里数字站的逻辑
2、生成URL后要保存到url表中    【完成】
3、修改生成逻辑,如果url在url表中存在，通过ruleid直接后去rule 【完成】


2015-09-02
4、如何得到urlkey？是否需要去掉channel