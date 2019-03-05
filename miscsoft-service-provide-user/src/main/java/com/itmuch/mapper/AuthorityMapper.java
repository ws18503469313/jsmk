package com.itmuch.mapper;

import java.util.Collection;

import com.itmuch.model.Authority;
import com.itmuch.util.MyMapper;

public interface AuthorityMapper extends MyMapper<Authority> {

	Collection<? extends String> selectAuthorityByRole(String role);
}