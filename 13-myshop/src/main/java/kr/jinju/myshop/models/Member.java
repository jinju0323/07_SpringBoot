package kr.jinju.myshop.models;

import lombok.Data;

@Data
public class Member {
    private int id;
    private String userId;
    private String userPw;
    private String name;
    private String email;
    private String phone;
    private String birthday;
    private String gender;
    private String postcode;
    private String addr1;
    private String addr2;
    private String isOut;
    private String isAdmin;
    private String loginDate;
    private String regDate;
    private String editDate;
}
// public class Members {
//     private int id;
//     private String userid;
//     private String userpw;
//     private String name;
//     private String email;
//     private String phone;
//     private String birthday;
//     private String gender;
//     private String postcode;
//     private String addr1;
//     private String addr2;
//     private String isout;
//     private String isadmin;
//     private String logindate;
//     private String regdate;
//     private String editdate;
// }
   