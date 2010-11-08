/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2010-11-8 13:04:34                           */
/*==============================================================*/


drop table if exists Book;

drop table if exists Concept;

drop table if exists ELearner;

drop table if exists EducationMaterial;

drop table if exists Lecture;

drop table if exists Paper;

drop table if exists Resource;

drop table if exists ResourceType;

drop table if exists interest_concept;

drop table if exists interest_resource;

/*==============================================================*/
/* Table: Book                                                  */
/*==============================================================*/
create table Book
(
   education_material_id varchar(100),
   book_isbn            varchar(1000),
   book_name            varchar(1000),
   book_publisher       varbinary(1000)
);

/*==============================================================*/
/* Table: Concept                                               */
/*==============================================================*/
create table Concept
(
   concept_id           varchar(100) not null,
   concept_name         varchar(1000),
   concept_views        integer,
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
/* Table: EducationMaterial                                     */
/*==============================================================*/
create table EducationMaterial
(
   education_material_id varchar(100) not null,
   resource_id          varchar(100),
   education_material_name varchar(1000),
   education_material_classify varchar(1000),
   primary key (education_material_id)
);

/*==============================================================*/
/* Table: Lecture                                               */
/*==============================================================*/
create table Lecture
(
   education_material_id varchar(100),
   lecture_name         varbinary(1000),
   lecture_author       varbinary(1000)
);

/*==============================================================*/
/* Table: Paper                                                 */
/*==============================================================*/
create table Paper
(
   education_material_id varchar(100),
   paper_name           varbinary(1000),
   paper_author         varbinary(1000)
);

/*==============================================================*/
/* Table: Resource                                              */
/*==============================================================*/
create table Resource
(
   resource_id          varchar(100) not null,
   concept_id           varchar(100),
   resouce_type_id      varchar(100),
   resource_name        varchar(1000),
   resource_difficulty  varchar(1000),
   resource_views       integer,
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
   Interestingness      varbinary(1000)
);

/*==============================================================*/
/* Table: interest_resource                                     */
/*==============================================================*/
create table interest_resource
(
   elearner_id          varchar(100),
   resource_id          varchar(100)
);

alter table Book add constraint FK_Reference_4 foreign key (education_material_id)
      references EducationMaterial (education_material_id) on delete restrict on update restrict;

alter table EducationMaterial add constraint FK_material_relates_resource foreign key (resource_id)
      references Resource (resource_id) on delete restrict on update restrict;

alter table Lecture add constraint FK_Reference_10 foreign key (education_material_id)
      references EducationMaterial (education_material_id) on delete restrict on update restrict;

alter table Paper add constraint FK_Reference_9 foreign key (education_material_id)
      references EducationMaterial (education_material_id) on delete restrict on update restrict;

alter table Resource add constraint FK_resource_relates_concept foreign key (concept_id)
      references Concept (concept_id) on delete restrict on update restrict;

alter table Resource add constraint FK_resource_has_type foreign key (resouce_type_id)
      references ResourceType (resouce_type_id) on delete restrict on update restrict;

alter table interest_concept add constraint FK_Reference_5 foreign key (elearner_id)
      references ELearner (elearner_id) on delete restrict on update restrict;

alter table interest_concept add constraint FK_Reference_6 foreign key (concept_id)
      references Concept (concept_id) on delete restrict on update restrict;

alter table interest_resource add constraint FK_Reference_7 foreign key (elearner_id)
      references ELearner (elearner_id) on delete restrict on update restrict;

alter table interest_resource add constraint FK_Reference_8 foreign key (resource_id)
      references Resource (resource_id) on delete restrict on update restrict;

