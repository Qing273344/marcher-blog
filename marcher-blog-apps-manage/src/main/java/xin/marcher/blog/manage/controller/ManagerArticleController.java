package xin.marcher.blog.manage.controller;

/**
 * 文章
 *
 * @author marcher
 */

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客文章 apps article
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerArticleController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerArticleController {
}
