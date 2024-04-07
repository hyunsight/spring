package com.example.first.dao;

import com.example.first.dto.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //Mybatis mapper 파일하고 연동하기 위해 붙여야 함
public interface DeptDao {
  //mapper 파일에 작성한 쿼리문을 호출하기 위해 사용
  //- 쿼리문 호출을 위한 메서드명은 select id=""의 id명(selectList)을 그대로 사용 필요
  public List<Dept> selectList();

}
