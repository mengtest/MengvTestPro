/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : mytestrole

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-03-21 17:40:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `organization`
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `ID` varchar(36) NOT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `CODE` varchar(200) DEFAULT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `ICONCLS` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  `ORGANIZATIONID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_dgd9it46hgkwqe7k3mwmhsuap` (`ORGANIZATIONID`),
  CONSTRAINT `FK_dgd9it46hgkwqe7k3mwmhsuap` FOREIGN KEY (`ORGANIZATIONID`) REFERENCES `organization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------

-- ----------------------------
-- Table structure for `organization_resource`
-- ----------------------------
DROP TABLE IF EXISTS `organization_resource`;
CREATE TABLE `organization_resource` (
  `RESOURCE_ID` varchar(36) NOT NULL,
  `ORGANIZATION_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`RESOURCE_ID`,`ORGANIZATION_ID`),
  KEY `FK_dvjkukw5ngly2h74bteegllmv` (`ORGANIZATION_ID`),
  KEY `FK_fpn9vxy14e6p1rn8fb7v3q2gl` (`RESOURCE_ID`),
  CONSTRAINT `FK_fpn9vxy14e6p1rn8fb7v3q2gl` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `FK_dvjkukw5ngly2h74bteegllmv` FOREIGN KEY (`ORGANIZATION_ID`) REFERENCES `organization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `organization_user`
-- ----------------------------
DROP TABLE IF EXISTS `organization_user`;
CREATE TABLE `organization_user` (
  `USER_ID` varchar(36) NOT NULL,
  `ORGANIZATION_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ORGANIZATION_ID`),
  KEY `FK_e5jh0av92llatq04ipmxnrto4` (`ORGANIZATION_ID`),
  KEY `FK_kplvhs2l04und8196v5cfktmm` (`USER_ID`),
  CONSTRAINT `FK_kplvhs2l04und8196v5cfktmm` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`),
  CONSTRAINT `FK_e5jh0av92llatq04ipmxnrto4` FOREIGN KEY (`ORGANIZATION_ID`) REFERENCES `organization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization_user
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `ICONCLS` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `TARGET` varchar(100) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) DEFAULT NULL,
  `RESOURCETYPE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_cand0cs313wufvotyikd70t9j` (`RESOURCE_ID`),
  KEY `FK_bjlrqegc9iu81src5vlta7p00` (`RESOURCETYPE_ID`),
  CONSTRAINT `FK_bjlrqegc9iu81src5vlta7p00` FOREIGN KEY (`RESOURCETYPE_ID`) REFERENCES `resourcetype` (`ID`),
  CONSTRAINT `FK_cand0cs313wufvotyikd70t9j` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for `resourcetype`
-- ----------------------------
DROP TABLE IF EXISTS `resourcetype`;
CREATE TABLE `resourcetype` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resourcetype
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `ICON` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `ROLE_ID` varchar(36) NOT NULL,
  `RESOURCE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESOURCE_ID`),
  KEY `FK_eyrr8fy4xk8nqs4haoqav5vs4` (`RESOURCE_ID`),
  KEY `FK_7ig5nfqrf5vuwgic6g7ybk72a` (`ROLE_ID`),
  CONSTRAINT `FK_7ig5nfqrf5vuwgic6g7ybk72a` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_eyrr8fy4xk8nqs4haoqav5vs4` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `LOGINNAME` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `PHOTO` varchar(200) DEFAULT NULL,
  `PWD` varchar(100) DEFAULT NULL,
  `SEX` varchar(1) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `USER_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_qxlog73d0t2auuod93t5qfw9h` (`ROLE_ID`),
  KEY `FK_4b3tcd0afi19cpbf14t0llnb2` (`USER_ID`),
  CONSTRAINT `FK_4b3tcd0afi19cpbf14t0llnb2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`),
  CONSTRAINT `FK_qxlog73d0t2auuod93t5qfw9h` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
