# 概念术语 #

## 本体 ##
  1. E\_Concept (概念)
  1. E\_EducationMaterial (教育资源)
    * AssessmentMaterial (教学评估类资源) 用来评测学习效果的学习资源
      * Exam (考试)
      * Exercise (练习)
    * LearningExperience (经验分享类资源) 用于学习者之间相互交流的资源
      * Blog (博客)
      * LearningNotes (学习笔记)
    * LearningMaterial (学习资源)
      * Book (书籍)
      * Example (示例)
      * Lecture (讲座)
      * LectureNotes (讲义)
      * Paper (论文)
      * ProjectAssignment (项目课程设计)
  1. E\_OptionalConceptRelationship （动态概念关系）
  1. E\_Organizations (组织)
    * Corporations (公司)
    * Publisher (出版社)
    * University (大学)
      * Chinese\_University (中国大学)
        * China\_211\_Project\_University (211工程大学)
        * China\_985\_Project\_University (985工程大学)
      * US\_University (美国大学)
        * US\_Ivy\_League (常青藤大学)
  1. E\_People (用户)
    * E\_Learner (学习者)
    * E\_Teacher （教师）
      * Associate\_Professor (副教授)
      * Associate\_Research\_Fellow (助理研究员)
      * Lecturer (讲师)
      * Professor (教授)
      * Research\_Fellow (研究员)
  1. E\_People\_Characters (用户特征)
    * E\_Performance (用户认知水平)
    * E\_Personal (用户个人性息)
    * E\_Interest (用户兴趣)
    * E\_Study\_Style (用户偏好学习风格)
    * E\_Security (用户安全信息)
    * E\_Portfolio (用户已学过的资源)
  1. E\_Resource (资源)
    * E\_Resource\_AUDIO (音频资源)
    * E\_Resource\_CODE (源代码)
    * E\_Resource\_DOCUMENT (文档类资源)
      * E\_Resource\_CAJ (CAJ格式资源)
      * E\_Resource\_DOC (DOC格式资源)
      * E\_Resource\_PDF (PDF格式资源)
      * E\_Resource\_TXT (TXT格式资源)
    * E\_Resource\_FLASH (FLASH资源)
    * E\_Resource\_IMAGE (图片资源)
    * E\_Resource\_PPT (PPT资源)
    * E\_Resource\_VIDEO (视频资源)

## 本体关系 ##
**concept\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_Concept | is\_part\_of | E\_Concept |
| E\_Concept | inverse\_of\_is\_part\_of | E\_Concept |
| E\_Concept | is\_post\_concept\_of | E\_Concept |
| E\_Concept | is\_pre\_concept\_of | E\_Concept |
| E\_Concept | is\_concept\_of\_I | E\_Interest |
| E\_Concept | is\_concept\_of\_P | E\_Performance |
| E\_Interest | inverse\_of\_is\_concept\_of\_I | E\_Concept |
| E\_Performance | inverse\_of\_is\_concept\_of\_P | E\_Concept |

**optinal\_concept\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_OptionalConceptRelationship | inverse\_of\_is\_post\_of | E\_Concept |
| E\_OptionalConceptRelationship | inverse\_of\_is\_pre\_of | E\_Concept |
| E\_Concept | is\_post\_of | E\_OptionalConceptRelationship |
| E\_Concept | is\_pre\_of  | E\_OptionalConceptRelationship |

**organization\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_People  | graduate\_from | University |
| University | is\_member\_of | China\_211\_Project\_University |
| University | is\_member\_of | China\_985\_Project\_University |
| University | is\_member\_of | US\_Ivy\_League |
| Publisher  | publish      | Book      |
| Book       | is\_published\_by | Publisher |
| E\_People  | studying\_at | University |
| E\_People  | working\_at  | University |
| E\_People  | working\_at  || Corporations|

