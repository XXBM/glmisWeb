package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Post;
import com.glmis.service.personnel.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class PostController {
    @Autowired
    PostService postService;

    /**
     * 获取到所有的职位名称
     */
    @RequestMapping(value = "/findAllPost", method = RequestMethod.GET)
    public List<Post> findAllPost() throws Exception{
        List<Post> posts = postService.findAllT();
        return posts;
    }
    //实现分页
    @RequestMapping(value = "/displayAllPosts", method = RequestMethod.GET)
    public Map<String, Object> displayAllPosts(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "rows") Integer size) {
        Page<Post> list = this.postService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.postService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的职务
    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addPost(@RequestBody Post post) {
        this.postService.add(post);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("post", post);
        return map;
    }

    //修改职务信息
    @RequestMapping(value = "/updatePost", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updatePost(@RequestBody Post post) {
        this.postService.update(post);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("post", post);
        return map;
    }

    //删除一个职务
    @RequestMapping(value = "/deletePost", method = RequestMethod.DELETE)
    public void deletePost(@RequestParam("id") String id) {
        this.postService.deleteById(Long.parseLong(id));
    }
}
