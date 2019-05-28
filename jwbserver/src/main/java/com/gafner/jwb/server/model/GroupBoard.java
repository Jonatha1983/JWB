package com.gafner.jwb.server.model;

import com.gafner.jwb.api.model.GroupAPI;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@SuppressWarnings("unused")
@Document(collection = "group")
public class GroupBoard implements GroupAPI {

    @Id
    private String id;
    private String paint;
    private String conversation;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private final String groupName;

    public GroupBoard(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaint() {
        return paint;
    }

    public void setPaint(String paint) {
        this.paint = paint;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getConversation() { return conversation; }

    public void setConversation(String conversation) { this.conversation = conversation; }
}
