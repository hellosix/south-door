package com.lzz.dao;

import com.lzz.model.UrlModel;

import java.util.List;

/**
 * Created by lzz on 2018/2/4.
 */
public interface IUrlDao {
    boolean add(UrlModel urlModel);

    List<UrlModel> urlList();

    boolean delete(String showName);

    UrlModel getUrlModel(String showName);
}
