package com.gafner.jwb.client.conversation;

import com.gafner.jwb.client.utils.PairUserMessage;

import java.util.ArrayList;
import java.util.List;

public class GroupConversation {

    private final List<PairUserMessage> pairUserMessageList= new ArrayList<>();

    public GroupConversation() {

    }

    public List<PairUserMessage> getPairUserMessageList() {
        return pairUserMessageList;
    }

    public void addPair(PairUserMessage pairUserMessage) {
        pairUserMessageList.add(pairUserMessage);
    }
}
