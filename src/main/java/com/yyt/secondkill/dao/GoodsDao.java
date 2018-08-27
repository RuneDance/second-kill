package com.yyt.secondkill.dao;

import com.yyt.secondkill.entity.SecondKillGoods;
import com.yyt.secondkill.vo.GoodsVo;
import com.yyt.secondkill.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.second_kill_price from second_kill_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.second_kill_price from second_kill_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update second_kill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SecondKillGoods secondKillGoods);

    @Update("update second_kill_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(SecondKillGoods secondKillGoods);

}
