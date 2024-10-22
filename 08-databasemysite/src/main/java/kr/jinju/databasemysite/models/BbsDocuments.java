package kr.jinju.databasemysite.models;

import lombok.Data;

@Data
public class BbsDocuments {
    private int id;
    private String type;
    private String writer;
    private String password;
    private String email;
    private String subject;
    private String content;
    private String hit;
    private String regDate;
    private String editDate;
    private String membersId;
}
