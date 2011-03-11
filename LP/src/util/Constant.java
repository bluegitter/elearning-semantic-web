package util;

public class Constant {

    /*******************************************************************
     * Jena Reasoning Config
     *********************************************************************/
    public static String NS = "http://www.owl-ontologies.com/e-learning.owl#";
    public static String NSRDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static String OWLFile = "files\\owl\\conceptsAndresource_RDF-XML.owl";
    public static String RulesFile = "files\\owl\\elearning2.rules";
    public static String LuceneTestFile = "files\\lucene\\testlucene.txt";
    public static String TestLogFile = "files\\log\\lplog.log";
    
    /*********************************************************************
     * SPARQL Prefix
     ********************************************************************/
    public static String RDFPRE = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
    public static String BASEPRE = "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> ";
    public static String RDFSPRE = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
    public static String XSDPRE = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ";
    public static String FNPRE = "PREFIX fn: <http://www.w3.org/2005/xpath-functions#> ";
    /********************************************************************
     * Database Connection Config
     ********************************************************************/
    public static String DBURL = "jdbc:mysql://localhost:3306/elearning";
    //public static String DBURL = "jdbc:mysql://192.168.8.86:3306/elearning"; //destop ip
    //public static String DBURL = "jdbc:mysql://192.168.9.161:3306/elearning"; 	//laptop ip
    public static String DBUSER = "ms";
    public static String DBPASSWORD = "ms";
    public static String ISCBSERVER250 = "http://e6.thss.tsinghua.edu.cn/";
    public static String ISCBSERVER48 = "http://zyk.thss.tsinghua.edu.cn/";
    public static String SERVERURL = "http://192.168.8.86:8080/myelearner";
    public static String SERVERTESTURL = "http://192.168.8.86:8080/myelearner/webapp/mylife.jpg";
    /**********************************************************************
     * To Be Used, but now useless....
     **********************************************************************/
    public static String[] FILE_FORMAT = {"text/txt", "text/doc", "text/css", "text/rtf", "text/html", "text/xml", "audio/aiff", "audio/mid", "audio/mp3", "audio/wma", "audio/wav", "image/bmp", "image/gif", "image/jpg", "image/pict", "image/tif", "image/png", "video/asf", "video/avi", "video/wmv", "video/mpeg", "video/rm", "vrml/wrl", "application/Excel", "application/MS powerpoint", "application/Acrobat", "application/Authorware", "application/x-javascript", "application/x-shockwave-flash", "application/x-compressed", "application/zip", "application/系统软件"};
    public static String[] RESOURCE_PUBLIC_LEVEL = {"完全公开", "注册公开", "收费公开"};
    public static String[] RESOURCE_QUALITY = {"优", "良", "中", "差"};
    public static String[] ERESOURCE_TYPES = {};
    public static String[] EDUCATION_TYPES = {};
    public static String[] COURSE_CLASSIFICATIONS = {"全部", "马克思主义理论类", "中文类", "历史类", "哲学类", "政治学类", "社会学类", "法律类", "经济类", "管理类", "旅游类", "新闻传播类", "艺术类", "教育类", "体育类", "外语类", "对外汉语类", "数学类", "物理类", "化学、化工与制药类", "地理、地学、地矿类", "环境类", "生物类", "心理学类", "医学类", "计算机类", "电子、电气信息类", "力学类", "土建、水利类", "机械、能源类", "轻工、纺织、食品类", "材料类", "农业、林业类", "服务类"};
    public static String[] ERESOURCE_APPLICATION_TYPES = {"All", "Glossary", "Experiment", "TheoreticalMetaKnowledge", "Example", "Exercise", "Case", "MethodicalMetaKnowledge", "Application", "Feather", "Composition", "Classification", "ExaminationPaper", "TeachingDesign", "TeachingSlide", "FAQ", "VIP", "Courseware", "Reference"};
    public static String[] ERESOURCE_APPLICATION_TYPES_CN = {"全部", "名词术语", "实验", "理论型知识元", "例题", "习题", "案例", "方法型知识元", "应用", "功能、性能", "结构、组成", "类别、分类", "试卷", "教学设计", "教学演示文稿", "常见问题", "重要人物", "教学课件", "文献资料"};
    public static String[] ERESOURCE_MEDIA_TYPES = {"全部", "文本", "图片", "视频", "音频", "动画", "程序"};
    public static String[] GENDER = {"male", "female", "secret"};
    public static String[] RECOMMANDRULES = {};
    public static String[] titles = {"professor"};
    public static String[] COURSE_NAMES = {"全部", "编译原理网络课程", "毛泽东思想概论网络课程", "马克思主义哲学原理网络课程", "法律基础网络课程", "大学物理网络课程", "房屋建筑学网络课程", "多媒体技术网络课程", "液压与气压传动网络课程(华中科大)", "土木工程制图与计算机绘图网络课程(同济大学)", "CAD/CAM技术网络课程(北理工)", "线性代数网络课程", "概率论与数理统计网络课程", "方剂学网络课程", "工科大学物理网络课程", "普通化学网络课程", "有机化学网络课程", "中医内科学", "分析化学网络课程", "化工基础网络课程", "医学微生物学网络课程", "财政学网络课程", "会计学网络课程", "市场营销学网络课程", "刑事诉讼法网络课程", "机械原理网络课程", "机械设计网络课程", "理论力学网络课程", "结构力学网络课程", "电子技术网络课程", "电磁场与电磁波网络课程", "临床生物化学网络课程", "分子生物学网络课程", "中医诊断学网络课程", "畜禽疾病防治学网络课程", "计算机软件技术基础网络课程", "物理学网络课程", "Internet与Wep技术概论网络课程", "高校教师教育技术培训网络课程", "世界古代史网络课程", "外国文学网络课程", "现代汉语网络课程", "大众传播学网络课程", "电脑广告设计与制作网络课程", "广告文案写作网络课程", "现代中国文学网络课程", "中国近现代史网络课程", "经济数学基础网络课程", "普通化学网络课程", "无机化学网络课程", "化工原理网络课程", "物理化学课程", "机械工程测试技术网络课程(华中)", "画法几何与工程制图网络课程", "土木工程施工网络课程", "机械设计基础网络课程", "CAD/CAM网络课程(东北大学)", "液压与气压传动网络课程(哈工大)", "士木工程材料网络课程", "土木类各种专业制图及计算机绘图", "交通运输概论网络课程", "材料成形技术基础网络课程", "结构力学网络课程", "通信原理网络课程", "模拟电子技术网络课程", "数字电子技术基础网络课程", "地理信息系统网络课程", "遥感概论网络课程络课程", "信号与系统网络课程", "广播电视测量技术网络课程", "计算机文化基础网络课程", "软件工程网络课程(东南)", "多媒体应用基础网络课程", "离散数学网络课程", "数据结构网络课程", "计算机组成原理网络课程", "软件工程网络课程(电子科技大)", "计算机体系结构网络课程", "政治经济学网络课程", "电子商务网络课程", "网页设计与制作网络课程", "弹性力学网络课程", "管理信息系统网络课程", "中医儿科学网络课程", "微型计算机技术网络课程", "人工智能网络课程(中南)", "信息资源的检索与利用网络课程", "图像处理和分析网络课程", "机械设计网络课程(西南交大)", "昆虫学网络课程", "当代世界经济与政治", "工程力学网络课程", "文科数学基础网络课程", "史学概论网络课程", "流体力学网络课程", "机电传动控制网络课程", "计算机硬件技术基础网络课程", "人工智能网络课程(浙大)", "信息检索与利用网络课程", "微机原理及应用网络课程", "计算机科学导论网络课程", "钢琴网络课程", "藏族古典文学", "中国现当代文学史", "民法学", "中国古代文学史", "中国哲学史", "财务管理", "投资学", "货币金融学", "马克思主义民族理论与政策", "信息资源共享", "体育与健康", "区域社会史导论", "毛泽东思想、邓小平理论和三个代表重要思想概论", "大学文化与成人之道"};
}
