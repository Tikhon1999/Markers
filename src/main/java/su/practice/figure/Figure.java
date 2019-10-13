package su.practice.figure;


public class Figure {
    private String wkt;
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId(){
        return id;
    }

    public void setWkt(String wkt){
        this.wkt = wkt;
    }
    public String getWkt(){
        return wkt;
    }

}
