package com.ch.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ch.money.mapper.LoanInfoMapper;
import com.ch.money.model.LoanInfo;
import com.ch.money.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 产品业务实现类
 */
@Component
@Service(interfaceClass = LoanInfoService.class ,version = "1.0.0" , timeout = 20000)
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    LoanInfoMapper loanInfoMapper;
    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //查询历史年华收益率
    @Override
    public Double queryLoanInfoHistoryRateAvg() {
        Double loanIofoHistoryRateAvg = (Double) redisTemplate.opsForValue().get("loanIofoHistoryRateAvg");
        if (loanIofoHistoryRateAvg == null){
            loanIofoHistoryRateAvg = loanInfoMapper.selectLoanInfoHistoryRateAvg();
            redisTemplate.opsForValue().set("loanIofoHistoryRateAvg", loanIofoHistoryRateAvg,30, TimeUnit.SECONDS);
            System.out.println("----缓存更新----");
        }else {
            System.out.println("----缓存命中----");
        }
        return loanIofoHistoryRateAvg;
    }

    /**
     *
     * @param parasMap sql查询语句参数
     * @return
     */
    @Override
    public List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap) {
        return loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }


    /**
     * 根据产品类型与分页模型查询产品信息
     * @param ptype
     * @param pageModel
     * @return
     */
    @Override
    public List<LoanInfo> queryLoanInfosByTypeAndPageModel(Integer ptype, PageModel pageModel) {
        Map<String,Object> parasMap = new HashMap<>();
        parasMap.put("ptype", ptype);

        parasMap.put("start", (pageModel.getCunPage()-1)*pageModel.getPageSize());
        parasMap.put( "content", pageModel.getPageSize());

        return loanInfoMapper.selectLoanInfosByTypeAndPageModel(parasMap);
    }

    //查询总记录数
    @Override
    public Long queryLoanInfoCountByType(Integer ptype) {
        return loanInfoMapper.selectLoanInfoCountByType(ptype);
    }

    /**
     * 根据id查询商品信息
     * @param loanId
     * @return
     */
    @Override
    public LoanInfo queryLoanInfoById(Integer loanId) {
        return loanInfoMapper.selectByPrimaryKey(loanId);
    }
}
