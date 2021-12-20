package cn.enncy.funny.entity;


import java.util.List;

/**
 * 分页实体类
 * <br/>Created in 20:23 2021/12/20
 *
 * @author enncy
 */
public class PageEntity<T> extends BaseEntity{

    private int page;
    private int size;
    private int totalPage;
    private List<T> data;

}
