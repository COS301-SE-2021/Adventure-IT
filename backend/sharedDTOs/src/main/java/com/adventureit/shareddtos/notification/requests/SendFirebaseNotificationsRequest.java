package com.adventureit.shareddtos.notification.requests;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SendFirebaseNotificationsRequest {
    private List<UUID> userIds;
    private String title;
    private String body;
    private Map<String, String> data;

    public SendFirebaseNotificationsRequest(List<UUID> userIds, String title, String body, Map<String, String> data) {
        this.userIds = userIds;
        this.title = title;
        this.body = body;
        this.data = data;
    }

    public List<UUID> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UUID> userIds) {
        this.userIds = userIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
