package com.example.vettest;

public class ForumModel {
    String forumUsername,forumUUID,forumDescription;

    ForumModel(){

    }

    public ForumModel(String forumUsername, String forumUUID, String forumDescription) {
        this.forumUsername = forumUsername;
        this.forumUUID = forumUUID;
        this.forumDescription = forumDescription;
    }


    public String getForumUsername() {
        return forumUsername;
    }

    public void setForumUsername(String forumUsername) {
        this.forumUsername = forumUsername;
    }

    public String getForumUUID() {
        return forumUUID;
    }

    public void setForumUUID(String forumUUID) {
        this.forumUUID = forumUUID;
    }

    public String getForumDescription() {
        return forumDescription;
    }

    public void setForumDescription(String forumDescription) {
        this.forumDescription = forumDescription;
    }
}
