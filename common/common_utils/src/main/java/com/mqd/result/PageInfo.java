package com.mqd.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageInfo {
    private Long current;
    private Long size;
    private Long total;
    private Long pages;

    public PageInfo(IPage<?> page){
        this.pages = page.getPages();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.current = page.getCurrent();
    }
}
