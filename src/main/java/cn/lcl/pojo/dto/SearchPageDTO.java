package cn.lcl.pojo.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchPageDTO<T> {

    @NotNull(message = "当前页数不能为空")
    Integer pageCurrent; // 这个直接对应的是第几页，Mybatis Plus 映射的sql会自动减一开始limit

    @NotNull(message = "每页数量不能为空")
    Integer pageSize;

    // search param
    T data;

    public <E> Page<E> getParamPage() {
        return new Page<>(pageCurrent, pageSize);
    }
}
