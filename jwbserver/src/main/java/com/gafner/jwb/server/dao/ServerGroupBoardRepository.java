package com.gafner.jwb.server.dao;

import com.gafner.jwb.server.model.GroupBoard;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerGroupBoardRepository extends MongoRepository<GroupBoard,Integer>   {

    GroupBoard findGroupBoardByGroupName(String groupName);

    @Override
    List<GroupBoard> findAll();


}
