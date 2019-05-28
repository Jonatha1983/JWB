package com.gafner.jwb.server.services.groups;

import com.gafner.jwb.api.CanvasListenerInterface;
import com.gafner.jwb.api.service.group.GroupConnectionService;
import com.gafner.jwb.server.dao.ServerGroupBoardRepository;
import com.gafner.jwb.server.model.GroupBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class GroupConnectionServiceImpl implements GroupConnectionService {

    @Autowired
    private ServerGroupBoardRepository serverGroupBoardRepository;

    private Map<String, List<CanvasListenerInterface>> stages;

    public GroupConnectionServiceImpl() {
        stages = new ConcurrentHashMap<>();

    }

    @Override
    public  List<String> getAllGroupsName() {
        return serverGroupBoardRepository.findAll().stream().map(GroupBoard::getGroupName).sorted().collect(Collectors.toList());
    }

    @Override
    public  boolean createGroup(String groupName, String jwbDrawJson, String conversation, CanvasListenerInterface stageManagerInterface) {
        if (serverGroupBoardRepository.findGroupBoardByGroupName(groupName) != null) {
            return false;
        } else {

            GroupBoard groupBoard = new GroupBoard(groupName);
            groupBoard.setPaint(jwbDrawJson);
            groupBoard.setConversation(conversation);
            serverGroupBoardRepository.save(groupBoard);
            add(groupName, stageManagerInterface);
            return true;

        }
    }

    @Override
    public  boolean joinGroup(String groupName, CanvasListenerInterface stageManagerInterface) {
        if (serverGroupBoardRepository.findGroupBoardByGroupName(groupName) != null) {
            add(groupName, stageManagerInterface);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public  String loadPaint(String groupName, CanvasListenerInterface canvasListenerInterface) {
        GroupBoard groupBoardByGroupName = serverGroupBoardRepository.findGroupBoardByGroupName(groupName);

        return groupBoardByGroupName.getPaint();
    }


    @Override
    public String loadConversation(String groupName, CanvasListenerInterface clientListener) {
        GroupBoard groupBoardByGroupName = serverGroupBoardRepository.findGroupBoardByGroupName(groupName);

        return groupBoardByGroupName.getConversation();

    }

    @Override
    public void updateGroupPaint(String groupName, String jwbDraw, CanvasListenerInterface stageManagerInterface) {
        GroupBoard groupBoard = serverGroupBoardRepository.findGroupBoardByGroupName(groupName);
        groupBoard.setPaint(jwbDraw);
        serverGroupBoardRepository.save(groupBoard);

        stages.get(groupName).forEach(t -> {
            try {
                t.updatePaint();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public  void updateGroupConversation(String groupName, String conversation, CanvasListenerInterface stageManagerInterface) {
        GroupBoard groupBoard = serverGroupBoardRepository.findGroupBoardByGroupName(groupName);
        groupBoard.setConversation(conversation);
        serverGroupBoardRepository.save(groupBoard);

        stages.get(groupName).forEach(t -> {
            try {
                t.updateChat();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public  GroupBoard findByGroupName(String groupName) {
        return serverGroupBoardRepository.findGroupBoardByGroupName(groupName);

    }

    @Override
    public  void add(String name, CanvasListenerInterface stageManagerInterface) {
        if(stages.get(name)==null){
            List<CanvasListenerInterface> canvasListenerInterfaces = new ArrayList<>();
            canvasListenerInterfaces.add(stageManagerInterface);
            stages.putIfAbsent(name,canvasListenerInterfaces);
        }else{
            stages.get(name).add(stageManagerInterface);
        }
    }


}
