package com.fisheep.dao;

import java.util.List;
import java.util.Map;

public interface UserHasGroupMapper {
    List<Map<Integer, String>> getGroupByUid(Integer uid);
}
