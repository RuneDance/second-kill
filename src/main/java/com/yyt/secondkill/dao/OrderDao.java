package com.yyt.secondkill.dao;

import com.yyt.secondkill.entity.Order;
import com.yyt.secondkill.entity.SecondKillOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from second_kill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SecondKillOrder getSecondKillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into orders(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, order_status, create_date) values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{orderStatus},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(Order order);

    @Insert("insert into second_kill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertSecondKillOrder(SecondKillOrder secondKillOrder);

    @Select("select * from orders where id = #{orderId}")
    Order getOrderById(@Param("orderId") long orderId);

    @Delete("delete from orders")
    void deleteOrders();

    @Delete("delete from second_kill_order")
    void deleteSecondKillOrders();

}
