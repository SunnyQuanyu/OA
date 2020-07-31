package cn.lcl.config.shrio;

import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.pojo.result.Result;
import cn.lcl.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FilterUntil {

    public static boolean onAccessDenied(HttpServletResponse response, ResultEnum resultEnum) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        Result result = ResultUtil.error(resultEnum.getCode(),
                resultEnum.getMsg());

        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
        writer.close();
        return false;
    }

}
