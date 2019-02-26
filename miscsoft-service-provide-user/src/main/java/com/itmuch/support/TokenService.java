package com.itmuch.support;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	private static Set<String> set = new HashSet();
	private static Lock lock = new ReentrantLock();
	private static int CACHE_SIZE = 100;

	public boolean isValid(String token) {
		try {
			lock.lock();
			if (StringUtils.isBlank(token)) {
				return false;
			}
			if (set.size() < 1) {
				init();
			}
			boolean bool = set.remove(token);
			return bool;
		} finally {
			lock.unlock();
		}
//		throw localObject;
	}

	public String get() {
		try {
			lock.lock();
			if (set.size() < 1) {
				init();
			}
			String str = set.toArray()[0].toString();
			return str;
		} finally {
			lock.unlock();
		}
//		throw localObject;
	}

	private void init() {
		for (int i = 0; i < CACHE_SIZE; i++)
			set.add(UUID.randomUUID().toString());
	}
}