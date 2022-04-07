package com.library.services.managed;

import com.google.inject.Inject;
import com.library.services.db.dto.Book;
import com.library.services.db.dto.Issue;
import com.library.services.db.dto.User;
import io.dropwizard.lifecycle.Managed;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.List;

@Slf4j
public class DistributedCacheProvider implements Managed {
    private RedissonClient redissonClient;

    private RMap<String, Book> bookMap;
    private RMap<String, User> userMap;
    private RMap<String, List<Book>> userBookMap;

    public DistributedCacheProvider(Config config) {
        redissonClient = Redisson.create(config);
        this.clearAllMaps();
    }

    private void clearAllMaps() {
        getBookMap().clear();
        getUserMap().clear();
        getUserBookMap().clear();
    }

    @Override
    public void start() throws Exception {
        log.info("Starting Distributed cache Manager");
    }

    @Override
    public void stop() throws Exception {
        log.info("Stopping Distributed cache Manager");
        redissonClient.shutdown();
    }

    public RMap<String, Book> getBookMap() {
        if (bookMap == null) {
            log.info("Initializing {} map from redis", "BOOK_MAP");
            bookMap = redissonClient.getMap("BOOK_MAP");
        }
        return bookMap;
    }

    public RMap<String, User> getUserMap() {
        if (userMap == null) {
            log.info("Initializing {} map from redis", "USER_MAP");
            userMap = redissonClient.getMap("USER_MAP");
        }
        return userMap;
    }

    public RMap<String, List<Book>> getUserBookMap() {
        if (userBookMap == null) {
            log.info("Initializing {} map from redis", "USER_BOOK_MAP");
            userBookMap = redissonClient.getMap("USER_BOOK_MAP");
        }
        return userBookMap;
    }
}
