package com.itmuch.mapper;

import java.util.Set;

import com.itmuch.model.Access;
import com.itmuch.util.MyMapper;

public interface AccessMapper extends MyMapper<Access> {

	Set<String> getAccessByRole(String ro);
}