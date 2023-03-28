package com.retail.colonel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.Team;
import com.retail.colonel.domain.TeamRecord;
import com.retail.colonel.mapper.TeamMapper;
import com.retail.colonel.mapper.TeamRecordMapper;
import com.retail.colonel.service.TeamService;
import com.retail.common.result.Result;
import com.retail.common.utils.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 拼团表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    @Value("${des.decryptKey}")
    private String decryptKey;
    @Autowired
    private TeamRecordMapper teamRecordMapper;
    @Override
    public Result applyUser(Long id, String content) {
        List<TeamRecord> teamRecords = teamRecordMapper.selectList(null);
        if (teamRecords.size()==4){
            return Result.error("拼团人数够了 ,请换一个");
        }
        String s = DESUtil.aesDecryptForFront(content, decryptKey);
        if (s==null){
            return Result.error("密文不正确错误");
        }
        TeamRecord teamRecord = new TeamRecord();
        teamRecord.setTeamOrderTime(new Date());
        teamRecord.setUserId(id);
        teamRecord.setStatus(1);
        teamRecord.setTeamId(Long.valueOf(s));
        teamRecordMapper.insert(teamRecord);
        return Result.success(null,"添加成功");
    }
}
