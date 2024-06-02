package com.example.vettest;

public class RepliesModel {

    String forumUsernameReply,forumContentReply;

    RepliesModel(){

    }

    public RepliesModel(String forumUsernameReply, String forumContentReply) {
        this.forumUsernameReply = forumUsernameReply;
        this.forumContentReply = forumContentReply;
    }

    public String getForumUsernameReply() {
        return forumUsernameReply;
    }

    public void setForumUsernameReply(String forumUsernameReply) {
        this.forumUsernameReply = forumUsernameReply;
    }

    public String getForumContentReply() {
        return forumContentReply;
    }

    public void setForumContentReply(String forumContentReply) {
        this.forumContentReply = forumContentReply;
    }
}
