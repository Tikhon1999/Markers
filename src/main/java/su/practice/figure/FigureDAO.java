package su.practice.figure;
import org.apache.ibatis.session.SqlSession;
import su.practice.util.MyBatisUtil;

import java.util.ArrayList;


public class FigureDAO {
    public void save(Figure figure){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        FigureMapper mapper = session.getMapper(FigureMapper.class);
        mapper.insertFigure(figure);
        session.commit();
        session.close();
    }

    public ArrayList<String> getCoordinates() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        FigureMapper mapper = session.getMapper(FigureMapper.class);
        ArrayList<String> figure = mapper.selectCoordinates();
        session.close();
        return figure;
    }

    public void delete(String wkt){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        FigureMapper mapper = session.getMapper(FigureMapper.class);
        mapper.deleteFigure(wkt);
        session.commit();
        session.close();
    }
    public Integer getId(String wkt) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        FigureMapper mapper = session.getMapper(FigureMapper.class);
        Integer id = mapper.selectId(wkt);
        session.close();
        return id;
    }

    public void update(Figure figure){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        FigureMapper mapper = session.getMapper(FigureMapper.class);
        mapper.updateFigure(figure);
        session.commit();
        session.close();
    }
}
