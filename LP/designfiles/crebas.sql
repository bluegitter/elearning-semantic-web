/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2010/12/19 10:14:21                          */
/*==============================================================*/


drop table if exists AuthorName;

drop table if exists Book;

drop table if exists Course;

drop table if exists CourseInfo;

drop table if exists CourseResourceInfo;

drop table if exists EConcept;

drop table if exists ELearner;

drop table if exists EResource;

drop table if exists OwlFile;

drop table if exists ResourceContent;

drop table if exists ResourceName;

drop table if exists ResourceType;

drop table if exists interest_concept;

drop table if exists interest_resource;

drop table if exists resource_concept;

/*==============================================================*/
/* Table: AuthorName                                            */
/*==============================================================*/
create table AuthorName
(
   resource_id          varchar(100) not null,
   author_name          varbinary(1000),
   primary key (resource_id)
);

/*==============================================================*/
/* Table: Book                                                  */
/*==============================================================*/
create table Book
(
   resource_id          varchar(100) not null,
   book_isbn            varchar(1000),
   book_publisher       varbinary(1000),
   book_author          varbinary(1000),
   primary key (resource_id)
);

/*==============================================================*/
/* Table: Course                                                */
/*==============================================================*/
create table Course
(
   resource_id          varchar(100),
   资源标题                 varchar(255),
   文件路径                 varchar(500)
);

/*==============================================================*/
/* Table: CourseInfo                                            */
/*==============================================================*/
create table CourseInfo
(
   课程编号                 varchar(255) not null,
   课程名称                 varchar(255),
   课程安装性质               varchar(255),
   课程运行位置               varchar(255),
   资源数量                 int,
   primary key (课程编号)
);

/*==============================================================*/
/* Table: CourseResourceInfo                                    */
/*==============================================================*/
create table CourseResourceInfo
(
   course_resource_id   varchar(255) not null,
   资源标题                 varchar(500),
   所属课程                 varchar(500),
   文件路径                 varchar(500),
   关键词                  varchar(500),
   作者                   varchar(500),
   学科1                  varchar(500),
   学科2                  varchar(500),
   所属知识点1               varchar(500),
   所属知识点2               varchar(500),
   资源描述                 varchar(500),
   媒体类型                 varchar(500),
   资源应用类型               varchar(500),
   文件格式                 varchar(500),
   primary key (course_resource_id)
);

/*==============================================================*/
/* Table: EConcept                                              */
/*==============================================================*/
create table EConcept
(
   concept_id           varchar(100) not null,
   concept_name         varchar(1000),
   primary key (concept_id)
);

/*==============================================================*/
/* Table: ELearner                                              */
/*==============================================================*/
create table ELearner
(
   elearner_id          varchar(100) not null,
   people_name          varchar(1000),
   grade                varchar(1000),
   password             varchar(1000),
   primary key (elearner_id)
);

/*==============================================================*/
/* Table: EResource                                             */
/*==============================================================*/
create table EResource
(
   resource_id          varchar(100) not null,
   resouce_type_id      varchar(100),
   resource_education_type varchar(100),
   resource_name        varchar(1000),
   resource_difficulty  varchar(1000),
   primary key (resource_id)
);

/*==============================================================*/
/* Table: OwlFile                                               */
/*==============================================================*/
create table OwlFile
(
   owl_id               varbinary(100) not null,
   owl_file             longblob,
   owl_date             date,
   primary key (owl_id)
);

/*==============================================================*/
/* Table: ResourceContent                                       */
/*==============================================================*/
create table ResourceContent
(
   resource_id          varchar(100) not null,
   resource_content     longblob,
   resource_uri         varbinary(1000),
   primary key (resource_id)
);

/*==============================================================*/
/* Table: ResourceName                                          */
/*==============================================================*/
create table ResourceName
(
   resource_id          varchar(100) not null,
   resource_name        varchar(1000),
   primary key (resource_id)
);

/*==============================================================*/
/* Table: ResourceType                                          */
/*==============================================================*/
create table ResourceType
(
   resouce_type_id      varchar(100) not null,
   resouce_type_name    varchar(1000),
   primary key (resouce_type_id)
);

/*==============================================================*/
/* Table: interest_concept                                      */
/*==============================================================*/
create table interest_concept
(
   elearner_id          varchar(100),
   concept_id           varchar(100),
   interestness         varbinary(1000)
);

/*==============================================================*/
/* Table: interest_resource                                     */
/*==============================================================*/
create table interest_resource
(
   elearner_id          varchar(100),
   resource_id          varchar(100),
   interestness         varbinary(1000)
);

/*==============================================================*/
/* Table: resource_concept                                      */
/*==============================================================*/
create table resource_concept
(
   resource_id          varchar(100),
   concept_id           varchar(100)
);

alter table AuthorName add constraint FK_resource_author foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table Book add constraint FK_book_resource foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table Course add constraint FK_FK_course_resource foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table EResource add constraint FK_education_type foreign key (resource_education_type)
      references ResourceType (resouce_type_id) on delete restrict on update restrict;

alter table EResource add constraint FK_resource_has_type foreign key (resouce_type_id)
      references ResourceType (resouce_type_id) on delete restrict on update restrict;

alter table ResourceContent add constraint FK_resource_content foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table ResourceName add constraint FK_resource_name foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table interest_concept add constraint FK_concept_interest_elearner foreign key (concept_id)
      references EConcept (concept_id) on delete restrict on update restrict;

alter table interest_concept add constraint FK_elearner_interest_concept foreign key (elearner_id)
      references ELearner (elearner_id) on delete restrict on update restrict;

alter table interest_resource add constraint FK_elearner_interest_resource foreign key (elearner_id)
      references ELearner (elearner_id) on delete restrict on update restrict;

alter table interest_resource add constraint FK_resource_interest_elearner foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

alter table resource_concept add constraint FK_concept_resource foreign key (concept_id)
      references EConcept (concept_id) on delete restrict on update restrict;

alter table resource_concept add constraint FK_resource_concept foreign key (resource_id)
      references EResource (resource_id) on delete restrict on update restrict;

