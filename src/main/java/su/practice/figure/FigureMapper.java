package su.practice.figure;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


public interface FigureMapper {
    @Results({
            @Result(property = "wkt", column = "wkt"),
            @Result(property = "id", column = "id")
    })
    @Insert("INSERT IGNORE markers.figure(wkt) VALUES(#{wkt})" )
    public void insertFigure(Figure figure);

    @Select("SELECT wkt FROM markers.figure")
    public ArrayList<String> selectCoordinates();

    @Select("SELECT id from markers.figure WHERE wkt = #{wkt}")
    public Integer selectId(String wkt);

    @Update("UPDATE markers.figure SET wkt=#{wkt} WHERE id=#{id}")
    public void updateFigure(Figure figure);

    @Delete("DELETE FROM markers.figure WHERE wkt =#{wkt}")
    public void deleteFigure(String wkt);
}
