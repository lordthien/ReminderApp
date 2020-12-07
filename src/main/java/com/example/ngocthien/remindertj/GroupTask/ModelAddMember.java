package com.example.ngocthien.remindertj.GroupTask;

public class ModelAddMember {
    String namegrouptask, namehost, memberphone;

    public ModelAddMember() {
    }

    public ModelAddMember(String namegrouptask, String namehost, String memberphone) {
        this.namegrouptask = namegrouptask;
        this.namehost = namehost;
        this.memberphone = memberphone;
    }

    public String getNamegrouptask() {
        return namegrouptask;
    }

    public void setNamegrouptask(String namegrouptask) {
        this.namegrouptask = namegrouptask;
    }

    public String getNamehost() {
        return namehost;
    }

    public void setNamehost(String namehost) {
        this.namehost = namehost;
    }

    public String getMemberphone() {
        return memberphone;
    }

    public void setMemberphone(String memberphone) {
        this.memberphone = memberphone;
    }
}