**optinal\_concept\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_OptionalConceptRelationship | inverse\_of\_is\_post\_of | E\_Concept |
| E\_OptionalConceptRelationship | inverse\_of\_is\_pre\_of | E\_Concept |
| E\_Concept | is\_post\_of | E\_OptionalConceptRelationship |
| E\_Concept | is\_pre\_of  | E\_OptionalConceptRelationship |

**organization\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_People  | graduate\_from | University |
| University | is\_member\_of | China\_211\_Project\_University |
| University | is\_member\_of | China\_985\_Project\_University |
| University | is\_member\_of | US\_Ivy\_League |
| Publisher  | publish      | Book      |
| Book       | is\_published\_by | Publisher |
| E\_People  | studying\_at | University |
| E\_People  | working\_at  | University |
| E\_People  | working\_at  | Corporations |

**people\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_Learner | has\_interest | E\_Interest |
| E\_Interest | inverse\_of\_has\_interest | E\_Learner |
| E\_Learner | has\_performance | E\_Performance |
| E\_Interest | inverse\_of\_has\_performance | E\_Learner |
| E\_Learner | has\_portfolio | E\_Portfolio |
| E\_Portfolio | inverse\_of\_has\_portfolio | E\_Learner |
| E\_Learner | has\_study\_style | E\_Study\_Style |
| E\_Study\_Style | inverse\_of\_has\_study\_style | E\_Learner |

**people\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_Learner | has\_interest | E\_Interest |
| E\_Interest | inverse\_of\_has\_interest | E\_Learner |
| E\_Learner | has\_performance | E\_Performance |
| E\_Interest | inverse\_of\_has\_performance | E\_Learner |
| E\_Learner | has\_portfolio | E\_Portfolio |
| E\_Portfolio | inverse\_of\_has\_portfolio | E\_Learner |
| E\_Learner | has\_study\_style | E\_Study\_Style |
| E\_Study\_Style | inverse\_of\_has\_study\_style | E\_Learner |
| E\_People  | has\_personal| E\_Personal |
| E\_Learner | has\_security| E\_Security |
| E\_Concept | is\_recommended\_of\_c\_0 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_1 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_2 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_3 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_4 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_5 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_6 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_7 | E\_Learner |
| E\_Concept | is\_recommended\_of\_c\_8 | E\_Learner |
| E\_Resource | is\_recommended\_of\_r\_0 | E\_Learner |
| E\_Resource | is\_recommended\_of\_r\_1 | E\_Learner |
| E\_Resource | is\_recommended\_of\_r\_2 | E\_Learner |
| E\_Learner | is\_recommended\_of\_L\_0 | E\_Learner |
| E\_Learner | is\_recommended\_of\_L\_1 | E\_Learner |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_0 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_1 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_2 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_3 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_4 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_5 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_6 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_7 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_c\_8 | E\_Concept |
| E\_Learner | inverse\_of\_is\_recommended\_of\_r\_0 | E\_Resource |
| E\_Learner | inverse\_of\_is\_recommended\_of\_r\_1 | E\_Resource |
| E\_Learner | inverse\_of\_is\_recommended\_of\_r\_2 | E\_Resource |
| E\_Learner | inverse\_of\_is\_recommended\_of\_L\_0 | E\_Learner |
| E\_Learner | inverse\_of\_is\_recommended\_of\_L\_1 | E\_Learner |

**resource\_properties**
| **Domain** | **Property** | **Range** |
|:-----------|:-------------|:----------|
| E\_EducationMaterial | is\_application\_type\_of | E\_Resouce |
| E\_Resouce | inverse\_of\_is\_application\_type\_of | E\_EducationMaterial |
| E\_Resouce | is\_resource\_of\_C | E\_Concept |
| E\_Resouce | is\_resource\_of\_P | E\_Portfolio |
| E\_Concept | inverse\_of\_is\_resource\_of\_C | E\_Resource |
| E\_Portfolio | inverse\_of\_is\_resource\_of\_P | E\_Resource |

# 系统中术语 #