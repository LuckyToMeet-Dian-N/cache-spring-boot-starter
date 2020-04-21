package com.gentle.cache.test;

import com.gentle.cache.annotation.PutCache;
import com.gentle.cache.annotation.RemoveCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gentle
 * @date 2020/04/15 : 13:32
 */
@RestController
public class Controller {

    @GetMapping(value = "abc")
    @PutCache(key = "name",prefix = "test")
    public String aaa(Users users,String name){

        return users.toString();
    }
    @GetMapping(value = "bbb")
    @RemoveCache(key = "user.name",prefix = "test")
    public String bbb(Users users){
        return users.toString();
    }

}
