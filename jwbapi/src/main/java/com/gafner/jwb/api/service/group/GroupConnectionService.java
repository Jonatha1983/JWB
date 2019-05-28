package com.gafner.jwb.api.service.group;


import com.gafner.jwb.api.CanvasListenerInterface;

import com.gafner.jwb.api.model.GroupAPI;

import java.rmi.RemoteException;
import java.util.List;

public interface GroupConnectionService<T extends GroupAPI> {

    boolean createGroup(String groupName, String jwbDraw,String conversation, CanvasListenerInterface stageManagerInterface);

    List<String> getAllGroupsName();

    boolean joinGroup(String groupName, CanvasListenerInterface stageManagerInterface);

    String loadPaint(String groupName, CanvasListenerInterface clientListener);

    void updateGroupPaint(String groupName, String jwbDraw, CanvasListenerInterface stageManagerInterface);

    void updateGroupConversation(String groupName, String conversation, CanvasListenerInterface stageManagerInterface);

    GroupAPI findByGroupName(String groupName);

    void add(String name, CanvasListenerInterface stageManagerInterface);

    String loadConversation(String groupName, CanvasListenerInterface  clientListener);
}
