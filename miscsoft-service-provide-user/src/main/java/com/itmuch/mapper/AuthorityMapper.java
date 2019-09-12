package com.itmuch.mapper;

import java.util.Collection;

import com.cloud.model.Authority;
import com.itmuch.util.MyMapper;

public interface AuthorityMapper extends MyMapper<Authority> {

	Collection<? extends String> selectAuthorityByRole(String role);
}